package codemetropolis.toolchain.converter.gitstat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import codemetropolis.toolchain.commons.cdf.CdfProperty;

public class GitStatHTMLParser {
	
	public static List<CdfProperty> getPropertiesFromHTMLFiles(String folder){
		List<CdfProperty> properties = new ArrayList<CdfProperty>();
		
		List<String> lines = getLinesFromHTMLFiles(folder);
		
		Iterator itr = lines.iterator();
		
		while (itr.hasNext()) {
			String next = itr.next().toString().trim().replaceAll("\t", "");
			if (next.startsWith("<dl>")) {
				List<CdfProperty> newProperties = descriptionListToProperty(itr);
				properties.addAll(newProperties);
			}
			else if (next.startsWith("<table class=\"sortable\" id=\"ext\">")) {
				List<CdfProperty> newProperties = tableToProperty(itr, "Extensions");
				properties.addAll(newProperties);
			}
		}
		System.out.println(properties);
		return properties;
	}
	
	public static List<String> getLinesFromHTMLFiles(String folder){
		List<String> lines = new ArrayList<String>();
		try {
			List<String> files = GitStatFileHelper.searchForFile(folder, ".html");
			for (String file : files) {
				System.out.println(file);
				List<String> newLines = GitStatFileHelper.getLinesFromFile(file);
				lines.addAll(newLines);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines;
	}
	
	private static List<CdfProperty> descriptionListToProperty(Iterator itr){
		List<CdfProperty> newProperties = new ArrayList<CdfProperty>();
		String term = itr.next().toString().trim().replaceAll("\t", "");
		do {
			term = term.replaceAll("<dt>", "").replaceAll("</dt>", "");
			String description = itr.next().toString().trim().replaceAll("\t", "").replaceAll("<dd>", "").replaceAll("</dd>", "");
			if (isNumber(description)) {
				newProperties.add(new CdfProperty(term, description, CdfProperty.Type.INT));
			}
			else {
				newProperties.add(new CdfProperty(term, description, CdfProperty.Type.STRING));
			}
			//System.out.println(newProperties);
			term = itr.next().toString().trim();
		}
		while (!term.startsWith("</dl>"));
		
		return newProperties;
	}
	
	private static List<CdfProperty> tableToProperty(Iterator itr, String tableName){
		int rowCount = 1;
		List<CdfProperty> newProperties = new ArrayList<CdfProperty>();		
		List<String> headers = new ArrayList<String>();
		
		itr.next();
		String line = itr.next().toString().trim().replaceAll("\t", "");
		
		while (!line.startsWith("</tr>")) {
			line = line.replaceAll("<th>", "").replaceAll("</th>", "");
			headers.add(line);
			line = itr.next().toString().trim().replaceAll("\t", "");
		}
		
		line = itr.next().toString().trim().replaceAll("\t", "");
		
		while (!line.startsWith("</table>")) {
			for (int i=0; i< headers.size(); i++) {
				line = itr.next().toString().trim().replaceAll("\t", "").replaceAll("<td>", "").replaceAll("</td>", "");
				if (isNumber(line)) {
					newProperties.add(new CdfProperty(tableName + "_Table_Row" + rowCount + "_" + headers.get(i), line, CdfProperty.Type.INT));
				}
				else {
					newProperties.add(new CdfProperty(tableName + "_Table_Row" + rowCount + "_" + headers.get(i), line, CdfProperty.Type.STRING));
				}
			}
			rowCount++;
			itr.next();
			line = itr.next().toString().trim().replaceAll("\t", "");
		}
		return newProperties;
	}
	
	public static boolean isNumber(String value) {
		try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }
	}
	
}