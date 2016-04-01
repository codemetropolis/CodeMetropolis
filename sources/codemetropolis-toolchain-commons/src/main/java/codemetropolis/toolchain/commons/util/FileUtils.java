package codemetropolis.toolchain.commons.util;

import java.io.File;

public class FileUtils {
	
	public static void deleteDirectory(File directory) {
		if(directory.isDirectory()) {
			for(File f : directory.listFiles()) {
				deleteDirectory(f);
			}
		}
		directory.delete();
	}
	
	public static void createContainingDirs(String path) {
		File parent = new File(path).getParentFile();
		if(parent != null) {
			parent.mkdirs();
		}
	}
	
}
