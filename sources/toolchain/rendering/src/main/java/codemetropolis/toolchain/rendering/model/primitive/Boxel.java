package codemetropolis.toolchain.rendering.model.primitive;

import codemetropolis.toolchain.commons.blockmodifier.World;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.model.BlockType;
import codemetropolis.toolchain.commons.model.property.PropertyOrdinal;
import codemetropolis.toolchain.commons.util.EU;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.util.JsonUtil;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Boxel implements Primitive {

    public BasicBlock block;
    public Point position;
    public String info;

    public Boxel(BlockType block, Point position) {
        super();
        this.block = new BasicBlock(block);
        this.position = position;
    }

    public Boxel(BlockType block, Point position, String info) {
        this(block, position);
        this.info = info;
    }

    public Boxel(BasicBlock block, Point position) {
        super();
        this.block = block;
        this.position = position;
    }

    public Boxel(BasicBlock block, Point position, String info) {
        this(block, position);
        this.info = info;
    }

    /**
     * Parses a CSV string to create a Boxel object.
     * <p>
     * This method splits the input CSV string into its components, extracts properties,
     * and constructs a new Boxel object using the parsed values.
     * </p>
     *
     * @param csv The CSV string to parse, which should be in the format:
     *            "stringId;shortId;properties;x;y;z;info".
     * @return A new Boxel object constructed from the parsed CSV values.
     * @throws IllegalArgumentException if the CSV string format is invalid.
     */
    public static Boxel parseCSV(String csv) {
        String[] parts = csv.split(";");
        List<Integer> properties = parseProperties(parts[2]);

        return new Boxel(new BasicBlock(parts[0], Short.parseShort(parts[1]), properties),
                new Point(Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5])),
                (parts[6].equals("NULL") ? "" : parts[6]));
    }

    public static List<Integer> parseProperties(String propertiesPart) {
        if (propertiesPart.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> resultList = new ArrayList<>();
        for (var ordinal : PropertyOrdinal.values()) {
            resultList.add(ordinal.getValue(), -1);
        }

        if (propertiesPart.contains("&")) {
            Arrays.stream(propertiesPart.split("&"))
                    .map(prop -> prop.split("="))
                    .forEach(prop -> {
                        addProperty(prop, resultList);
                    });
        } else {
            String[] prop = propertiesPart.split("=");
            if (prop.length == 2) {
                addProperty(prop, resultList);
            } else {
                throw new IllegalArgumentException("Invalid properties format");
            }
        }

        return resultList;
    }

    private static void addProperty(String[] prop, List<Integer> resultList){
        int index = -1;
        for (var ordinal : PropertyOrdinal.values()){
            if (prop[0].equalsIgnoreCase(ordinal.toString())){
                index = ordinal.getValue();
            }
        }

        int value = Integer.parseInt(prop[1]);
        resultList.add(index, value);
    }


    /**
     * Renders the Boxel object within the specified world.
     * <p>
     * This method checks the y-coordinate of the position to ensure it is within valid bounds (0 to 254).
     * Depending on the block type, it delegates rendering to the appropriate method in the World object.
     * </p>
     *
     * @param world The World object where the Boxel will be rendered. This object contains all necessary
     *              data and methods for creating blocks and other elements in the Minecraft world.
     */
    public void render(World world) {
        if (position.getY() < 0 || position.getY() >= 255) return;
        Map<String, String> blockData;

        if(block.getStringId().contains("banner")){
            world.setBanner(position.getX(),
                    position.getY(),
                    position.getZ(),
                    block.getShortId(),
                    block.getiProperties());
            return;
        }

        switch (block.getStringId()) {

            case "minecraft:oak_sign":
                world.setSignPost(position.getX(), position.getY(), position.getZ(), block.getiProperties(), info);
                break;
            case "minecraft:mob_spawner":
                blockData = JsonUtil.convertJsonStringToMap(this.info);

                world.setSpawner(position.getX(), position.getY(), position.getZ(), block.getiProperties(),
                        blockData.get("idOfEntity"), Short.parseShort(blockData.get("dangerValue")));
                break;
            case "minecraft:chest":
                world.setChest(position.getX(), position.getY(), position.getZ(), block.getiProperties(), new int[]{276, 1});
                break;
            case "minecraft:wall_sign":
                world.setWallSign(position.getX(), position.getY(), position.getZ(), block.getiProperties(), info);
                break;
            default:
                world.setBlock(position.getX(), position.getY(), position.getZ(), block.getShortId(), block.getiProperties());
        }
    }

    /**
     * Converts the Boxel object to a CSV formatted string.
     * <p>
     * The CSV string is formatted as follows:
     * <pre>
     * stringId;shortId;properties;x;y;z;info
     * </pre>
     * Where:
     * <ul>
     *   <li>stringId: The string ID of the block</li>
     *   <li>shortId: The short ID of the block</li>
     *   <li>properties: The properties of the block, formatted as key=value pairs joined by &</li>
     *   <li>x: The x-coordinate of the position</li>
     *   <li>y: The y-coordinate of the position</li>
     *   <li>z: The z-coordinate of the position</li>
     *   <li>info: Additional information, or "NULL" if info is null or empty</li>
     * </ul>
     *
     * @return A CSV formatted string representing the Boxel object, or null if the block's string ID is empty.
     */
    public String toCSV() {
        if (block.getStringId().isEmpty()) {
            return null;
        }
        String fancyProperties = block.getProperties().stream()
                .sorted(Comparator.comparingInt(e -> {
                    String className = e.getClass().getSimpleName();
                    for (PropertyOrdinal ordinal : PropertyOrdinal.values()) {
                        if (ordinal.name().equalsIgnoreCase(className)) {
                            return ordinal.getValue();
                        }
                    }
                    return Integer.MAX_VALUE;
                }))
                .map(e -> e.getClass().getSimpleName() + "=" + getEnumValue((Enum<?>) e))
                .collect(Collectors.joining("&"));

        return String.format("%s;%d;%s;%d;%d;%d;%s", block.getStringId(), block.getShortId(), fancyProperties, position.getX(), position.getY(),
                position.getZ(), (info == null || info.equals("") ? "NULL" : info));
    }

    private int getEnumValue(Enum<?> e) {
        try {
            return (Integer) e.getClass().getMethod("getValue").invoke(e);
        }catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
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
