package codemetropolis.blockmodifier;

import codemetropolis.blockmodifier.ext.NBTException;
import codemetropolis.blockmodifier.ext.NBTTag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;


//TODO: Refactor this class
public class Chunk {

    public NBTTag tag;


    //TODO: Fix RuntimeException to make it NBTException
    private Chunk(NBTTag tag) {
        if (tag.getType() != NBTTag.Type.TAG_Compound) {
            try {
                throw new NBTException("Chunk tag must be compound.");
            } catch (NBTException e) {
                throw new RuntimeException(e);
            }
        }
        this.tag = tag;

    }


    public Chunk(int x, int z) {

        byte terrainPopulated = 1;
        long inhabitedTime = 0L;
        long lastUpdate = 0L;

        int[] heightMap = new int[256];
        Arrays.fill(heightMap, 0);

        byte[] biomes = new byte[256];
        Arrays.fill(biomes, (byte) -1);

        NBTTag terrainPopulatedTag = new NBTTag(NBTTag.Type.TAG_Byte, "TerrainPopulated", terrainPopulated);
        NBTTag xPosTag = new NBTTag(NBTTag.Type.TAG_Int, "xPos", x);
        NBTTag zPosTag = new NBTTag(NBTTag.Type.TAG_Int, "zPos", z);
        NBTTag inhabitedTimeTag = new NBTTag(NBTTag.Type.TAG_Long, "InhabitedTime", inhabitedTime);
        NBTTag lastUpdateTag = new NBTTag(NBTTag.Type.TAG_Long, "LastUpdate", lastUpdate);
        NBTTag biomesTag = new NBTTag(NBTTag.Type.TAG_Byte_Array, "Biomes", biomes);
        NBTTag entitiesTag = new NBTTag(NBTTag.Type.TAG_List, "Entities", NBTTag.Type.TAG_Byte);
        NBTTag tileEntitiesTag = new NBTTag(NBTTag.Type.TAG_List, "TileEntities", NBTTag.Type.TAG_Byte);
        NBTTag heightMapTag = new NBTTag(NBTTag.Type.TAG_Int_Array, "HeightMap", heightMap);
        NBTTag sectionsTag = new NBTTag("Sections", NBTTag.Type.TAG_List);

        NBTTag[] tagList = new NBTTag[]{terrainPopulatedTag, xPosTag, zPosTag, inhabitedTimeTag, biomesTag, lastUpdateTag, sectionsTag, entitiesTag, tileEntitiesTag, heightMapTag, new NBTTag(NBTTag.Type.TAG_End, null, null)};
        NBTTag levelTag = new NBTTag(NBTTag.Type.TAG_Compound, "Level", tagList);
        this.tag = new NBTTag(NBTTag.Type.TAG_Compound, "", new NBTTag[]{levelTag, new NBTTag(NBTTag.Type.TAG_End, null, null)});

    }

    public static Chunk parseNBT(NBTTag t) {
        return new Chunk(t);
    }

    private static void setNibble(byte[] a, int index, byte value) {
        if (index / 2 < 0 || index / 2 > a.length - 1) return;

        boolean lastBits = ((double) index / 2) % 1 == 0 ? true : false;

        if (lastBits) {
            value = (byte) ((a[index / 2] & 0xF0) | value);
        } else {
            value = (byte) ((a[index / 2] & 0x0F) | (value << 4));
        }

        a[index / 2] = value;
    }

    public NBTTag toNBT() {
        return tag;
    }

    public void setBlock(int x, int y, int z, byte type, byte data) {
        int index = y >> 4;
        NBTTag section = getSection(index);
        if (section == null) {
            section = addSection(index);
        }

        int blockIndex = (y % 16) * 256 + z * 16 + x;
        ((byte[]) section.getSubtagByName("Blocks").getValue())[blockIndex] = type;

        boolean lastBits = ((double) x / 2) % 1 == 0 ? true : false;
        byte value = ((byte[]) section.getSubtagByName("Data").getValue())[blockIndex / 2];
        if (lastBits) {
            value = (byte) ((value & 0xF0) | data);
        } else {
            value = (byte) ((value & 0x0F) | (data << 4));
        }
        ((byte[]) section.getSubtagByName("Data").getValue())[blockIndex / 2] = value;

        int[] heightMap = (int[]) tag.getSubtagByName("Level").getSubtagByName("HeightMap").getValue();

        if (heightMap[z * 16 + x] < y + 1)
            heightMap[z * 16 + x] = y + 1;
    }

