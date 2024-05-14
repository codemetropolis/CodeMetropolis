package codemetropolis.toolchain.rendering.model.primitive;

import codemetropolis.toolchain.commons.blockmodifier.World;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.util.EU;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.util.JsonUtil;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Boxel implements Primitive {

    public BasicBlock block;
    public Point position;
    public String info;

    public Boxel(BasicBlock block, Point position) {
        super();
        this.block = block;
        this.position = position;
    }

    public Boxel(BasicBlock block, Point position, String info) {
        this(block, position);
        this.info = info;
    }

    public static Boxel parseCSV(String csv) {
        String[] parts = csv.split(";");
        return new Boxel(
                new BasicBlock(
                        Short.parseShort(parts[0]),
                        Integer.parseInt(parts[1])),
                new Point(
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4])),
                (parts[5].equals("NULL") ? "" : parts[5])
        );

    }

    /**
     * This method renders the world between 0 and 255 y coordinates based on pre collected world data from the csv file
     *
     * @param world World object in which all the data, for example blocks and their data, needs for the creation of the
     *              Minecraft world is stored
     */
    public void render(World world) {
        if (position.getY() < 0 || position.getY() >= 255) return;

        createBlocks(world, block.getId());
    }

    /**
     * This creates the individual blocks based on the block id
     *
     * @param world World object which contains all the information of the Minecraft world
     * @param blockID the id of the block that is being created
     */
    private void createBlocks(World world, short blockID){
        Map<String, String> blockData = new HashMap<>();

        switch (blockID) {
            case 52:
                blockData = JsonUtil.convertJsonStringToMap(this.info);

                world.setSpawner(position.getX(), position.getY(), position.getZ(), block.getData(),
                        blockData.get("idOfEntity"), Short.parseShort(blockData.get("dangerValue")));
                break;
            case 54:
                world.setChest(position.getX(), position.getY(), position.getZ(), block.getData(), new int[]{276, 1});
                break;
            case 63:
                blockData = JsonUtil.convertJsonStringToMap(this.info);

                world.setSignPost(position.getX(), position.getY(), position.getZ(), block.getData(),
                        blockData.get("textOnSign"));
                break;
            case 68:
                blockData = JsonUtil.convertJsonStringToMap(this.info);

                world.setWallSign(position.getX(), position.getY(), position.getZ(), block.getData(),
                        blockData.get("textOnSign"));
                break;
            case 176:
                blockData = JsonUtil.convertJsonStringToMap(this.info);

                world.setBanner(position.getX(), position.getY(), position.getZ(), block.getData(),
                        World.BannerColor.valueOf(blockData.get("bannerColor").toUpperCase()));
                break;
            default:
                world.setBlock(position.getX(), position.getY(), position.getZ(), block.getId(), block.getData());
        }
    }

    public String toCSV() {
        if (block.getId() == -1) return null;
        return String.format("%d;%d;%d;%d;%d;%s", block.getId(), block.getData(), position.getX(), position.getY(), position.getZ(), (info == null || info.equals("") ? "NULL" : info));
    }

    /**
     * This method creates a directory based on directory parameter path and then, in this directory
     * writes the blocks' data into a csv file
     *
     * @param directory the directory where the csv containing blocks' data will be created
     */
    @Override
    public int toCSVFile(File directory) {
        int x = position.getX() >> 9;
        int z = position.getZ() >> 9;

        File file;

        createDirectory(directory);

        String filename = String.format("blocks.%d.%d.csv", x, z);
        file = new File(directory, filename);

        writeBlocksToFile(file);
        return 1;
    }

    private void createDirectory(File directory) {
        if (!directory.exists()) {
            try {
                EU.tryUnchecked(directory::mkdirs);
            } catch (Exception e) {
                throw new RuntimeException("Directory creation failed.",
                        e.getClass().getName().equals("java.nio.file.FileAlreadyExistsException") ? null : e);
            }
        }
    }

    /**
     * This method writes the blocks' data in a predefined style into a csv file
     *
     * @param file where teh blocks' data will be written into
     */
    private void writeBlocksToFile(File file){
        //TODO: Fix IoException catch
        try {
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
                String csv = toCSV();
                if (csv != null) writer.println(csv);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public int getNumberOfBlocks() {
        return 1;
    }

}
