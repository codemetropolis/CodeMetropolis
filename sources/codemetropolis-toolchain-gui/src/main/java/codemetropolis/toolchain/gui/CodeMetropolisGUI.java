package codemetropolis.toolchain.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import codemetropolis.toolchain.gui.components.CMButton;
import codemetropolis.toolchain.gui.components.CMCheckBox;
import codemetropolis.toolchain.gui.components.CMLabel;
import codemetropolis.toolchain.gui.components.CMPasswordField;
import codemetropolis.toolchain.gui.components.CMTextField;

/**
 * GUI window for the CodeMetropolis toolchain.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class CodeMetropolisGUI extends JFrame {

  private static final long serialVersionUID = 1L;

  /**
   * Instantiates the CodeMetropolis GUI.
   */
  public CodeMetropolisGUI() {
    super("CodeMetropolis");

    JPanel panel = createBasePanel();
    addHeaderImages(panel);
    addTitle(panel);
    addProjectNameField(panel);
    addMetricTabs(panel);
    addMappingPathBrowser(panel);
    addMinecraftRootBrowser(panel);
    addShowMapCheckbox(panel);
    addStartButton(panel);

    this.setResizable(false);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setContentPane(panel);
    this.pack();
    this.setLocationRelativeTo(null);
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
  private static final void addHeaderImages(JPanel panel) {
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
  private static final void addTitle(JPanel panel) {
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
  private static final void addProjectNameField(JPanel panel) {
    CMLabel label = new CMLabel("Project name:");
    label.setBounds(15, 325, 120, 30);
    CMTextField projectName = new CMTextField();
    projectName.setBounds(145, 325, 340, 30);
    panel.add(label);
    panel.add(projectName);
  }

  /**
   * Adds the metric generation tabbed pane to the {@code panel}
   *
   * @param panel The panel to add the components to.
   */
  private static final void addMetricTabs(JPanel panel) {
    JTabbedPane tabbedPane = new JTabbedPane();
    tabbedPane.addTab("SourceMeter", createSourceMeterTab());
    tabbedPane.addTab("SonarQube", createSonarQubeTab());
    tabbedPane.setBounds(15, 365, 472, 180);
    tabbedPane.setFont(new Font("Source Sans Pro", Font.PLAIN, 16));
    panel.add(tabbedPane);
  }

  /**
   * Creates the tab for the fields related to SourceMeter based metric generation.
   *
   * @return The assembled SourceMeter tab content panel.
   */
  private static final JPanel createSourceMeterTab() {
    JPanel sourceMeterPanel = new JPanel();
    sourceMeterPanel.setLayout(null);

    CMLabel projectRootLabel = new CMLabel("Project root:");
    projectRootLabel.setBounds(5, 5, 120, 30);
    CMTextField projectRootPath = new CMTextField();
    projectRootPath.setBounds(130, 5, 225, 30);
    CMButton projectRootBrowse = new CMButton("Browse");
    projectRootBrowse.setBounds(360, 5, 100, 30);
    CMLabel sourceMeterLabel = new CMLabel("SourceMeter exe:");
    sourceMeterLabel.setBounds(5, 40, 120, 30);
    CMTextField sourceMeterPath = new CMTextField();
    sourceMeterPath.setBounds(130, 40, 225, 30);
    CMButton sourceMeterBrowse = new CMButton("Browse");
    sourceMeterBrowse.setBounds(360, 40, 100, 30);

    sourceMeterPanel.add(projectRootLabel);
    sourceMeterPanel.add(projectRootPath);
    sourceMeterPanel.add(projectRootBrowse);
    sourceMeterPanel.add(sourceMeterLabel);
    sourceMeterPanel.add(sourceMeterPath);
    sourceMeterPanel.add(sourceMeterBrowse);

    return sourceMeterPanel;
  }

  /**
   * Creates the tab for the fields related to SonarQube based metric generation.
   *
   * @return The assembled SonarQube tab content panel.
   */
  private static final JPanel createSonarQubeTab() {
    JPanel sonarQubePanel = new JPanel();
    sonarQubePanel.setLayout(null);

    CMLabel usernameLabel = new CMLabel("Username:");
    usernameLabel.setBounds(5, 5, 80, 30);
    CMTextField username = new CMTextField();
    username.setBounds(90, 5, 370, 30);
    sonarQubePanel.add(usernameLabel);
    sonarQubePanel.add(username);

    CMLabel passwordLabel = new CMLabel("Password:");
    passwordLabel.setBounds(5, 40, 80, 30);
    CMPasswordField password = new CMPasswordField();
    password.setBounds(90, 40, 370, 30);
    sonarQubePanel.add(passwordLabel);
    sonarQubePanel.add(password);

    CMLabel projectsLabel = new CMLabel("Projects:");
    projectsLabel.setBounds(5, 75, 80, 30);
    CMTextField projects = new CMTextField();
    projects.setBounds(90, 75, 370, 30);
    sonarQubePanel.add(projectsLabel);
    sonarQubePanel.add(projects);

    CMCheckBox splitDirs = new CMCheckBox();
    splitDirs.setBounds(5, 110, 20, 30);
    CMLabel splitDirsLabel = new CMLabel("Split dirs");
    splitDirsLabel.setBounds(30, 110, 370, 30);
    sonarQubePanel.add(splitDirs);
    sonarQubePanel.add(splitDirsLabel);

    return sonarQubePanel;
  }

  /**
   * Adds the mapping path browser to the {@code panel}.
   *
   * @param panel The panel to add the components to.
   */
  private static final void addMappingPathBrowser(JPanel panel) {
    CMLabel mappingLabel = new CMLabel("Mapping xml:");
    mappingLabel.setBounds(15, 555, 120, 30);
    CMTextField mappingPath = new CMTextField();
    mappingPath.setBounds(145, 555, 235, 30);
    CMButton mappingBrowse = new CMButton("Browse");
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
  private static final void addMinecraftRootBrowser(JPanel panel) {
    CMLabel mcRootLabel = new CMLabel("Minecraft root:");
    mcRootLabel.setBounds(15, 590, 120, 30);
    CMTextField mcRootPath = new CMTextField();
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
  private static final void addShowMapCheckbox(JPanel panel) {
    CMCheckBox showMap = new CMCheckBox();
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
  private static final void addStartButton(JPanel panel) {
    CMButton start = new CMButton("Generate");
    start.setBounds(200, 660, 100, 30);
    panel.add(start);
  }

}
