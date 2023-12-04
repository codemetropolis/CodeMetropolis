package codemetropolis.toolchain.rendering.control;

import java.io.File;
import java.io.IOException;

public class Level {

    private static final LevelHelper HELPER = new LevelHelper();
	private LevelFile levelFile;
	private NBTTag tag;

	public Level(World world) {
		File file = new File(String.format("%s/level.dat", world.PATH));
		boolean alreadyExists = file.exists();
		this.levelFile = new LevelFile(file);

		if (alreadyExists) {

			try {
				tag = NBTTag.readFrom(levelFile.getLevelDataInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {

			tag = createNewLevelFile(world);
		}

	}

	private NBTTag createNewLevelFile(World world) {
		NBTTag versionTag = new NBTTag(NBTTag.Type.TAG_Int, "version", 19133);
		NBTTag initializedTag = new NBTTag(NBTTag.Type.TAG_Byte, "initialized", (byte) 1);
		NBTTag levelNameTag = new NBTTag(NBTTag.Type.TAG_String, "LevelName", world.NAME);
		NBTTag generatorNameTag = new NBTTag(NBTTag.Type.TAG_String, "generatorName", "flat");
		NBTTag generatorVersionTag = new NBTTag(NBTTag.Type.TAG_Int, "generatorVersion", 0);
		NBTTag randomSeedTag = new NBTTag(NBTTag.Type.TAG_Long, "RandomSeed", 0L);
		NBTTag mapFeaturesTag = new NBTTag(NBTTag.Type.TAG_Byte, "MapFeatures", (byte) 0);
		NBTTag lastPlayedTag = new NBTTag(NBTTag.Type.TAG_Long, "LastPlayed", System.currentTimeMillis());
		NBTTag sizeOnDiskTag = new NBTTag(NBTTag.Type.TAG_Long, "SizeOnDisk", 0L);
		NBTTag allowCommandsTag = new NBTTag(NBTTag.Type.TAG_Byte, "allowCommands", (byte) 1);
		NBTTag hardcoreTag = new NBTTag(NBTTag.Type.TAG_Byte, "hardcore", (byte) 0);
		NBTTag gameTypeTag = new NBTTag(NBTTag.Type.TAG_Int, "GameType", 1);
		NBTTag difficultyTag = new NBTTag(NBTTag.Type.TAG_Byte, "Difficulty", (byte) 2);
		NBTTag difficultyLockedTag = new NBTTag(NBTTag.Type.TAG_Byte, "DifficultyLocked", (byte) 0);
		NBTTag timeTag = new NBTTag(NBTTag.Type.TAG_Long, "Time", 3000L);
		NBTTag dayTimeTag = new NBTTag(NBTTag.Type.TAG_Long, "DayTime", 3000L);
		NBTTag spawnXTag = new NBTTag(NBTTag.Type.TAG_Int, "SpawnX", 0);
		NBTTag spawnYTag = new NBTTag(NBTTag.Type.TAG_Int, "SpawnY", world.GROUNDLEVEL + 1);
		NBTTag spawnZTag = new NBTTag(NBTTag.Type.TAG_Int, "SpawnZ", 0);
		NBTTag borderCenterXTag = new NBTTag(NBTTag.Type.TAG_Double, "BorderCenterX", (double) 0);
		NBTTag borderCenterZTag = new NBTTag(NBTTag.Type.TAG_Double, "BorderCenterZ", (double) 0);

		NBTTag borderSizeTag = new NBTTag(NBTTag.Type.TAG_Double, "BorderSize", (double) 60000000);
		NBTTag borderSafeZoneTag = new NBTTag(NBTTag.Type.TAG_Double, "BorderSafeZone", (double) 5);
		NBTTag borderWarningBlocksTag = new NBTTag(NBTTag.Type.TAG_Double, "BorderWarningBlocks", (double) 5);
		NBTTag borderWarningTimeTag = new NBTTag(NBTTag.Type.TAG_Double, "BorderWarningTime", (double) 15);
		NBTTag borderSizeLerpTargetTag = new NBTTag(NBTTag.Type.TAG_Double, "BorderSizeLerpTarget", (double) 60000000);
		NBTTag borderSizeLerpTimeTag = new NBTTag(NBTTag.Type.TAG_Long, "BorderSizeLerpTime", 0L);
		NBTTag borderDamagePerBlockTag = new NBTTag(NBTTag.Type.TAG_Double, "BorderSizeLerpTarget", 0.2);

		NBTTag dataVersionTag = new NBTTag(NBTTag.Type.TAG_Int, "DataVersion", 1631);
		
		NBTTag rainingTag = new NBTTag(NBTTag.Type.TAG_Byte, "raining", (byte) 0);
		NBTTag rainTimeTag = new NBTTag(NBTTag.Type.TAG_Int, "rainTime", Integer.MAX_VALUE);
		NBTTag thunderingTag = new NBTTag(NBTTag.Type.TAG_Byte, "thundering", (byte) 0);
		NBTTag thunderTimeTag = new NBTTag(NBTTag.Type.TAG_Int, "thunderTime", Integer.MAX_VALUE);
		NBTTag clearWeatherTimeTag = new NBTTag(NBTTag.Type.TAG_Int, "clearWeatherTime", Integer.MAX_VALUE);

		/**
		 * Gamerules:
		 * 
		 */

		NBTTag announceAdvancementsTag = new NBTTag(NBTTag.Type.TAG_String, "announceAdvancements", "true");
		NBTTag commandBlockOutputTag = new NBTTag(NBTTag.Type.TAG_String, "commandBlockOutput", "true");
		NBTTag disableElytraMovementCheckTag = new NBTTag(NBTTag.Type.TAG_String, "disableElytraMovementCheck", "false");
		NBTTag doDaylightCycleTag = new NBTTag(NBTTag.Type.TAG_String, "doDaylightCycle", "false");
		NBTTag doEntityDropsTag = new NBTTag(NBTTag.Type.TAG_String, "doEntityDrops", "true");
		NBTTag doFireTickTag = new NBTTag(NBTTag.Type.TAG_String, "doFireTick", "true");
		NBTTag doLimitedCraftingTag = new NBTTag(NBTTag.Type.TAG_String, "doLimitedCrafting", "false");
		NBTTag doMobLootTag = new NBTTag(NBTTag.Type.TAG_String, "doMobLoot", "true");
		NBTTag doMobSpawningTag = new NBTTag(NBTTag.Type.TAG_String, "doMobSpawning", "false");
		NBTTag doTileDropsTag = new NBTTag(NBTTag.Type.TAG_String, "doTileDrops", "true");
		NBTTag doWeatherCycleTag = new NBTTag(NBTTag.Type.TAG_String, "doWeatherCycle", "true");
		NBTTag keepInventoryTag = new NBTTag(NBTTag.Type.TAG_String, "keepInventory", "true");
		NBTTag logAdminCommandsTag = new NBTTag(NBTTag.Type.TAG_String, "logAdminCommands", "true");
		NBTTag maxCommandChainLengthTag = new NBTTag(NBTTag.Type.TAG_String, "maxCommandChainLength", "65536");
		NBTTag maxEntityCrammingTag = new NBTTag(NBTTag.Type.TAG_String, "maxEntityCramming", "24");
		NBTTag mobGriefingTag = new NBTTag(NBTTag.Type.TAG_String, "mobGriefing", "false");
		NBTTag naturalRegenerationTag = new NBTTag(NBTTag.Type.TAG_String, "naturalRegeneration", "true");
		NBTTag randomTickSpeedTag = new NBTTag(NBTTag.Type.TAG_String, "randomTickSpeed", "3");
		NBTTag reducedDebugInfoTag = new NBTTag(NBTTag.Type.TAG_String, "reducedDebugInfo", "false");
		NBTTag sendCommandFeedbackTag = new NBTTag(NBTTag.Type.TAG_String, "sendCommandFeedback", "true");
		NBTTag showDeathMessagesTag = new NBTTag(NBTTag.Type.TAG_String, "showDeathMessages", "true");
		NBTTag spawnRadiusTag = new NBTTag(NBTTag.Type.TAG_String, "spawnRadius", "10");
		NBTTag spectatorsGenerateChunksTag = new NBTTag(NBTTag.Type.TAG_String, "spectatorsGenerateChunks", "true");

		NBTTag[] ruleList = new NBTTag[] { announceAdvancementsTag, commandBlockOutputTag, disableElytraMovementCheckTag, doDaylightCycleTag, doEntityDropsTag, doFireTickTag, doLimitedCraftingTag,
				doMobLootTag, doMobSpawningTag, doTileDropsTag, doWeatherCycleTag, keepInventoryTag, logAdminCommandsTag, maxCommandChainLengthTag, maxEntityCrammingTag, mobGriefingTag,
				naturalRegenerationTag, randomTickSpeedTag, reducedDebugInfoTag, sendCommandFeedbackTag, showDeathMessagesTag, spawnRadiusTag, spectatorsGenerateChunksTag,
				new NBTTag(NBTTag.Type.TAG_End, null, null) };

		NBTTag gameRulesTag = new NBTTag(NBTTag.Type.TAG_Compound, "GameRules", ruleList);

		/**
		 * Version:
		 */

		NBTTag versionIdTag = new NBTTag(NBTTag.Type.TAG_Int, "Id", 1631);
		NBTTag versionNameTag = new NBTTag(NBTTag.Type.TAG_String, "Name", "1.13.2");
		NBTTag isSnapshotTag = new NBTTag(NBTTag.Type.TAG_Byte, "Snapshot", (byte) 0);

		NBTTag[] versionParameters = new NBTTag[] { versionIdTag, versionNameTag, isSnapshotTag, NBTTag.END_TAG };
		NBTTag version = new NBTTag(NBTTag.Type.TAG_Compound, "Version", versionParameters);

		/**
		 * Generator options:
		 */

		NBTTag structureTag = new NBTTag(NBTTag.Type.TAG_Compound, "structures", new NBTTag[] { NBTTag.END_TAG });
		NBTTag biomeTag = new NBTTag(NBTTag.Type.TAG_String, "biome", "minecraft:desert");

		NBTTag layers = new NBTTag("layers", NBTTag.Type.TAG_List);
		layers.addTag(HELPER.getLayerTagByBlockAndHeight("minecraft:bedrock", (byte) 1));
		layers.addTag(HELPER.getLayerTagByBlockAndHeight("minecraft:dirt", (byte) (world.GROUNDLEVEL - 1)));
		layers.addTag(HELPER.getLayerTagByBlockAndHeight("minecraft:grass_block", (byte) 1));

		NBTTag generatorOptionsTag = new NBTTag(NBTTag.Type.TAG_Compound, "generatorOptions", new NBTTag[] { structureTag, biomeTag, layers, NBTTag.END_TAG });

		NBTTag[] tagList = new NBTTag[] { versionTag, dataVersionTag,difficultyTag, difficultyLockedTag, borderCenterXTag, initializedTag, clearWeatherTimeTag, borderDamagePerBlockTag, borderSizeLerpTimeTag,
				borderCenterZTag, borderWarningBlocksTag, borderSizeLerpTargetTag, borderWarningTimeTag, borderSafeZoneTag, borderSizeTag, levelNameTag, generatorNameTag, generatorVersionTag,
				generatorOptionsTag, randomSeedTag, mapFeaturesTag, lastPlayedTag, sizeOnDiskTag, allowCommandsTag, hardcoreTag, gameTypeTag, timeTag, dayTimeTag, spawnXTag, spawnYTag, spawnZTag, rainingTag,
				rainTimeTag, thunderingTag, thunderTimeTag, gameRulesTag, version, NBTTag.END_TAG };

		NBTTag dataTag = new NBTTag(NBTTag.Type.TAG_Compound, "Data", tagList);
		return new NBTTag(NBTTag.Type.TAG_Compound, "", new NBTTag[] { dataTag, NBTTag.END_TAG });
	}

	public void writeToFile() {
		try {
			tag.writeTo(levelFile.getLevelDataOutputStream());
			levelFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}