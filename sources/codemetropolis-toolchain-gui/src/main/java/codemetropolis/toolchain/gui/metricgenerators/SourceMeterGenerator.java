package codemetropolis.toolchain.gui.metricgenerators;

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
    setTabTitle("SourceMeter");
    setConverterType(ConverterType.SOURCEMETER);

    setLayout(null);

    addProjectRootField();
    addSourceMeterExecutableField();
  }

  /**
   * Adds the project root browser to the panel.
   */
  public void addProjectRootField() {
    CMLabel label = new CMLabel("Project root:", 5, 5, 120, 30);
    projectRootPath = new CMTextField(130, 5, 225, 30);
    CMButton browseButton = new CMButton("Browse", 360, 5, 100, 30);
    browseButton.addActionListener(new BrowseListener(projectRootPath, JFileChooser.DIRECTORIES_ONLY, null));

    add(label);
    add(projectRootPath);
    add(browseButton);
  }

  /**
   * Adds the SourceMeter executable browser to the panel.
   */
  public void addSourceMeterExecutableField() {
    CMLabel label = new CMLabel("SourceMeter exe:", 5, 40, 120, 30);
    sourceMeterPath = new CMTextField(130, 40, 225, 30);
    CMButton browseButton = new CMButton("Browse", 360, 40, 100, 30);
    browseButton.addActionListener(new BrowseListener(sourceMeterPath, JFileChooser.FILES_ONLY, EXE_FILTER));

    add(label);
    add(sourceMeterPath);
    add(browseButton);
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
        GuiUtils.showError("Invalid SourceMeter exe file!");
        return false;
      } else if (projectRoot == null || !projectRoot.exists() || !projectRoot.isDirectory() || !projectRoot.canRead()) {
        GuiUtils.showError("Invalid project root!");
        return false;
      }
    } catch (ClassCastException e) {
      GuiUtils.showError("Unexpected error occured!");
    }

    return true;
  }

}
