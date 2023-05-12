package codemetropolis.toolchain.converter.gitstat;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GitStatFileHelper {
	
	/**Loading all lines from a file into an ArrayList<>	   
	   * @param filepath string representation of the file path
	   * @return all lines of the file loaded in a list
	  **/
	public static List<String> getLinesFromFile(String filepath) throws IOException
	{	
		if (filepath == null) {
			return null;
		}
		List<String> lines = Files.readAllLines(Paths.get(filepath), Charset.forName("UTF-8"));
		/*for(String line : lines){
		  System.out.println(line);
		}*/
		return lines;
	}
	
	/**Lists all files in a folder that ends with the given file extension or filename.
	   * @param folder string representation of the folder path
	   * @param type file extension or filename with extension e.g.: ".txt" or "tags.html"
	   * @return Path of the files in the directory with the given extension or name in a List<>
	  **/
	public static List<String> searchForFile(String folder, String type)
	{		
		if (folder != null && type != null ) {
			List<String> selectedFiles = new ArrayList<String>();
			 DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
		         public boolean accept(Path file) throws IOException {
		             return (!Files.isDirectory(file) && file.getFileName().toString().toLowerCase().endsWith(type));
		         }
		     };
		     
		     try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(folder), filter)) {
		    	 for (Path entry : stream) {
		    		 selectedFiles.add(entry.toString());
		    	 }
		     }
		     catch (Exception ex) {
		    	 ex.printStackTrace();
		     }
		     return selectedFiles;		
		} else {
			return new ArrayList<String>();
		}
	}
}