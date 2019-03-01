package codemetropolis.toolchain.gui.components.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JList;
import javax.swing.ListModel;

import codemetropolis.toolchain.gui.components.CMTable;
import codemetropolis.toolchain.mapping.conversions.Conversion;
import codemetropolis.toolchain.mapping.exceptions.MappingWriterException;
import codemetropolis.toolchain.mapping.model.Binding;
import codemetropolis.toolchain.mapping.model.Constant;
import codemetropolis.toolchain.mapping.model.Linking;
import codemetropolis.toolchain.mapping.model.Mapping;
import codemetropolis.toolchain.mapping.model.Parameter;

public class SaveMappingListener implements ActionListener {
	
	//private String savePath;	
	private List<CMTable> tables;
	private JList<String> resources;
	
	/**
	 * Creates a new instance of the {@link SaveMappingListener} class.
	 */
	public SaveMappingListener() {}
	
	/**
	 * Creates a new instance of the {@link SaveMappingListener} class with the given parameters.
	 * @param savePath The path where we want to save the mapping file.
	 * @param tables The list of tables which contain every necessary information to create linkings, bindings and conversions.
	 * @param resources The list of resources defined by the user.
	 */
	public SaveMappingListener(String savePath, List<CMTable> tables, JList<String> resources) {
		//this.savePath = savePath;
		//savePath.replace("\\", "/"); Didn't work...
		this.tables = tables;
		this.resources = resources;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Mapping mapping = new Mapping();
		mapping.setVersion("2.0");
		
		addResources(mapping);
		addLinkings(mapping);
		
		try {
			//!!!!
			mapping.writeToXML("C:\\Users\\Viktor\\Documents\\output_mappingX.xml");
		} catch (MappingWriterException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Adds the list of resources to a {@link Mapping} instance.
	 * @param mapping The {@link Mapping} instance to which we want to add the list of resources.
	 */
	private void addResources(Mapping mapping) {
		ListModel<String> model = resources.getModel();
		
		for(int i = 0; i < model.getSize(); i++) {
			String id = model.getElementAt(i).split(": ")[0];
			String value = model.getElementAt(i).split(": ")[1];
			
			Constant resourceToAdd = new Constant(id, value);
			
			mapping.addResource(resourceToAdd);
		}
	}
	
	/**
	 * Adds the list of linkings to the {@link Mapping} object.
	 * @param mapping The {@link Mapping} object to which we want to add the linkings.
	 */
	private void addLinkings(Mapping mapping) {
		mapping.addLinking(new Linking("package", "ground"));
		for(CMTable table : tables) {			
			Linking linking = new Linking();
			linking.setTarget(table.getTarget());
			linking.setSource(table.getSource());
			addBindings(linking, table);
			mapping.addLinking(linking);
		}
	}

	/**
	 * Adds the list of bindings to the {@link Linking} given as the parameter.
	 * @param linking The {@link Linking} to which we want to add the list of bindings.
	 * @param table The {@link CMTable} which holds the binding information. A row of the table represents a binding.
	 */
	private void addBindings(Linking linking, CMTable table) {
		//Iteration through the rows of the table - every row represents a possible binding.
		for(int i = 0; i < table.getRowCount(); i++) {
			Object assignedProperty = table.getValueAt(i, 1);
			if(assignedProperty != null) {
				Binding binding = new Binding();
				
				String to = ((String) table.getValueAt(i, 0)).split(": ")[0];
				String from = null;
				//Deciding that the assigned property is a resource or a metric.
				String var = (String) table.getValueAt(i, 1);
				if(!isResource(var)) {
					from = var.split(": ")[0];
				}
				else {
					//Apply "resource/constant" formatting...
					from = "${" + var.split(": ")[0] + "}";
				}
				
				binding.setFrom(from);
				binding.setTo(to);
				
				//Check if we have to use a conversion or not. In the second case we add it to the binding.
				Object conversion = table.getConversionList().get(i);
				Conversion conversionToAdd = null;
				if(conversion != null) {
					if(conversion instanceof String) {
						//Possible types: to_int, to_double, normalize
						//No need to set parameters(?)
						conversionToAdd = Conversion.createFromName((String) conversion);
					}
					if(conversion instanceof codemetropolis.toolchain.gui.conversions.QuantizationConversion) {
						//If in the table stored conversion is a quantization, then we instantiate a QuantizationConversion, then load its parameters...
						conversionToAdd = new codemetropolis.toolchain.mapping.conversions.QuantizationConversion();
						
						List<String> quantizationLevels = ((codemetropolis.toolchain.gui.conversions.QuantizationConversion) conversion).getLevels();
						for(int j = 0; j < quantizationLevels.size(); j++) {
							String name = "level" + j; // "level2", "level4", ...
							String value = quantizationLevels.get(j);
							Parameter param = new Parameter(name, value);
							conversionToAdd.addParameter(param);
						}
					}
					binding.addConversion(conversionToAdd);
				}
				
				linking.addBinding(binding);
			}
		}
	}
	
	/**
	 * Returns if property is a resource or not.
	 * @param var The cell value we want to examine.
	 * @return The property is a resource or not.
	 */
	private boolean isResource(String var) {
		String varType = var.split(": ")[1];
		if(varType.equals("int") || varType.equals("float") || varType.equals("string")) {
			return false;
		}
		return true;
	}

}
