package codemetropolis.blockmodifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;

import codemetropolis.blockmodifier.ext.NBTException;

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

    private Chunk setBlockInChunk(int x, int y, int z, int type, int data) {
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

        int[] blockTypes = new int[]{63, 68, 54, 176, 52};
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
                              int blockX, int y, int blockZ, int type, int data) {
        Region region = getRegion(regionX, regionZ);
        Chunk chunk = region.getChunk(xChunkIndex, zChunkIndex);
        if (chunk == null) {
            chunk = new Chunk(chunkX, chunkZ);
            if (groundBuilding)
                chunk.fill(GROUNDLEVEL, (byte) 2);
            region.setChunk(xChunkIndex, zChunkIndex, chunk);
        }

        chunk.setBlock(blockX, y, blockZ, (byte) type, (byte) data);

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

    /**
     * This method sets a block in the world at the specified coordinates with the specified type and data.
     * This is a general method that can be used for any type of block.
     *
     * @param x The x-coordinate of the block.
     * @param y The y-coordinate of the block.
     * @param z The z-coordinate of the block.
     * @param type The type of the block.
     * @param data The data of the block.
     */
    public void setBlock(int x, int y, int z, int type, int data) {
        setBlockInChunk(x, y, z, type, data);
    }

    /**
     * Sets the position data and type of regular block in CodeMetropolis and delegates the block
     * setup to another method.
     *
     * @param x The x-coordinate index of the block.
     * @param y The y-coordinate index of the block.
     * @param z The z-coordinate index of the block.
     * @param type The type of the block.
     */
    public void setBlock(int x, int y, int z, int type) {
        setBlock(x, y, z, type, 0);
    }

    /**
     * Removes a block at the specified coordinates.
     *
     * @param x The x-coordinate index of the block.
     * @param y The y-coordinate index of the block.
     * @param z The z-coordinate index of the block.
     */
    public void removeBlock(int x, int y, int z) {
        setBlock(x, y, z, 0);
    }

    /**
     * Sets a sign post block at the specified coordinates with the given data and text.
     * The type 63 represents sign posts.
     *
     * @param x The x-coordinate index of the sign post.
     * @param y The y-coordinate index of the sign post.
     * @param z The z-coordinate index of the sign post.
     * @param data The data of the sign post block.
     * @param text The text to be displayed on the sign post.
     */
    public void setSignPost(int x, int y, int z, int data, String text) {
        Chunk currentChunk = setBlockInChunk(x, y, z, 63, data);
        currentChunk.setSignText(x, y, z, text);
    }

    /**
     * Sets the position data and text of sign post blocks in CodeMetropolis and delegates the block
     * setup to another method.
     *
     * @param x The x-coordinate index of the sign post.
     * @param y The y-coordinate index of the sign post.
     * @param z The z-coordinate index of the sign post.
     * @param text The text to be displayed on the sign post.
     */
    public void setSignPost(int x, int y, int z, String text) {
        setSignPost(x, y, z, 0, text);
    }

    /**
     * Sets a wall sign block at the specified coordinates with the given data and text.
     * The type 68 represents wall signs.
     *
     * @param x The x-coordinate index of the wall sign.
     * @param y The y-coordinate index of the wall sign.
     * @param z The z-coordinate index of the wall sign.
     * @param data The data of the wall sign block.
     * @param text The text to be displayed on the wall sign.
     */
    public void setWallSign(int x, int y, int z, int data, String text) {
        Chunk currentChunk = setBlockInChunk(x, y, z, 68, data);
        currentChunk.setSignText(x, y, z, text);
    }

    /**
     * Sets the position data and text of wall sign blocks in CodeMetropolis and delegates the block
     * setup to another method.
     *
     * @param x The x-coordinate index of the wall sign.
     * @param y The y-coordinate index of the wall sign.
     * @param z The z-coordinate index of the wall sign.
     * @param text The text to be displayed on the wall sign.
     */
    public void setWallSign(int x, int y, int z, String text) {
        setWallSign(x, y, z, 0, text);
    }

    /**
     * Sets spawner blocks at the specified coordinates. The type 52 represents spawners.
     *
     * @param x The x-coordinate index of the spawner.
     * @param y The y-coordinate index of the spawner.
     * @param z The z-coordinate index of the spawner.
     * @param data The data of the spawner block.
     * @param dangerLevel The danger level associated with the spawner.
     */
    public void setSpawner(int x, int y, int z, int data, String entityId, Short dangerLevel) {
        Chunk currentChunk = setBlockInChunk(x, y, z, 52, data);
        currentChunk.setSpawnerContent(x, y, z, entityId, dangerLevel);
    }

    /**
     * Sets the position data and monster entity of spawner blocks in CodeMetropolis and delegates the block
     * setup to another method.
     *
     * @param x The x-coordinate index of the spawner.
     * @param y The y-coordinate index of the spawner.
     * @param z The z-coordinate index of the spawner.
     * @param entityId The identifier of the entity spawned by the spawner. Example: "minecraft:zombie".
     * @param dangerLevel The danger level associated with the spawner.
     */
    public void setSpawner(int x, int y, int z, String entityId, Short dangerLevel) {
        setSpawner(x, y, z, 0, entityId, dangerLevel);
    }

    /**
     * Sets chest blocks at the specified coordinates. The type 54 represents chests.
     *
     * @param x The x-coordinate index of the chest.
     * @param y The y-coordinate index of the chest.
     * @param z The z-coordinate index of the chest.
     * @param data The data of the chest block.
     * @param items An array representing the items to be placed in the chest.
     *              The array should contain pairs of values: item ID followed by quantity.
     */
    public void setChest(int x, int y, int z, int data, int[] items) {
        Chunk currentChunk = setBlockInChunk(x, y, z, 54, data);
        for (int i = 0; i < items.length; i += 2)
            currentChunk.addChestItem(x, y, z, items[i], items[i + 1]);
    }

    /**
     * Sets the position data and items of chest blocks in CodeMetropolis and delegates the block
     * setup to another method.
     *
     * @param x The x-coordinate index of the chest.
     * @param y The y-coordinate index of the chest.
     * @param z The z-coordinate index of the chest.
     * @param items An array representing the items to be placed in the chest.
     *              The array should contain pairs of values: item ID followed by quantity.
     */
    public void setChest(int x, int y, int z, int[] items) {
        setChest(x, y, z, 0, items);
    }

    /**
     * Sets banner blocks at the specified coordinates. The type 176 represents banners.
     *
     * @param x The x-coordinate index of the banner.
     * @param y The y-coordinate index of the banner.
     * @param z The z-coordinate index of the banner.
     * @param data The data of the banner block.
     * @param color The color of the banner represented by a BannerColor enum value.
     */
    public void setBanner(int x, int y, int z, int data, BannerColor color) {
        Chunk currentChunk = setBlockInChunk(x, y, z, 176, data);
        currentChunk.setBannerColor(x, y, z, color.ordinal());
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
