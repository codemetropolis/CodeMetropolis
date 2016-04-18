package codemetropolis.toolchain.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileFilter;

import codemetropolis.toolchain.gui.beans.ExecutionOptions;
import codemetropolis.toolchain.gui.components.CMButton;
import codemetropolis.toolchain.gui.components.CMCheckBox;
import codemetropolis.toolchain.gui.components.CMComboBox;
import codemetropolis.toolchain.gui.components.CMLabel;
import codemetropolis.toolchain.gui.components.CMMetricPanel;
import codemetropolis.toolchain.gui.components.CMSpinner;
import codemetropolis.toolchain.gui.components.CMTextField;
import codemetropolis.toolchain.gui.components.listeners.BrowseListener;
import codemetropolis.toolchain.gui.utils.GuiUtils;
import codemetropolis.toolchain.gui.utils.XmlFileFilter;
import codemetropolis.toolchain.placing.layout.LayoutAlgorithm;

/**
 * GUI window for the CodeMetropolis toolchain.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class CodeMetropolisGUI extends JFrame {

  private static final long serialVersionUID = 1L;

  private static final FileFilter XML_FILTER = new XmlFileFilter();
  private static final int COVER_IMAGE_COUNT = 4;
  private static final Random rng = new Random();

  private GUIController controller;

  private CMTextField projectName;
  private JTabbedPane metricTabbedPane;
  private CMTextField mappingPath;
  private CMTextField mcRootPath;
  private CMCheckBox showMap;
  private CMCheckBox validateStructure;
  private CMSpinner scaleSpinner;
  private CMComboBox<LayoutAlgorithm> layoutSelector;

  /**
   * Instantiates the CodeMetropolis GUI.
   *
   * @param controller The {@link GUIController} instance.
   */
  public CodeMetropolisGUI(GUIController controller) {
    super("CodeMetropolis");
    this.controller = controller;

    JPanel panel = createBasePanel();
    addHeaderImages(panel);
    addTitle(panel);
    addProjectNameField(panel);
    addMetricTabs(panel);
    addMappingOptions(panel);
    addPlacingOptions(panel);
    addMinecraftRootBrowser(panel);
    addStartButton(panel);

    initFields();

    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(panel);
    this.pack();
    this.setLocationRelativeTo(null);
  }

  /**
   * Does automatic initialization for some of the fields. Search for Minecraft folder if exists.
   */
  public void initFields() {
    String minecraftRoot = GuiUtils.findMinecraftRoot();
    if (minecraftRoot != null) {
      mcRootPath.setText(minecraftRoot);
    }
  }

  /**
   * Creates the base panel for the CodeMetropolis GUI.
   *
   * @return The generated {@link JPanel}.
   */
  private static final JPanel createBasePanel() {
    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setBackground(Color.WHITE);
    panel.setBounds(0, 0, 500, 700);

    Dimension size = new Dimension(500, 750);
    panel.setMinimumSize(size);
    panel.setPreferredSize(size);
    panel.setMaximumSize(size);

    return panel;
  }

  /**
   * Adds the cover image and the logo to the top of the {@code panel}.
   *
   * @param panel The {@link JPanel} to add the components to.
   */
  private final void addHeaderImages(JPanel panel) {
    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(null);

    Image coverImage = new ImageIcon(ClassLoader.getSystemResource("images/cm-background-"
        + (rng.nextInt(COVER_IMAGE_COUNT) + 1) + ".png")).getImage().getScaledInstance(500, 200, Image.SCALE_SMOOTH);
    ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("images/cm-logo-border.png"));
    Image logoImage = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);

    JLabel coverImageContainer = new JLabel(new ImageIcon(coverImage));
    coverImageContainer.setBounds(0, 0, 500, 200);
    JLabel logoImageContainer = new JLabel(new ImageIcon(logoImage));
    logoImageContainer.setBounds(175, 125, 150, 150);

    // Add the icon to the window title bar as well
    setIconImage(logoIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));

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
   * @param panel The {@link JPanel} to add the components to.
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
   * @param panel The {@link JPanel} to add the components to.
   */
  private final void addProjectNameField(JPanel panel) {
    CMLabel label = new CMLabel("Project name:", 15, 325, 120, 30);
    projectName = new CMTextField(145, 325, 340, 30);

    panel.add(label);
    panel.add(projectName);
  }

  /**
   * Adds the metric generation tabbed pane to the {@code panel}
   *
   * @param panel The {@link JPanel} to add the components to.
   */
  private final void addMetricTabs(JPanel panel) {
    metricTabbedPane = new JTabbedPane();

    for (CMMetricPanel metricPanel : controller.getMetricGeneratorPanels()) {
      metricTabbedPane.add(metricPanel.getTabTitle(), metricPanel);
    }

    metricTabbedPane.setBounds(15, 365, 472, 180);
    metricTabbedPane.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
    panel.add(metricTabbedPane);
  }

  /**
   * Adds the options for the mapping tool to the {@code panel}.
   *
   * @param panel The {@link JPanel} to add the components to.
   */
  private final void addMappingOptions(JPanel panel) {
    CMLabel mappingLabel = new CMLabel("Mapping xml:", 15, 555, 120, 30);
    mappingPath = new CMTextField(145, 555, 235, 30);
    CMButton mappingBrowse = new CMButton("Browse", 385, 555, 100, 30);
    mappingBrowse.addActionListener(new BrowseListener(mappingPath, JFileChooser.FILES_ONLY, XML_FILTER));

    CMLabel scaleLabel = new CMLabel("Scale:", 15, 590, 120, 30);
    scaleSpinner = new CMSpinner(145, 590, 120, 30);

    validateStructure = new CMCheckBox(275, 590, 20, 30);
    CMLabel validateStructureLabel = new CMLabel("Validate structure elements", 300, 590, 185, 30);

    panel.add(mappingLabel);
    panel.add(mappingPath);
    panel.add(mappingBrowse);
    panel.add(scaleLabel);
    panel.add(scaleSpinner);
    panel.add(validateStructure);
    panel.add(validateStructureLabel);
  }

  /**
   * Adds the options for the placing tool to the {@code panel}.
   *
   * @param panel The {@link JPanel} to add the components to.
   */
  private final void addPlacingOptions(JPanel panel) {
    CMLabel layoutLabel = new CMLabel("Layout algorithm:", 15, 625, 120, 30);
    layoutSelector = new CMComboBox<LayoutAlgorithm>(LayoutAlgorithm.values());
    layoutSelector.setBounds(145, 625, 120, 30);

    showMap = new CMCheckBox(275, 625, 20, 30);
    CMLabel showMapLabel = new CMLabel("Show generated map", 300, 625, 185, 30);

    panel.add(layoutLabel);
    panel.add(layoutSelector);
    panel.add(showMap);
    panel.add(showMapLabel);
  }

  /**
   * Adds the minecraft root folder browser. This should actually be automatically filled, but in case it is not found
   * or the user wishes to save the results to a different location, it enables them to do so.
   *
   * @param panel The {@link JPanel} to add the components to.
   */
  private final void addMinecraftRootBrowser(JPanel panel) {
    CMLabel mcRootLabel = new CMLabel("Minecraft root:", 15, 660, 120, 30);
    mcRootPath = new CMTextField(145, 660, 235, 30);
    CMButton mcRootBrowse = new CMButton("Browse", 385, 660, 100, 30);
    mcRootBrowse.addActionListener(new BrowseListener(mcRootPath, JFileChooser.DIRECTORIES_ONLY, null));

    panel.add(mcRootLabel);
    panel.add(mcRootPath);
    panel.add(mcRootBrowse);
  }

  /**
   * Adds the start button to the bottom of panel.
   *
   * @param panel The {@link JPanel} to add the components to.
   */
  private final void addStartButton(JPanel panel) {
    CMButton start = new CMButton("Generate", 190, 705, 120, 30);
    start.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent event) {
        ExecutionOptions executionOptions = controller.getExecutionOptions();
        if (!fillAndValidateMetricOptions(executionOptions)) {
          return;
        }
        fillOptions(executionOptions);

        if (GuiUtils.validateOptions(controller.getExecutionOptions())) {
          start.setEnabled(false);
          start.setText("Running...");

          new SwingWorker<Void, Integer>() {
            private boolean successful = false;

            @Override
            protected Void doInBackground() throws Exception {
              controller.execute();
              successful = true;
              return null;
            }

            @Override
            protected void done() {
              start.setText("Generate");
              start.setEnabled(true);
              if (successful) {
                JOptionPane.showMessageDialog(null, "World generation successfully finished!", "Finished!",
                    JOptionPane.INFORMATION_MESSAGE);
              } else {
                JOptionPane.showMessageDialog(null, "World generation failed! Check logs for details!", "Error!",
                    JOptionPane.ERROR_MESSAGE);
              }
              super.done();
            }
          }.execute();
        }
      }
    });

    panel.add(start);
  }

  /**
   * Fills the data required for the metric generation tools.
   *
   * @param executionOptions The target {@link ExecutionOptions} instance.
   * @return True if the options are valid, false otherwise.
   */
  private final boolean fillAndValidateMetricOptions(ExecutionOptions executionOptions) {
    executionOptions.getMetricGenerationParams().clear();

    CMMetricPanel currentTab = (CMMetricPanel) metricTabbedPane.getSelectedComponent();
    currentTab.fillFields(executionOptions);
    return currentTab.validateFields(executionOptions);
  }

  /**
   * Fills the data from the UI fields to the given {@link ExecutionOptions} instance.
   *
   * @param executionOptions The target instance.
   */
  private final void fillOptions(ExecutionOptions executionOptions) {
    Double scale = (Double) scaleSpinner.getValue();
    executionOptions.setProjectName(projectName.getText());
    executionOptions.setMappingXml(new File(mappingPath.getText()));
    executionOptions.setScale(scale.floatValue());
    executionOptions.setValidate(validateStructure.isSelected());
    executionOptions.setLayoutAlgorithm((LayoutAlgorithm) layoutSelector.getSelectedItem());
    executionOptions.setShowMap(showMap.isSelected());
    executionOptions.setMinecraftRoot(new File(mcRootPath.getText()));
  }

}
