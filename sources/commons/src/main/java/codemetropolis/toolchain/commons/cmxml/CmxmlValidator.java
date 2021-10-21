package codemetropolis.toolchain.commons.cmxml;

import java.io.IOException;

import codemetropolis.toolchain.commons.exceptions.InvalidSchemeException;
import codemetropolis.toolchain.commons.exceptions.SchemeNotSetException;
import codemetropolis.toolchain.commons.util.XmlValidator;

public class CmxmlValidator {
	
	private static final String SCHEME_PATH = "cmxml_scheme.xsd";
	private static XmlValidator xmlValidator;
	
	static {
		xmlValidator = new XmlValidator();
		try {
			xmlValidator.setScheme(SCHEME_PATH);
		} catch (InvalidSchemeException e) {
			System.err.println("CMXML validator initialization failed.");
		}
	}
	
	public static boolean validate(String xmlPath) throws IOException {
		try {
			return xmlValidator.validate(xmlPath);
		} catch(SchemeNotSetException e) {
			System.err.println("CMXML validation failed.");
			return false;
		}
	}
	
}
