package codemetropolis.toolchain.converter.control;

import java.util.Map;

import codemetropolis.toolchain.commons.cdf.converter.CdfConverter;
import codemetropolis.toolchain.converter.sonarqube.SonarQubeConverter;
import codemetropolis.toolchain.converter.sourcemeter.GraphConverter;

public class ConverterLoader {
	
	private ConverterLoader() {}
	
	public static CdfConverter load(ConverterType converterType, Map<String, String> params, boolean verboseMode) {
		switch(converterType) {
			case SOURCEMETER:
				return new GraphConverter(params, verboseMode);
			case SONARQUBE:
				return new SonarQubeConverter(params, verboseMode);
			default:
				return null;
		}
	}
}
