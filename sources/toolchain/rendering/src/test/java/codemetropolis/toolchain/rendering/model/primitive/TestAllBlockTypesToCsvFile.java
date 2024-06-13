package codemetropolis.toolchain.rendering.model.primitive;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import static codemetropolis.toolchain.rendering.model.BasicBlock.BasicBlockType.*;
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

    public void toCsv(LinkedList<Primitive> primitives, File tempDir, Path createdFile, Path testFile){
        for (Primitive p : primitives) {
            p.toCSVFile(tempDir);
        }

        try {
            compareTwoCsvFile(testFile.toString(), createdFile.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void compareTwoCsvFile(String file1, String file2) throws IOException {
        List<String> fileContent1 = Files.readAllLines(Paths.get(file1));
        List<String> fileContent2 = Files.readAllLines(Paths.get(file2));

        assertEquals(fileContent1, fileContent2, "The files do not match!");
    }

    @Test
    public void testWoolBlocksToCSVFile() {
        File tempDir = new File(projectRoot.resolve("sources/worldWool").toString(), "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new Boxel(WHITE_WOOL, new Point(1, 61, 1)));
        primitives.add(new Boxel(ORANGE_WOOL, new Point(1, 61, 2)));
        primitives.add(new Boxel(MAGENTA_WOOL, new Point(1, 61, 3)));
        primitives.add(new Boxel(LIGHT_BLUE_WOOL, new Point(1, 61, 4)));
        primitives.add(new Boxel(YELLOW_WOOL, new Point(1, 61, 5)));
        primitives.add(new Boxel(LIME_WOOL, new Point(1, 61, 6)));
        primitives.add(new Boxel(PINK_WOOL, new Point(1, 61, 7)));
        primitives.add(new Boxel(GRAY_WOOL, new Point(1, 61, 8)));
        primitives.add(new Boxel(LIGHT_GRAY_WOOL, new Point(1, 61, 9)));
        primitives.add(new Boxel(CYAN_WOOL, new Point(4, 61, 1)));
        primitives.add(new Boxel(PURPLE_WOOL, new Point(4, 61, 2)));
        primitives.add(new Boxel(BLUE_WOOL, new Point(4, 61, 3)));
        primitives.add(new Boxel(BROWN_WOOL, new Point(4, 61, 4)));
        primitives.add(new Boxel(GREEN_WOOL, new Point(4, 61, 5)));
        primitives.add(new Boxel(RED_WOOL, new Point(4, 61, 6)));
        primitives.add(new Boxel(BLACK_WOOL, new Point(4, 61, 7)));

        toCsv(primitives,
                tempDir,
                projectRoot.resolve("sources/worldWool/TEMP/blocks.0.0.csv"),
                projectRoot.resolve("docs/tests/manual/Test Files/worldWool/TEMP/blocks.0.0.csv"));
    }

    @Test
    public void testBannerBlocksColorToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldBannerColor").toString(), "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new Banner(WHITE_BANNER.getBlock(),
                Banner.Orientation.NORTH, new Point(1,61,1)));
        primitives.add(new Banner(ORANGE_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(1,61,2)));
        primitives.add(new Banner(MAGENTA_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(1,61,3)));
        primitives.add(new Banner(LIGHT_BLUE_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(1,61,4)));
        primitives.add(new Banner(YELLOW_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(1,61,5)));
        primitives.add(new Banner(LIME_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(1,61,6)));
        primitives.add(new Banner(PINK_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(1,61,7)));
        primitives.add(new Banner(GRAY_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(1,61,8)));
        primitives.add(new Banner(LIGHT_GRAY_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(1,61,9)));
        primitives.add(new Banner(CYAN_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(1,61,10)));
        primitives.add(new Banner(PURPLE_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(1,61,11)));
        primitives.add(new Banner(BLUE_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(1,61,12)));
        primitives.add(new Banner(BROWN_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(1,61,13)));
        primitives.add(new Banner(GREEN_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(1,61,14)));
        primitives.add(new Banner(RED_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(1,61,15)));
        primitives.add(new Banner(BLACK_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(1,61,16)));
        primitives.add(new Banner(WHITE_WALL_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(4,62,1)));
        primitives.add(new Banner(ORANGE_WALL_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(4,62,2)));
        primitives.add(new Banner(MAGENTA_WALL_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(4,62,3)));
        primitives.add(new Banner(LIGHT_BLUE_WALL_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(4,62,4)));
        primitives.add(new Banner(YELLOW_WALL_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(4,62,5)));
        primitives.add(new Banner(LIME_WALL_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(4,62,6)));
        primitives.add(new Banner(PINK_WALL_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(4,62,7)));
        primitives.add(new Banner(GRAY_WALL_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(4,62,8)));
        primitives.add(new Banner(LIGHT_GRAY_WALL_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(4,62,9)));
        primitives.add(new Banner(CYAN_WALL_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(4,62,10)));
        primitives.add(new Banner(PURPLE_WALL_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(4,62,11)));
        primitives.add(new Banner(BLUE_WALL_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(4,62,12)));
        primitives.add(new Banner(BROWN_WALL_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(4,62,13)));
        primitives.add(new Banner(GREEN_WALL_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(4,62,14)));
        primitives.add(new Banner(RED_WALL_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(4,62,15)));
        primitives.add(new Banner(BLACK_WALL_BANNER.getBlock(),
                Banner.Orientation.NORTH,new Point(4,62,16)));

        toCsv(primitives,
                tempDir,
                projectRoot.resolve("sources/worldBannerColor/TEMP/blocks.0.0.csv"),
                projectRoot.resolve("docs/tests/manual/Test Files/worldBannerColor/TEMP/blocks.0.0.csv"));
    }

    @Test
    public void testBannerBlocksOrientationToCSVFile() {
        File tempDir = new File(projectRoot.resolve("sources/worldBannerOrientation").toString(), "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new Banner(WHITE_BANNER.getBlock(),
                Banner.Orientation.SOUTH, new Point(1,61,1)));
        primitives.add(new Banner(WHITE_BANNER.getBlock(),
                Banner.Orientation.SOUTHWEST, new Point(1,61,2)));
        primitives.add(new Banner(WHITE_BANNER.getBlock(),
                Banner.Orientation.WEST, new Point(1,61,3)));
        primitives.add(new Banner(WHITE_BANNER.getBlock(),
                Banner.Orientation.NORTHWEST, new Point(1,61,4)));
        primitives.add(new Banner(WHITE_BANNER.getBlock(),
                Banner.Orientation.NORTH, new Point(1,61,5)));
        primitives.add(new Banner(WHITE_BANNER.getBlock(),
                Banner.Orientation.NORTHEAST, new Point(1,61,6)));
        primitives.add(new Banner(WHITE_BANNER.getBlock(),
                Banner.Orientation.EAST, new Point(1,61,7)));
        primitives.add(new Banner(WHITE_BANNER.getBlock(),
                Banner.Orientation.SOUTHEAST, new Point(1,61,8)));

        toCsv(primitives,
                tempDir,
                projectRoot.resolve("sources/worldBannerOrientation/TEMP/blocks.0.0.csv"),
                projectRoot.resolve("docs/tests/manual/Test Files/worldBannerOrientation/TEMP/blocks.0.0.csv"));
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


        toCsv(primitives,
                tempDir,
                projectRoot.resolve("sources/worldSignPost/TEMP/blocks.0.0.csv"),
                projectRoot.resolve("docs/tests/manual/Test Files/worldSignPost/TEMP/blocks.0.0.csv"));
    }

    @Test
    public void testWallSignBlocksToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldWallSign").toString(), "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new WallSign(1,61,1,WallSign.Orientation.NORTH,"North"));
        primitives.add(new WallSign(1,61,2,WallSign.Orientation.SOUTH,"South"));
        primitives.add(new WallSign(1,61,3,WallSign.Orientation.WEST,"West"));
        primitives.add(new WallSign(1,61,4,WallSign.Orientation.EAST,"East"));

        toCsv(primitives,
                tempDir,
                projectRoot.resolve("sources/worldWallSign/TEMP/blocks.0.0.csv"),
                projectRoot.resolve("docs/tests/manual/Test Files/worldWallSign/TEMP/blocks.0.0.csv"));
    }

    @Test
    public void testDoorBlocksToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldDoor").toString(), "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new Door(1,61,1,Door.Orientation.WEST));
        primitives.add(new Door(1,61,2,Door.Orientation.NORTH));
        primitives.add(new Door(1,61,3,Door.Orientation.EAST));
        primitives.add(new Door(1,61,4,Door.Orientation.SOUTH));

        toCsv(primitives,
                tempDir,
                projectRoot.resolve("sources/worldDoor/TEMP/blocks.0.0.csv"),
                projectRoot.resolve("docs/tests/manual/Test Files/worldDoor/TEMP/blocks.0.0.csv"));
    }

    @Test
    public void testTorchBlocksToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldTorch").toString(), "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();
        BasicBlock torch = TORCH.getBlock();

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

        toCsv(primitives,
                tempDir,
                projectRoot.resolve("sources/worldTorch/TEMP/blocks.0.0.csv"),
                projectRoot.resolve("docs/tests/manual/Test Files/worldTorch/TEMP/blocks.0.0.csv"));
    }

    @Test
    public void testChestBlocksToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldChest").toString(), "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new SingleBlock(CHEST.getBlock(), new Point(1,61,1), SingleBlock.Orientation.NORTH));
        primitives.add(new SingleBlock(CHEST.getBlock(), new Point(3,61,1), SingleBlock.Orientation.WEST));
        primitives.add(new SingleBlock(CHEST.getBlock(), new Point(5,61,1), SingleBlock.Orientation.SOUTH));
        primitives.add(new SingleBlock(CHEST.getBlock(), new Point(7,61,1), SingleBlock.Orientation.EAST));

        toCsv(primitives,
                tempDir,
                projectRoot.resolve("sources/worldChest/TEMP/blocks.0.0.csv"),
                projectRoot.resolve("docs/tests/manual/Test Files/worldChest/TEMP/blocks.0.0.csv"));
    }

    @Test
    public void testSpawnerBlockToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldSpawner").toString(), "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new SingleBlock(MOB_SPAWNER.getBlock(), new Point(1,61,1), "5"));

        toCsv(primitives,
                tempDir,
                projectRoot.resolve("sources/worldSpawner/TEMP/blocks.0.0.csv"),
                projectRoot.resolve("docs/tests/manual/Test Files/worldSpawner/TEMP/blocks.0.0.csv"));
    }

    @Test
    public void testRedstoneLampBlockToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldRedstone").toString(), "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new Boxel(REDSTONE_BLOCK, new Point(1,61,1)));
        primitives.add(new Boxel(REDSTONE_LAMP, new Point(1,62,1)));

        toCsv(primitives,
                tempDir,
                projectRoot.resolve("sources/worldRedstone/TEMP/blocks.0.0.csv"),
                projectRoot.resolve("docs/tests/manual/Test Files/worldRedstone/TEMP/blocks.0.0.csv"));
    }

    @Test
    public void testSimpleBlocksToCSVFile(){
        File tempDir = new File(projectRoot.resolve("sources/worldSimple").toString(), "TEMP");
        LinkedList<Primitive> primitives = new LinkedList<>();

        primitives.add(new Boxel(STONE, new Point(1,61,1)));
        primitives.add(new Boxel(STONE_BRICKS, new Point(1,61,2)));
        primitives.add(new Boxel(SANDSTONE, new Point(1,61,3)));
        primitives.add(new Boxel(GRASS_BLOCK, new Point(1,61,4)));
        primitives.add(new Boxel(FENCE, new Point(1,61,5)));
        primitives.add(new Boxel(POPPY, new Point(1,61,6)));
        primitives.add(new Boxel(DANDELION, new Point(1,61,7)));
        primitives.add(new Boxel(BROWN_MUSHROOM, new Point(1,61,8)));
        primitives.add(new Boxel(OAK_SAPLING, new Point(1,61,9)));
        primitives.add(new Boxel(COBBLESTONE, new Point(4,61,1)));
        primitives.add(new Boxel(MOSSY_COBBLESTONE, new Point(4,61,2)));
        primitives.add(new Boxel(OBSIDIAN, new Point(4,61,3)));
        primitives.add(new Boxel(OAK_WOOD, new Point(4,61,4)));
        primitives.add(new Boxel(DARK_OAK_WOOD, new Point(4,61,5)));
        primitives.add(new Boxel(BIRCH_WOOD, new Point(4,61,6)));
        primitives.add(new Boxel(OAK_PLANKS, new Point(4,61,7)));
        primitives.add(new Boxel(DARK_OAK_PLANKS, new Point(4,61,8)));
        primitives.add(new Boxel(IRON_BLOCK, new Point(4,61,9)));
        primitives.add(new Boxel(DIRT, new Point(7,61,1)));
        primitives.add(new Boxel(CUT_SANDSTONE, new Point(7,61,2)));
        primitives.add(new Boxel(RED_SAND, new Point(7,61,3)));
        primitives.add(new Boxel(BRICK_BLOCK, new Point(7,61,4)));
        primitives.add(new Boxel(GLASS, new Point(7,61,5)));
        primitives.add(new Boxel(GOLD_BLOCK, new Point(7,61,6)));
        primitives.add(new Boxel(DIAMOND_BLOCK, new Point(7,61,7)));

        toCsv(primitives,
                tempDir,
                projectRoot.resolve("sources/worldSimple/TEMP/blocks.0.0.csv"),
                projectRoot.resolve("docs/tests/manual/Test Files/worldSimple/TEMP/blocks.0.0.csv"));
    }
}
