package codemetropolis.toolchain.rendering.model.primitive;

import java.io.File;

public interface Primitive {
	public int toCSVFile(File directory);
	public int getNumberOfBlocks();
}
