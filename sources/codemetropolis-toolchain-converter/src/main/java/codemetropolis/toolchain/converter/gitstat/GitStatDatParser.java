package codemetropolis.toolchain.converter.gitstat;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import codemetropolis.toolchain.commons.cdf.CdfProperty;

public class GitStatDatParser {
	//dat files names:
	final String[] names = { "commits_by_year", "commits_by_year_month", "day_of_week", "domains", "files_by_date",
			"hour_of_day", "lines_of_code", "month_of_year" };

	/**Load all lines from GitStat dat files into an ArrayList of CdfProperty
	 * @param folder string representation of the dat files folder
	 * @return An ArrayList of CdfProperty
	 */
	public static List<CdfProperty> getPropertiesFromDatFiles(String folder) {
		List<CdfProperty> properties = new ArrayList<CdfProperty>();
		
		//get a List of string representation from all dat files. The from is for example: commits_by_year_2014 1
		List<String> lines = getLinesFromDatFiles(folder);

		Iterator<String> itr = lines.iterator();

		while (itr.hasNext()) {
			String next = itr.next().toString();
			//cut the line on whitespace and replace _ to whitespace for example: commits_by_year_2014 1 ->  term = "commits by year 2014", description = "1"		
			StringTokenizer st = new StringTokenizer(next);
			String term = st.nextToken().trim().replace("_", " ");
			String description = st.nextToken();
			
			//add a new CdfProperty to properties list
			properties.add(new CdfProperty(term, description, CdfProperty.Type.INT));
		}

		System.out.println(properties);
		return properties;
	}

	
	/**Get lines from all rows of dat files plus add filename for example commits_by_year_2014 1
	 * @param folder string representation of the folder path
	 * @return lines a list of string from all rows of dat files 
	 */
	public static List<String> getLinesFromDatFiles(String folder) {
		List<String> lines = new ArrayList<String>();
		try {
			List<String> files = GitStatFileHelper.searchForFile(folder, ".dat");
			for (String file : files) {
				
				List<String> newLines = GitStatFileHelper.getLinesFromFile(file);
				
				for (String line : newLines) {
					line = GitStatDatParser.getPropertyPreNameFromFileName(file) + line;
					
					lines.add(line);
				}
					
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	/**Get filename without extension and a _ character
	 * @param fileName string representation of the dat filename
	 * @return filename without extension and a _ character for example commits_by_year_
	 */
	public static String getPropertyPreNameFromFileName(String fileName) {
				
		return fileName + "_";

	}

}
