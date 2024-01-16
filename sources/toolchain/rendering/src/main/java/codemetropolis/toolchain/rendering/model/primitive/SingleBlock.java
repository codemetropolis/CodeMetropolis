package codemetropolis.toolchain.rendering.model.primitive;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;

import java.io.File;

public class SingleBlock implements Primitive {

    private Point position;
    private String name;
    private Orientation orientation;
    private String entityId;

    public SingleBlock(String name, int x, int y, int z) {
        super();
        this.position = new Point(x, y, z);
        this.name = name;
        this.orientation = Orientation.NORTH;
    }

    public SingleBlock(String name, Point position, String entitiId) {
        super();
        this.position = position;
        this.name = name;
        this.entityId = entitiId;
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
            new Boxel(new BasicBlock((short) 52), position, entityId).toCSVFile(directory);
        } else {
            new Boxel(new BasicBlock(name, orientation.getValue()), position).toCSVFile(directory);
        }

        return 1;
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
