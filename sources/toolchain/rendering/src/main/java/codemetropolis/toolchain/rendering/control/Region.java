package codemetropolis.toolchain.rendering.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
public class Region {
	
	private int x;
	private int z;
	RegionFile regionFile;
	public Chunk[] chunks = new Chunk[1024];
	
	public Region(int x, int z, World world) {
		
		this.x = x;
		this.z = z;
		File file = new File(String.format("%s/region/r.%d.%d.mca", world.PATH, x, z));
		file.getParentFile().mkdirs();
		this.regionFile = new RegionFile(file);
		
		try {
			
			for(int chunkX = 0; chunkX < 32; chunkX++) {
				for(int chunkZ = 0; chunkZ < 32; chunkZ++) {
					if(regionFile.hasChunk(chunkX, chunkZ)) {
						DataInputStream inputStream = regionFile.getChunkDataInputStream(chunkX, chunkZ);
						NBTTag chunkTag = NBTTag.readFrom(inputStream);
						Chunk chunk = Chunk.parseNBT(chunkTag);
						setChunk(chunkX, chunkZ, chunk);
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void setChunk(int x, int z, Chunk c) {
		chunks[z * 32 + x] = c;
	}
	
	public Chunk getChunk(int x, int z) {
		return chunks[z * 32 + x];
	}
	
	public static Region loadFromFile(int x, int z, World world) {
		return new Region(x, z, world);
	}
	
	public void writeToFile() {
		try {
			connectNeighbouringFecnes();
			for(int i = 0; i < 1024; i++) {
				Chunk c = chunks[i];
				if(c != null) {
					DataOutputStream outputStream = regionFile.getChunkDataOutputStream(i % 32, i / 32);
					c.calculateLighting();
					//c.filterMagicalBlocks();
					c.toNBT().writeTo(outputStream);
				}
			}
			regionFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void connectNeighbouringFecnes() {
		for(int x=0; x<512; x++) {
			for(int y=0; y<256; y++) {
				for(int z=0; z<512; z++) {
					String blockId = getBlockIdAt(x, y, z);

					if( blockId.equals("minecraft:oak_fence") ) {
                        Map<String, String> fenceProperties = new HashMap<>();

							if( z > 0 && getBlockIdAt(x, y, z-1).equals("minecraft:oak_fence") ) {
									fenceProperties.put("north", "true");
							}
							if( x > 0 && getBlockIdAt(x-1, y, z).equals("minecraft:oak_fence") ) {
									fenceProperties.put("west", "true");
							}
							if( z < 512-1 && getBlockIdAt(x, y, z+1).equals("minecraft:oak_fence") ) {
									fenceProperties.put("south", "true");
							}
							if( x < 512-1 && getBlockIdAt(x+1, y, z).equals("minecraft:oak_fence") ) {
									fenceProperties.put("east", "true");
							}

							if( !fenceProperties.isEmpty() ) {
								setBlockAt(x, y, z, "minecraft:oak_fence", fenceProperties);
							}
						}

					if( blockId.equals("minecraft:acacia_fence") ) {
                        Map<String, String> fenceProperties = new HashMap<>();

                        if( z > 0 && getBlockIdAt(x, y, z-1).equals("minecraft:acacia_fence") ) {
                                fenceProperties.put("north", "true");
                        }
                        if( x > 0 && getBlockIdAt(x-1, y, z).equals("minecraft:acacia_fence") ) {
                                fenceProperties.put("west", "true");
                        }
                        if( z < 512-1 && getBlockIdAt(x, y, z+1).equals("minecraft:acacia_fence") ) {
                                fenceProperties.put("south", "true");
                        }
                        if( x < 512-1 && getBlockIdAt(x+1, y, z).equals("minecraft:acacia_fence") ) {
                                fenceProperties.put("east", "true");
                        }

                        if( !fenceProperties.isEmpty() ) {
                        	setBlockAt(x, y, z, "minecraft:acacia_fence", fenceProperties);
                        }
					}
				
					if( blockId.equals("minecraft:cobblestone_wall") ) {
                        Map<String, String> fenceProperties = new HashMap<>();

                        if( z > 0 && getBlockIdAt(x, y, z-1).equals("minecraft:cobblestone_wall") ) {
                                fenceProperties.put("north", "true");
                        }
                        if( x > 0 && getBlockIdAt(x-1, y, z).equals("minecraft:cobblestone_wall") ) {
                                fenceProperties.put("west", "true");
                        }
                        if( z < 512-1 && getBlockIdAt(x, y, z+1).equals("minecraft:cobblestone_wall") ) {
                                fenceProperties.put("south", "true");
                        }
                        if( x < 512-1 && getBlockIdAt(x+1, y, z).equals("minecraft:cobblestone_wall") ) {
                                fenceProperties.put("east", "true");
                        }

                        if( !fenceProperties.isEmpty() ) {
                        	setBlockAt(x, y, z, "minecraft:cobblestone_wall", fenceProperties);
                        }
					}
				}
			}
		}
	}
	
	public String getBlockIdAt(int x, int y, int z) {
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		int chunkIndex = chunkZ * 32 + chunkX;
		Chunk chunk = chunks[chunkIndex];
		
		if( chunk == null ) {
			return "";
		}
		
		int blockX = x & 0xF;
		int blockZ = z & 0xF;
		return chunk.getBlockId(blockX, y, blockZ);
	}
	
	public void setBlockAt(int x, int y, int z, String blockId, Map<String, String> blockProperties) {
		int chunkX = x >> 4;
		int chunkZ = z >> 4;
		int chunkIndex = chunkZ * 32 + chunkX;
		Chunk chunk = chunks[chunkIndex];
		int blockX = x & 0xF;
		int blockZ = z & 0xF;
		chunk.setBlock(blockX, y, blockZ, blockId, blockProperties);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Chunk c : chunks) {
			if(c != null)
				sb.append(c.toString());
		}
		return sb.toString();
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}

}