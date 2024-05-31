package codemetropolis.toolchain.rendering.model.primitive;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.LinkedList;

public class TestAllBlockTypesToCsvFile {

    @Test
    public void testWoolBlocksToCSVFile(){
        File tempDir = new File("D:/suli/szakgyak/CodeMetropolis/sources/world", "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new Boxel(BasicBlock.WHITE_WOOL,new Point(1,61,1)));
        primitives.add(new Boxel(BasicBlock.ORANGE_WOOL,new Point(1,61,2)));
        primitives.add(new Boxel(BasicBlock.MAGENTA_WOOL,new Point(1,61,3)));
        primitives.add(new Boxel(BasicBlock.LIGHT_BLUE_WOOL,new Point(1,61,4)));
        primitives.add(new Boxel(BasicBlock.YELLOW_WOOL,new Point(1,61,5)));
        primitives.add(new Boxel(BasicBlock.LIME_WOOL,new Point(1,61,6)));
        primitives.add(new Boxel(BasicBlock.PINK_WOOL,new Point(1,61,7)));
        primitives.add(new Boxel(BasicBlock.GRAY_WOOL,new Point(1,61,8)));
        primitives.add(new Boxel(BasicBlock.LIGHT_GRAY_WOOL,new Point(1,61,9)));
        primitives.add(new Boxel(BasicBlock.CYAN_WOOL,new Point(4,61,1)));
        primitives.add(new Boxel(BasicBlock.PURPLE_WOOL,new Point(4,61,2)));
        primitives.add(new Boxel(BasicBlock.BLUE_WOOL,new Point(4,61,3)));
        primitives.add(new Boxel(BasicBlock.BROWN_WOOL,new Point(4,61,4)));
        primitives.add(new Boxel(BasicBlock.GREEN_WOOL,new Point(4,61,5)));
        primitives.add(new Boxel(BasicBlock.RED_WOOL,new Point(4,61,6)));
        primitives.add(new Boxel(BasicBlock.BLACK_WOOL,new Point(4,61,7)));

        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }
    }

    @Test
    public void testBannerBlocksToCSVFile(){
        File tempDir = new File("D:/suli/szakgyak/CodeMetropolis/sources/world", "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new Boxel(new BasicBlock("minecraft:white_banner",(short) 176),new Point(1,61,1)));
        primitives.add(new Boxel(new BasicBlock("minecraft:orange_banner",(short) 176),new Point(1,61,2)));
        primitives.add(new Boxel(new BasicBlock("minecraft:magenta_banner",(short) 176),new Point(1,61,3)));
        primitives.add(new Boxel(new BasicBlock("minecraft:light_blue_banner",(short) 176),new Point(1,61,4)));
        primitives.add(new Boxel(new BasicBlock("minecraft:yellow_banner",(short) 176),new Point(1,61,5)));
        primitives.add(new Boxel(new BasicBlock("minecraft:lime_banner",(short) 176),new Point(1,61,6)));
        primitives.add(new Boxel(new BasicBlock("minecraft:pink_banner",(short) 176),new Point(1,61,7)));
        primitives.add(new Boxel(new BasicBlock("minecraft:gray_banner",(short) 176),new Point(1,61,8)));
        primitives.add(new Boxel(new BasicBlock("minecraft:light_gray_banner",(short) 176),new Point(1,61,9)));
        primitives.add(new Boxel(new BasicBlock("minecraft:cyan_banner",(short) 176),new Point(1,61,10)));
        primitives.add(new Boxel(new BasicBlock("minecraft:purple_banner",(short) 176),new Point(1,61,11)));
        primitives.add(new Boxel(new BasicBlock("minecraft:blue_banner",(short) 176),new Point(1,61,12)));
        primitives.add(new Boxel(new BasicBlock("minecraft:brown_banner",(short) 176),new Point(1,61,13)));
        primitives.add(new Boxel(new BasicBlock("minecraft:green_banner",(short) 176),new Point(1,61,14)));
        primitives.add(new Boxel(new BasicBlock("minecraft:red_banner",(short) 176),new Point(1,61,15)));
        primitives.add(new Boxel(new BasicBlock("minecraft:black_banner",(short) 176),new Point(1,61,16)));
        primitives.add(new Boxel(new BasicBlock("minecraft:white_wall_banner",(short) 177),new Point(4,62,1)));
        primitives.add(new Boxel(new BasicBlock("minecraft:orange_wall_banner",(short) 177),new Point(4,62,2)));
        primitives.add(new Boxel(new BasicBlock("minecraft:magenta_wall_banner",(short) 177),new Point(4,62,3)));
        primitives.add(new Boxel(new BasicBlock("minecraft:light_blue_wall_banner",(short) 177),new Point(4,62,4)));
        primitives.add(new Boxel(new BasicBlock("minecraft:yellow_wall_banner",(short) 177),new Point(4,62,5)));
        primitives.add(new Boxel(new BasicBlock("minecraft:lime_wall_banner",(short) 177),new Point(4,62,6)));
        primitives.add(new Boxel(new BasicBlock("minecraft:pink_wall_banner",(short) 177),new Point(4,62,7)));
        primitives.add(new Boxel(new BasicBlock("minecraft:gray_wall_banner",(short) 177),new Point(4,62,8)));
        primitives.add(new Boxel(new BasicBlock("minecraft:light_gray_wall_banner",(short) 177),new Point(4,62,9)));
        primitives.add(new Boxel(new BasicBlock("minecraft:cyan_wall_banner",(short) 177),new Point(4,62,10)));
        primitives.add(new Boxel(new BasicBlock("minecraft:purple_wall_banner",(short) 177),new Point(4,62,11)));
        primitives.add(new Boxel(new BasicBlock("minecraft:blue_wall_banner",(short) 177),new Point(4,62,12)));
        primitives.add(new Boxel(new BasicBlock("minecraft:brown_wall_banner",(short) 177),new Point(4,62,13)));
        primitives.add(new Boxel(new BasicBlock("minecraft:green_wall_banner",(short) 177),new Point(4,62,14)));
        primitives.add(new Boxel(new BasicBlock("minecraft:red_wall_banner",(short) 177),new Point(4,62,15)));
        primitives.add(new Boxel(new BasicBlock("minecraft:black_wall_banner",(short) 177),new Point(4,62,16)));

        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }
    }