    /**
     * This method sets the spawner block NBT tags correctly depending on the entity, and then adds it to the rest of the
     * tile entities
     *
     * @param x x index of spawner
     * @param y y index of spawner
     * @param z z index of spawner
     * @param entity entity id of the entity we place into the spawner example: minecraft:spider
     */
    public void setSpawnerContent(int x, int y, int z, String entity, short dangerLevel) {
        NBTTag tileEntities = getAllTileEntities();
        String convertedEntity = convertEntityIntoCorrectForm(entity);
        NBTTag spawnDataEntityTag = createNewEntity(convertedEntity);

        NBTTag tileEntity = getSpawnerTileEntityIfExists(tileEntities, x, y, z);

        if(tileEntity == null){
            NBTTag[] spawnerNBTTags = createNBTTagListForSpawner(dangerLevel, x, y, z, convertedEntity);
            tileEntity = new NBTTag(NBTTag.Type.TAG_Compound, "", spawnerNBTTags);
            tileEntities.addTag(tileEntity);
        }

        updateSpawnerTileEntityWithSpawnData(tileEntity, spawnDataEntityTag);
    }

    private NBTTag getAllTileEntities() {
        return tag.getSubtagByName("Level").getSubtagByName("TileEntities");
    }

    /**
     * This method cuts the "minecraft:" part of an entity's name and then capitalizes it for the NBT tag creation
     *
     * @param entity the entity's name which name's format needs to be corrected
     */
    private String convertEntityIntoCorrectForm(String entity) {
        String cutEntity = entity.replace("minecraft:", "");
        return cutEntity.substring(0, 1).toUpperCase() + cutEntity.substring(1);
    }

    /**
     * This method creates a new entity NBT tag based on the correct NBT format
     *
     * @param convertedEntity the name of the entity which is already in the correct format for the NBT tag creation
     */
    private NBTTag createNewEntity(String convertedEntity) {
        NBTTag entityId = new NBTTag(NBTTag.Type.TAG_String, SpawnerNBTTag.ID.getTagName(), convertedEntity);
        return new NBTTag(NBTTag.Type.TAG_Compound, "entity",
                new NBTTag[]{entityId, new NBTTag(NBTTag.Type.TAG_End, null, null)});
    }

    /**
     * This method iterates through all the tile entities and checks if the spawner already exists, if it does then the
     * method sets the spawner block NBT tags correctly depending on the entity and add it to the rest of the tile
     * entities
     *
     * @param tileEntities the entities that are already in the chunk
     * @param x x index of spawner
     * @param y y index of spawner
     * @param z z index of spawner
     */
    private NBTTag getSpawnerTileEntityIfExists(NBTTag tileEntities, int x, int y, int z) {
        for (NBTTag tileEntity : (NBTTag[]) tileEntities.getValue()) {
            if (isSpawnerFound(tileEntity, x, y, z)) {
                return tileEntity;
            }
        }
        return null;
    }

    private void updateSpawnerTileEntityWithSpawnData(NBTTag tileEntity, NBTTag spawnDataEntityTag) {
        tileEntity.getSubtagByName(SpawnerNBTTag.SPAWN_DATA.getTagName()).setValue(new NBTTag[]{spawnDataEntityTag,
                new NBTTag(NBTTag.Type.TAG_End, null, null)});
    }


    private boolean isSpawnerFound(NBTTag tileEntity, int x, int y, int z) {
        return (int) tileEntity.getSubtagByName(SpawnerNBTTag.X.getTagName()).getValue() == x &&
                (int) tileEntity.getSubtagByName(SpawnerNBTTag.Y.getTagName()).getValue() == y &&
                (int) tileEntity.getSubtagByName(SpawnerNBTTag.Z.getTagName()).getValue() == z &&
                SpawnerNBTTag.ID_VALUE.getTagName().equals(
                        tileEntity.getSubtagByName(SpawnerNBTTag.ID.getTagName()).getValue());
    }

