package codemetropolis.toolchain.rendering.control;

import java.io.*;
import java.util.zip.*;

public class LevelFile {
	
	private final File fileName;
    private RandomAccessFile file;
    
    public LevelFile(File path) {
    	fileName = path;
    	path.getParentFile().mkdirs();
        try {
            file = new RandomAccessFile(path, "rw");  
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public synchronized DataInputStream getLevelDataInputStream() {
    	try {
			byte[] data = new byte[(int) file.length()];
			file.read(data);
			return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(data))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public DataOutputStream getLevelDataOutputStream() {
        try {
			return new DataOutputStream(new GZIPOutputStream(new FileOutputStream(fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    public void close() throws IOException {
        file.close();
    }
}
