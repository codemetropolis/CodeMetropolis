package codemetropolis.toolchain.rendering.control;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;
public class Chunk {

	public NBTTag tag;

    private Chunk(NBTTag tag) {
        if (tag.getType() != NBTTag.Type.TAG_Compound) {
            throw new IllegalArgumentException("Chunk tag must be compound.");
        }
        this.tag = tag;
    }

	public Chunk(int x, int z) {

		byte terrainPopulated = 1;
		long inhabitedTime = 0L;
		long lastUpdate = 0L;

		int[] heightMap = new int[256];
		Arrays.fill(heightMap, 0);

		NBTTag terrainPopulatedTag = new NBTTag(NBTTag.Type.TAG_Byte, "TerrainPopulated", terrainPopulated);
		NBTTag xPosTag = new NBTTag(NBTTag.Type.TAG_Int, "xPos", x);
		NBTTag zPosTag = new NBTTag(NBTTag.Type.TAG_Int, "zPos", z);
		NBTTag statusTag = new NBTTag(NBTTag.Type.TAG_String, "Status", "empty");
		NBTTag inhabitedTimeTag = new NBTTag(NBTTag.Type.TAG_Long, "InhabitedTime", inhabitedTime);
		NBTTag lastUpdateTag = new NBTTag(NBTTag.Type.TAG_Long, "LastUpdate", lastUpdate);
		NBTTag entitiesTag = new NBTTag(NBTTag.Type.TAG_List, "Entities", NBTTag.Type.TAG_Byte);
		NBTTag tileEntitiesTag = new NBTTag(NBTTag.Type.TAG_List, "TileEntities", NBTTag.Type.TAG_Byte);
		NBTTag heightMapTag = new NBTTag(NBTTag.Type.TAG_Int_Array, "HeightMap", heightMap);
		NBTTag sectionsTag = new NBTTag("Sections", NBTTag.Type.TAG_List);
		NBTTag tileTicksTag = new NBTTag("TileTicks", NBTTag.Type.TAG_List);

		NBTTag[] tagList = new NBTTag[] { xPosTag, terrainPopulatedTag, zPosTag, inhabitedTimeTag, statusTag,
				lastUpdateTag, sectionsTag, entitiesTag, tileEntitiesTag, heightMapTag, tileTicksTag, NBTTag.END_TAG };
		NBTTag levelTag = new NBTTag(NBTTag.Type.TAG_Compound, "Level", tagList);
		// NBTTag dataVersion = new NBTTag(NBTTag.Type.TAG_Int, "DataVersion", 1631);
		this.tag = new NBTTag(NBTTag.Type.TAG_Compound, "",
				new NBTTag[] { levelTag/* , dataVersion */, NBTTag.END_TAG });

	}

	public static Chunk parseNBT(NBTTag t) {
		return new Chunk(t);
	}

	public NBTTag toNBT() {
		return tag;
	}

	public void setBiome(int biomeId){
		int[] biomes = new int[256];
		Arrays.fill(biomes, biomeId);
		//NBTTag biomeTag = new NBTTag(NBTTag.Type.TAG_String, "biome", biomeId);
		NBTTag biomeTag = new NBTTag(NBTTag.Type.TAG_Int_Array, "Biomes", biomes);
		tag.getSubtagByName("Level").addTag(biomeTag);
	}