    /**
     * This method puts together the tag list for the spawner based on the previously created individual NBT tags
     *
     * @param dangerLevel the danger level associated with the spawner, can be between 1 and 10
     * @param x the x coordinate of the spawner
     * @param y the y coordinate of the spawner
     * @param z the z coordinate of the spawner
     * @param convertedEntity the name of the entity which is already in the correct format for the NBT tag creation
     */
    private NBTTag[] createNBTTagListForSpawner(short dangerLevel, int x, int y, int z, String convertedEntity) {
        NBTTag xTag = new NBTTag(NBTTag.Type.TAG_Int, SpawnerNBTTag.X.getTagName(), x);
        NBTTag yTag = new NBTTag(NBTTag.Type.TAG_Int, SpawnerNBTTag.Y.getTagName(), y);
        NBTTag zTag = new NBTTag(NBTTag.Type.TAG_Int, SpawnerNBTTag.Z.getTagName(), z);
        NBTTag eIdTag = new NBTTag(NBTTag.Type.TAG_String, SpawnerNBTTag.ENTITY_ID.getTagName(), convertedEntity);
        NBTTag idTag = new NBTTag(NBTTag.Type.TAG_String, SpawnerNBTTag.ID.getTagName(),
                SpawnerNBTTag.ID_VALUE.getTagName());
        NBTTag requiredPlayerRange = new NBTTag(NBTTag.Type.TAG_Short, SpawnerNBTTag.REQUIRED_PLAYER_RANGE.getTagName(),
                (short) 16);
        NBTTag spawnDataTag = new NBTTag(NBTTag.Type.TAG_Compound, SpawnerNBTTag.SPAWN_DATA.getTagName(), new NBTTag[]{
                new NBTTag(NBTTag.Type.TAG_End, null, null)});

        List<NBTTag> nbtTags = new ArrayList<>(Arrays.asList(xTag, yTag, zTag, idTag, eIdTag, requiredPlayerRange, spawnDataTag, new NBTTag(NBTTag.Type.TAG_End, null, null)));

        if (dangerLevel > 0) {
            NBTTag maxNearbyEntitiesTag = new NBTTag(NBTTag.Type.TAG_Short, SpawnerNBTTag.MAX_NEARBY_ENTITIES.getTagName(), (short) Math.min(dangerLevel, 10));
            nbtTags.add(nbtTags.size() - 2, maxNearbyEntitiesTag);
        }

        return nbtTags.toArray(new NBTTag[0]);

    }

    public void setSignText(int x, int y, int z, String text) {

        String[] texts = new String[4];
        for (int i = 0; i < 4; i++) {
            if (text.length() > 15) {
                texts[i] = text.substring(0, 14);
                text = text.substring(14);
            } else if (text.length() > 0) {
                texts[i] = text.substring(0);
                text = "";
            } else {
                texts[i] = "";
            }
        }

        NBTTag tileEntities = tag.getSubtagByName("Level").getSubtagByName("TileEntities");
        for (NBTTag t : (NBTTag[]) tileEntities.getValue()) {
            if (
                    (int) t.getSubtagByName("x").getValue() == x &&
                            (int) t.getSubtagByName("y").getValue() == y &&
                            (int) t.getSubtagByName("z").getValue() == z &&
                            ((String) t.getSubtagByName("id").getValue()).equals("Sign")
            ) {

                for (int i = 0; i < texts.length; i++) {
                    t.getSubtagByName("Text" + (i + 1)).setValue(texts[i]);
                }
                return;
            }
        }

        NBTTag xTag = new NBTTag(NBTTag.Type.TAG_Int, "x", x);
        NBTTag yTag = new NBTTag(NBTTag.Type.TAG_Int, "y", y);
        NBTTag zTag = new NBTTag(NBTTag.Type.TAG_Int, "z", z);
        NBTTag idTag = new NBTTag(NBTTag.Type.TAG_String, "id", "Sign");
        NBTTag text1Tag = new NBTTag(NBTTag.Type.TAG_String, "Text1", texts[0]);
        NBTTag text2Tag = new NBTTag(NBTTag.Type.TAG_String, "Text2", texts[1]);
        NBTTag text3Tag = new NBTTag(NBTTag.Type.TAG_String, "Text3", texts[2]);
        NBTTag text4Tag = new NBTTag(NBTTag.Type.TAG_String, "Text4", texts[3]);
        NBTTag[] tagList = new NBTTag[]{xTag, yTag, zTag, idTag, text1Tag, text2Tag, text3Tag, text4Tag, new NBTTag(NBTTag.Type.TAG_End, null, null)};
        NBTTag tileEntityTag = new NBTTag(NBTTag.Type.TAG_Compound, "", tagList);

        tileEntities.addTag(tileEntityTag);

    }

