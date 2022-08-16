package codemetropolis.toolchain.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PipedOutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.border.Border;

import codemetropolis.toolchain.gui.beans.ExecutionOptions;
import codemetropolis.toolchain.gui.components.*;
import codemetropolis.toolchain.gui.components.listeners.BrowseListener;
import codemetropolis.toolchain.gui.utils.ExecutionWorker;
import codemetropolis.toolchain.gui.utils.GuiUtils;
import codemetropolis.toolchain.gui.utils.Translations;
import codemetropolis.toolchain.gui.utils.XmlFileFilter;
import codemetropolis.toolchain.placing.layout.LayoutAlgorithm;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PipedOutputStream;
import java.net.URL;
import java.util.Random;
import java.util.List;

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
    super(Translations.t("gui_title"));
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
    versionUpdateWarning();
    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(panel);
    this.pack();
    this.setLocationRelativeTo(null);
  }
  public CodeMetropolisGUI(){
      super(Translations.t("gui_title"));
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
   * Gets the latest version from GitHub,via Web Scraping
   * GitHub Releases/latest will give back the latest realease.
   */
  public String getNewestVersion(String url) {
      try {
          final URL latestURL = new URL(url);
          StringBuilder newestVersion = new StringBuilder();
          BufferedReader urlReader = new BufferedReader(new InputStreamReader(latestURL.openStream()));
          String inputLine;
          while ((inputLine = urlReader.readLine()) != null) {
              if (inputLine.contains("CodeMetropolis/releases/tag/")) {
                  for (int i = inputLine.indexOf("tag/") + 4; i > 0; i++) {
                      if (Character.isDigit(inputLine.charAt(i)) || inputLine.charAt(i) == '.') {
                          newestVersion.append(inputLine.charAt(i));
                      }
                      else{
                          break;
                      }
                  }
                  break;
              }
          }
          urlReader.close();
          return newestVersion.toString();
      } catch (Exception e) {
          return null;
      }
  }

    /**
     * Gets the actual version from the pom.xml file and filters it to only contains numbers and dots.
     */
    public String getActualVersion() {
        String version = this.getClass().getPackage().getImplementationVersion();
        StringBuilder fixVersion = new StringBuilder();
        if(version == null){
            return "1.4.0";
        }
        for (int i = 0; i < version.length(); i++) {
            if (Character.isDigit(version.charAt(i)) || version.charAt(i) == '.') {
                fixVersion.append(version.charAt(i));
            }
            else{
                break;
            }
        }
        return checkDot(fixVersion.toString());
    }

    /**
     * Validates the version string with regular expressions.
     *
     * @param version a CodeMetropolis version.
     */
    public boolean validator(String version) {
        return version.matches("[0-9]+(\\.[0-9]+){0,2}");
    }

    /**
     * Compares the new and the old version.
     *
     * @return a true statement if our version is outdated.
     */
    public boolean compareVersion(String actualVersionSub, String newVersionSub) {

        if (newVersionSub == null || actualVersionSub == null) {
            return false;
        }

        if (!validator(actualVersionSub) && !validator(newVersionSub)) {
            return false;
        }

        String[] actual = actualVersionSub.split("\\.");
        String[] newVersion = newVersionSub.split("\\.");

        for (int i = 0; i < newVersionSub.length(); i++) {
            try {
                if (Integer.parseInt(newVersion[i]) > Integer.parseInt(actual[i])) {
                    return true;
                } else if (Integer.parseInt(newVersion[i]) < Integer.parseInt(actual[i])) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Checks the amount of dot in the version string.
     * If it's less than 2,we are appending a zero to the end.
     *
     * @param version the checkable version.
     */
    public String checkDot(String version) {
        int counter = StringUtils.countMatches(version, '.');
        if (counter < 2) {
            version += ".0";
        }
        return version;
    }

    /**
     * Checks if we have to notify our user to update the version of CodeMetropolis.
     */
    public void versionUpdateWarning() {
        if (compareVersion(getActualVersion(),checkDot(getNewestVersion("https://github.com/codemetropolis/CodeMetropolis/releases/latest")))) {
            JOptionPane.showMessageDialog(null, "You are using an old version of Codemetropolis\n"
                    + "Download the latest release from the link below: \n"
                    + "https://github.com/codemetropolis/CodeMetropolis/releases/latest", "New release available to download", JOptionPane.INFORMATION_MESSAGE);
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

    // Load the cover and logo images
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
    JLabel title = new JLabel(Translations.t("gui_title"));
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
    CMLabel label = new CMLabel(Translations.t("gui_l_project_name"), 15, 325, 120, 30);
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

        // Load the cover and logo images
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
        JLabel title = new JLabel(Translations.t("gui_title"));
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
        CMLabel label = new CMLabel(Translations.t("gui_l_project_name"), 15, 325, 120, 30);
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
        CMLabel mappingLabel = new CMLabel(Translations.t("gui_l_mapping"), 15, 555, 120, 30);
        mappingPath = new CMTextField(145, 555, 235, 30);
        CMButton mappingBrowse = new CMButton(Translations.t("gui_b_browse"), 385, 555, 100, 30);
        //mappingBrowse.setBorder(new RoundBtn(30));
        mappingBrowse.addActionListener(new BrowseListener(mappingPath, JFileChooser.FILES_ONLY, XML_FILTER));

        CMLabel scaleLabel = new CMLabel(Translations.t("gui_l_scale"), 15, 590, 120, 30);
        scaleSpinner = new CMSpinner(145, 590, 120, 30);

        validateStructure = new CMCheckBox(275, 590, 20, 30);
        CMLabel validateStructureLabel = new CMLabel(Translations.t("gui_l_validate_structure"), 300, 590, 185, 30);

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
        CMLabel layoutLabel = new CMLabel(Translations.t("gui_l_layout"), 15, 625, 120, 30);
        layoutSelector = new CMComboBox<LayoutAlgorithm>(LayoutAlgorithm.values());
        layoutSelector.setBounds(145, 625, 120, 30);

        showMap = new CMCheckBox(275, 625, 20, 30);
        CMLabel showMapLabel = new CMLabel(Translations.t("gui_l_show_map"), 300, 625, 185, 30);

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
        CMLabel mcRootLabel = new CMLabel(Translations.t("gui_l_mcroot"), 15, 660, 120, 30);
        mcRootPath = new CMTextField(145, 660, 235, 30);
        CMButton mcRootBrowse = new CMButton(Translations.t("gui_b_browse"), 385, 660, 100, 30);
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
        CodeMetropolisGUI self = this;
        CMButton start = new CMButton(Translations.t("gui_b_generate"), 190, 705, 120, 30);
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                ExecutionOptions executionOptions = controller.getExecutionOptions();
                fillOptions(executionOptions);
                /* HTML-like field validation added
                    Check for emptiness
                */
                if(projectName.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter project name!");
                    return;
                }else if(mappingPath.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please select mapping file!");
                    return;
                }else if(scaleSpinner.getValue()==null) {
                    JOptionPane.showMessageDialog(null, "Please select scale!");
                    return;
                }else if(mcRootPath.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please set the Minecraft root path!");
                    return;
                }
                if (!fillAndValidateMetricOptions(executionOptions)) {
                    return;
                }


                if (GuiUtils.validateOptions(controller.getExecutionOptions())) {
                    PipedOutputStream out = new PipedOutputStream();
                    ExecutionDialog dialog = new ExecutionDialog(self, out);
                    dialog.setVisible(true);
                    ExecutionWorker worker = new ExecutionWorker(start, controller, out);
                    worker.execute();
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

  private final void readProTipsJSON() {
    protips = new ArrayList<>();

    try {
      File myObj = new File("..\\gui\\src\\main\\resources\\protips.json");

      Scanner myReader = new Scanner(myObj);
      StringBuilder data = new StringBuilder();

      while (myReader.hasNextLine()) {
        data.append(myReader.nextLine());
      }

      Matcher m = Pattern.compile("\".+?\"").matcher(data.toString());
      while (m.find()) {
        protips.add(m.group());
      }

      protips.remove(0);

      myReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds the lightbulb image next to the pro tip.
   *
   * @param panel The {@link JPanel} to add the components to.
   */
  private final void addProTipImage(JPanel panel) {
    // Load the cover and logo images
    ImageIcon bulbIcon = new ImageIcon(ClassLoader.getSystemResource("images/cm-lightbulb.png"));
    Image bulbLogo = bulbIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

    JLabel logoImageContainer = new JLabel(new ImageIcon(bulbLogo));
    logoImageContainer.setBounds(60, 290, 30, 30);

    panel.add(logoImageContainer);
  }

  /**
   * Adds a random pro tip to the {@code panel}
   *
   * @param panel The {@link JPanel} to add the components to.
   */
  private final void addProTip(JPanel panel) {
    readProTipsJSON();

    JLabel tip = new JLabel(protips.get(rng.nextInt(protips.size())));
    tip.setFont(new Font("Source Sans Pro", Font.BOLD, 10));
    tip.setHorizontalAlignment(SwingConstants.CENTER);
    tip.setBounds(100, 300, 300, 20);
    tip.setOpaque(true);
    tip.setBackground(Color.orange);

    panel.add(tip);
  }

}

