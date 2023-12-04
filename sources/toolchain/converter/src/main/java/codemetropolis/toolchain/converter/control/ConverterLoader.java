package codemetropolis.toolchain.converter.control;

import java.util.Map;
import codemetropolis.toolchain.converter.gitstat.GitStatConverter;
import codemetropolis.toolchain.converter.gitlab.GitLabConverter;
import codemetropolis.toolchain.commons.cdf.converter.CdfConverter;
import codemetropolis.toolchain.converter.sonarqube.SonarQubeConverter;
import codemetropolis.toolchain.converter.sourcemeter.GraphConverter;
import codemetropolis.toolchain.converter.pmd.PmdConverter;

public class ConverterLoader {
	
	private ConverterLoader() {}
	
	public static CdfConverter load(ConverterType converterType, Map<String, String> params) {
		switch(converterType) {
			case GITLAB:
				return new GitLabConverter(params);
			case GITSTATS:
				return new GitStatConverter(params);
			case SOURCEMETER:
				return new GraphConverter(params);
			case SONARQUBE:
				return new SonarQubeConverter(params);
			case PMD:
				return new PmdConverter(params);
			default:
				return null;
		}
	}
}
