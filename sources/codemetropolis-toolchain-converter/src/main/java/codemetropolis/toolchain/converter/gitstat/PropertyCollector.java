package codemetropolis.toolchain.converter.gitstat;

import java.util.ArrayList;
import java.util.List;

import codemetropolis.toolchain.commons.cdf.CdfProperty;

public class PropertyCollector {

	public static List<CdfProperty> collect(String source) {
		List<CdfProperty> collectedProperties = new ArrayList<>();
		
		if ( source != null ) {
			collectedProperties.addAll(GitStatHTMLParser.getPropertiesFromHTMLFiles(source));
			collectedProperties.addAll(GitStatDatParser.getPropertiesFromDatFiles(source));
		}
		
		return collectedProperties;
			
	}
	
}