	public void setBlock(int x, int y, int z, String blockId, Map<String, String> blockProperties) {
		setBlock(x, y, z, blockId, PropertiesToTags(blockProperties));
	}
	public void setBlock(int x, int y, int z, String blockId, NBTTag[] blockProperties) {
		int index = y >> 4;
		NBTTag section = getSection(index);
		if (section == null) {
			section = addSection(index);
		}

		int blockIndex = (y % 16) * 256 + z * 16 + x;

		long[] blockStates = (long[]) section.getSubtagByName("BlockStates").getValue();
		NBTTag palette = section.getSubtagByName("Palette");

		long paletteIndex = getIndexFromPalette(palette, blockId, blockProperties);
		int numberOfBitsUsedToRepresentPalette;

		int cX = (int) tag.getSubtagByName("Level").getSubtagByName("xPos").getValue();
		int cZ = (int) tag.getSubtagByName("Level").getSubtagByName("zPos").getValue();

		if (paletteIndex == -1) {
			int oldNumberOfBitsUsedToRepresentPalette = getNumberOfBitsUsedToRepresentPalette(palette);

			paletteIndex = insertIntoPalette(palette, blockId, blockProperties);

			numberOfBitsUsedToRepresentPalette = getNumberOfBitsUsedToRepresentPalette(palette);
			if (oldNumberOfBitsUsedToRepresentPalette != numberOfBitsUsedToRepresentPalette) {

				//int cY = y/16;
				//System.out.println("ChangeBlockStateBitRepresentation("+x+" "+y%16+" "+z+" in "+cX+" "+cY+" "+cZ+" ["+(cX*16+x)+" "+y+" "+(cZ*16+z)+"], bId:"+blockId+", from:"+oldNumberOfBitsUsedToRepresentPalette+", to:"+numberOfBitsUsedToRepresentPalette+")");

				blockStates = changeBlockStateBitRepresentation(blockStates, 4096,
						oldNumberOfBitsUsedToRepresentPalette, numberOfBitsUsedToRepresentPalette);
				section.getSubtagByName("BlockStates").setValue(blockStates);
			}
		} else {
			numberOfBitsUsedToRepresentPalette = getNumberOfBitsUsedToRepresentPalette(palette);
		}

		setBlockState(blockStates, blockIndex, paletteIndex, numberOfBitsUsedToRepresentPalette);

		int[] heightMap = (int[]) tag.getSubtagByName("Level").getSubtagByName("HeightMap").getValue();

		if (heightMap[z * 16 + x] < y + 1)
			heightMap[z * 16 + x] = y + 1;

		if (blockId.equals("minecraft:repeating_command_block")) {
			NBTTag blockIdTag = new NBTTag(NBTTag.Type.TAG_String, "i", blockId);
			NBTTag delayTag = new NBTTag(NBTTag.Type.TAG_Int, "t", new Random().nextInt(10));
			NBTTag priorityTag = new NBTTag(NBTTag.Type.TAG_Int, "p", 0);
			NBTTag posXTag = new NBTTag(NBTTag.Type.TAG_Int, "x", cX * 16 + x);
			NBTTag posYTag = new NBTTag(NBTTag.Type.TAG_Int, "y", y);
			NBTTag posZTag = new NBTTag(NBTTag.Type.TAG_Int, "z", cZ * 16 + z);
			NBTTag tileTickTag = new NBTTag(NBTTag.Type.TAG_Compound, "",
					new NBTTag[] { blockIdTag, delayTag, priorityTag, posXTag, posYTag, posZTag, NBTTag.END_TAG });

			tag.getSubtagByName("Level").getSubtagByName("TileTicks").addTag(tileTickTag);
		}
	}

	public String getBlockId(int x, int y, int z) {
		int index = y >> 4;
		NBTTag section = getSection(index);
		if (section == null) {
			section = addSection(index);
		}

		long[] blockStates = (long[]) section.getSubtagByName("BlockStates").getValue();
		NBTTag palette = section.getSubtagByName("Palette");
		int numberOfBitsUsedToRepresentPalette = getNumberOfBitsUsedToRepresentPalette(palette);
		int blockIndex = (y % 16) * 256 + z * 16 + x;

		return (String) palette
				.getSubtags()[(int) getBlockState(blockStates, blockIndex, numberOfBitsUsedToRepresentPalette)]
						.getSubtagByName("Name").getValue();
	}

	public static long getIndexFromPalette(NBTTag palette, String blockId, NBTTag[] blockProperties) {
		NBTTag[] subtags = palette.getSubtags();

		tagsLoop: for (int i = 0; i < subtags.length; i++) {
			NBTTag t = subtags[i];

			if (t.getSubtagByName("Name").getValue().equals(blockId)) {
				NBTTag propertiesTag = t.getSubtagByName("Properties");

				if( propertiesTag == null || propertiesTag.getValue() == null || ((NBTTag[]) propertiesTag.getValue()).length == 0 ) {
					if( blockProperties == null || blockProperties.length == 0 ) {
						return i;
					}else {
						return -1;
					}
				}
				
				NBTTag[] properties = (NBTTag[]) propertiesTag.getValue();

				if (blockProperties.length != properties.length) {
					continue;
				}

				expectedLoop: for (NBTTag expected : blockProperties) {
					for (NBTTag given : properties) {
						String expectedName = expected.getName();
						String givenName = given.getName();
						if (expectedName == null || givenName == null) {
							if (expectedName != givenName) {
								continue;
							} else {
								continue expectedLoop;
							}

						}
						if (!expectedName.equals(givenName)) {
							continue;
						}

						String expectedValue = (String) expected.getValue();
						String givenValue = (String) given.getValue();

						if ((expectedValue == null || givenValue == null) && expectedValue != givenValue) {
							continue;
						}
						if (!expectedValue.equals(givenValue)) {
							continue;
						}

						continue expectedLoop;
					}
					continue tagsLoop;
				}
				return i;
			}
		}
		return -1;
	}

