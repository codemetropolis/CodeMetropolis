package codemetropolis.toolchain.rendering.model.primitive;

import codemetropolis.blockmodifier.World;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.util.EU;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
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

    public void render(World world) {
        if (position.getY() < 0 || position.getY() >= 255) return;

        createBlocks(world, block.getId());
    }

    private void createBlocks(World world, short blockID){
        switch (blockID) {
            case 52:
                Map<String, String> spawnData = jsonToMap(this.info);

                world.setSpawner(position.getX(), position.getY(), position.getZ(), block.getData(),
                        spawnData.get("idOfEntity"), Short.parseShort(spawnData.get("dangerValue")));
                break;
            case 54:
                world.setChest(position.getX(), position.getY(), position.getZ(), block.getData(), new int[]{276, 1});
                break;
            case 63:
                world.setSignPost(position.getX(), position.getY(), position.getZ(), block.getData(), info);
                break;
            case 68:
                world.setWallSign(position.getX(), position.getY(), position.getZ(), block.getData(), info);
                break;
            case 176:
                world.setBanner(position.getX(), position.getY(), position.getZ(), block.getData(), World.BannerColor.valueOf(info.toUpperCase()));
                break;
            default:
                world.setBlock(position.getX(), position.getY(), position.getZ(), block.getId(), block.getData());
        }
    }

    private Map<String, String> jsonToMap(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, String>> typeRef = new TypeReference<Map<String, String>>() {};
            return objectMapper.readValue(json, typeRef);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toCSV() {
        if (block.getId() == -1) return null;
        return String.format("%d;%d;%d;%d;%d;%s", block.getId(), block.getData(), position.getX(), position.getY(), position.getZ(), (info == null || info.equals("") ? "NULL" : info));
    }

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