    @Test
    public void testSignPostBlocksToCSVFile(){
        File tempDir = new File("D:/suli/szakgyak/CodeMetropolis/sources/world", "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new SignPost(1,61,1,SignPost.Orientation.SOUTH,"South"));
        primitives.add(new SignPost(1,61,2,SignPost.Orientation.SOUTHWEST,"SouthWest"));
        primitives.add(new SignPost(1,61,3,SignPost.Orientation.WEST,"West"));
        primitives.add(new SignPost(1,61,4,SignPost.Orientation.NORTHWEST,"NorthWest"));
        primitives.add(new SignPost(1,61,5,SignPost.Orientation.NORTH,"North"));
        primitives.add(new SignPost(1,61,6,SignPost.Orientation.NORTHEAST,"NorthEast"));
        primitives.add(new SignPost(1,61,7,SignPost.Orientation.EAST,"East"));
        primitives.add(new SignPost(1,61,8,SignPost.Orientation.SOUTHEAST,"SouthEast"));


        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }
    }

    @Test
    public void testWallSignBlocksToCSVFile(){
        File tempDir = new File("D:/suli/szakgyak/CodeMetropolis/sources/world", "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new WallSign(1,61,1,WallSign.Orientation.NORTH,"North"));
        primitives.add(new WallSign(1,61,2,WallSign.Orientation.SOUTH,"South"));
        primitives.add(new WallSign(1,61,3,WallSign.Orientation.WEST,"West"));
        primitives.add(new WallSign(1,61,4,WallSign.Orientation.EAST,"East"));



        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }
    }

    @Test
    public void testDoorBlocksToCSVFile(){
        File tempDir = new File("D:/suli/szakgyak/CodeMetropolis/sources/world", "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new Door(1,61,1,Door.Orientation.WEST));
        primitives.add(new Door(1,61,2,Door.Orientation.NORTH));
        primitives.add(new Door(1,61,3,Door.Orientation.EAST));
        primitives.add(new Door(1,61,4,Door.Orientation.SOUTH));



        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }
    }

    @Test
    public void testTorchBlocksToCSVFile(){
        File tempDir = new File("D:/suli/szakgyak/CodeMetropolis/sources/world", "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();
        BasicBlock torch = BasicBlock.TORCH;

        primitives.add(new Row(new Point(5,61,1),
                2,
                Row.Direction.EAST,
                new BasicBlock[]{torch, torch},
                Row.BlockFacing.WEST));
        primitives.add(new Row(new Point(2,61,1),
                2,
                Row.Direction.WEST,
                new BasicBlock[]{torch, torch},
                Row.BlockFacing.EAST));
        primitives.add(new Row(new Point(1,61,5),
                2,
                Row.Direction.SOUTH,
                new BasicBlock[]{torch, torch},
                Row.BlockFacing.NORTH));
        primitives.add(new Row(new Point(1,61,2),
                2,
                Row.Direction.NORTH,
                new BasicBlock[]{torch, torch},
                Row.BlockFacing.SOUTH));



        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }
    }