	public static long insertIntoPalette(NBTTag palette, String blockId, NBTTag[] blockProperties) {
		if (0 < blockProperties.length
				&& blockProperties[blockProperties.length - 1].getType() != NBTTag.Type.TAG_End) {
			throw new IllegalArgumentException("BlockProperties must be empty or have a TAG_End type item at the end.");
		}

		NBTTag blockNameTag = new NBTTag(NBTTag.Type.TAG_String, "Name", blockId);
		NBTTag[] blockStateTagList;
		if (blockProperties.length > 1) {
			NBTTag blockPropertiesTag = new NBTTag(NBTTag.Type.TAG_Compound, "Properties", blockProperties);
			blockStateTagList = new NBTTag[] { blockNameTag, blockPropertiesTag, NBTTag.END_TAG };
		} else {
			blockStateTagList = new NBTTag[] { blockNameTag, NBTTag.END_TAG };
		}
		NBTTag blockStateTag = new NBTTag(NBTTag.Type.TAG_Compound, "", blockStateTagList);

		palette.addTag(blockStateTag);
		return palette.getSubtags().length - 1;
	}

	public static int getNumberOfBitsUsedToRepresentPalette(NBTTag palette) {
		int elements = palette.getSubtags().length;
		double log2 = Math.log(elements) / Math.log(2);
		int bitsNeeded = (int) Math.ceil(log2);
		return Math.max(4, bitsNeeded);
	}

	public static void setBlockState(long[] blockStates, int index, long value, int numberOfUsedBits) {
		if (numberOfUsedBits < 4) {
			throw new IllegalArgumentException("Number of used bits must not be less than 4");
		}

		int startBit = index * numberOfUsedBits;
		int startELement = startBit / 64;
		int startBitOfElement = startBit - (startELement * 64);

		int elementIndex = startELement;
		int bitIndex = startBitOfElement;
		int bitsLeft = numberOfUsedBits;

		while (true) {
			long mask = createMask(bitIndex, bitsLeft);

			long toBeInserted = value << bitIndex;
			blockStates[elementIndex] = (blockStates[elementIndex] & mask) | toBeInserted;

			int insertedBits = 64 - bitIndex;
			if (insertedBits < bitsLeft) {
				elementIndex++;
				bitIndex = 0;
				bitsLeft -= insertedBits;
				value >>>= insertedBits;
			} else {
				break;
			}
		}
	}

	public static long createMask(int start, int length) {
		long mask = 0;

		if (start > 0) {
			mask |= 0xFFFFFFFFFFFFFFFFL >>> (64 - start);
		}

		if (start + length < 64) {
			mask |= 0xFFFFFFFFFFFFFFFFL << (start + length);
		}

		return mask;
	}
	
	public static long createInverseMask(int start, int length) {
		return ~createMask(start, length);
	}

	public static long getBlockState(long[] blockStates, int index, int numberOfUsedBits) {
		if (numberOfUsedBits < 4) {
			throw new IllegalArgumentException("Number of used bits must not be less than 4");
		}

		long value = 0;

		int startBit = index * numberOfUsedBits;
		int startELement = startBit / 64;
		int startBitOfElement = startBit - (startELement * 64);

		int elementIndex = startELement;
		int bitIndex = startBitOfElement;
		int bitsLeft = numberOfUsedBits;

		while (true) {
			long mask = createInverseMask(bitIndex, bitsLeft);
			long masked = blockStates[elementIndex] & mask;
			long maskedAligned = masked >>> bitIndex;
			value |= maskedAligned << (numberOfUsedBits - bitsLeft);

			int readBits = 64 - bitIndex;
			if (readBits < bitsLeft) {
				elementIndex++;
				bitIndex = 0;
				bitsLeft -= readBits;
			} else {
				break;
			}
		}

		return value;
	}

