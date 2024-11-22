package codemetropolis.toolchain.rendering.model.primitive;

import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.commons.model.BlockType;
import codemetropolis.toolchain.commons.model.property.Orientation4;
import codemetropolis.toolchain.rendering.model.BasicBlock;
import codemetropolis.toolchain.rendering.util.JsonUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static codemetropolis.toolchain.commons.model.BlockType.MOB_SPAWNER;

public class SingleBlock implements Primitive {

    private Point position;
    private String stringId;
    private short shortId;
    private Orientation4 orientation4;
    private String dangerValue;

    public SingleBlock(BasicBlock block, int x, int y, int z) {
        super();
        this.position = new Point(x, y, z);
        this.stringId = block.getStringId();
        this.shortId = block.getShortId();
        this.orientation4 = Orientation4.NORTH;
    }

    public SingleBlock(BlockType block, Point position, String dangerAttrValue) {
        super();
        this.position = position;
        this.stringId = block.getStringId();
        this.shortId = block.getShortId();
        this.dangerValue = dangerAttrValue;
    }

    public SingleBlock(BasicBlock block, Point position) {
        super();
        this.position = position;
        this.stringId = block.getStringId();
        this.shortId = block.getShortId();
        this.orientation4 = Orientation4.NORTH;
    }

    public SingleBlock(BlockType block, Point position, Orientation4 orientation4) {
        super();
        this.position = position;
        this.stringId = block.getStringId();
        this.shortId = block.getShortId();
        this.orientation4 = orientation4;
    }

    /**
     * This method, based on block type (default or spawner), creates a csv file which stores the data of the block as
     * numbers separated by semicolons
     *
     * @param directory directory where the csv file will be created
     */
    @Override
    public int toCSVFile(File directory) {
        if (stringId.equals("minecraft:mob_spawner")) {
            String jsonString = JsonUtil.convertMapToJson(setSpawnerData(dangerValue));

            new Boxel(MOB_SPAWNER, position, jsonString).toCSVFile(directory);
        } else {
            for (var block : BlockType.values()) {
                if (stringId.equals(block.getStringId())) {
                    BasicBlock basicBlock = new BasicBlock(block);
                    basicBlock.addProperty(orientation4);
                    new Boxel(basicBlock, position).toCSVFile(directory);
                }
            }
        }

        return 1;
    }

    /**
     * This method creates a map for the spawner's data. It consists of the monster type the spawner should spawn,
     * and its related danger value to control max spawn able entities
     */
    private Map<String, String> setSpawnerData(String dangerValue) {
        Map<String, String> spawnerMap = new HashMap<>();
        spawnerMap.put("idOfEntity", "minecraft:zombie");
        spawnerMap.put("dangerValue", dangerValue);

        return spawnerMap;
    }

    @Override
    public int getNumberOfBlocks() {
        return 1;
    }
}
