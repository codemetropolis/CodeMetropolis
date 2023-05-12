package codemetropolis.toolchain.converter.gitstat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import codemetropolis.toolchain.commons.cdf.CdfElement;
import codemetropolis.toolchain.commons.cdf.CdfProperty;
import codemetropolis.toolchain.commons.cdf.CdfTree;

public class CdfTreeBuilder { 
	
	public static CdfTree createTreeFromMap( Map<CdfElement, List<CdfProperty>> map ) {
		CdfTree tree = new CdfTree();
		if ( map != null) {
			map.keySet()
				.stream()
				.forEachOrdered(element->addPropertiesToElement(element, map.get(element), tree));
		}
		return tree;
	}

	public static void addPropertiesToElement(CdfElement element,
			List<CdfProperty> list, CdfTree tree) {
		list.stream()
			.forEachOrdered(p->addPropertyToElement(element, p, tree));
	}
	
	public static void addPropertyToElement(CdfElement element, CdfProperty p, CdfTree tree) {
		if ( element != null && p != null && tree !=null) {
			element.addProperty(p.getName(), p.getValue(), p.getType());
			if ( tree.getRoot() == null) {
				tree.setRoot(element);
			} else {
				tree.getRoot().addChildElement(element);
			}
		}
	}

	public static CdfTree createTree(CdfElement element, List<CdfProperty> properties) {
		
		CdfTree tree = new CdfTree();
		Map<String, CdfElement> rowElementMap = new HashMap<>();
		Map<String, CdfElement> dateStatisticsMap = new HashMap<>();
		List<CdfProperty> otherProperties = new ArrayList<>();
		
		for (CdfProperty property : properties) {
			String propertyName = property.getName();
			if (propertyName.endsWith("Extension")) {
				propertyName = (propertyName.substring(0, propertyName.length() - "Extension".length()));
				String rowNumber = propertyName.substring(
						propertyName.indexOf("Row") + "Row".length(), propertyName.length() - 1);
				System.out.println("rownumber = " +rowNumber);
				rowElementMap.put("Row_" + rowNumber, new CdfElement(property.getValue(), "Extension"));
			} else if (propertyName.contains("Extensions")) {
				String suffix = propertyName.substring(propertyName.lastIndexOf('_') + 1, 
						propertyName.lastIndexOf(' ') == -1 ? propertyName.length() : propertyName.lastIndexOf(' ') );
				System.out.println("suffix: " + suffix);
				propertyName = (propertyName.substring(0, propertyName.indexOf(suffix) - 1));
				System.out.println("propertyName: " + propertyName);
				String rowNumber = propertyName.substring(
						propertyName.indexOf("Row") + "Row".length(), propertyName.length());
				String key = "Row_" + rowNumber;
				System.out.println("key " + key);
				if ( rowElementMap.containsKey(key)  ) {
					System.out.println("FOUND!");
					rowElementMap.get(key).addProperty(suffix, property.getValue().substring(
							0, property.getValue().lastIndexOf(' ') == -1 ? property.getValue().length() : property.getValue().lastIndexOf(' ')), CdfProperty.Type.INT);
				}
			} else if ( propertyName.contains("commits by year") ) {
				if (!dateStatisticsMap.containsKey("commits by year")) {
					dateStatisticsMap.put("commits by year", new CdfElement("commits by year", "statistics"));
				} 
				dateStatisticsMap.get("commits by year").addProperty(propertyName.substring("commits by year".length()), property.getValue(), CdfProperty.Type.INT);
			} else if ( propertyName.contains("day of week") ) {
				if (!dateStatisticsMap.containsKey("day of week")) {
					dateStatisticsMap.put("day of week", new CdfElement("day of week", "statistics"));
				} 
				dateStatisticsMap.get("day of week").addProperty(propertyName.substring("day of week".length()), property.getValue(), CdfProperty.Type.INT);
			} else if ( propertyName.contains("files by date") ) {
				if (!dateStatisticsMap.containsKey("files by date")) {
					dateStatisticsMap.put("files by date", new CdfElement("files by date", "statistics"));
				} 
				dateStatisticsMap.get("files by date").addProperty(propertyName.substring("files by date".length()), property.getValue(), CdfProperty.Type.INT);
			} else if ( propertyName.contains("hour of day") ) {
				if (!dateStatisticsMap.containsKey("hour of day")) {
					dateStatisticsMap.put("hour of day", new CdfElement("hour of day", "statistics"));
				} 
				dateStatisticsMap.get("hour of day").addProperty(propertyName.substring("hour of day".length()), property.getValue(), CdfProperty.Type.INT);
			} else if ( propertyName.contains("lines of code") ) {
				if (!dateStatisticsMap.containsKey("lines of code")) {
					dateStatisticsMap.put("lines of code", new CdfElement("lines of code", "statistics"));
				} 
				dateStatisticsMap.get("lines of code").addProperty(propertyName.substring("lines of code".length()), property.getValue(), CdfProperty.Type.INT);
			} else if ( propertyName.contains("month of year") ) {
				if (!dateStatisticsMap.containsKey("month of year")) {
					dateStatisticsMap.put("month of year", new CdfElement("month of year", "statistics"));
				} 
				dateStatisticsMap.get("month of year").addProperty(propertyName.substring("month of year".length()), property.getValue(), CdfProperty.Type.INT);
			} else {
				otherProperties.add(property);
			}
		}
		
		
		
		
		CdfElement root = new CdfElement("root", "root");
		tree.setRoot(root);
		for ( String key : rowElementMap.keySet() ) {
			CdfElement row = new CdfElement(key, "row");
			row.addChildElement(rowElementMap.get(key));
			root.addChildElement(row);
		}
		for ( String key : dateStatisticsMap.keySet()) {
			CdfElement stat = new CdfElement(key, "stat");
			stat.addChildElement(dateStatisticsMap.get(key));
			root.addChildElement(stat);
		}
		for ( CdfProperty property : otherProperties) {
			root.addProperty(property.getName(), property.getValue(), property.getType());
		}
		
		return tree;
	}

}