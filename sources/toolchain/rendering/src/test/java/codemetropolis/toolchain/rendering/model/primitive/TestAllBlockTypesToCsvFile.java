package codemetropolis.toolchain.rendering.model.primitive;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

//TODO: If a new block implementation is added, a corresponding test for it should be created in this test file!
public class TestAllBlockTypesToCsvFile {
    private static Path projectRoot;

    static {
        try {
            projectRoot = Paths.get(TestAllBlockTypesToCsvFile
                            .class
                            .getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI())
                    .getParent().getParent().getParent().getParent().getParent();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void compareTwoCsvFile(String file1, String file2) throws IOException {
        List<String> fileContent1 = Files.readAllLines(Paths.get(file1));
        List<String> fileContent2 = Files.readAllLines(Paths.get(file2));

        assertEquals(fileContent1, fileContent2, "The files do not match!");
    }

    @Test
    public void testWoolBlocksToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldWool").toString(), "TEMP");
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

        try {
            compareTwoCsvFile(projectRoot.resolve("sources/worldWool/TEMP/blocks.0.0.csv").toString(),
                    projectRoot.resolve("docs/tests/manual/Test Files/worldWool/TEMP/blocks.0.0.csv").toString());
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testBannerBlocksToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldBanner").toString(), "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new Boxel(BasicBlock.WHITE_BANNER,new Point(1,61,1)));
        primitives.add(new Boxel(BasicBlock.ORANGE_BANNER,new Point(1,61,2)));
        primitives.add(new Boxel(BasicBlock.MAGENTA_BANNER,new Point(1,61,3)));
        primitives.add(new Boxel(BasicBlock.LIGHT_BLUE_BANNER,new Point(1,61,4)));
        primitives.add(new Boxel(BasicBlock.YELLOW_BANNER,new Point(1,61,5)));
        primitives.add(new Boxel(BasicBlock.LIME_BANNER,new Point(1,61,6)));
        primitives.add(new Boxel(BasicBlock.PINK_BANNER,new Point(1,61,7)));
        primitives.add(new Boxel(BasicBlock.GRAY_BANNER,new Point(1,61,8)));
        primitives.add(new Boxel(BasicBlock.LIGHT_GRAY_BANNER,new Point(1,61,9)));
        primitives.add(new Boxel(BasicBlock.CYAN_BANNER,new Point(1,61,10)));
        primitives.add(new Boxel(BasicBlock.PURPLE_BANNER,new Point(1,61,11)));
        primitives.add(new Boxel(BasicBlock.BLUE_BANNER,new Point(1,61,12)));
        primitives.add(new Boxel(BasicBlock.BROWN_BANNER,new Point(1,61,13)));
        primitives.add(new Boxel(BasicBlock.GREEN_BANNER,new Point(1,61,14)));
        primitives.add(new Boxel(BasicBlock.RED_BANNER,new Point(1,61,15)));
        primitives.add(new Boxel(BasicBlock.BLACK_BANNER,new Point(1,61,16)));
        primitives.add(new Boxel(BasicBlock.WHITE_WALL_BANNER,new Point(4,62,1)));
        primitives.add(new Boxel(BasicBlock.ORANGE_WALL_BANNER,new Point(4,62,2)));
        primitives.add(new Boxel(BasicBlock.MAGENTA_WALL_BANNER,new Point(4,62,3)));
        primitives.add(new Boxel(BasicBlock.LIGHT_BLUE_WALL_BANNER,new Point(4,62,4)));
        primitives.add(new Boxel(BasicBlock.YELLOW_WALL_BANNER,new Point(4,62,5)));
        primitives.add(new Boxel(BasicBlock.LIME_WALL_BANNER,new Point(4,62,6)));
        primitives.add(new Boxel(BasicBlock.PINK_WALL_BANNER,new Point(4,62,7)));
        primitives.add(new Boxel(BasicBlock.GRAY_WALL_BANNER,new Point(4,62,8)));
        primitives.add(new Boxel(BasicBlock.LIGHT_GRAY_WALL_BANNER,new Point(4,62,9)));
        primitives.add(new Boxel(BasicBlock.CYAN_WALL_BANNER,new Point(4,62,10)));
        primitives.add(new Boxel(BasicBlock.PURPLE_WALL_BANNER,new Point(4,62,11)));
        primitives.add(new Boxel(BasicBlock.BLUE_WALL_BANNER,new Point(4,62,12)));
        primitives.add(new Boxel(BasicBlock.BROWN_WALL_BANNER,new Point(4,62,13)));
        primitives.add(new Boxel(BasicBlock.GREEN_WALL_BANNER,new Point(4,62,14)));
        primitives.add(new Boxel(BasicBlock.RED_WALL_BANNER,new Point(4,62,15)));
        primitives.add(new Boxel(BasicBlock.BLACK_WALL_BANNER,new Point(4,62,16)));

        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }

        try {
            compareTwoCsvFile(projectRoot.resolve("sources/worldBanner/TEMP/blocks.0.0.csv").toString(),
                    projectRoot.resolve("docs/tests/manual/Test Files/worldBanner/TEMP/blocks.0.0.csv").toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSignPostBlocksToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldSignPost").toString(), "TEMP");
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

        try {
            compareTwoCsvFile(projectRoot.resolve("sources/worldSignPost/TEMP/blocks.0.0.csv").toString(),
                    projectRoot.resolve("docs/tests/manual/Test Files/worldSignPost/TEMP/blocks.0.0.csv").toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testWallSignBlocksToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldWallSign").toString(), "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new WallSign(1,61,1,WallSign.Orientation.NORTH,"North"));
        primitives.add(new WallSign(1,61,2,WallSign.Orientation.SOUTH,"South"));
        primitives.add(new WallSign(1,61,3,WallSign.Orientation.WEST,"West"));
        primitives.add(new WallSign(1,61,4,WallSign.Orientation.EAST,"East"));



        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }

        try {
            compareTwoCsvFile(projectRoot.resolve("sources/worldWallSign/TEMP/blocks.0.0.csv").toString(),
                    projectRoot.resolve("docs/tests/manual/Test Files/worldWallSign/TEMP/blocks.0.0.csv").toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDoorBlocksToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldDoor").toString(), "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new Door(1,61,1,Door.Orientation.WEST));
        primitives.add(new Door(1,61,2,Door.Orientation.NORTH));
        primitives.add(new Door(1,61,3,Door.Orientation.EAST));
        primitives.add(new Door(1,61,4,Door.Orientation.SOUTH));



        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }

        try {
            compareTwoCsvFile(projectRoot.resolve("sources/worldDoor/TEMP/blocks.0.0.csv").toString(),
                    projectRoot.resolve("docs/tests/manual/Test Files/worldDoor/TEMP/blocks.0.0.csv").toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testTorchBlocksToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldTorch").toString(), "TEMP");
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

        try {
            compareTwoCsvFile(projectRoot.resolve("sources/worldTorch/TEMP/blocks.0.0.csv").toString(),
                    projectRoot.resolve("docs/tests/manual/Test Files/worldTorch/TEMP/blocks.0.0.csv").toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testChestBlocksToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldChest").toString(), "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new SingleBlock(BasicBlock.CHEST, new Point(1,61,1), SingleBlock.Orientation.NORTH));
        primitives.add(new SingleBlock(BasicBlock.CHEST, new Point(3,61,1), SingleBlock.Orientation.WEST));
        primitives.add(new SingleBlock(BasicBlock.CHEST, new Point(5,61,1), SingleBlock.Orientation.SOUTH));
        primitives.add(new SingleBlock(BasicBlock.CHEST, new Point(7,61,1), SingleBlock.Orientation.EAST));



        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }

        try {
            compareTwoCsvFile(projectRoot.resolve("sources/worldChest/TEMP/blocks.0.0.csv").toString(),
                    projectRoot.resolve("docs/tests/manual/Test Files/worldChest/TEMP/blocks.0.0.csv").toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSpawnerBlockToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldSpawner").toString(), "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new SingleBlock(BasicBlock.MOB_SPAWNER, new Point(1,61,1), "5"));



        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }

        try {
            compareTwoCsvFile(projectRoot.resolve("sources/worldSpawner/TEMP/blocks.0.0.csv").toString(),
                    projectRoot.resolve("docs/tests/manual/Test Files/worldSpawner/TEMP/blocks.0.0.csv").toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testRedstoneLampBlockToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldRedstone").toString(), "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new Boxel(BasicBlock.REDSTONE_BLOCK, new Point(1,61,1)));
        primitives.add(new Boxel(BasicBlock.REDSTONE_LAMP, new Point(1,62,1)));



        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }

        try {
            compareTwoCsvFile(projectRoot.resolve("sources/worldRedstone/TEMP/blocks.0.0.csv").toString(),
                    projectRoot.resolve("docs/tests/manual/Test Files/worldRedstone/TEMP/blocks.0.0.csv").toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSimpleBlocksToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldSimple").toString(), "TEMP");
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

        try {
            compareTwoCsvFile(projectRoot.resolve("sources/worldSimple/TEMP/blocks.0.0.csv").toString(),
                    projectRoot.resolve("docs/tests/manual/Test Files/worldSimple/TEMP/blocks.0.0.csv").toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
