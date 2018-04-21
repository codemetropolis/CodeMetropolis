package codemetropolis.toolchain.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Rectangle;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileFilter;

import codemetropolis.toolchain.gui.beans.BadConfigFileFomatException;
import codemetropolis.toolchain.gui.beans.QuantizationInformation;
import codemetropolis.toolchain.gui.components.CMButton;
import codemetropolis.toolchain.gui.components.CMCheckBox;
import codemetropolis.toolchain.gui.components.CMLabel;
import codemetropolis.toolchain.gui.components.CMScrollPane;
import codemetropolis.toolchain.gui.components.CMTextField;
import codemetropolis.toolchain.gui.components.listeners.BrowseListener;
import codemetropolis.toolchain.gui.conversions.Conversion;
import codemetropolis.toolchain.gui.conversions.QuantizationConversion;
import codemetropolis.toolchain.gui.utils.BuildableSettings;
import codemetropolis.toolchain.gui.utils.Property;
import codemetropolis.toolchain.gui.utils.PropertyCollector;
import codemetropolis.toolchain.gui.utils.TransferHelper;
import codemetropolis.toolchain.gui.utils.Translations;
import codemetropolis.toolchain.gui.utils.XmlFileFilter;

/**
 * Dialog for the mapping file editor.
 * @author Viktor Meszaros {@literal <MEVXAAT.SZE>}
 */