	public static long[] changeBlockStateBitRepresentation(long[] oldBlockStates, int numberOfBlocks, int oldUsedBits,
			int newUsedBits) {
		int newBlockStatesSize = (int)Math.ceil((numberOfBlocks * newUsedBits) / 64f);
		long[] newBlockStates = new long[newBlockStatesSize];
		for (int i = 0; i < numberOfBlocks; i++) {
			long value = getBlockState(oldBlockStates, i, oldUsedBits);
			setBlockState(newBlockStates, i, value, newUsedBits);
		}
		return newBlockStates;
	}

	private String toTextJson(String text) {
		return String.format("{\"text\":\"%s\"}", text);
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

			texts[i] = toTextJson(texts[i]);
		}

		NBTTag tileEntities = tag.getSubtagByName("Level").getSubtagByName("TileEntities");
		for (NBTTag t : (NBTTag[]) tileEntities.getValue()) {
			if ((int) t.getSubtagByName("x").getValue() == x && (int) t.getSubtagByName("y").getValue() == y
					&& (int) t.getSubtagByName("z").getValue() == z
					&& ((String) t.getSubtagByName("id").getValue()).equals("minecraft:sign")) {

				for (int i = 0; i < texts.length; i++) {
					t.getSubtagByName("Text" + (i + 1)).setValue(texts[i]);
				}
				return;
			}
		}

		NBTTag xTag = new NBTTag(NBTTag.Type.TAG_Int, "x", x);
		NBTTag yTag = new NBTTag(NBTTag.Type.TAG_Int, "y", y);
		NBTTag zTag = new NBTTag(NBTTag.Type.TAG_Int, "z", z);
		NBTTag idTag = new NBTTag(NBTTag.Type.TAG_String, "id", "minecraft:sign");
		NBTTag text1Tag = new NBTTag(NBTTag.Type.TAG_String, "Text1", texts[0]);
		NBTTag text2Tag = new NBTTag(NBTTag.Type.TAG_String, "Text2", texts[1]);
		NBTTag text3Tag = new NBTTag(NBTTag.Type.TAG_String, "Text3", texts[2]);
		NBTTag text4Tag = new NBTTag(NBTTag.Type.TAG_String, "Text4", texts[3]);
		NBTTag[] tagList = new NBTTag[] { xTag, yTag, zTag, idTag, text1Tag, text2Tag, text3Tag, text4Tag,
				NBTTag.END_TAG };
		NBTTag tileEntityTag = new NBTTag(NBTTag.Type.TAG_Compound, "", tagList);