    public void setBannerColor(int x, int y, int z, int color) {

        NBTTag tileEntities = tag.getSubtagByName("Level").getSubtagByName("TileEntities");
        for (NBTTag t : (NBTTag[]) tileEntities.getValue()) {
            if (
                    (int) t.getSubtagByName("x").getValue() == x &&
                            (int) t.getSubtagByName("y").getValue() == y &&
                            (int) t.getSubtagByName("z").getValue() == z &&
                            ((String) t.getSubtagByName("id").getValue()).equals("Banner")
            ) {
                t.getSubtagByName("Base").setValue(color);
                return;
            }
        }

        NBTTag baseTag = new NBTTag(NBTTag.Type.TAG_Int, "Base", color);
        NBTTag xTag = new NBTTag(NBTTag.Type.TAG_Int, "x", x);
        NBTTag yTag = new NBTTag(NBTTag.Type.TAG_Int, "y", y);
        NBTTag zTag = new NBTTag(NBTTag.Type.TAG_Int, "z", z);
        NBTTag idTag = new NBTTag(NBTTag.Type.TAG_String, "id", "Banner");
        NBTTag[] tagList = new NBTTag[]{baseTag, xTag, yTag, zTag, idTag, new NBTTag(NBTTag.Type.TAG_End, null, null)};
        NBTTag tileEntityTag = new NBTTag(NBTTag.Type.TAG_Compound, "", tagList);

        tileEntities.addTag(tileEntityTag);

    }

    public void removeSignText(int x, int y, int z) {
        removeTileEntity(x, y, z, "Sign");
    }

    public void addChestItem(int x, int y, int z, int id, int quantity) {
        NBTTag tileEntity = getTileEntity(x, y, z, "Chest");

        if (tileEntity == null) {
            NBTTag xTag = new NBTTag(NBTTag.Type.TAG_Int, "x", x);
            NBTTag yTag = new NBTTag(NBTTag.Type.TAG_Int, "y", y);
            NBTTag zTag = new NBTTag(NBTTag.Type.TAG_Int, "z", z);
            NBTTag idTag = new NBTTag(NBTTag.Type.TAG_String, "id", "Chest");
            NBTTag itemsTag = new NBTTag("Items", NBTTag.Type.TAG_Compound);
            NBTTag[] tagList = new NBTTag[]{xTag, yTag, zTag, idTag, itemsTag, new NBTTag(NBTTag.Type.TAG_End, null, null)};
            tileEntity = new NBTTag(NBTTag.Type.TAG_Compound, "", tagList);
            tag.getSubtagByName("Level").getSubtagByName("TileEntities").addTag(tileEntity);
        }

        NBTTag items = tileEntity.getSubtagByName("Items");
        Set<Byte> usedSlots = new HashSet<Byte>();
        for (NBTTag t : (NBTTag[]) items.getValue()) {
            usedSlots.add((byte) t.getSubtagByName("Slot").getValue());
        }

        for (byte i = 0; i < 27; i++) {
            if (!usedSlots.contains(i)) {
                NBTTag idTag = new NBTTag(NBTTag.Type.TAG_Short, "id", (short) id);
                NBTTag slotTag = new NBTTag(NBTTag.Type.TAG_Byte, "Slot", i);
                NBTTag countTag = new NBTTag(NBTTag.Type.TAG_Byte, "Count", (byte) quantity);
                NBTTag damageTag = new NBTTag(NBTTag.Type.TAG_Short, "Damage", (short) 0);
                NBTTag[] tagList = new NBTTag[]{idTag, slotTag, countTag, damageTag, new NBTTag(NBTTag.Type.TAG_End, null, null)};
                items.addTag(new NBTTag(NBTTag.Type.TAG_Compound, "", tagList));
                return;
            }
        }

    }

