package codemetropolis.toolchain.rendering.control;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;


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
			for(int i = 0; i < 1024; i++) {
				Chunk c = chunks[i];
				if(c != null) {
					DataOutputStream outputStream = regionFile.getChunkDataOutputStream(i % 32, i / 32);
					c.calculateLighting();
					c.toNBT().writeTo(outputStream);
				}
			}
			regionFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
