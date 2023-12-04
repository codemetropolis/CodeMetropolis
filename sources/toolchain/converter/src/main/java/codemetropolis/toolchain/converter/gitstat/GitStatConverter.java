package codemetropolis.toolchain.converter.gitstat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.cdf.converter.CdfConverter;
import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;


public class GitStatConverter  extends CdfConverter {

	private static final String GITSTAT_ELEMENT_NAME = "GitStat";
	private static final String GITSTAT_ELEMENT_BASE_TYPE = "statistics";

	public GitStatConverter(Map<String, String> params) {
		super(params);
	}

	@Override
	public CdfTree createElements(String source) throws CodeMetropolisException {
		List<CdfProperty> properties = new ArrayList<>();
		properties = PropertyCollector.collect(source);
		
		// this version only makes one element, which contains all the properties
		// with TreeBuilder it can be extended to create nested elements
		CdfElement element = new CdfElement(GITSTAT_ELEMENT_NAME, GITSTAT_ELEMENT_BASE_TYPE);
		CdfTree tree = CdfTreeBuilder.createTree(element, properties);
		
		return tree;
	}

}