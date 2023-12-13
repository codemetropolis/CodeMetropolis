package codemetropolis.toolchain.rendering.model.primitive;

import codemetropolis.blockmodifier.World;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;

import java.io.*;

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

        switch (block.getId()) {
            case 52:
                world.setSpawner(position.getX(), position.getY(), position.getZ(), block.getData(), info);
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

    public String toCSV() {
        if (block.getId() == -1) return null;
        return String.format("%d;%d;%d;%d;%d;%s", block.getId(), block.getData(), position.getX(), position.getY(), position.getZ(), (info == null || info.equals("") ? "NULL" : info));
    }

    @Override
    public int toCSVFile(File directory) {
        int x = position.getX() >> 9;
        int z = position.getZ() >> 9;

        File file = null;

        if (directory.exists()) {
            System.out.println("Directory already exists.");
        } else {
            boolean isDirectoryCreated = directory.mkdirs();
            if (isDirectoryCreated) {
                String filename = String.format("blocks.%d.%d.csv", x, z);
                file = new File(directory, filename);
            } else {
                System.err.println("Failed to create the directory.");
            }
        }

        try {
            assert file != null;
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
                String csv = toCSV();
                if (csv != null) writer.println(csv);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return 1;
    }

    @Override
    public int getNumberOfBlocks() {
        return 1;
    }

}
