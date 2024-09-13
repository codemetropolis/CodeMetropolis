package codemetropolis.toolchain.commons.blockmodifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import codemetropolis.toolchain.commons.blockmodifier.ext.NBTException;

public class World {
	
	public final String PATH;
	public final String NAME;
	public final int GROUNDLEVEL;
	
	private boolean groundBuilding = true;
	private int maxLoadedRegions = 1;
	private LinkedList<Region> loadedRegions = new LinkedList<Region>();
	
	public World(String path, int groundLevel) {
		
		this.PATH = path;
		this.GROUNDLEVEL = groundLevel;
		String[] splitPath = path.split("[/\\\\]");
		this.NAME = splitPath[splitPath.length - 1];
		Level level = new Level(this);
		level.writeToFile();
	}

    private Chunk setBlockInChunk(int x, int y, int z, int type, List<Integer> data) {
        checkCoordinateYBoundaries(y);

        int regionX = getRegionCoordinate(x);
        int regionZ = getRegionCoordinate(z);

        int chunkX = getChunkCoordinate(x);
        int chunkZ = getChunkCoordinate(z);
        int chunkIndexX = getChunkIndex(x);
        int chunkIndexZ = getChunkIndex(z);

        int blockX = getBlockCoordinate(x);
        int blockZ = getBlockCoordinate(z);

        Chunk activeChunk = locateChunk(chunkIndexX, chunkIndexZ, chunkX, chunkZ, regionX, regionZ, blockX, y, blockZ, type, data);

        int[] blockTypes = new int[]{63, 68, 54, 176, 52}; //standing sign 63, wallsign 68, chest 54, standing banner 176, mob spawner 52
        Arrays.sort(blockTypes);
        if (Arrays.binarySearch(blockTypes, type) >= 0) {
            activeChunk.clearTileEntitiesAt(blockX, y, blockZ);
        }

        return activeChunk;
    }

    private int getRegionCoordinate(int a) {
        return a >> 9;
    }

    private int getChunkCoordinate(int a) {
        return a >> 4;
    }

    private int getChunkIndex(int a) {
        int chunkIndexA = (a % 512) >> 4;
        chunkIndexA = chunkIndexA < 0 ? chunkIndexA + 32 : chunkIndexA;

        return chunkIndexA;
    }

    private int getBlockCoordinate(int a) {
        int blockA = (a % 512) % 16;
        blockA = a < 0 ? blockA + 15 : blockA;

        return blockA;
    }


    private Chunk locateChunk(int xChunkIndex, int zChunkIndex, int chunkX, int chunkZ, int regionX, int regionZ,
                              int blockX, int y, int blockZ, int type, List<Integer> data) {
        Region region = getRegion(regionX, regionZ);
        Chunk chunk = region.getChunk(xChunkIndex, zChunkIndex);
        if (chunk == null) {
            chunk = createChunkIfNotExist(chunkX, chunkZ, region, xChunkIndex, zChunkIndex);
        }

        chunk.setBlock(blockX, y, blockZ, (byte) type, data);

        return chunk;
    }

    private Chunk createChunkIfNotExist(int chunkX, int chunkZ, Region region,int xChunkIndex, int zChunkIndex){
        Chunk chunk = new Chunk(chunkX, chunkZ);
        if (groundBuilding)
            chunk.fill(GROUNDLEVEL, (byte) 2);
        region.setChunk(xChunkIndex, zChunkIndex, chunk);
        return chunk;
    }

    private void checkCoordinateYBoundaries(int y) {
        if (y < 0 || y > 255) {
            try {
                throw new NBTException("Block's 'y' coordinate must be between 0 and 255");
            } catch (NBTException e) {
                e.printStackTrace();
            }
        }
    }

    public void setBlock(int x, int y, int z, int type, List<Integer> data) {
        setBlockInChunk(x, y, z, type, data);
    }

    public void setBlock(int x, int y, int z, int type) {
        setBlock(x, y, z, type, null);
    }

    public void removeBlock(int x, int y, int z) {
        setBlock(x, y, z, 0);
    }

    public void setSignPost(int x, int y, int z, List<Integer> data, String text) {
        Chunk currentChunk = setBlockInChunk(x, y, z, 63, data); //signPost id = 63
        currentChunk.setSignText(x, y, z, text);
    }

    public void setSignPost(int x, int y, int z, String text) {
        setSignPost(x, y, z, null, text);
    }

    public void setWallSign(int x, int y, int z, List<Integer> data, String text) {
        Chunk currentChunk = setBlockInChunk(x, y, z, 68, data); //wallSign id = 68
        currentChunk.setSignText(x, y, z, text);
    }

    public void setSpawner(int x, int y, int z, List<Integer> data, String entityId, Short dangerLevel) {
        Chunk currentChunk = setBlockInChunk(x, y, z, 52, data); // mobSpawner id = 52
        currentChunk.setSpawnerContent(x, y, z, entityId, dangerLevel);
    }

    public void setChest(int x, int y, int z, List<Integer> data, int[] items) {
        Chunk currentChunk = setBlockInChunk(x, y, z, 54, data); // chest id = 54
        for (int i = 0; i < items.length; i += 2)
            currentChunk.addChestItem(x, y, z, items[i], items[i + 1]);
    }

    public void setBanner(int x, int y, int z,int shortId, List<Integer> data) {
        Chunk currentChunk = setBlockInChunk(x, y, z, shortId, data);
        currentChunk.setBannerColor(x, y, z, data.get(1));
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
			r.writeToFile();
		}
		loadedRegions.clear();
	}

    /**
     * Returns a string representation of the regions and their details in the region directory.
     *
     * <p>This method constructs a string that includes the names of all region files in the
     * specified directory and their respective details. It looks for files matching the
     * pattern "r.<x>.<z>.mca" and retrieves the corresponding region information.</p>
     *
     * @return a string representation of the region files and their details
     */
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
}
