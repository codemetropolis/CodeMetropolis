package codemetropolis.toolchain.rendering.model.primitive;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.util.JsonUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SingleBlock implements Primitive {

    private Point position;
    private String name;
    private Orientation orientation;
    private String dangerValue;

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
     * @param dangerAttrValue the value related to the danger attribute of spawners
     */
    public SingleBlock(String name, Point position, String dangerAttrValue) {
        super();
        this.position = position;
        this.name = name;
        this.dangerValue = dangerAttrValue;
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
     *
     * @param directory directory where the csv file will be created
     */
    @Override
    public int toCSVFile(File directory) {
        if (name.equals("minecraft:mob_spawner")) {
            String jsonString = JsonUtil.convertMapToJson(setSpawnerData(dangerValue));

            new Boxel(new BasicBlock((short) 52), position, jsonString).toCSVFile(directory);
        } else {
            new Boxel(new BasicBlock(name, orientation.getValue()), position).toCSVFile(directory);
        }

        return 1;
    }

    /**
     * This method creates a map for the spawner's data. It consists of the monster type the spawner should spawn,
     * and its related danger value to control max spawn able entities
     */
    private Map<String, String> setSpawnerData(String dangerValue) {
        Map<String, String> spawnerMap = new HashMap<>();
        spawnerMap.put("idOfEntity", "minecraft:zombie");
        spawnerMap.put("dangerValue", dangerValue);

        return spawnerMap;
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
