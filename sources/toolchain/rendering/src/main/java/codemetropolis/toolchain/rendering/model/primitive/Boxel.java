package codemetropolis.toolchain.rendering.model.primitive;

import codemetropolis.toolchain.commons.blockmodifier.World;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.util.EU;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.util.JsonUtil;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
        Map<String, String> properties = Collections.emptyMap();
        try {
            String[] rawProperties = parts[2].split("&");
            if(parts[2].length() > 1) {
                properties = Arrays.stream(rawProperties)
                        .collect(Collectors.toMap(e -> e.split("=")[0], e -> e.split("=")[1]));
            }
        } catch (Exception e2) {
            throw new RuntimeException(e2);
        }

        return new Boxel(new BasicBlock(parts[0], Short.parseShort(parts[1]), properties),
                new Point(Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5])),
                (parts[6].equals("NULL") ? "" : parts[6]));
    }

    /**
     * This method renders the world between 0 and 255 y coordinates based on pre collected world data from the csv file
     *
     * @param world World object in which all the data, for example blocks and their data, needs for the creation of the
     *              Minecraft world is stored
     */
    public void render(World world) {
        if (position.getY() < 0 || position.getY() >= 255) return;
        Map<String, String> blockData = new HashMap<>();

        switch (block.getStringId()) {

            case "minecraft:sign":
                world.setSignPost(position.getX(), position.getY(), position.getZ(), block.getProperties(), info);
                break;
            case "minecraft:mob_spawner":
                blockData = JsonUtil.convertJsonStringToMap(this.info);

                world.setSpawner(position.getX(), position.getY(), position.getZ(), block.getProperties(),
                        blockData.get("idOfEntity"), Short.parseShort(blockData.get("dangerValue")));
                break;
            case "minecraft:wall_sign":
                world.setWallSign(position.getX(), position.getY(), position.getZ(), block.getProperties(), info);
                break;
            case "minecraft:white_banner":
                world.setBanner(position.getX(), position.getY(), position.getZ(), block.getProperties());
                break;
            default:
                world.setBlock(position.getX(), position.getY(), position.getZ(), block.getShortId(), block.getProperties());
        }
    }

    /**
     * This creates the individual blocks based on the block id
     *
     * @param world   World object which contains all the information of the Minecraft world
     * @param blockID the id of the block that is being created
     */
    private void createBlocks(World world, short blockID) {
        Map<String, String> blockData = new HashMap<>();

        switch (blockID) {
            case 52:
                blockData = JsonUtil.convertJsonStringToMap(this.info);

                world.setSpawner(position.getX(), position.getY(), position.getZ(), block.getProperties(),
                        blockData.get("idOfEntity"), Short.parseShort(blockData.get("dangerValue")));
                break;
            case 54:
                world.setChest(position.getX(), position.getY(), position.getZ(), block.getProperties(), new int[]{276, 1});
                break;
            case 63:
                blockData = JsonUtil.convertJsonStringToMap(this.info);

                world.setSignPost(position.getX(), position.getY(), position.getZ(), block.getProperties(),
                        blockData.get("textOnSign"));
                break;
            case 68:
                blockData = JsonUtil.convertJsonStringToMap(this.info);

                world.setWallSign(position.getX(), position.getY(), position.getZ(), block.getProperties(),
                        blockData.get("textOnSign"));
                break;
            case 176:
                world.setBanner(position.getX(), position.getY(), position.getZ(), block.getProperties());
                break;
            default:
                world.setBlock(position.getX(), position.getY(), position.getZ(), block.getShortId(), block.getProperties());
        }
    }

    public String toCSV() {
        if (block.getStringId().equals("")) {
            return null;
        }
        String fancyProperties = block.getProperties().entrySet().stream().map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("&"));

        return String.format("%s;%d;%s;%d;%d;%d;%s", block.getStringId(), block.getShortId(), fancyProperties, position.getX(), position.getY(),
                position.getZ(), (info == null || info.equals("") ? "NULL" : info));
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
    private void writeBlocksToFile(File file) {
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

    @Override
    public String toString() {
        return "Boxel [block=" + block + ", position=" + position + ", info=" + info + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((block == null) ? 0 : block.hashCode());
        result = prime * result + ((info == null) ? 0 : info.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Boxel other = (Boxel) obj;
        if (block == null) {
            if (other.block != null)
                return false;
        } else if (!block.equals(other.block))
            return false;
        if (info == null) {
            if (other.info != null)
                return false;
        } else if (!info.equals(other.info))
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        return true;
    }
}
