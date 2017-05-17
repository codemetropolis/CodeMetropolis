package codemetropolis.toolchain.rendering.control;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;


public class World {
	
	public final String PATH;
	public final String NAME;
	public final int GROUNDLEVEL;
	public final boolean HASMOB;
	public final byte BIOMEID;
	private boolean groundBuilding = true;
	private int maxLoadedRegions = 1;
	private int mobCounter = 0;
	public final long mobLimit = 300;
	private LinkedList<Region> loadedRegions = new LinkedList<Region>();
	
	public World(String path, int groundLevel, byte id, boolean hasMob) {
		
		this.PATH = path;
		this.GROUNDLEVEL = groundLevel;
		this.BIOMEID = id;
		this.HASMOB = hasMob;
		String[] splitPath = path.split("[/\\\\]");
		this.NAME = splitPath[splitPath.length - 1];
		Level level = new Level(this);
		level.writeToFile();
	}
	
	private void setBlock(int x, int y, int z, int type, int data, Object other) {
		
		if(y < 0 || y > 255) {
			try {
				throw new NBTException("Block's 'y' coordinate must be between 0 and 255");
			} catch (NBTException e) {
				e.printStackTrace();
			}
		}
		
		int regionX = x >> 9;
		int regionZ = z >> 9;
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		int chunkIndexX = (x % 512) >> 4;
		int chunkIndexZ = (z % 512) >> 4;
		chunkIndexX = chunkIndexX < 0 ? chunkIndexX + 32 : chunkIndexX;
		chunkIndexZ = chunkIndexZ < 0 ? chunkIndexZ + 32 : chunkIndexZ;
		int blockX = (x % 512) % 16;
		int blockZ = (z % 512) % 16;
		blockX = x < 0 ? blockX + 15 : blockX;
		blockZ = z < 0 ? blockZ + 15 : blockZ;
		
		Region region = getRegion(regionX, regionZ);
		Chunk chunk = region.getChunk(chunkIndexX, chunkIndexZ);
		if(chunk == null) {
			chunk = new Chunk(chunkX, chunkZ);
			if(groundBuilding)
				chunk.fill(GROUNDLEVEL, (byte) 2);
			region.setChunk(chunkIndexX, chunkIndexZ, chunk);
		}
		chunk.setBlock(blockX, y, blockZ, (byte) type, (byte) data);
		
		if(type == 63 || type == 68) {
			chunk.setSignText(x, y, z, (String) other);
		} else if (type == 54) {
			chunk.clearChestItems(x, y, z);
			int[] items = (int[])other;
			for(int i = 0; i < items.length; i += 2)
				chunk.addChestItem(x, y, z, items[i], items[i+1]);
		} else if (type == 176) {
			chunk.setBannerColor(x, y, z, (int)other);
		} else {
			chunk.clearTileEntitiesAt(x, y, z);
		}

		
		
		if(HASMOB && type == -1 && mobCounter < mobLimit){
			chunk.setMob(x, y, z, "Pig");
			mobCounter++;
		}
		
	

	}
	
	public void setBlock(int x, int y, int z, int type, int data) {
		setBlock(x, y, z, type, data, null);
	}
	
	public void setBlock(int x, int y, int z, int type) {
		setBlock(x, y, z, type, 0, null);
	}
	
	public void removeBlock(int x, int y, int z) {
		setBlock(x, y, z, 0);
	}
	
	public void setSignPost(int x, int y, int z, int data, String text) {
		setBlock(x, y, z, 63, data, text);
	}
	
	public void setSignPost(int x, int y, int z, String text) {
		setSignPost(x, y, z, 0, text);
	}
	
	public void setWallSign(int x, int y, int z, int data, String text) {
		setBlock(x, y, z, 68, data, text);
	}
	
	public void setWallSign(int x, int y, int z, String text) {
		setWallSign(x, y, z, 0, text);
	}
	
	public void setChest(int x, int y, int z, int data, int[] items) {
		setBlock(x, y, z, 54, data, items);	
	}
	
	public void setChest(int x, int y, int z, int[] items) {
		setChest(x, y, z, 0, items);	
	}

	public void setMob(int x, int y, int z, String name){ setBlock(x, y, z, -1, 0, name); }
	
	public void setBanner(int x, int y, int z, int data, BannerColor color) {
		setBlock(x, y, z, 176, data, color.ordinal());	
	}
	
	private Region getRegion(int x, int z) {
		
		for(Region r : loadedRegions) {
			if(r.getX() == x && r.getZ() == z) {
				return r;
			}
		}
		
		if(loadedRegions.size() >= maxLoadedRegions) {
			loadedRegions.removeFirst().writeToFile();
		} 
		
		Region result = Region.loadFromFile(x, z, this);
		loadedRegions.add(result);
		return result;
	}

	public void groundBuildingOn() {
		this.groundBuilding = true;
	}
	
	public void groundBuildingOff() {
		this.groundBuilding = false;
	}
	
	public void setMaximumNumberOfLoadedRegions(int max) {
		this.maxLoadedRegions = max;
	}

	public void finish() {
		for(Region r : loadedRegions) {
			setBiomeForRegion(r);
			r.writeToFile();
		}
		loadedRegions.clear();
	}
	
	public void setBiomeForRegion(Region r){
		for(Chunk c : r.chunks){
			if(c != null){				
				c.setBiome(BIOMEID);
			}
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
        File regionDirectory = new File(PATH + "/region");
        for(File f : regionDirectory.listFiles()) {
            if(f.getName().matches("r\\.-?[0-9]*\\.-?[0-9]*.mca")) {
                String[] parts = f.getName().split("\\.");
                Region region = getRegion(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
                sb.append("** RegionFile: " + f.getName() + " **\n");
                sb.append(region.toString());
                sb.append("\n");
            }
        }    
        return sb.toString();
	}
	
	public void toNBTFile(String path) {
		try {
			PrintWriter writer = new PrintWriter(new File(path + ".nbt"));
			writer.println(this);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void toNBTFile() {
		toNBTFile(NAME);
	}
	
	public enum BannerColor {
		BLACK,
		RED,
		GREEN,
		BROWN,
		BLUE,
		PURPLE,
		TURQUOISE,
		LIGHT_GRAY,
		GRAY,
		PINK,
		LIGHT_GREEN,
		YELLOW,
		LIGHT_BLUE,
		LIGHT_PURPLE,
		ORANGE,
		WHITE;
	}
	
}
