package codemetropolis.toolchain.rendering.model.primitive;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;

public class SingleBlock implements Primitive {

    private Point position;
    private String name;
    private Orientation orientation;
    private Map<String, String> spawnerData;

    public SingleBlock(String name, int x, int y, int z) {
        super();
        this.position = new Point(x, y, z);
        this.name = name;
        this.orientation = Orientation.NORTH;
    }

    /**
     * This is a constructor for SingleBlock class which is used to create individual blocks
     * @param name name of the block
     * @param position position of the block using a Point object which contains x, y and z coordinates
     * @param spawnData spawnData map object which contains the data needed for spawner blocks
     */
    public SingleBlock(String name, Point position, Map<String, String> spawnData) {
        super();
        this.position = position;
        this.name = name;
        this.spawnerData = spawnData;
    }

    public SingleBlock(String name, Point position) {
        super();
        this.position = position;
        this.name = name;
        this.orientation = Orientation.NORTH;
    }

    public SingleBlock(String name, Point position, Orientation orientation) {
        super();
        this.position = position;
        this.name = name;
        this.orientation = orientation;
    }

    /**
     * This method, based on block type (default or spawner), creates a csv file which stores the data of the block as
     * numbers separated by semicolons
     * @param directory directory where the csv file will be created
     */
    @Override
    public int toCSVFile(File directory) {
        if (name.equals("minecraft:mob_spawner")) {
            String jsonString = convertMapToJson(spawnerData);

            assertJsonString(jsonString);

            new Boxel(new BasicBlock((short) 52), position, jsonString).toCSVFile(directory);
        } else {
            new Boxel(new BasicBlock(name, orientation.getValue()), position).toCSVFile(directory);
        }

        return 1;
    }

    /**
     * This method converts a map of spawner block data into a json string which is then returned
     * @param map map object in which the spawner data is stored
     */
    private static String convertMapToJson(Map<String, String> map) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * This method checks if the string that contains spawner data contains ; character, if it does, it throws an exception
     * @param jsonString the string that contains spawner data and need to be checked for ; character
     */
    private void assertJsonString(String jsonString) {
        if (jsonString.contains(";")) {
            throw new IllegalArgumentException("Json string cannot contain semicolons! The blocks' individual " +
                    "data such as position and block type are separated by semicolons in the csv file. A semicolon in " +
                    "the json would break the structure of the csv file.");
        }
    }

    @Override
    public int getNumberOfBlocks() {
        return 1;
    }

    public enum Orientation {
        NORTH(2),
        SOUTH(4),
        WEST(3),
        EAST(5);

        private final int value;

        Orientation(int v) {
            value = v;
        }

        public int getValue() {
            return value;
        }
    }
}
