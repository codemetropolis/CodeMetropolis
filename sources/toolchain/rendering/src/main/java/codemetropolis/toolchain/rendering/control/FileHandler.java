package codemetropolis.toolchain.rendering.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileHandler {

	public static void copy(String srcPath, String destPath, String... ignore) {
		File sourceLocation = new File(srcPath);
		File targetLocation = new File(destPath);
		copy(sourceLocation, targetLocation, ignore);
	}
	
	public static void copy(File sourceLocation, File targetLocation, String... ignore) {
		for(String s : ignore)
			if(sourceLocation.getName().equals(s)) return;
		try {
			if (sourceLocation.isDirectory()) {
			    copyDirectory(sourceLocation, targetLocation, ignore);
			} else {
			    copyFile(sourceLocation, targetLocation);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void copyDirectory(File source, File target, String... ignore) throws IOException {
	    if (!target.exists()) {
	        target.mkdirs();
	    }

	    for (String f : source.list()) {
	        copy(new File(source, f), new File(target, f), ignore);
	    }
	}

	private static void copyFile(File source, File target) throws IOException {
		try (
	            InputStream in = new FileInputStream(source);
	            OutputStream out = new FileOutputStream(target)
	    ) {
	        byte[] buf = new byte[1024];
	        int length;
	        while ((length = in.read(buf)) > 0) {
	            out.write(buf, 0, length);
	        }
	    }
	}
	
}