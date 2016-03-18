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
	
	public static void createDirectories(String path) {
		File file = new File(path);
		if(file.isDirectory()) {
			file.mkdirs();
		} else {
			File parent = file.getParentFile();
			if(parent != null) {
				file.mkdirs();
			}
		}
	}
	
}
