package codemetropolis.toolchain.rendering.model.primitive;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import codemetropolis.blockmodifier.World;
import codemetropolis.toolchain.commons.cmxml.Point;
import codemetropolis.toolchain.rendering.model.BasicBlock;

public class Boxel implements Primitive {
	
	public BasicBlock block;
	public Point position;
	public String info;
	
	public Boxel(BasicBlock block, Point position) {
		super();
		this.block = block;
		this.position = position;
	}
	
	public Boxel(BasicBlock block, Point position, String info) {
		this(block, position);
		this.info = info;
	}
	
	public void render(World world) {
		if(position.getY() < 0 || position.getY() >= 255) return;
		
		switch(block.getId()) {
			case 63:
				world.setSignPost(position.getX(), position.getY(), position.getZ(), block.getData(), info);
				break;
			case 68:
				world.setWallSign(position.getX(), position.getY(), position.getZ(), block.getData(), info);
				break;
			case 176:
				world.setBanner(position.getX(), position.getY(), position.getZ(), block.getData(), World.BannerColor.valueOf(info.toUpperCase()));
				break;
			default:
				world.setBlock(position.getX(), position.getY(), position.getZ(), block.getId(), block.getData());	
		}
	}
	
	public String toCSV() {
		//if(block == BasicBlock.NonBlock) return null;
		if (block.getId() == -1) return null;
		return String.format("%d;%d;%d;%d;%d;%s", block.getId(), block.getData(), position.getX(), position.getY(), position.getZ(), (info == null || info.equals("") ? "NULL" : info));
	}
	
	public static Boxel parseCSV(String csv) {
		String[] parts = csv.split(";");
		return new Boxel(
			BasicBlock.get(
				Short.parseShort(parts[0]),
				Integer.parseInt(parts[1])),
			new Point(
				Integer.parseInt(parts[2]),
				Integer.parseInt(parts[3]),
				Integer.parseInt(parts[4])),
			(parts[5].equals("NULL") ? "" : parts[5])
		);
	}
	
	@Override
	public int toCSVFile(File directory) {
		int x = position.getX() >> 9;
		int z = position.getZ() >> 9;
		
		directory.mkdirs();
		String filename = String.format("blocks.%d.%d.csv", x, z);
		File file = new File(directory, filename);
		
		try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {	
			String csv = toCSV();
			if(csv != null) writer.println(csv);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return 1;
	}

	@Override
	public int getNumberOfBlocks() {
		return 1;
	}

}
