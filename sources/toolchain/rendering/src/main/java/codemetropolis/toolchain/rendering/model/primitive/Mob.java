package codemetropolis.toolchain.rendering.model.primitive;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;

import java.io.File;


public class Mob implements Primitive{

    public static final String[] SupportedMobs = {"pig"};

    private Point position;
    private String name;

    public Mob(int x, int y, int z, String name){
        super();
        this.position = new Point(x, y, z);
        this.name = name;
    }

    @Override
    public int toCSVFile(File directory){
        new Boxel(new BasicBlock(BasicBlock.Mob), position,  name).toCSVFile(directory);
        return 1;
    }

    @Override
    public int getNumberOfBlocks(){ return 1; }
}
