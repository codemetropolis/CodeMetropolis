package codemetropolis.toolchain.commons.blockmodifier;

import java.io.File;
import java.io.IOException;

import codemetropolis.toolchain.commons.blockmodifier.ext.LevelFile;
import codemetropolis.toolchain.commons.blockmodifier.ext.NBTTag;

public class Level {

	private LevelFile levelFile;
	private NBTTag tag;

	public Level(World world) {
		File file = new File(String.format("%s/level.dat", world.PATH));
		boolean alreadyExists = file.exists();
		this.levelFile = new LevelFile(file);
		
		if(alreadyExists) {
			try {
				tag = NBTTag.readFrom(levelFile.getLevelDataInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} else {
			NBTTag allowCommandsTag = new NBTTag(NBTTag.Type.TAG_Byte, "allowCommands", (byte)0);
			NBTTag hardcoreTag = new NBTTag(NBTTag.Type.TAG_Byte, "hardcore", (byte)0);
			NBTTag initializedTag = new NBTTag(NBTTag.Type.TAG_Byte, "initialized", (byte)1);
			NBTTag mapFeaturesTag = new NBTTag(NBTTag.Type.TAG_Byte, "MapFeatures", (byte)0);
			NBTTag rainingTag = new NBTTag(NBTTag.Type.TAG_Byte, "raining", (byte)0);
			NBTTag thunderingTag = new NBTTag(NBTTag.Type.TAG_Byte, "thundering", (byte)0);
			NBTTag gameTypeTag = new NBTTag(NBTTag.Type.TAG_Int, "GameType", 1);
			NBTTag generatorVersionTag = new NBTTag(NBTTag.Type.TAG_Int, "generatorVersion", 1);
			NBTTag rainTimeTag = new NBTTag(NBTTag.Type.TAG_Int, "rainTime", Integer.MAX_VALUE);
			NBTTag spawnXTag = new NBTTag(NBTTag.Type.TAG_Int, "SpawnX", 0);
			NBTTag spawnYTag = new NBTTag(NBTTag.Type.TAG_Int, "SpawnY", world.GROUNDLEVEL + 1);
			NBTTag spawnZTag = new NBTTag(NBTTag.Type.TAG_Int, "SpawnZ", 0);
			NBTTag thunderTimeTag = new NBTTag(NBTTag.Type.TAG_Int, "thunderTime", Integer.MAX_VALUE);
			NBTTag versionTag = new NBTTag(NBTTag.Type.TAG_Int, "version", 19133);
			NBTTag dayTimeTag = new NBTTag(NBTTag.Type.TAG_Long, "DayTime", 3000L);
			NBTTag lastPlayedTag = new NBTTag(NBTTag.Type.TAG_Long, "LastPlayed", 0L);
			NBTTag randomSeedTag = new NBTTag(NBTTag.Type.TAG_Long, "RandomSeed", 0L);
			NBTTag sizeOnDiskTag = new NBTTag(NBTTag.Type.TAG_Long, "SizeOnDisk", 0L);
			NBTTag timeTag = new NBTTag(NBTTag.Type.TAG_Long, "Time", 3000L);
			NBTTag generatorNameTag = new NBTTag(NBTTag.Type.TAG_String, "generatorName", "flat");
			NBTTag generatorOptionsTag = new NBTTag(NBTTag.Type.TAG_String, "generatorOptions", "3;minecraft:bedrock," + (world.GROUNDLEVEL - 1) + "*minecraft:dirt,minecraft:grass");
			NBTTag levelNameTag = new NBTTag(NBTTag.Type.TAG_String, "LevelName", world.NAME);
			
			NBTTag commandBlockOutputTag = new NBTTag(NBTTag.Type.TAG_String, "commandBlockOutput", "true");
			NBTTag doDaylightCycleTag = new NBTTag(NBTTag.Type.TAG_String, "doDaylightCycle", "false");
			NBTTag doFireTickTag = new NBTTag(NBTTag.Type.TAG_String, "doFireTick", "true");
			NBTTag doMobLootTag = new NBTTag(NBTTag.Type.TAG_String, "doMobLoot", "true");
			NBTTag doMobSpawningTag = new NBTTag(NBTTag.Type.TAG_String, "doMobSpawning", "false");
			NBTTag doTileDropsTag = new NBTTag(NBTTag.Type.TAG_String, "doTileDrops", "true");
			NBTTag keepInventoryTag = new NBTTag(NBTTag.Type.TAG_String, "keepInventory", "true");
			NBTTag mobGriefingTag = new NBTTag(NBTTag.Type.TAG_String, "mobGriefing", "false");
			NBTTag naturalRegenerationTag = new NBTTag(NBTTag.Type.TAG_String, "naturalRegeneration", "true");
			
			NBTTag[] ruleList = new NBTTag[] {commandBlockOutputTag, doDaylightCycleTag, doFireTickTag, doMobLootTag, doMobSpawningTag, doTileDropsTag,
					keepInventoryTag, mobGriefingTag, naturalRegenerationTag, new NBTTag(NBTTag.Type.TAG_End, null, null)};
			NBTTag gameRulesTag = new NBTTag(NBTTag.Type.TAG_Compound, "GameRules", ruleList);
			
			NBTTag[] tagList = new NBTTag[] {versionTag, initializedTag, levelNameTag, generatorNameTag, generatorVersionTag, generatorOptionsTag, randomSeedTag,
					mapFeaturesTag, lastPlayedTag, sizeOnDiskTag, allowCommandsTag, hardcoreTag, gameTypeTag, timeTag, dayTimeTag, spawnXTag, spawnYTag,
					spawnZTag, rainingTag, rainTimeTag, thunderingTag, thunderTimeTag, gameRulesTag, new NBTTag(NBTTag.Type.TAG_End, null, null)};
			NBTTag dataTag = new NBTTag(NBTTag.Type.TAG_Compound, "Data", tagList);
			tag = new NBTTag(NBTTag.Type.TAG_Compound, "", new NBTTag[] {dataTag, new NBTTag(NBTTag.Type.TAG_End, null, null)});
		}
	
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