    public void clearChestItems(int x, int y, int z) {
        removeTileEntity(x, y, z, "Chest");
    }

    private NBTTag getTileEntity(int x, int y, int z, String id) {
        NBTTag tileEntities = tag.getSubtagByName("Level").getSubtagByName("TileEntities");
        for (NBTTag t : (NBTTag[]) tileEntities.getValue()) {
            if (
                    (int) t.getSubtagByName("x").getValue() == x &&
                            (int) t.getSubtagByName("y").getValue() == y &&
                            (int) t.getSubtagByName("z").getValue() == z &&
                            ((String) t.getSubtagByName("id").getValue()).equals(id)
            ) {
                return t;
            }
        }
        return null;
    }

    public void removeTileEntity(int x, int y, int z, String id) {

        Set<Integer> tagsToRemoveIndex = new HashSet<Integer>();
        NBTTag tileEntities = tag.getSubtagByName("Level").getSubtagByName("TileEntities");
        NBTTag[] tileEntitiesArray = (NBTTag[]) tileEntities.getValue();
        for (int i = 0; i < tileEntitiesArray.length; i++) {
            NBTTag t = tileEntitiesArray[i];
            if (
                    (int) t.getSubtagByName("x").getValue() == x &&
                            (int) t.getSubtagByName("y").getValue() == y &&
                            (int) t.getSubtagByName("z").getValue() == z
            ) {
                if (id == null) {
                    tagsToRemoveIndex.add(i);
                } else if (((String) t.getSubtagByName("id").getValue()).equals(id)) {
                    tagsToRemoveIndex.add(i);
                    break;
                }
            }
        }
        for (int i : tagsToRemoveIndex)
            tileEntities.removeTag(i);

    }

    public void clearTileEntitiesAt(int x, int y, int z) {
        removeTileEntity(x, y, z, null);
    }

    private NBTTag getSection(int y) {
        for (NBTTag t : tag.getSubtagByName("Level").getSubtagByName("Sections").getSubtags()) {
            if ((byte) t.getSubtagByName("Y").getValue() == y) {
                return t;
            }
        }
        return null;
    }

    private NBTTag addSection(int y) {

        byte[] blockLight = new byte[2048];
        Arrays.fill(blockLight, (byte) 0);

        byte[] blocks = new byte[4096];
        Arrays.fill(blocks, (byte) 0);

        byte[] data = new byte[2048];
        Arrays.fill(data, (byte) 0);

        byte[] skyLight = new byte[2048];
        Arrays.fill(skyLight, (byte) 255);


        NBTTag yTag = new NBTTag(NBTTag.Type.TAG_Byte, "Y", (byte) y);
        NBTTag blockLightTag = new NBTTag(NBTTag.Type.TAG_Byte_Array, "BlockLight", blockLight);
        NBTTag blocksTag = new NBTTag(NBTTag.Type.TAG_Byte_Array, "Blocks", blocks);
        NBTTag dataTag = new NBTTag(NBTTag.Type.TAG_Byte_Array, "Data", data);
        NBTTag skyLightTag = new NBTTag(NBTTag.Type.TAG_Byte_Array, "SkyLight", skyLight);
        NBTTag[] tagList = new NBTTag[]{dataTag, skyLightTag, blockLightTag, yTag, blocksTag, new NBTTag(NBTTag.Type.TAG_End, null, null)};
        NBTTag sectionTag = new NBTTag(NBTTag.Type.TAG_Compound, "", tagList);
        tag.getSubtagByName("Level").getSubtagByName("Sections").addTag(sectionTag);

        return sectionTag;
    }

    public NBTTag addSection(int y, byte[] blocks, byte[] skyLight) {
        NBTTag section = addSection(y);
        section.getSubtagByName("Blocks").setValue(blocks);
        section.getSubtagByName("SkyLight").setValue(skyLight);
        return section;
    }