    @Test
    public void testChestBlocksToCSVFile(){
        File tempDir = new File("D:/suli/szakgyak/CodeMetropolis/sources/world", "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new SingleBlock(BasicBlock.CHEST, new Point(1,61,1), SingleBlock.Orientation.NORTH));
        primitives.add(new SingleBlock(BasicBlock.CHEST, new Point(3,61,1), SingleBlock.Orientation.WEST));
        primitives.add(new SingleBlock(BasicBlock.CHEST, new Point(5,61,1), SingleBlock.Orientation.SOUTH));
        primitives.add(new SingleBlock(BasicBlock.CHEST, new Point(7,61,1), SingleBlock.Orientation.EAST));



        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }
    }

    @Test
    public void testSpawnerBlockToCSVFile(){
        File tempDir = new File("D:/suli/szakgyak/CodeMetropolis/sources/world", "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new SingleBlock(BasicBlock.MOB_SPAWNER, new Point(1,61,1), "5"));



        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }
    }

    @Test
    public void testRedstoneLampBlockToCSVFile(){
        File tempDir = new File("D:/suli/szakgyak/CodeMetropolis/sources/world", "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new Boxel(BasicBlock.REDSTONE_BLOCK, new Point(1,61,1)));
        primitives.add(new Boxel(BasicBlock.REDSTONE_LAMP, new Point(1,62,1)));



        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }
    }

    @Test
    public void testSimpleBlocksToCSVFile(){
        File tempDir = new File("D:/suli/szakgyak/CodeMetropolis/sources/world", "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new Boxel(BasicBlock.STONE, new Point(1,61,1)));
        primitives.add(new Boxel(BasicBlock.STONE_BRICKS, new Point(1,61,2)));
        primitives.add(new Boxel(BasicBlock.SANDSTONE, new Point(1,61,3)));
        primitives.add(new Boxel(BasicBlock.GRASS_BLOCK, new Point(1,61,4)));
        primitives.add(new Boxel(BasicBlock.FENCE, new Point(1,61,5)));
        primitives.add(new Boxel(BasicBlock.POPPY, new Point(1,61,6)));
        primitives.add(new Boxel(BasicBlock.DANDELION, new Point(1,61,7)));
        primitives.add(new Boxel(BasicBlock.BROWN_MUSHROOM, new Point(1,61,8)));
        primitives.add(new Boxel(BasicBlock.OAK_SAPLING, new Point(1,61,9)));
        primitives.add(new Boxel(BasicBlock.COBBLESTONE, new Point(4,61,1)));
        primitives.add(new Boxel(BasicBlock.MOSSY_COBBLESTONE, new Point(4,61,2)));
        primitives.add(new Boxel(BasicBlock.OBSIDIAN, new Point(4,61,3)));
        primitives.add(new Boxel(BasicBlock.OAK_WOOD, new Point(4,61,4)));
        primitives.add(new Boxel(BasicBlock.DARK_OAK_WOOD, new Point(4,61,5)));
        primitives.add(new Boxel(BasicBlock.BIRCH_WOOD, new Point(4,61,6)));
        primitives.add(new Boxel(BasicBlock.OAK_PLANKS, new Point(4,61,7)));
        primitives.add(new Boxel(BasicBlock.DARK_OAK_PLANKS, new Point(4,61,8)));
        primitives.add(new Boxel(BasicBlock.IRON_BLOCK, new Point(4,61,9)));
        primitives.add(new Boxel(BasicBlock.DIRT, new Point(7,61,1)));
        primitives.add(new Boxel(BasicBlock.CUT_SANDSTONE, new Point(7,61,2)));
        primitives.add(new Boxel(BasicBlock.RED_SAND, new Point(7,61,3)));
        primitives.add(new Boxel(BasicBlock.BRICK_BLOCK, new Point(7,61,4)));
        primitives.add(new Boxel(BasicBlock.GLASS, new Point(7,61,5)));
        primitives.add(new Boxel(BasicBlock.GOLD_BLOCK, new Point(7,61,6)));
        primitives.add(new Boxel(BasicBlock.DIAMOND_BLOCK, new Point(7,61,7)));



        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }
    }
}
