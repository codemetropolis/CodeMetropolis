package codemetropolis.toolchain.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PipedOutputStream;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import java.awt.Cursor;

import codemetropolis.toolchain.gui.beans.ExecutionOptions;
import codemetropolis.toolchain.gui.components.CMButton;
import codemetropolis.toolchain.gui.components.CMCheckBox;
import codemetropolis.toolchain.gui.components.CMComboBox;
import codemetropolis.toolchain.gui.components.CMLabel;
import codemetropolis.toolchain.gui.components.CMMetricPanel;
import codemetropolis.toolchain.gui.components.CMSpinner;
import codemetropolis.toolchain.gui.components.CMTextField;
import codemetropolis.toolchain.gui.components.listeners.BrowseListener;
import codemetropolis.toolchain.gui.utils.ExecutionWorker;
import codemetropolis.toolchain.gui.utils.GuiUtils;
import codemetropolis.toolchain.gui.utils.Translations;
import codemetropolis.toolchain.gui.utils.XmlFileFilter;
import codemetropolis.toolchain.placing.layout.LayoutAlgorithm;

import java.net.URI;
import java.net.URL;
import java.awt.Desktop;
import java.net.URISyntaxException;

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

    addToolTips(panel);

    addAboutMenu();

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


  private final void addToolTips(JPanel panel){
    CodeMetropolisGUI self = this;
    CMButton q_root = new CMButton(Translations.t("gui_questionmark"), 120, 330, 20, 20);
    q_root.setFont(new Font("Arial",Font.BOLD,8));
    q_root.setMargin(new Insets(1,1,1,1));
    q_root.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        JFrame newFrame = new JFrame(Translations.t("gui_questionmark"));
        JLabel label1 = new JLabel("Projekt elérési útvonal");
        label1.setBounds(280,20, 800,70);
        label1.setFont(new Font("Source Sans Pro", Font.PLAIN, 20));
        label1.setVisible(true);
        newFrame.pack();
        newFrame.setVisible(true);
        newFrame.setSize(300, 100);
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.add(label1);
      }
    });

    panel.add(q_root);

  }
  protected ImageIcon createImageIcon(String path,
                                      String description) {
    java.net.URL imgURL = getClass().getResource(path);
    if (imgURL != null) {
      return new ImageIcon(imgURL, description);
    } else {
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }


  /**
   * Hozzáadja a MenuBar, Menu és MenuItem elemet az alkalmazás fõoldalának tetejéhez.   *
   * Létrehoz egy felugró JFrame ablakot, amely az 'About window', ahol további információk találhatók a Codemetropolis logo mellett,
   * és egy kattintható címkét, amely a CodeMetropolis weboldalára viszi a felhasználót.
   * @throws IOException -t dob,ha az átadott String átadásaközben valamilyen I/O kivétel történt, esetleg megszakadt az I\O folyamat.
   * @throws URISyntaxException kivételt dob annak jelzésére, hogy az átadott String nem értelmezhetõ URI hivatkozásként.   *
   */
  private final void addAboutMenu() {
    CodeMetropolisGUI self = this;
    JMenu menu;
    JMenuItem about;
    JMenuBar menuBar = new JMenuBar();
    menu = new JMenu(Translations.t("gui_menu"));
    about = new JMenuItem(Translations.t("gui_b_about"));
    menu.add(about);
    menuBar.add(menu);
    self.setJMenuBar(menuBar);
    about.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        JFrame newFrame = new JFrame(Translations.t("gui_about"));
        CMLabel label1 = new CMLabel("CodeMetropolis");
        CMLabel label2 = new CMLabel("This is the application of the CodeMetropolis.");
        CMLabel label3 = new CMLabel("CodeMetropolis is a software visualisation tool,");
        CMLabel label4 = new CMLabel("where you can generate a Minecraft world,");
        CMLabel label5 = new CMLabel("using the values of source code metrics and");
        CMLabel label6 = new CMLabel("the structure of the source code on the main page.");
        CMLabel label7 = new CMLabel("CodeMetropolis uses city metaphor for the visualisation of");
        CMLabel label8 = new CMLabel("code elements like classes, functions or attributes.");

        ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("images/cm-logo-border.png")); // A rendszerosztálybetöltõvel (ClassLoader.getSystemResource()) érjük el a program indításakor használt erõforrást az útvonalon keresztül .
        newFrame.setIconImage(logoIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)); // Beállítjuk az ablak iconját a flag (getScaledInstance) segítségével, amely jelzi a kép újramintavételezéséhez használandó algoritmus típusát(Image.SCALE_SMOOTH).

        String pathPhoto = "images/cm-logo-border.png";
        CMLabel photo = new CMLabel();
        photo.setBounds(115,25,50,50);
        ImageIcon photoImageIcon = new ImageIcon(ClassLoader.getSystemResource("images/cm-logo-border.png"));
        Image img = photoImageIcon.getImage();
        Image scaled = img.getScaledInstance(photo.getWidth(),photo.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaled);
        photo.setIcon(scaledIcon);


        label1.setBounds(175,15, 800,70);
        label1.setFont(new Font("Source Sans Pro", Font.PLAIN, 40));
        label1.setVisible(true);

        label2.setBounds(30,50, 800,100);
        label2.setFont(new Font("Source Sans Pro", Font.PLAIN, 20));
        label2.setVisible(true);

        label3.setBounds(30,85, 800,100);
        label3.setFont(new Font("Source Sans Pro", Font.PLAIN, 20));
        label3.setVisible(true);

        label4.setBounds(30,110, 800,100);
        label4.setFont(new Font("Source Sans Pro", Font.PLAIN, 20));
        label4.setVisible(true);

        label5.setBounds(30,135, 800,100);
        label5.setFont(new Font("Source Sans Pro", Font.PLAIN, 20));
        label5.setVisible(true);

        label6.setBounds(30,160, 800,100);
        label6.setFont(new Font("Source Sans Pro", Font.PLAIN, 20));
        label6.setVisible(true);

        label7.setBounds(30,195, 800,100);
        label7.setFont(new Font("Source Sans Pro", Font.PLAIN, 20));
        label7.setVisible(true);

        label8.setBounds(30,220, 800,100);
        label8.setFont(new Font("Source Sans Pro", Font.PLAIN, 20));
        label8.setVisible(true);

        CMLabel clickHere = new CMLabel(Translations.t("gui_click"));
        clickHere.setBounds(110,255, 220,100);
        clickHere.setFont(new Font("Source Sans Pro", Font.PLAIN, 20));

        CMLabel link = new CMLabel(Translations.t("gui_site")); // Label szöveg beállítása külsõ fájlból a szöveg elnevezésének használatával.
        link.setCursor(new Cursor(Cursor.HAND_CURSOR));
        link.setBounds(325,295, 120,20);
        link.setFont(new Font("Source Sans Pro", Font.PLAIN, 20));
        link.setForeground(Color.BLUE);
        link.addMouseListener(new MouseAdapter() {  // Label szövege kattinthatóságát állítja be,mely böngészõn keresztül nyitja meg a COdeMetropolis weblapot.
                                public void mouseClicked(MouseEvent e) {
                                  if (e.getClickCount() > 0) {
                                    if (Desktop.isDesktopSupported()) {
                                      Desktop desktop = Desktop.getDesktop();
                                      try {
                                        URI uri = new URI("http://codemetropolis.github.io/CodeMetropolis/");
                                        desktop.browse(uri);
                                      } catch (IOException ex) {
                                        ex.printStackTrace();
                                      } catch (URISyntaxException ex) {
                                        ex.printStackTrace();
                                      }
                                    }
                                  }
                                }
                              });
        newFrame.pack();
        newFrame.setLocationRelativeTo(self);
        newFrame.setLayout(null);
        newFrame.add(link);
        newFrame.setVisible(true);
        newFrame.setSize(555, 400);
        newFrame.add(photo);
        newFrame.add(label1);
        newFrame.add(label2);
        newFrame.add(label3);
        newFrame.add(label4);
        newFrame.add(label5);
        newFrame.add(label6);
        newFrame.add(label7);
        newFrame.add(label8);
        newFrame.add(clickHere);
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      }
    });
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
