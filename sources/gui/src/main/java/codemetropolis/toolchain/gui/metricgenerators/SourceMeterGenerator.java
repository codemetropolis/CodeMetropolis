package codemetropolis.toolchain.gui.metricgenerators;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import codemetropolis.toolchain.converter.control.ConverterType;
import codemetropolis.toolchain.gui.beans.ExecutionOptions;
import codemetropolis.toolchain.gui.components.CMButton;
import codemetropolis.toolchain.gui.components.CMLabel;
import codemetropolis.toolchain.gui.components.CMMetricPanel;
import codemetropolis.toolchain.gui.components.CMTextField;
import codemetropolis.toolchain.gui.components.listeners.BrowseListener;
import codemetropolis.toolchain.gui.utils.ExeFileFilter;
import codemetropolis.toolchain.gui.utils.GuiUtils;
import codemetropolis.toolchain.gui.utils.Translations;

/**
 * Metric generation settings panel for the SonarQube settings.
 *
 * @author Abel Szkalisity {@literal <SZAVAET.SZE>}
 */
public class SourceMeterGenerator extends CMMetricPanel {

  private static final long serialVersionUID = 1L;

  private static final FileFilter EXE_FILTER = new ExeFileFilter();

  private CMTextField projectRootPath;
  private CMTextField sourceMeterPath;

  /**
   * Instantiates a SourceMeter settings panel.
   */
  public SourceMeterGenerator() {
    setTabTitle(Translations.t("gui_tab_sm"));
    setConverterType(ConverterType.SOURCEMETER);

    setLayout(null);

    addProjectRootField();
    addSourceMeterExecutableField();
    addSourceMeterDownloadButton();
    
  }

  /**
   * Adds the project root browser to the panel.
   */
  public void addProjectRootField() {
    CMLabel label = new CMLabel(Translations.t("gui_l_project_root"), 5, 5, 120, 30);
    projectRootPath = new CMTextField(130, 5, 225, 30);
    CMButton browseButton = new CMButton(Translations.t("gui_b_browse"), 360, 5, 100, 30);
    browseButton.addActionListener(new BrowseListener(projectRootPath, JFileChooser.DIRECTORIES_ONLY, null));

    add(label);
    add(projectRootPath);
    add(browseButton);
  }

  /**
   * Adds the SourceMeter executable browser to the panel.
   */
  public void addSourceMeterExecutableField() {
    CMLabel label = new CMLabel(Translations.t("gui_l_sm_exe"), 5, 40, 120, 30);
    sourceMeterPath = new CMTextField(130, 40, 225, 30);
    CMButton browseButton = new CMButton(Translations.t("gui_b_browse"), 360, 40, 100, 30);
    browseButton.addActionListener(new BrowseListener(sourceMeterPath, JFileChooser.FILES_ONLY, EXE_FILTER));
    
    add(label);
    add(sourceMeterPath);
    add(browseButton);
    
  }
  
  /***
   * Adds the Download SourceMeter button to the panel which opens the user default browser.
   * @author Taskovics Lorinc Karoly {@literal <IT4VZW>} 
   */
  public void addSourceMeterDownloadButton() {
	  CMButton downloadSrcM = new CMButton("Download SourceMeter", 30, 100, 300, 30);
	    downloadSrcM.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            try {
	                String url = "https://sourcemeter.com/download";
	                java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
	            } catch (java.io.IOException exp) {
	                System.out.println(exp.getMessage());
	            }
	        }
	    });
	    add(downloadSrcM);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void fillFields(ExecutionOptions executionOptions) {
    File sourceMeterExe = new File(sourceMeterPath.getText());
    File projectRoot = new File(projectRootPath.getText());

    Map<String, Object> params = executionOptions.getMetricGenerationParams();
    params.put("sourceMeterExe", sourceMeterExe);
    params.put("projectRoot", projectRoot);

    executionOptions.setConverterType(converterType);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean validateFields(ExecutionOptions executionOptions) {
    Map<String, Object> params = executionOptions.getMetricGenerationParams();

    try {
      File sourceMeterExe = (File) params.get("sourceMeterExe");
      File projectRoot = (File) params.get("projectRoot");

      if (sourceMeterExe == null || !sourceMeterExe.exists() || !sourceMeterExe.isFile() || !sourceMeterExe.canRead()
          || !sourceMeterExe.canExecute() || !sourceMeterExe.getName().endsWith(".exe")) {
        GuiUtils.showError(Translations.t("gui_err_invalid_sm_exe"));
        return false;
      } else if (projectRoot == null || !projectRoot.exists() || !projectRoot.isDirectory() || !projectRoot.canRead()) {
        GuiUtils.showError(Translations.t("gui_err_invalid_project_root"));
        return false;
      }
    } catch (ClassCastException e) {
      GuiUtils.showError(Translations.t("gui_err_unexpected_err"));
    }

    return true;
  }

}
