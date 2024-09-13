package codemetropolis.toolchain.rendering.model.primitive;

import codemetropolis.toolchain.rendering.control.WorldBuilder;
import codemetropolis.toolchain.rendering.exceptions.RenderingException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

//TODO: If a new block implementation is added, a corresponding test for it should be created in this test file!
public class TestAllBlockTypesPlacement {
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

    @Test
    public void testGenerateWoolBlocks(){
        File tempDir = projectRoot.resolve("sources/worldWool/TEMP").toFile();

        WorldBuilder worldBuilder = new WorldBuilder(projectRoot.resolve("sources/worldWool").toString());
        try {
            worldBuilder.build(tempDir);
        } catch (RenderingException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testGenerateBannerColorBlocks(){
        File tempDir = projectRoot.resolve("sources/worldBannerColor/TEMP").toFile();

        WorldBuilder worldBuilder = new WorldBuilder(projectRoot.resolve("sources/worldBannerColor").toString());
        try {
            worldBuilder.build(tempDir);
        } catch (RenderingException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testGenerateBannerOrientationBlocks(){
        File tempDir = projectRoot.resolve("sources/worldBannerOrientation/TEMP").toFile();

        WorldBuilder worldBuilder = new WorldBuilder(projectRoot.resolve("sources/worldBannerOrientation").toString());
        try {
            worldBuilder.build(tempDir);
        } catch (RenderingException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testGenerateSignPostBlocks(){
        File tempDir = projectRoot.resolve("sources/worldSignPost/TEMP").toFile();

        WorldBuilder worldBuilder = new WorldBuilder(projectRoot.resolve("sources/worldSignPost").toString());
        try {
            worldBuilder.build(tempDir);
        } catch (RenderingException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testGenerateWallSignBlocks(){
        File tempDir = projectRoot.resolve("sources/worldWallSign/TEMP").toFile();

        WorldBuilder worldBuilder = new WorldBuilder(projectRoot.resolve("sources/worldWallSign").toString());
        try {
            worldBuilder.build(tempDir);
        } catch (RenderingException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testGenerateChestBlocks(){
        File tempDir = projectRoot.resolve("sources/worldChest/TEMP").toFile();

        WorldBuilder worldBuilder = new WorldBuilder(projectRoot.resolve("sources/worldChest").toString());
        try {
            worldBuilder.build(tempDir);
        } catch (RenderingException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testGenerateSpawnerBlocks(){
        File tempDir = projectRoot.resolve("sources/worldSpawner/TEMP").toFile();

        WorldBuilder worldBuilder = new WorldBuilder(projectRoot.resolve("sources/worldSpawner").toString());
        try {
            worldBuilder.build(tempDir);
        } catch (RenderingException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testGenerateDoorBlocks(){
        File tempDir = projectRoot.resolve("sources/worldDoor/TEMP").toFile();

        WorldBuilder worldBuilder = new WorldBuilder(projectRoot.resolve("sources/worldDoor").toString());
        try {
            worldBuilder.build(tempDir);
        } catch (RenderingException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testGenerateRedstoneLampBlocks(){
        File tempDir = projectRoot.resolve("sources/worldRedstone/TEMP").toFile();

        WorldBuilder worldBuilder = new WorldBuilder(projectRoot.resolve("sources/worldRedstone").toString());
        try {
            worldBuilder.build(tempDir);
        } catch (RenderingException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testGenerateTorchBlocks(){
        File tempDir = projectRoot.resolve("sources/worldTorch/TEMP").toFile();

        WorldBuilder worldBuilder = new WorldBuilder(projectRoot.resolve("sources/worldTorch").toString());
        try {
            worldBuilder.build(tempDir);
        } catch (RenderingException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testGenerateSimpleBlocks(){
        File tempDir = projectRoot.resolve("sources/worldSimple/TEMP").toFile();

        WorldBuilder worldBuilder = new WorldBuilder(projectRoot.resolve("sources/worldSimple").toString());
        try {
            worldBuilder.build(tempDir);
        } catch (RenderingException e) {
            System.err.println(e.getMessage());
        }
    }
}
