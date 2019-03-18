package codemetropolis.toolchain.rendering.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import codemetropolis.toolchain.rendering.util.Colour;
import codemetropolis.toolchain.rendering.RenderingExecutor;

public class BasicBlock{
	
	public static final BasicBlock											NonBlock;
	protected static final HashMap<Short, HashMap<Integer, BasicBlock>>		idToBlock;
	protected static final HashMap<String, HashMap<Integer, BasicBlock>>	nameToBlock;
	protected static final HashMap<String, BasicBlock>						humanReadableNameToBlock;
	protected static final HashMap<String, Boolean>							used;
	
	static{
		NonBlock = new BasicBlock((short) -1, 0, "NonBlock", "A non-existant block", (short) 2);
		idToBlock = new HashMap<Short, HashMap<Integer, BasicBlock>>();
		nameToBlock = new HashMap<String, HashMap<Integer, BasicBlock>>();
		humanReadableNameToBlock = new HashMap<String, BasicBlock>();
		used = new HashMap<String, Boolean>();
		
		InputStream csvStream = RenderingExecutor.class.getClassLoader().getResourceAsStream("blocks.csv");
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(csvStream, "UTF-8"))){
			String line;
			while ((line = bufferedReader.readLine()) != null){
				String[] blockInfo = line.split(",");
				BasicBlock block = new BasicBlock(
						Short.parseShort(blockInfo[0]),
						Short.parseShort(blockInfo[1]),
						blockInfo[2],
						blockInfo[3],
						Short.parseShort(blockInfo[4])
				);
				
				add(idToBlock, block.id, block);
				add(nameToBlock, block.name, block);
				
				humanReadableNameToBlock.put(block.getHumanReadableName().toLowerCase(), block);
				// System.out.println(block);
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private short	id;
	private int		data;
	private String	name;
	private String	humanReadableName;
	private short	hazardous;
	
	public BasicBlock(BasicBlock original){
		this.id = original.id;
		this.data = original.data;
		this.name = original.name;
		this.humanReadableName = original.humanReadableName;
		this.hazardous = original.hazardous;
	}
	
	protected BasicBlock(short id, int data, String name, String humanReadableName, short hazardous){
		this.id = id;
		this.data = data;
		this.name = name;
		this.humanReadableName = humanReadableName;
		this.hazardous = hazardous;
	}
	
	protected static <K> void add(HashMap<K, HashMap<Integer, BasicBlock>> map, K key, BasicBlock block){
		/*
		 * This method overwrites previous key-BasicBlock pairs in the HashMap
		 * This is intentional behaviour, since .csv may contain
		 * "simplifications"
		 */
		HashMap<Integer, BasicBlock> blocks = map.get(key);
		if (blocks == null){
			blocks = new HashMap<Integer, BasicBlock>();
			map.put(key, blocks);
		}
		blocks.put(block.data, block);
	}
	
	// Return block
	// String is the human readable name
	// This method is used on user-inputed strings and it checks if it's allowed
	public static BasicBlock userBlock(String name, BasicBlock fallback){
		BasicBlock block = humanReadableNameToBlock.get(name);
		if (!used.containsKey(name)){
			used.put(name, true);
			if (block == null){
				System.out.println("ERROR\tBlock \"" + name + "\" was not found!");
				System.out.println("\tInstead, using \"" + fallback.getHumanReadableName() + "\".");
			}
			else{
				switch (block.getHazardous()){
					case 0:
						System.out.println("SUCCESS\tUsing \"" + block.getHumanReadableName() + "\" block.");
						break;
					case 1:
						System.out.println("WARNING\tUsing \"" + block.getHumanReadableName() + "\" DANGEROUS block.");
						break;
					default:
						System.out.println("ILLEGAL\tUse of \"" + block.getHumanReadableName() + "\" is restricted!");
						System.out.println("\tFalling back to \"" + fallback.getHumanReadableName() + "\".");
						break;
				}
			}
		}
		return (block != null && block.getHazardous() != 2) ? block : fallback;
	}
	
	// Return block
	// String is the in-game name
	public static BasicBlock get(String name, int data){
		BasicBlock ret = nameToBlock.get(name).get(data);
		if (ret == null)
			System.out.println("NullPointerException incoming!\nBlock name: " + name + ", data: " + data);
		return ret;
	}
	
	public static BasicBlock get(String name, Colour clr){
		return get(name, clr.getValue());
	}
	
	public static BasicBlock get(String name){
		return get(name, 0);
	}
	
	// Return block
	// Int is the ID
	public static BasicBlock get(short id, int data){
		BasicBlock ret = idToBlock.get(id).get(data);
		if (ret == null)
			System.out.println("NullPointerException incoming!\nBlock id: " + id + ", data: " + data);
		return ret;
	}
	
	public static BasicBlock get(short id, Colour clr){
		return get(id, clr.getValue());
	}
	
	public static BasicBlock get(short id){
		return get(id, 0);
	}
	
	public short getId(){
		return id;
	}
	
	public int getData(){
		return data;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getHumanReadableName(){
		return this.humanReadableName;
	}
	
	public short getHazardous(){
		return this.hazardous;
	}
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + data;
		result = prime * result + id;
		return result;
	}
	
	@Override
	public boolean equals(Object obj){
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
	public String toString(){
		String str = "Block object: \"" + getHumanReadableName() + "\"";
		str += " (id: " + id + ", name: " + name + ", data: " + data + ", hazard: ";
		switch (hazardous){
			case 0:
				str += "safe";
				break;
			case 1:
				str += "warn";
				break;
			default:
				str += "forbidden";
				break;
		}
		str += ")";
		return str;
	}
	
}