		tileEntities.addTag(tileEntityTag);

	}

	public void setSpawner(int x, int y, int z) {
		NBTTag tileEntities = tag.getSubtagByName("Level").getSubtagByName("TileEntities");
		
			NBTTag xTag = new NBTTag(NBTTag.Type.TAG_Int, "x", x);
			NBTTag yTag = new NBTTag(NBTTag.Type.TAG_Int, "y", y);
			NBTTag zTag = new NBTTag(NBTTag.Type.TAG_Int, "z", z);
			NBTTag idTag = new NBTTag(NBTTag.Type.TAG_String, "id", "minecraft:mob_spawner");

			NBTTag spawnerTag = new NBTTag(NBTTag.Type.TAG_String, "id", "minecraft:villager");
			NBTTag[] tagList2 = new NBTTag[] { spawnerTag, NBTTag.END_TAG };
			NBTTag tileEntityTag2 = new NBTTag(NBTTag.Type.TAG_Compound, "SpawnData", tagList2);

			NBTTag[] tagList = new NBTTag[] { xTag, yTag, zTag, idTag, tileEntityTag2, NBTTag.END_TAG };
			NBTTag tileEntityTag = new NBTTag(NBTTag.Type.TAG_Compound, "", tagList);
			tileEntities.addTag(tileEntityTag);

	}

	public void setMob(double  x, double y, double z, String name) {

		NBTTag entities = tag.getSubtagByName("Level").getSubtagByName("Entities");

		NBTTag xTag = new NBTTag(NBTTag.Type.TAG_Double, "x", x);
		NBTTag yTag = new NBTTag(NBTTag.Type.TAG_Double, "y", y);
		NBTTag zTag = new NBTTag(NBTTag.Type.TAG_Double, "z", z);
        NBTTag pos = new NBTTag(NBTTag.Type.TAG_List, "Pos", new NBTTag[]{xTag, yTag, zTag});
//modositas
		NBTTag idTag = new NBTTag(NBTTag.Type.TAG_String, "id", name);
		NBTTag noAiTag = new NBTTag(NBTTag.Type.TAG_Byte, "NoAI", (byte)0);
		NBTTag[] tagList = new NBTTag[] {pos, idTag, noAiTag, NBTTag.END_TAG};
		NBTTag entityTag = new NBTTag(NBTTag.Type.TAG_Compound, "", tagList);

		entities.addTag(entityTag);

	}

	public void setCommandBlockCommand(int x, int y, int z, String command) {

		NBTTag tileEntities = tag.getSubtagByName("Level").getSubtagByName("TileEntities");
		for (NBTTag t : (NBTTag[]) tileEntities.getValue()) {
			if ((int) t.getSubtagByName("x").getValue() == x && (int) t.getSubtagByName("y").getValue() == y
					&& (int) t.getSubtagByName("z").getValue() == z
					&& ((String) t.getSubtagByName("id").getValue()).equals("minecraft:command_block")) {

				t.getSubtagByName("Command").setValue(command);
				return;
			}
		}

		NBTTag xTag = new NBTTag(NBTTag.Type.TAG_Int, "x", x);
		NBTTag yTag = new NBTTag(NBTTag.Type.TAG_Int, "y", y);
		NBTTag zTag = new NBTTag(NBTTag.Type.TAG_Int, "z", z);
		NBTTag idTag = new NBTTag(NBTTag.Type.TAG_String, "id", "minecraft:command_block");
		NBTTag commandTag = new NBTTag(NBTTag.Type.TAG_String, "Command", command);
		NBTTag lastOutputTag = new NBTTag(NBTTag.Type.TAG_String, "LastOutput", "");
		NBTTag autoTag = new NBTTag(NBTTag.Type.TAG_Byte, "auto", (byte) 1);
		NBTTag conditionMetTag = new NBTTag(NBTTag.Type.TAG_Byte, "conditionMet", (byte) 1);
		NBTTag keepPackedTag = new NBTTag(NBTTag.Type.TAG_Byte, "keepPacked", (byte) 0);
		NBTTag poweredTag = new NBTTag(NBTTag.Type.TAG_Byte, "powered", (byte) 1);
		NBTTag successCountTag = new NBTTag(NBTTag.Type.TAG_Int, "SuccessCount", 0);
		NBTTag trackOutputTag = new NBTTag(NBTTag.Type.TAG_Byte, "TrackOutput", (byte) 1);
		NBTTag updateLastExecutionTag = new NBTTag(NBTTag.Type.TAG_Byte, "UpdateLastExecution", (byte) 1);
		NBTTag lastExecutionTag = new NBTTag(NBTTag.Type.TAG_Long, "LastExecution", (long) 0);
		NBTTag customNameTag = new NBTTag(NBTTag.Type.TAG_String, "CustomName", "{\"text\":\"@\"}");

		NBTTag[] tagList = new NBTTag[] { xTag, yTag, zTag, idTag, commandTag, lastOutputTag, autoTag, customNameTag,
				conditionMetTag, lastExecutionTag, keepPackedTag, poweredTag, successCountTag, trackOutputTag,
				updateLastExecutionTag, NBTTag.END_TAG };
		NBTTag tileEntityTag = new NBTTag(NBTTag.Type.TAG_Compound, "", tagList);

		tileEntities.addTag(tileEntityTag);

	}

	public void setBannerColor(int x, int y, int z, int color) {

		NBTTag tileEntities = tag.getSubtagByName("Level").getSubtagByName("TileEntities");
		for (NBTTag t : (NBTTag[]) tileEntities.getValue()) {
			if ((int) t.getSubtagByName("x").getValue() == x && (int) t.getSubtagByName("y").getValue() == y
					&& (int) t.getSubtagByName("z").getValue() == z
					&& ((String) t.getSubtagByName("id").getValue()).equals("Banner")) {
				t.getSubtagByName("Base").setValue(color);
				return;
			}
		}

		NBTTag baseTag = new NBTTag(NBTTag.Type.TAG_Int, "Base", color);
		NBTTag xTag = new NBTTag(NBTTag.Type.TAG_Int, "x", x);
		NBTTag yTag = new NBTTag(NBTTag.Type.TAG_Int, "y", y);
		NBTTag zTag = new NBTTag(NBTTag.Type.TAG_Int, "z", z);
		NBTTag idTag = new NBTTag(NBTTag.Type.TAG_String, "id", "Banner");
		NBTTag[] tagList = new NBTTag[] { baseTag, xTag, yTag, zTag, idTag, NBTTag.END_TAG };
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
			NBTTag[] tagList = new NBTTag[] { xTag, yTag, zTag, idTag, itemsTag, NBTTag.END_TAG };
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
				NBTTag[] tagList = new NBTTag[] { idTag, slotTag, countTag, damageTag, NBTTag.END_TAG };
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
			if ((int) t.getSubtagByName("x").getValue() == x && (int) t.getSubtagByName("y").getValue() == y
					&& (int) t.getSubtagByName("z").getValue() == z
					&& ((String) t.getSubtagByName("id").getValue()).equals(id)) {
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
			if ((int) t.getSubtagByName("x").getValue() == x && (int) t.getSubtagByName("y").getValue() == y
					&& (int) t.getSubtagByName("z").getValue() == z) {
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

		long[] blockStates = new long[256];
		Arrays.fill(blockStates, (long) 0);

		byte[] skyLight = new byte[2048];
		Arrays.fill(skyLight, (byte) 255);

		NBTTag yTag = new NBTTag(NBTTag.Type.TAG_Byte, "Y", (byte) y);
		NBTTag paletteTag = new NBTTag("Palette", NBTTag.Type.TAG_List);
		insertIntoPalette(paletteTag, "minecraft:air", new NBTTag[0]);
		NBTTag blockLightTag = new NBTTag(NBTTag.Type.TAG_Byte_Array, "BlockLight", blockLight);
		NBTTag blockStatesTag = new NBTTag(NBTTag.Type.TAG_Long_Array, "BlockStates", blockStates);
		NBTTag skyLightTag = new NBTTag(NBTTag.Type.TAG_Byte_Array, "SkyLight", skyLight);
		NBTTag[] tagList = new NBTTag[] { yTag, paletteTag, skyLightTag, blockStatesTag, blockLightTag,
				NBTTag.END_TAG };
		NBTTag sectionTag = new NBTTag(NBTTag.Type.TAG_Compound, "", tagList);
		tag.getSubtagByName("Level").getSubtagByName("Sections").addTag(sectionTag);

		return sectionTag;
	}

	public NBTTag addSection(int y, NBTTag palette, long[] blockStates, byte[] skyLight) {
		NBTTag section = addSection(y);

		section.removeSubtag(section.getSubtagByName("Palette")); // need to replace, cuz setvalue didn't wanna work
		section.addTag(palette);

		section.getSubtagByName("BlockStates").setValue(blockStates);
		section.getSubtagByName("SkyLight").setValue(skyLight);
		return section;
	}

	public NBTTag addSectionFilled(int y, String blockId, NBTTag[] blockProperties, int height) {

		NBTTag palette = new NBTTag("Palette", NBTTag.Type.TAG_List);
		long airIndex = 0;
		long blockIndex = 0;

		if (height < 16) {
			insertIntoPalette(palette, "minecraft:air", new NBTTag[0]);
			blockIndex++;
		}
		insertIntoPalette(palette, blockId, blockProperties);

		long[] blockStates = new long[256];
		byte[] skyLight = new byte[2048];

		for (int _x = 0; _x < 16; _x++) {
			for (int _y = 0; _y < 16; _y++) {
				for (int _z = 0; _z < 16; _z++) {
					if (_y < height) {
						setBlockState(blockStates, _y * 256 + _z * 16 + _x, blockIndex, 4);
					} else {
						setBlockState(blockStates, _y * 256 + _z * 16 + _x, airIndex, 4);
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

		return addSection(y, palette, blockStates, skyLight);
	}

	public NBTTag addSectionFilled(int y, String blockId, NBTTag[] blockProperties) {
		return addSectionFilled(y, blockId, blockProperties, 16);
	}

	public void fill(int y, String blockId, NBTTag[] blockProperties) {

		for (int _y = 0; _y < (y >> 4); _y++) {
			addSectionFilled(_y, blockId, blockProperties);
		}
		addSectionFilled(y >> 4, blockId, blockProperties, y % 16 + 1);

	}

	public void calculateLighting() {
		Set<String> lightingObjects = new HashSet<>();
		lightingObjects.add("minecraft:redstone_lamp");
		lightingObjects.add("minecraft:wall_torch");

		NBTTag[] sectionTags = tag.getSubtagByName("Level").getSubtagByName("Sections").getSubtags();
		for (NBTTag section : sectionTags) {
			byte[] skyLight = (byte[]) section.getSubtagByName("SkyLight").getValue();
			byte[] blockLight = (byte[]) section.getSubtagByName("BlockLight").getValue();
			long[] blockStates = (long[]) section.getSubtagByName("BlockStates").getValue();
			NBTTag palette = section.getSubtagByName("Palette");
			int paletteBits = getNumberOfBitsUsedToRepresentPalette(palette);
			for (int blockZ = 0; blockZ < 16; blockZ++)
				for (int y = 0; y < 16; y++)
					for (int blockX = 0; blockX < 16; blockX++) {
						// Temporary solution
						int paletteId = (int) getBlockState(blockStates, y * 256 + blockZ * 16 + blockX, paletteBits);
						String blockId = (String) palette.getSubtags()[paletteId].getSubtagByName("Name").getValue();

						if (!blockId.equals("minecraft:air")) {
							setNibble(skyLight, (y * 256 + blockZ * 16 + blockX), (byte) 15);
							if (lightingObjects.contains(blockId)) {
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
			section.getSubtagByName("SkyLight").setValue(skyLight);
			section.getSubtagByName("BlockLight").setValue(blockLight);
		}
	}

	public void filterMagicalBlocks() {
		NBTTag[] sectionTags = tag.getSubtagByName("Level").getSubtagByName("Sections").getSubtags();
		int chunkX = (int) tag.getSubtagByName("Level").getSubtagByName("xPos").getValue();
		int chunkZ = (int) tag.getSubtagByName("Level").getSubtagByName("zPos").getValue();

		for (NBTTag section : sectionTags) {
			int sectionY = (byte) section.getSubtagByName("Y").getValue();

			for (int blockZ = 0; blockZ < 16; blockZ++) {
				for (int y = 0; y < 16; y++) {
					for (int blockX = 0; blockX < 16; blockX++) {

						long[] blockStates = (long[]) section.getSubtagByName("BlockStates").getValue();
						NBTTag palette = section.getSubtagByName("Palette");
						int paletteBits = getNumberOfBitsUsedToRepresentPalette(palette);

						int paletteId = (int) getBlockState(blockStates, y * 256 + blockZ * 16 + blockX, paletteBits);
						String blockId = (String) palette.getSubtags()[paletteId].getSubtagByName("Name").getValue();

						if (blockId.equals("minecraft:oak_fence")) {
							int absoluteY = sectionY * 16 + y;
							int absoluteX = chunkX * 16 + blockX;
							int absoluteZ = chunkZ * 16 + blockZ;
							String command = "fill " + absoluteX + " " + absoluteY + " " + absoluteZ + " " + absoluteX
									+ " " + absoluteY + " " + absoluteZ + " " + blockId;
							setBlock(blockX, absoluteY, blockZ, "minecraft:repeating_command_block", new NBTTag[] {
									new NBTTag(NBTTag.Type.TAG_String, "conditional", "false"), NBTTag.END_TAG });
							setCommandBlockCommand(absoluteX, absoluteY, absoluteZ, command);
						}

					}
				}
			}
		}
	}

	public static void setNibble(byte[] a, int index, byte value) {
		if (index / 2 < 0 || index / 2 > a.length - 1)
			return;

		boolean lastBits = ((double) index / 2) % 1 == 0 ? true : false;

		if (lastBits) {
			value = (byte) ((a[index / 2] & 0xF0) | value);
		} else {
			value = (byte) ((a[index / 2] & 0x0F) | (value << 4));
		}

		a[index / 2] = value;
	}

	@Override
	public String toString() {
		return tag.toString();
	}

	public static NBTTag[] PropertiesToTags(Map<String, String> properties) {
		if (properties.isEmpty()) {
			return new NBTTag[0];
		} else {
			return Stream.concat(
					properties.entrySet().stream()
							.map(e -> new NBTTag(NBTTag.Type.TAG_String, e.getKey(), e.getValue())),
					Stream.of(NBTTag.END_TAG)).toArray(size -> new NBTTag[size]);
		}
	}

}