public class MappingFileEditorDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private static final FileFilter XML_FILTER;

	static public List<Conversion> cellarConversion;
	static public List<Conversion> gardenConversion;
	static public List<Conversion> floorConversion;

	static public Map<QuantizationInformation, QuantizationConversion> cellarQuant;
	static public Map<QuantizationInformation, QuantizationConversion> gardenQuant;
	static public Map<QuantizationInformation, QuantizationConversion> floorQuant;
	
	/**
	 * Contains the possible results of assigning a metric to a property of a buildable type.
	 */
	public enum AssignResult {CANNOT_ASSIGN, NO_CONVERSION, TO_INT, TO_DOUBLE, NORMALIZE, QUANTIZATON};
	
	public static final Map<String, Map<String, AssignResult>> ASSIGN_RESULT_MATRIX;
	
	static {
		XML_FILTER = new XmlFileFilter();

		cellarConversion = new ArrayList<Conversion>();
		gardenConversion = new ArrayList<Conversion>();
		floorConversion = new ArrayList<Conversion>();

		cellarQuant = new HashMap<QuantizationInformation, QuantizationConversion>();
		gardenQuant = new HashMap<QuantizationInformation, QuantizationConversion>();
		floorQuant = new HashMap<QuantizationInformation, QuantizationConversion>();

		ASSIGN_RESULT_MATRIX = new HashMap<String, Map<String, AssignResult>>();
		
		ASSIGN_RESULT_MATRIX.put("int", new HashMap<String, AssignResult>());
		ASSIGN_RESULT_MATRIX.put("int(0 to 5)", new HashMap<String, AssignResult>());
		ASSIGN_RESULT_MATRIX.put("string", new HashMap<String, AssignResult>());
		ASSIGN_RESULT_MATRIX.put("float(0 to 1)", new HashMap<String, AssignResult>());
		//First row of the "matrix"
		ASSIGN_RESULT_MATRIX.get("int").put("int", AssignResult.NO_CONVERSION);
		ASSIGN_RESULT_MATRIX.get("int").put("float", AssignResult.TO_INT);
		ASSIGN_RESULT_MATRIX.get("int").put("string", AssignResult.CANNOT_ASSIGN);
		//Second row of the "matrix"
		ASSIGN_RESULT_MATRIX.get("int(0 to 5)").put("int", AssignResult.QUANTIZATON);
		ASSIGN_RESULT_MATRIX.get("int(0 to 5)").put("float", AssignResult.QUANTIZATON);
		ASSIGN_RESULT_MATRIX.get("int(0 to 5)").put("string", AssignResult.CANNOT_ASSIGN);
		//Third row of the "matrix"
		ASSIGN_RESULT_MATRIX.get("string").put("int", AssignResult.QUANTIZATON);
		ASSIGN_RESULT_MATRIX.get("string").put("float", AssignResult.QUANTIZATON);
		ASSIGN_RESULT_MATRIX.get("string").put("string", AssignResult.NO_CONVERSION);
		//Fourth row of the "matrix"
		ASSIGN_RESULT_MATRIX.get("float(0 to 1)").put("int", AssignResult.NORMALIZE);
		ASSIGN_RESULT_MATRIX.get("float(0 to 1)").put("float", AssignResult.NORMALIZE);
		ASSIGN_RESULT_MATRIX.get("float(0 to 1)").put("string", AssignResult.CANNOT_ASSIGN);
	}
		
	private Map<String, String[]> displayedBuildableAttributes;
	private Map<String, List<Property>> sourceCodeElementProperties;
	
	private JTabbedPane buildableTabbedPane;	
	private JPanel cellarPanel;
	private JPanel floorPanel;
	private JPanel gardenPanel;
	private JPanel groundPanel;
	private static JTable cellarTable;
	private static JTable floorTable;
	private static JTable gardenTable;
	
	//ListModel and JList for the buildables: cellar, floor, garden
	private ListModel<String> cellarListmodel;
	private JList<String> cellarList;
	private ListModel<String> floorListmodel;
	private JList<String> floorList;
	private ListModel<String> gardenListmodel;
	private JList<String> gardenList;
	
	//ListModel and JList for the resources
	private ListModel<String> resourcesListmodel;
	private JList<String> resourcesList;
	
	private CMCheckBox useMappingCheckBox;
	private CMTextField pathField;
	
	/**
	 * Loads the list of the buildable attributes which are desired to display on the GUI from the configuration file.
	 * Loads the list of the source code element from the given input cdf xml file.
	 * @param cdfFilePath The path of the input cdf xml file.
	 */
	private void loadDisplayedInfo(String cdfFilePath) {
		try {
			BuildableSettings settings = new BuildableSettings();
			displayedBuildableAttributes = settings.readSettings();
						
		}
		catch(BadConfigFileFomatException e) {
			JOptionPane.showMessageDialog(
					null,
					Translations.t("gui_err_invaild_config_file_format"),
					Translations.t("gui_err_title"),
					JOptionPane.ERROR_MESSAGE);
			
			displayedBuildableAttributes = BuildableSettings.DEFAULT_SETTINGS;
		}
		catch(FileNotFoundException e) {
			JOptionPane.showMessageDialog(
					null,
					Translations.t("gui_err_config_file_not_found"),
					Translations.t("gui_err_title"),
					JOptionPane.ERROR_MESSAGE);
			
			displayedBuildableAttributes = BuildableSettings.DEFAULT_SETTINGS;
		}
		try {
			PropertyCollector pc = new PropertyCollector();
			sourceCodeElementProperties = pc.getFromCdf(cdfFilePath);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	 /**
	  * Instantiates the Mapping file editor dialog.
	  *
	  * @param cdfFilePath The path of the input cdf xml file.
	  * @param cmGui The parent window of the dialog.
	  */
	public MappingFileEditorDialog(String cdfFilePath, CodeMetropolisGUI cmGui) {
		super(cmGui, Translations.t("gui_mapping_editor_title") ,true);
		loadDisplayedInfo(cdfFilePath);
		
		JPanel panel = createBasePanel();
		addResourceOptions(panel);
		addSaveOptions(panel);
		addBuildableTabs(panel);
		addConversionOptions(panel);
		
		this.setResizable(false);
	    this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
	    this.setContentPane(panel);
	    this.pack();
	    this.setLocationRelativeTo(cmGui);
	}
	
	/**
	 * Creates the base panel for the Mapping file editor dialog.
	 * 
	 * @return The created {@link JPanel}.
	 */
	private JPanel createBasePanel() {
		JPanel panel = new JPanel();
	    panel.setLayout(null);
	    panel.setBounds(0, 0, 800, 550);

	    Dimension size = new Dimension(800, 550);
	    panel.setMinimumSize(size);
	    panel.setPreferredSize(size);
	    panel.setMaximumSize(size);

	    return panel;
	}
	
	/**
	 * Adds the resource options to the {@code panel}.
	 * @param panel The {@link JPanel} to which the components will be added to.
	 */
	private void addResourceOptions(JPanel panel) {
		CMLabel resourcesLabel = new CMLabel(Translations.t("gui_l_resources"), 10, 0, 120, 30);
		
		resourcesListmodel = new DefaultListModel<String>();
	    resourcesList = new JList<String>(resourcesListmodel);
	    resourcesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    resourcesList.setLayoutOrientation(JList.VERTICAL);
	    resourcesList.setVisibleRowCount(-1);
		CMScrollPane resourcesScrollPane = new CMScrollPane(resourcesList, 10, 35, 240, 120);
		
		CMButton resourcesAddButton = new CMButton(Translations.t("gui_b_add"), 265, 35, 120, 30);
		resourcesAddButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextField nameField = new JTextField();
				JTextField valueField = new JTextField();
				
				JPanel addResourcePanel = new JPanel();
				addResourcePanel.setLayout(new GridLayout(4, 2));
				addResourcePanel.add(new JLabel("Resource name:"));
				addResourcePanel.add(nameField);
				addResourcePanel.add(new JLabel("Resource value:"));
				addResourcePanel.add(valueField);
				
				int result = JOptionPane.showConfirmDialog(null, addResourcePanel, Translations.t("gui_add_resource_title"), JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(result == JOptionPane.OK_OPTION) {
					if (nameField.getText().matches("[a-zA-Z0-9]+") &&
						(valueField.getText().matches("[0-9]+(.[0-9]+)?")) ||  BuildableSettings.VALID_CHARACTER_TYPES.contains(valueField.getText())) {
						//Produce the resource string from the text fields...
						String resourceToAdd = nameField.getText() + ": " + valueField.getText();
						//Add the newly defined resource to the property lists of the buildables and to the resource list (on top left of the window).
						List<JList<String>> lists = Arrays.asList(resourcesList, cellarList, floorList, gardenList);
						for (JList<String> list : lists) {
							DefaultListModel<String> listModel = (DefaultListModel<String>) list.getModel();
							listModel.addElement(resourceToAdd);
						} 
					}
					else {
						JOptionPane.showMessageDialog(
								null,
								Translations.t("gui_err_name_value_not_valid"),
								Translations.t("gui_err_title"),
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		CMButton resourcesRemoveButton = new CMButton(Translations.t("gui_b_remove"), 265, 80, 120, 30);
		resourcesRemoveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int indexToRemove = resourcesList.getSelectedIndex();
				if(indexToRemove == -1) {
					JOptionPane.showMessageDialog(
							null,
							Translations.t("gui_err_resources_empty_no_selected"),
							Translations.t("gui_err_title"),
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					String resourceToRemove = resourcesList.getModel().getElementAt(indexToRemove);
					List<JList<String>> lists = Arrays.asList(resourcesList, cellarList, floorList, gardenList);
					List<JTable> tables = Arrays.asList(cellarTable, floorTable, gardenTable);
					for(JList<String> list : lists) {
						DefaultListModel<String> listModel = (DefaultListModel<String>) list.getModel();
						listModel.removeElement(resourceToRemove);
					}
					for(JTable table : tables) {
						int rows = table.getRowCount();
						int columns = table.getColumnCount();
						for(int i = 0; i < rows; i++)
							for(int j = 0; j < columns; j++) {
								String cellValue = (String) table.getValueAt(i, j);
								if(resourceToRemove.equals(cellValue)) {
									table.setValueAt(null, i, j);
								}
							}
					}
				}				
			}
		});
		
		panel.add(resourcesLabel);
		panel.add(resourcesScrollPane);
		panel.add(resourcesAddButton);
		panel.add(resourcesRemoveButton);
	}
	
	/**
	 * Adds the saving options to the {@code panel}.
	 * @param panel The {@link JPanel} to which the components will be added to.
	 */
	private void addSaveOptions(JPanel panel) {
		CMLabel saveSettingsLabel = new CMLabel(Translations.t("gui_l_save_settings"), 415, 0, 120, 30);
		CMLabel pathLabel = new CMLabel(Translations.t("gui_l_path"), 415, 35, 60, 30);
		pathField = new CMTextField(475, 35, 270, 30);
		CMButton specifyPathButton = new CMButton(Translations.t("gui_b_specify_path"), 415, 80, 120, 30);
		useMappingCheckBox = new CMCheckBox(550, 80, 30, 30);
		CMLabel useMappingLabel = new CMLabel(Translations.t("gui_l_use_mapping_file"),575, 80, 180, 30);
		CMButton saveMappingFileButton = new CMButton(Translations.t("gui_b_save_mapping_file"), 415, 120, 165, 30);
		specifyPathButton.addActionListener(new BrowseListener(pathField, JFileChooser.FILES_ONLY, XML_FILTER));
		
		panel.add(saveSettingsLabel);
		panel.add(pathLabel);
		panel.add(pathField);
		panel.add(specifyPathButton);
		panel.add(useMappingCheckBox);
		panel.add(useMappingLabel);
		panel.add(saveMappingFileButton);
	}
	
	/**
	 * Adds the the tabs of the buildables to the {@code buildableTabbedPane} {@link JTabbedPane}.
	 * @param panel The {@link JPanel} to which the {@code buildableTabbedPane} will be added to.
	 */
	private void addBuildableTabs(JPanel panel) {
		buildableTabbedPane = new JTabbedPane();
		
		createCellarTab();
		createFloorTab();
		createGardenTab();
		createGroundTab();
		
		buildableTabbedPane.add(Translations.t("gui_tab_cellar"), cellarPanel);
		buildableTabbedPane.add(Translations.t("gui_tab_floor"), floorPanel);
		buildableTabbedPane.add(Translations.t("gui_tab_garden"), gardenPanel);
		buildableTabbedPane.add(Translations.t("gui_tab_ground"), groundPanel);
		
		buildableTabbedPane.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
		buildableTabbedPane.setBounds(10, 175, 780, 300);
		
		panel.add(buildableTabbedPane);
		
	}
	
	/**
	 * Creates the tab to the buildable type cellar, where the buildable attributes and their desired values can be paired.
	 */
	private void createCellarTab() {
		cellarPanel = new JPanel();
		cellarPanel.setLayout(null);
	    cellarPanel.setBounds(0, 0, 780, 285);

	    Dimension size = new Dimension(780, 285);
	    cellarPanel.setMinimumSize(size);
	    cellarPanel.setPreferredSize(size);
	    cellarPanel.setMaximumSize(size);
	    
	    CMLabel assignedLabel = new CMLabel(Translations.t("gui_l_assigned_to"), 15, 15, 270, 30);
	    CMLabel attributeLabel = new CMLabel(Translations.t("gui_l_attribute"), 270, 15, 60, 30);
	    CMLabel propertiesLabel = new CMLabel(Translations.t("gui_l_properties"), 525, 15, 120, 30);
	    
	    cellarTable = setUpBuildableTable("CELLAR");
	    Rectangle bounds = cellarTable.getBounds();
	    CMScrollPane scrollPane = new CMScrollPane(cellarTable, bounds.x, bounds.y, bounds.width, bounds.height + 30);
	    
	    cellarListmodel = initializeListModel("attribute");
	    cellarList = new JList<String>();
	    cellarList.setModel(cellarListmodel);
	    cellarList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    cellarList.setLayoutOrientation(JList.VERTICAL);
	    cellarList.setVisibleRowCount(-1);
	    cellarList.setDragEnabled(true);
	    cellarList.setDropMode(DropMode.INSERT);

	    CMScrollPane cellarScrollPane = new CMScrollPane(cellarList, 525, 50, 240, 180);
	    
	    cellarPanel.add(assignedLabel);
	    cellarPanel.add(attributeLabel);
	    cellarPanel.add(propertiesLabel);
	    cellarPanel.add(scrollPane);
	    cellarPanel.add(cellarScrollPane);	    
	}
	
	/**
	 * Creates the tab to the buildable type floor, where the buildable attributes and their desired values can be paired.
	 */
	private void createFloorTab() {
		floorPanel = new JPanel();
		floorPanel.setLayout(null);
	    floorPanel.setBounds(0, 0, 780, 285);

	    Dimension size = new Dimension(780, 285);
	    floorPanel.setMinimumSize(size);
	    floorPanel.setPreferredSize(size);
	    floorPanel.setMaximumSize(size);
	    
	    CMLabel assignedLabel = new CMLabel(Translations.t("gui_l_assigned_to"), 15, 15, 270, 30);
	    CMLabel methodLabel = new CMLabel(Translations.t("gui_l_method"), 270, 15, 60, 30);
	    CMLabel propertiesLabel = new CMLabel(Translations.t("gui_l_properties"), 525, 15, 120, 30);		
	    
	    floorTable = setUpBuildableTable("FLOOR");
	    Rectangle bounds = floorTable.getBounds();
	    CMScrollPane scrollPane = new CMScrollPane(floorTable, bounds.x, bounds.y, bounds.width, bounds.height + 30);
	    
	    floorListmodel = initializeListModel("method");
	    floorList = new JList<String>();
	    floorList.setModel(floorListmodel);
	    floorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    floorList.setLayoutOrientation(JList.VERTICAL);
	    floorList.setVisibleRowCount(-1);
	    floorList.setDragEnabled(true);
	    floorList.setDropMode(DropMode.INSERT);
	    
	    CMScrollPane floorScrollPane = new CMScrollPane(floorList, 525, 50, 240, 180);
	    
	    floorPanel.add(assignedLabel);
	    floorPanel.add(methodLabel);
	    floorPanel.add(propertiesLabel);
	    floorPanel.add(scrollPane);
	    floorPanel.add(floorScrollPane);
	}
	
	/**
	 * Creates the tab to the buildable type garden, where the buildable attributes and their desired values can be paired.
	 */
	private void createGardenTab() {
		gardenPanel = new JPanel();
		gardenPanel.setLayout(null);
	    gardenPanel.setBounds(0, 0, 780, 285);

	    Dimension size = new Dimension(780, 285);
	    gardenPanel.setMinimumSize(size);
	    gardenPanel.setPreferredSize(size);
	    gardenPanel.setMaximumSize(size);
	    
	    CMLabel assignedLabel = new CMLabel(Translations.t("gui_l_assigned_to"), 15, 15, 270, 30);
	    CMLabel classLabel = new CMLabel(Translations.t("gui_l_class"), 270, 15, 60, 30);
	    CMLabel propertiesLabel = new CMLabel(Translations.t("gui_l_properties"), 525, 15, 120, 30);
	    
	    gardenTable = setUpBuildableTable("GARDEN");
	    Rectangle bounds = gardenTable.getBounds();
	    CMScrollPane scrollPane = new CMScrollPane(gardenTable, bounds.x, bounds.y, bounds.width, bounds.height + 30);
	    
	    gardenListmodel = initializeListModel("class");
	    gardenList = new JList<String>();
	    gardenList.setModel(gardenListmodel);
	    gardenList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    gardenList.setLayoutOrientation(JList.VERTICAL);
	    gardenList.setVisibleRowCount(-1);
	    gardenList.setDragEnabled(true);
	    gardenList.setDropMode(DropMode.INSERT);
	    
	    CMScrollPane gardenScrollPane = new CMScrollPane(gardenList, 525, 50, 240, 180);
	    
	    gardenPanel.add(assignedLabel);
	    gardenPanel.add(classLabel);
	    gardenPanel.add(propertiesLabel);
	    gardenPanel.add(scrollPane);
	    gardenPanel.add(gardenScrollPane);
	}
	
	/**
	 * Creates the tab to the buildable type ground.
	 */
	private void createGroundTab() {
		groundPanel = new JPanel();
		groundPanel.setLayout(null);
	    groundPanel.setBounds(0, 0, 780, 285);

	    Dimension size = new Dimension(780, 285);
	    groundPanel.setMinimumSize(size);
	    groundPanel.setPreferredSize(size);
	    groundPanel.setMaximumSize(size);
	    
	    CMLabel assignedLabel = new CMLabel(Translations.t("gui_l_assigned_to"), 15, 15, 270, 30);
	    CMLabel packageLabel = new CMLabel(Translations.t("gui_l_package"), 270, 15, 60, 30);
	    CMLabel noAttrsLabel = new CMLabel(Translations.t("gui_l_no_attributes"), 15, 60, 300, 30);
	    
	    groundPanel.add(assignedLabel);
	    groundPanel.add(packageLabel);
	    groundPanel.add(noAttrsLabel);
	}
	
	/**
	 * Sets up the table of a buildable type which contains the attributes of the buildable (height, character, etc.) and provides a second column for their values.
	 * @param buildableType The type of the buildable (method, attribute, etc.).
	 * @return The JTable contains the buildable attributes.
	 */
	private JTable setUpBuildableTable(String buildableType) {
		String[] displayedProperties = displayedBuildableAttributes.get(buildableType);
	    
		Object[] columnNames = new String[] {Translations.t("gui_t_attribute"), Translations.t("gui_t_assigned_propery")};
	    Object[][] initData = new Object[displayedProperties.length][2];
	    
	    for(int i = 0; i < displayedProperties.length; i++) {
	    	initData[i][0] = displayedProperties[i] + ": " + BuildableSettings.BUILDABLE_ATTRIBUTE_TYPES.get(displayedProperties[i]);
	    	initData[i][1] = null;
	    }

	    JTable table = new JTable(initData, columnNames);
	    table.setFont(new Font("Source Sans Pro", Font.PLAIN, 14));
	    table.setRowHeight(30);
	    table.setBounds(15, 50, 480, displayedProperties.length * 30);
        table.setDragEnabled(true);
        table.setDropMode(DropMode.USE_SELECTION);
        table.setTransferHandler(new TransferHelper());
        table.setRowSelectionAllowed(false);
        table.setCellSelectionEnabled(true);

	    return table;
	}

	/**
	 * Fills up the list model of the given source code element type with its own properties/metrics.
	 * @param sourceCodeElementType Type of the source code element (method, attribute, etc.).
	 * @return The {@link ListModel} contains all of the properties/metrics.
	 */
	public ListModel<String> initializeListModel(String sourceCodeElementType) {
		List<Property> propertyList = sourceCodeElementProperties.get(sourceCodeElementType);

		DefaultListModel<String> model = new DefaultListModel<String>();
		
		for(Property p : propertyList) {
			model.addElement(p.name + ": " + p.type);
		}
		
		return model;
	}
	
	/**
	 * Adds the conversion options to the {@code panel}.
	 * @param panel The {@link JPanel} to which the options will be added to.
	 */
	private void addConversionOptions(JPanel panel) {
		CMButton conversionButton = new CMButton(Translations.t("gui_b_conversions"), 10, 490, 150, 30);
		panel.add(conversionButton);
		
		MappingFileEditorDialog self = this;
		conversionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index;
				String buildableAttribute;
				String metric;

				for (Conversion element :  cellarConversion) {
					if ( element instanceof QuantizationConversion ) {
						QuantizationInformation cellar = new QuantizationInformation();

						index = cellarConversion.indexOf(element);
						buildableAttribute = (String) cellarTable.getModel().getValueAt(index, 0);
						metric = (String) cellarTable.getModel().getValueAt(index, 1);

						cellar.setIndex(index);
						cellar.setBuildableAttribute(buildableAttribute);
						cellar.setMetric(metric);

						cellarQuant.put(cellar, new QuantizationConversion());
					}
				}
				for (Conversion element :  gardenConversion) {
					if ( element instanceof QuantizationConversion ) {
						QuantizationInformation garden = new QuantizationInformation();

						index = gardenConversion.indexOf(element);
						buildableAttribute = (String) gardenTable.getModel().getValueAt(index, 0);
						metric = (String) gardenTable.getModel().getValueAt(index, 1);

						garden.setIndex(index);
						garden.setBuildableAttribute(buildableAttribute);
						garden.setMetric(metric);

						gardenQuant.put(garden, new QuantizationConversion());
					}
				}
				for (Conversion element :  floorConversion) {
					if ( element instanceof QuantizationConversion ) {
						QuantizationInformation floor = new QuantizationInformation();

						index = floorConversion.indexOf(element);
						buildableAttribute = (String) floorTable.getModel().getValueAt(index, 0);
						metric = (String) floorTable.getModel().getValueAt(index, 1);

						floor.setIndex(index);
						floor.setBuildableAttribute(buildableAttribute);
						floor.setMetric(metric);

						floorQuant.put(floor, new QuantizationConversion());
					}
				}
				QuantizationSetterDialog dialog = new QuantizationSetterDialog(self);
				dialog.setVisible(true);
				
			}
		});
	}

    /**
     * @return floorTable
     */
    public static JTable getFloorTable() {
    	return floorTable;
    }

    /**
     * @return gardenTable
     */
    public static JTable getGardenTable() {
        return gardenTable;
    }

    /**
     * @return cellarTable
     */
    public static JTable getCellarTable() {
        return cellarTable;
	}
}
