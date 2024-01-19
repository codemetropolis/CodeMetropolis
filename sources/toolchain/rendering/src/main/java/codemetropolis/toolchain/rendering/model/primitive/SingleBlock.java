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

    @Override
    public int toCSVFile(File directory) {
        if (name.equals("minecraft:mob_spawner")) {
            String jsonString = convertMapToJson(spawnerData);

            new Boxel(new BasicBlock((short) 52), position, jsonString).toCSVFile(directory);
        } else {
            new Boxel(new BasicBlock(name, orientation.getValue()), position).toCSVFile(directory);
        }

        return 1;
    }

    private static String convertMapToJson(Map<String, String> map) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(map);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
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
