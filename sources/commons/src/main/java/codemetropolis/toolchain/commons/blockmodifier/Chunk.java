package codemetropolis.toolchain.commons.blockmodifier;

import codemetropolis.toolchain.commons.blockmodifier.ext.NBTException;
import codemetropolis.toolchain.commons.blockmodifier.ext.NBTTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;


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

    /**
     * Sets a 4-bit value (a nibble) at the specified index in the given byte array.
     *
     * <p>This method is used to manipulate individual nibbles within a byte array. It calculates
     * the appropriate byte index based on the given index and sets the nibble value accordingly.
     * If the index is out of bounds of the array or negative, no operation is performed.</p>
     *
     * @param a the byte array in which to set the nibble
     * @param index the index at which to set the nibble (0-based)
     * @param value the 4-bit value to set (0 to 15)
     */
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

    /**
     * Sets a block of the specified type at the given coordinates in the world.
     *
     * <p>This method sets a block of the specified type at the specified (x, y, z) coordinates
     * in the world. It divides the world into sections (16-block tall segments) and updates the
     * corresponding section to reflect the new block. Additionally, if there is associated data
     * with the block, it updates the data value accordingly.</p>
     *
     * @param x the x-coordinate of the block in the world
     * @param y the y-coordinate of the block in the world
     * @param z the z-coordinate of the block in the world
     * @param type the type of the block to set
     * @param data additional data for the block as a map of string keys and values
     */
    public void setBlock(int x, int y, int z, byte type, List<Integer> data) {
        int index = y >> 4;
        NBTTag section = getOrCreateSection(index);

        int blockIndex = getBlockIndex(x, y, z);
        setType(section, blockIndex, type);

        if (!data.isEmpty()) {
            byte value = getDataValue(section, blockIndex / 2);
            for (int dataValue : data) {
                if (dataValue >= 0)
                    value = updateDataValue(value, x, dataValue);
            }
            setDataValue(section, blockIndex / 2, value);
        }
        updateHeightMap(z, x, y);
    }

    /**
     * Retrieves or creates a section (16-block tall segment) at the specified index.
     *
     * <p>This method retrieves the section at the specified index if it exists, or creates
     * a new section if it doesn't. Sections are used to organize blocks in the world, with
     * each section representing a 16-block tall segment of the world's height.</p>
     *
     * @param index the index of the section to retrieve or create
     * @return the section at the specified index, either retrieved or newly created
     */
    private NBTTag getOrCreateSection(int index) {
        NBTTag section = getSection(index);
        if (section == null) {
            section = addSection(index);
        }
        return section;
    }

    private int getBlockIndex(int x, int y, int z) {
        return (y % 16) * 256 + z * 16 + x;
    }

    private void setType(NBTTag section, int blockIndex, byte type) {
        ((byte[]) section.getSubtagByName("Blocks").getValue())[blockIndex] = type;
    }

    private byte getDataValue(NBTTag section, int blockIndex) {
        return ((byte[]) section.getSubtagByName("Data").getValue())[blockIndex];
    }

    /**
     * Updates the data value of a block based on additional data and x-coordinate.
     *
     * <p>This method updates the data value of a block byte based on additional data provided
     * as a map of string keys and values. It also considers the x-coordinate to determine whether
     * to modify the upper or lower 4 bits of the byte. If no suitable numeric value is found
     * in the additional data, the original value is returned.</p>
     *
     * @param value the current data value of the block
     * @param x the x-coordinate of the block in the world
     * @param data additional data for the block as a map of string keys and values
     * @return the updated data value of the block byte
     */
    private byte updateDataValue(byte value, int x, int data) {
        boolean lastBits = ((double) x / 2) % 1 == 0;
        if (lastBits) {
            return (byte) ((value & 0xF0) | data);
        } else {
            return (byte) ((value & 0x0F) | (data << 4));
        }
    }

    private void setDataValue(NBTTag section, int blockIndex, byte value) {
        ((byte[]) section.getSubtagByName("Data").getValue())[blockIndex] = value;
    }

    /**
     * Updates the height map with the height of a block at the given coordinates.
     *
     * <p>This method updates the height map to reflect the height of a block at the specified
     * (x, y, z) coordinates in the world. It retrieves the height map array from the level data
     * and updates the corresponding entry based on the provided x, y, and z coordinates.</p>
     *
     * @param z the z-coordinate of the block in the world
     * @param x the x-coordinate of the block in the world
     * @param y the y-coordinate of the block in the world
     */
    private void updateHeightMap(int z, int x, int y) {
        int[] heightMap = (int[]) tag.getSubtagByName("Level").getSubtagByName("HeightMap").getValue();
        int heightMapIndex = z * 16 + x;
        if (heightMap[heightMapIndex] < y + 1) {
            heightMap[heightMapIndex] = y + 1;
        }
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

    /**
     * Sets the text on a sign block at the specified coordinates.
     *
     * <p>This method sets the text on a sign block at the provided (x, y, z) coordinates in the world.
     * It divides the input text into up to four lines of maximum length 15 characters each, ensuring
     * that the text fits within the sign's capacity. If the sign block already exists at the specified
     * coordinates, it updates the text. If not, it creates a new sign block with the provided text.</p>
     *
     * @param x the x-coordinate of the sign block in the world
     * @param y the y-coordinate of the sign block in the world
     * @param z the z-coordinate of the sign block in the world
     * @param text the text to set on the sign block
     */
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

    /**
     * Sets the color of a banner block at the specified coordinates.
     *
     * <p>This method sets the color of a banner block at the provided (x, y, z) coordinates in the world.
     * If the banner block already exists at the specified coordinates, it updates the color. If not, it
     * creates a new banner block with the specified color.</p>
     *
     * @param x the x-coordinate of the banner block in the world
     * @param y the y-coordinate of the banner block in the world
     * @param z the z-coordinate of the banner block in the world
     * @param color the color code representing the color of the banner
     */
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

    /**
     * Adds an item to a chest block at the specified coordinates.
     *
     * <p>This method adds an item to a chest block at the provided (x, y, z) coordinates in the world.
     * If a chest block doesn't exist at the specified coordinates, it creates a new one and adds the
     * item to it. It ensures that the item is added to an available slot within the chest, considering
     * existing items and their slots.</p>
     *
     * @param x the x-coordinate of the chest block in the world
     * @param y the y-coordinate of the chest block in the world
     * @param z the z-coordinate of the chest block in the world
     * @param id the ID of the item to add
     * @param quantity the quantity of the item to add
     */
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

    /**
     * Retrieves the tile entity at the specified coordinates and with the specified ID.
     *
     * <p>This method retrieves the tile entity (such as a chest or a sign) at the provided (x, y, z)
     * coordinates in the world, matching the given ID. If a tile entity exists at the specified coordinates
     * with the specified ID, it is returned. If not, null is returned.</p>
     *
     * @param x the x-coordinate of the tile entity in the world
     * @param y the y-coordinate of the tile entity in the world
     * @param z the z-coordinate of the tile entity in the world
     * @param id the ID of the tile entity to retrieve
     * @return the tile entity at the specified coordinates and with the specified ID, or null if not found
     */
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

    /**
     * Removes a tile entity at the specified coordinates and with the specified ID.
     *
     * <p>This method removes a tile entity (such as a chest or a sign) at the provided (x, y, z)
     * coordinates in the world, matching the given ID. If no ID is provided, it removes all tile
     * entities at the specified coordinates. If a matching tile entity is found, it is removed.</p>
     *
     * @param x the x-coordinate of the tile entity in the world
     * @param y the y-coordinate of the tile entity in the world
     * @param z the z-coordinate of the tile entity in the world
     * @param id the ID of the tile entity to remove, or null to remove all tile entities at the specified coordinates
     */
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

    /**
     * Adds a new section to the world data for a specified y-coordinate.
     *
     * <p>This method adds a new section to the world data for the specified y-coordinate. It initializes
     * the block light, blocks, data, and sky light arrays with default values. The section is then added
     * to the list of sections in the world data.</p>
     *
     * @param y the y-coordinate of the section in the world
     * @return the newly created section as a NBTTag
     */
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

    /**
     * Adds a new section filled with a specified block type up to a certain height.
     *
     * <p>This method adds a new section to the world data for the specified y-coordinate, filling it with
     * the specified block type up to the given height. Blocks above the specified height are set to air (ID 0).
     * The sky light levels are adjusted accordingly based on the specified height. The height map is updated
     * to reflect the highest block level within the section.</p>
     *
     * @param y the y-coordinate of the section in the world
     * @param type the block type to fill the section with
     * @param height the height up to which the section should be filled with the specified block type
     * @return the newly created section as a NBTTag
     */
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

    /**
     * Calculates lighting for all sections in the world data.
     *
     * <p>This method calculates lighting for all sections in the world data. It iterates through each
     * section and updates the sky light and block light levels based on the presence of lighting objects
     * such as torches and glowstones. Sky light levels are set to 0 if a block is present, while block
     * light levels are set to maximum (15) around lighting objects.</p>
     */
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
