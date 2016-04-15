package codemetropolis.toolchain.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import codemetropolis.toolchain.gui.components.CMButton;
import codemetropolis.toolchain.gui.components.CMCheckBox;
import codemetropolis.toolchain.gui.components.CMLabel;
import codemetropolis.toolchain.gui.components.CMTextField;
import codemetropolis.toolchain.gui.components.listeners.BrowseListener;
import codemetropolis.toolchain.gui.components.listeners.GenerateListener;
import codemetropolis.toolchain.gui.metricgenerators.MetricGenerator;

/**
 * GUI window for the CodeMetropolis toolchain.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class CodeMetropolisGUI extends JFrame {

  private static final long serialVersionUID = 1L;
  private GUIController controller;
  
  
  private CMTextField projectName;  
  private JTabbedPane metricTabbedPane;    
  private CMTextField mappingPath;
  private CMTextField mcRootPath;
  private CMCheckBox showMap;
  

  /**
   * Instantiates the CodeMetropolis GUI.
   * 
   * @param con
   */
  public CodeMetropolisGUI(GUIController con) {
    super("CodeMetropolis");        
    controller = con;

    JPanel panel = createBasePanel();
    addHeaderImages(panel);
    addTitle(panel);
    addProjectNameField(panel);
    addMetricTabs(panel);
    addMappingPathBrowser(panel);
    addMinecraftRootBrowser(panel);
    addShowMapCheckbox(panel);
    addStartButton(panel);
    
    initGUI();

    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(panel);
    this.pack();
    this.setLocationRelativeTo(null);       
  }
  
  /**
   * Do automatic initializations for possible fields.
   * Search for Minecraft folder if exists.
   */
  public void initGUI() {
	  File mcLocation = new File(System.getProperty("user.home") + "\\AppData\\Roaming\\.minecraft");
	  if (mcLocation.isDirectory()) {
		  mcRootPath.setText(mcLocation.getAbsolutePath());
	  } else {
		  mcRootPath.setText("Unable to find Minecraft folder");
	  }
  }

  /**
   * Creates the base panel for the CodeMetropolis GUI.
   *
   * @return The generated panel.
   */
  private static final JPanel createBasePanel() {
    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setBackground(Color.WHITE);
    panel.setBounds(0, 0, 500, 700);

    Dimension size = new Dimension(500, 705);
    panel.setMinimumSize(size);
    panel.setPreferredSize(size);
    panel.setMaximumSize(size);

    return panel;
  }

  /**
   * Adds the cover image and the logo to the top of the {@code panel}.
   *
   * @param panel The panel to add the components to.
   */
  private final void addHeaderImages(JPanel panel) {
    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(null);

    Image coverImage = new ImageIcon(ClassLoader.getSystemResource("images/cm-background-1.png")).getImage()
        .getScaledInstance(500, 200, java.awt.Image.SCALE_SMOOTH);
    Image logoImage = new ImageIcon(ClassLoader.getSystemResource("images/cm-logo-border.png")).getImage()
        .getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);

    JLabel coverImageContainer = new JLabel(new ImageIcon(coverImage));
    coverImageContainer.setBounds(0, 0, 500, 200);
    JLabel logoImageContainer = new JLabel(new ImageIcon(logoImage));
    logoImageContainer.setBounds(175, 125, 150, 150);

    headerPanel.setBackground(Color.WHITE);
    headerPanel.setBounds(0, 0, 500, 275);

    headerPanel.add(coverImageContainer);
    headerPanel.add(logoImageContainer);
    headerPanel.setComponentZOrder(coverImageContainer, 1);
    headerPanel.setComponentZOrder(logoImageContainer, 0);
    panel.add(headerPanel);
  }

  /**
   * Adds the CodeMetropolis title to the {@code panel}
   *
   * @param panel The panel to add the components to.
   */
  private final void addTitle(JPanel panel) {
    JLabel title = new JLabel("CodeMetropolis");
    title.setFont(new Font("Source Sans Pro", Font.BOLD, 26));
    title.setHorizontalAlignment(SwingConstants.CENTER);
    title.setBounds(0, 280, 500, 30);
    panel.add(title);
  }

  /**
   * Adds the project name field to the {@code panel}.
   *
   * @param panel The panel to add the components to.
   */
  private final void addProjectNameField(JPanel panel) {
    CMLabel label = new CMLabel("Project name:");
    label.setBounds(15, 325, 120, 30);
    projectName = new CMTextField();
    projectName.setBounds(145, 325, 340, 30);
    panel.add(label);
    panel.add(projectName);
  }

  /**
   * Adds the metric generation tabbed pane to the {@code panel}
   *
   * @param panel The panel to add the components to.
   */
  private final void addMetricTabs(JPanel panel) {
    metricTabbedPane = new JTabbedPane();
    List<MetricGenerator> metricList = controller.getGeneratorList();
    for (int i=0; i<metricList.size(); i++) {
    	metricTabbedPane.add(metricList.get(i).getName(),metricList.get(i).getGUIpanel(this));
    }
    
    metricTabbedPane.setBounds(15, 365, 472, 180);
    metricTabbedPane.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
    panel.add(metricTabbedPane);
  }  

  /**
   * Adds the mapping path browser to the {@code panel}.
   *
   * @param panel The panel to add the components to.
   */
  private final void addMappingPathBrowser(JPanel panel) {
    CMLabel mappingLabel = new CMLabel("Mapping xml:");
    mappingLabel.setBounds(15, 555, 120, 30);
    mappingPath = new CMTextField();
    mappingPath.setBounds(145, 555, 235, 30);
    CMButton mappingBrowse = new CMButton("Browse");
    mappingBrowse.addActionListener(new BrowseListener(this, mappingPath, JFileChooser.FILES_ONLY));
    mappingBrowse.setBounds(385, 555, 100, 30);    
    panel.add(mappingLabel);
    panel.add(mappingPath);
    panel.add(mappingBrowse);
  }

  /**
   * Adds the minecraft root folder browser. This should actually be automatically filled, but in case it is not found
   * or the user wishes to save the results to a different location, it enables them to do so.
   *
   * @param panel The panel to add the components to.
   */
  private final void addMinecraftRootBrowser(JPanel panel) {
    CMLabel mcRootLabel = new CMLabel("Minecraft root:");
    mcRootLabel.setBounds(15, 590, 120, 30);    
    mcRootPath = new CMTextField();
    mcRootPath.setBounds(145, 590, 235, 30);
    CMButton mcRootBrowse = new CMButton("Browse");
    mcRootBrowse.setBounds(385, 590, 100, 30);

    panel.add(mcRootLabel);
    panel.add(mcRootPath);
    panel.add(mcRootBrowse);
  }

  /**
   * Adds the checkbox for enabling showing the generated map blueprint.
   *
   * @param panel The panel to add the components to.
   */
  private final void addShowMapCheckbox(JPanel panel) {
    showMap = new CMCheckBox();
    showMap.setBounds(12, 625, 20, 30);
    CMLabel showMapLabel = new CMLabel("Show generated map");
    showMapLabel.setBounds(40, 625, 445, 30);
    panel.add(showMap);
    panel.add(showMapLabel);
  }

  /**
   * Adds the start button to the bottom of panel.
   *
   * @param panel The panel to add the components to.
   */
  private final void addStartButton(JPanel panel) {
    CMButton start = new CMButton("Generate");
    start.addActionListener(new GenerateListener(controller, this));
    start.setBounds(200, 660, 100, 30);
    panel.add(start);
  }

	public CMTextField getProjectName() {
		return projectName;
	}
	
	public void setProjectName(CMTextField projectName) {
		this.projectName = projectName;
	}
	
	public JTabbedPane getMetricTabbedPane() {
		return metricTabbedPane;
	}
	
	public void setMetricTabbedPane(JTabbedPane metricTabbedPane) {
		this.metricTabbedPane = metricTabbedPane;
	}
	
	public CMTextField getMappingPath() {
		return mappingPath;
	}
	
	public void setMappingPath(CMTextField mappingPath) {
		this.mappingPath = mappingPath;
	}
	
	public CMTextField getMcRootPath() {
		return mcRootPath;
	}
	
	public void setMcRootPath(CMTextField mcRootPath) {
		this.mcRootPath = mcRootPath;
	}
	
	public CMCheckBox getShowMap() {
		return showMap;
	}
	
	public void setShowMap(CMCheckBox showMap) {
		this.showMap = showMap;
	}  

}
