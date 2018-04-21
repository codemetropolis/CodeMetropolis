package codemetropolis.toolchain.converter.control;

import java.util.Map;

import codemetropolis.toolchain.commons.cdf.converter.CdfConverter;
import codemetropolis.toolchain.converter.browsinghistory.BrowsingHistoryConverter;
import codemetropolis.toolchain.converter.sonarqube.SonarQubeConverter;
import codemetropolis.toolchain.converter.gitinspector.GitInspectorConverter;
import codemetropolis.toolchain.converter.sourcemeter.GraphConverter;

public class ConverterLoader {
	
	private ConverterLoader() {}
	
	public static CdfConverter load(ConverterType converterType, Map<String, String> params) {
		switch(converterType) {
			case GITINSPECTOR:
				return new GitInspectorConverter(params);
			case SOURCEMETER:
				return new GraphConverter(params);
			case SONARQUBE:
				return new SonarQubeConverter(params);
			case BROWSINGHISTORY:
				return new BrowsingHistoryConverter(params);
			default:
				return null;
		}
	}
}
