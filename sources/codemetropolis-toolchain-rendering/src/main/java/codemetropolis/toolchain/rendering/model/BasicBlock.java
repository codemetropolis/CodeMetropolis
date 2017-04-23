package codemetropolis.toolchain.rendering.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import codemetropolis.toolchain.rendering.util.Block;
import codemetropolis.toolchain.rendering.util.Character;
import codemetropolis.toolchain.rendering.util.Colour;
import codemetropolis.toolchain.rendering.RenderingExecutor;

public class BasicBlock {

	public static final BasicBlock NonBlock;
	public static final Map<Short, String> idToName;
	public static final Map<Short, String> idToHumanReadableName;
	public static final Map<String, Short> nameToId;
	public static final Map<String, Short> humanReadableNameToId;
	public static final Map<String, Block> humanReadableNameToBlock;
	
	static {
		NonBlock = new BasicBlock((short)-1 );
		idToName = new HashMap<Short,String>();
		idToHumanReadableName = new HashMap<Short,String>();
		nameToId = new HashMap<String,Short>();
		humanReadableNameToId = new HashMap<String,Short>();
		humanReadableNameToBlock = new HashMap<String, Block>();
		
		InputStream csvStream = RenderingExecutor.class.getClassLoader().getResourceAsStream("blocks.csv");
		try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(csvStream, "UTF-8"))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] blockInfo = line.split(",");
				if (!idToName.containsKey(Short.parseShort(blockInfo[0]))){
					idToName.put(Short.parseShort(blockInfo[0]), blockInfo[2]);
					idToHumanReadableName.put(Short.parseShort(blockInfo[0]), blockInfo[3]);
					nameToId.put(blockInfo[2], Short.parseShort(blockInfo[0]));
				}
				humanReadableNameToBlock.put(blockInfo[3].toLowerCase(),
						new Block(
							Short.parseShort(blockInfo[0]),
							Short.parseShort(blockInfo[1]),
							blockInfo[2],
							blockInfo[3],
							Short.parseShort(blockInfo[4])
						)
				);
			}
			Character.init(humanReadableNameToBlock);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private short id;
	private int data;
	
	public BasicBlock(short id) {
		this(id, 0);
	}

	public BasicBlock(short id, int data) {
		this.id = id;
		this.data = data;
	}
	
	public BasicBlock(short id, Colour clr){
		this.id = id;
		this.data = clr.getValue();
	}
	
	public BasicBlock(String name) {
		this(nameToId.get(name), 0);
	}
	
	public BasicBlock(String name, int data) {
		this(nameToId.get(name), data);
	}
	
	public BasicBlock(String name, Colour clr){
		this(nameToId.get(name), clr.getValue());
	}
	
	public BasicBlock(BasicBlock original) {
		this.id = original.id;
		this.data = original.data;
	}
	
	public String getName() {
		return idToName.get(id);
	}
	
	public String getHumanReadableName() {
		return idToHumanReadableName.get(id);
	}

	public short getId() {
		return id;
	}

	public int getData() {
		return data;
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + data;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicBlock other = (BasicBlock) obj;
		if (data != other.data)
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getHumanReadableName() + (data != 0 ? data : "");
	}
	
}
