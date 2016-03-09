package codemetropolis.toolchain.converter.control;

import codemetropolis.toolchain.commons.cdf.CdfConverter;

public class ConverterLoader {
	
	private ConverterLoader() {}
	
	public static CdfConverter load(ConverterType converterType) {
		switch(converterType) {
			case SOURCEMETER:
				return new GraphConverter();
			case SONARQUBE:
				return new SonarQubeConverter();
			default:
				return null;
		}
	}
}