    public NBTTag addSectionFilled(int y, byte type, int height) {
        byte[] blocks = new byte[4096];
        byte[] skyLight = new byte[2048];

        for (int _x = 0; _x < 16; _x++) {
            for (int _y = 0; _y < 16; _y++) {
                for (int _z = 0; _z < 16; _z++) {
                    if (_y < height) {
                        blocks[_y * 256 + _z * 16 + _x] = type;
                    } else {
                        blocks[_y * 256 + _z * 16 + _x] = 0;
                    }
                }
            }
        }

        int[] heightMap = (int[]) tag.getSubtagByName("Level").getSubtagByName("HeightMap").getValue();
        for (int i = 0; i < 256; i++) {
            if (heightMap[i] < y * 16 + height)
                heightMap[i] = y * 16 + height;
        }

        Arrays.fill(skyLight, 0, height * 128, (byte) 0);
        Arrays.fill(skyLight, height * 128, 2048, (byte) 255);

        return addSection(y, blocks, skyLight);
    }

    public NBTTag addSectionFilled(int y, byte type) {
        return addSectionFilled(y, type, 16);
    }

    public void fill(int y, byte type) {

        for (int _y = 0; _y < (y >> 4); _y++) {
            addSectionFilled(_y, type);
        }
        addSectionFilled(y >> 4, type, y % 16 + 1);

    }

    public void calculateLighting() {
        byte[] lightingObjects = new byte[]{50, 124};
        NBTTag[] sectionTags = tag.getSubtagByName("Level").getSubtagByName("Sections").getSubtags();
        for (NBTTag section : sectionTags) {
            byte[] skyLight = (byte[]) section.getSubtagByName("SkyLight").getValue();
            byte[] blockLight = (byte[]) section.getSubtagByName("BlockLight").getValue();
            byte[] blocks = (byte[]) section.getSubtagByName("Blocks").getValue();
            //int sectionY = (byte)section.getSubtagByName("Y").getValue();
            for (int blockZ = 0; blockZ < 16; blockZ++)
                for (int y = 0; y < 16; y++)
                    for (int blockX = 0; blockX < 16; blockX++) {
                        //Temporary solution
                        if (blocks[y * 256 + blockZ * 16 + blockX] != 0) {
                            setNibble(skyLight, (y * 256 + blockZ * 16 + blockX), (byte) 0);
                            for (byte b : lightingObjects) {
                                if (blocks[y * 256 + blockZ * 16 + blockX] == b) {
                                    setNibble(blockLight, (y * 256 + blockZ * 16 + blockX), (byte) 15);
                                    setNibble(blockLight, (y * 256 + blockZ * 16 + blockX + 1), (byte) 15);
                                    setNibble(blockLight, (y * 256 + blockZ * 16 + blockX - 1), (byte) 15);
                                    setNibble(blockLight, ((y + 1) * 256 + blockZ * 16 + blockX), (byte) 15);
                                    setNibble(blockLight, ((y - 1) * 256 + blockZ * 16 + blockX), (byte) 15);
                                    setNibble(blockLight, (y * 256 + (blockZ + 1) * 16 + blockX), (byte) 15);
                                    setNibble(blockLight, (y * 256 + (blockZ - 1) * 16 + blockX), (byte) 15);
                                }
                            }
                        }

                    }
            section.getSubtagByName("SkyLight").setValue(skyLight);
            section.getSubtagByName("BlockLight").setValue(blockLight);
        }
    }

    @Override
    public String toString() {
        return tag.toString();
    }

    public enum SpawnerNBTTag {
        X("x"),
        Y("y"),
        Z("z"),
        ENTITY_ID("EntityId"),
        ID("id"),
        REQUIRED_PLAYER_RANGE("RequiredPlayerRange"),
        MAX_NEARBY_ENTITIES("MaxNearbyEntities"),
        SPAWN_DATA("SpawnData"),
        ID_VALUE("MobSpawner");

        private final String tagName;
        SpawnerNBTTag(String tagName) {
            this.tagName = tagName;
        }
        public String getTagName() {
            return this.tagName;
        }
    }

}
