package codemetropolis.toolchain.converter.gitinspector;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.commons.cdf.CdfTree;
import codemetropolis.toolchain.commons.cdf.converter.CdfConverter;
import codemetropolis.toolchain.commons.exceptions.CodeMetropolisException;

public class GitInspectorConverter extends CdfConverter {

	private static final String CHANGES_TAG = "changes";
	private static final String EMAIL_TAG = "email";
	private static final String BLAME_TAG = "blame";
	private static final String AUTHOR_TAG = "author";
	private static final String NAME_TAG = "name";
	private static final String COMMITS_TAG = "commits";
	private static final String INSERTIONS_TAG = "insertions";
	private static final String DELETIONS_TAG = "deletions";
	private static final String POC_TAG = "percentage-of-changes";
	private static final String POC_SHORT = "POC";
	private static final String ROWS_TAG = "rows";
	private static final String STABILITY_TAG = "stability";
	private static final String AGE_TAG = "age";
	private static final String PIC_TAG = "percentage-in-comments";
	private static final String PIC_SHORT = "PIC";
	private static final String FLOOR_TAG = "floor-metrics";
	private static final String REPOSITORY_TAG = "repository";
	private static final String DATE_TAG = "report-date";

	private HashMap<String, CdfElement> cdfElements;
	private CdfElement authorMetrics;
	private CdfElement floorMetrics;

	public HashMap<String, CdfElement> getCdfElements() {
		return cdfElements;
	}

	public CdfElement getAuthorMetrics() {
		return authorMetrics;
	}

	public CdfElement getFloorMetrics() {
		return floorMetrics;
	}

	public GitInspectorConverter(Map<String, String> params) {
		super(params);
		cdfElements = new HashMap<String, CdfElement>();
		authorMetrics = new CdfElement("", AUTHOR_TAG);
		floorMetrics = new CdfElement("", FLOOR_TAG);
	}

	@Override
	public CdfTree createElements(String source) throws CodeMetropolisException {
		Document doc = createDocumentFromSource(source);
		CdfElement rootElement = createRootelement(doc);
		traverseNodesFromDocument(doc);
		for (CdfElement element : cdfElements.values()) {
			rootElement.addChildElement(element);
		}
		return new CdfTree(rootElement);
	}

	public CdfElement createRootelement(Document doc) {
		StringBuilder rootName = new StringBuilder(doc.getElementsByTagName(REPOSITORY_TAG).item(0).getTextContent());
		rootName.append(" ");
		rootName.append(doc.getElementsByTagName(DATE_TAG).item(0).getTextContent());
		return new CdfElement(rootName.toString(), "root");
	}

	public Document createDocumentFromSource(String sourcePath) throws CodeMetropolisException {
		try {
			File inputFile = new File(sourcePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			return doc;
		} catch (Exception e) {
			throw new CodeMetropolisException(e.getMessage());
		}
	}

	private void traverseNodesFromDocument(Document doc) {
		Node changesNode = doc.getElementsByTagName(CHANGES_TAG).item(0);
		Node authorsNode = changesNode.getChildNodes().item(3);
		NodeList authorNodes = authorsNode.getChildNodes();

		for (int i = 0; i < authorNodes.getLength(); ++i) {
			Node authorNode = authorNodes.item(i);
			if (authorNode.getNodeType() == Node.ELEMENT_NODE) {
				Element authorElement = (Element) authorNode;
				CdfElement element = getMetricsFromFirstAuthorElementNode(authorElement);
				String name = authorElement.getElementsByTagName(NAME_TAG).item(0).getTextContent();
				cdfElements.put(name, element);
			}
		}

		Node blameNode = doc.getElementsByTagName(BLAME_TAG).item(0);
		authorsNode = blameNode.getChildNodes().item(3);
		authorNodes = authorsNode.getChildNodes();

		for (int i = 0; i < authorNodes.getLength(); ++i) {
			Node authorNode = authorNodes.item(i);
			if (authorNode.getNodeType() == Node.ELEMENT_NODE) {
				Element authorElement = (Element) authorNode;
				String name = authorElement.getElementsByTagName(NAME_TAG).item(0).getTextContent();

				CdfElement element = cdfElements.get(name);
				if (element != null) {
					element = updateMetricsFromSecondAuthorElementNode(authorElement, element);
					cdfElements.put(name, element);
				}
			}
		}
	}

	private CdfElement getMetricsFromFirstAuthorElementNode(Element authorElement) {
		String name = authorElement.getElementsByTagName(NAME_TAG).item(0).getTextContent();
		resetMetrics(name);

		updateMetrics(authorElement, EMAIL_TAG, EMAIL_TAG, CdfProperty.Type.STRING, false);
		updateMetrics(authorElement, COMMITS_TAG, COMMITS_TAG, CdfProperty.Type.INT, false);
		updateMetrics(authorElement, INSERTIONS_TAG, INSERTIONS_TAG, CdfProperty.Type.INT, true);
		updateMetrics(authorElement, DELETIONS_TAG, DELETIONS_TAG, CdfProperty.Type.INT, true);
		updateMetrics(authorElement, POC_TAG, POC_SHORT, CdfProperty.Type.FLOAT, false);

		authorMetrics.addChildElement(floorMetrics);
		return authorMetrics;
	}

	private CdfElement updateMetricsFromSecondAuthorElementNode(Element authorElement, CdfElement element) {
		authorMetrics = element;
		floorMetrics = authorMetrics.getChildElements().get(0);
		authorMetrics.removeChildElement(floorMetrics);

		updateMetrics(authorElement, ROWS_TAG, ROWS_TAG, CdfProperty.Type.INT, true);
		updateMetrics(authorElement, STABILITY_TAG, STABILITY_TAG, CdfProperty.Type.FLOAT, false);
		updateMetrics(authorElement, AGE_TAG, AGE_TAG, CdfProperty.Type.FLOAT, false);
		updateMetrics(authorElement, PIC_TAG, PIC_SHORT, CdfProperty.Type.FLOAT, false);

		authorMetrics.addChildElement(floorMetrics);
		return authorMetrics;
	}

	public static String downscalePossibleLargeNumericValue(String numberString) {
		long number = Long.parseLong(numberString);
		long newValue = (long) Math.floor(Math.sqrt(number));
		return Long.toString(newValue);
	}

	private void updateMetrics(Element authorElement, String readTag,
			String writeTag, CdfProperty.Type type, boolean remapNumeric) {
		String metricValue = authorElement.getElementsByTagName(readTag).item(0).getTextContent();
		if (remapNumeric) {
			metricValue = downscalePossibleLargeNumericValue(metricValue);
		}
		authorMetrics.addProperty(writeTag, metricValue, type);
		floorMetrics.addProperty(writeTag, metricValue, type);
	}

	public void resetMetrics(String name) {
		authorMetrics = new CdfElement(name, AUTHOR_TAG);
		floorMetrics = new CdfElement(name, FLOOR_TAG);
	}
}
