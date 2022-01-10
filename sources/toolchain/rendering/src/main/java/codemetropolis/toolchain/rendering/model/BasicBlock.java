package codemetropolis.toolchain.rendering.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import codemetropolis.toolchain.rendering.RenderingExecutor;

public class BasicBlock {

	public static final BasicBlock NON_BLOCK;
	public static final Map<Short, String> ID_TO_NAME;
	public static final Map<Short, String> ID_TO_HUMAN_READABLE_NAME;
	public static final Map<String, Short> NAME_TO_ID;
	public static final Map<String, Short> HUMAN_REAABLE_NAME_TO_ID;
	
	static {
		NON_BLOCK = new BasicBlock((short)-1 );
		ID_TO_NAME = new HashMap<Short,String>();
		ID_TO_HUMAN_READABLE_NAME = new HashMap<Short,String>();
		NAME_TO_ID = new HashMap<String,Short>();
		HUMAN_REAABLE_NAME_TO_ID = new HashMap<String,Short>();
		
		InputStream csvStream = RenderingExecutor.class.getClassLoader().getResourceAsStream("blocks.csv");
		try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(csvStream, "UTF-8"))) {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String[] blockInfo = line.split(",");
				ID_TO_NAME.put(Short.parseShort(blockInfo[0]), blockInfo[1]);
				ID_TO_HUMAN_READABLE_NAME.put(Short.parseShort(blockInfo[0]), blockInfo[2]);
				NAME_TO_ID.put(blockInfo[1], Short.parseShort(blockInfo[0]));
			}
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
	
	public BasicBlock(String name) {
		this(NAME_TO_ID.get(name), 0);
	}
	
	public BasicBlock(String name, int data) {
		this(NAME_TO_ID.get(name), data);
	}
	
	public BasicBlock(BasicBlock original) {
		this.id = original.id;
		this.data = original.data;
	}
	
	public String getName() {
		return ID_TO_NAME.get(id);
	}
	
	public String getHumanReadableName() {
		return ID_TO_HUMAN_READABLE_NAME.get(id);
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
