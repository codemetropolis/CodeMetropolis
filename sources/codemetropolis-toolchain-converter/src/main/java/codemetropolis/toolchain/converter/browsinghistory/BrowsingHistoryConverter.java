package codemetropolis.toolchain.converter.browsinghistory;

import java.util.Map;

import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.cdf.converter.CdfConverter;
import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;

public class BrowsingHistoryConverter extends CdfConverter {

	public BrowsingHistoryConverter(Map<String, String> params) {
		super(params);
	}

	@Override
	public CdfTree createElements(String source) throws CodeMetropolisException {
		return null;
	}

}
