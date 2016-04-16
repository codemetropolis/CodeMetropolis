package codemetropolis.toolchain.gui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import codemetropolis.toolchain.gui.components.CMMetricPanel;
import codemetropolis.toolchain.gui.executors.ConverterToolExecutor;
import codemetropolis.toolchain.gui.executors.MappingToolExecutor;
import codemetropolis.toolchain.gui.executors.MetricGeneratorExecutor;
import codemetropolis.toolchain.gui.executors.PlacingToolExecutor;
import codemetropolis.toolchain.gui.executors.RenderingToolExecutor;
import codemetropolis.toolchain.gui.metricgenerators.SonarQubeGenerator;
import codemetropolis.toolchain.gui.metricgenerators.SourceMeterGenerator;
import codemetropolis.toolhchain.gui.beans.ExecutionException;
import codemetropolis.toolhchain.gui.beans.ExecutionOptions;

/**
 * Controller class for the GUI that handles tasks like managing the toolchain execution.
 *
 * @author Abel Szkalisity {@literal <SZAVAET.SZE>}
 */
public class GUIController {

  private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyyMMdd-hhmmss-SSS");

  private ExecutionOptions executionOptions;
  private List<CMMetricPanel> metricGeneratorPanels = new ArrayList<CMMetricPanel>();

  /**
   * Instantiates a {@link GUIController} and adds the available metricGeneration options.
   */
  public GUIController() {
    executionOptions = new ExecutionOptions();

    metricGeneratorPanels.add(new SourceMeterGenerator());
    metricGeneratorPanels.add(new SonarQubeGenerator());
  }

  /**
   * Handles toolchain execution. Creates the folder that stores the intermediate project files, then it runs each part
   * of the toolchain in sequence.
   */
  public void execute() {
    try {
      File projectRoot = createTargetFolder();

      new MetricGeneratorExecutor().execute(projectRoot, executionOptions);
      new ConverterToolExecutor().execute(projectRoot, executionOptions);
      new MappingToolExecutor().execute(projectRoot, executionOptions);
      new PlacingToolExecutor().execute(projectRoot, executionOptions);
      new RenderingToolExecutor().execute(projectRoot, executionOptions);
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates the folder that will be used to store the intermediate project files.
   *
   * @return The {@link File} object for the generated directory.
   * @throws ExecutionException if creating the directory failed.
   */
  private File createTargetFolder() throws ExecutionException {
    File cmRoot = new File(executionOptions.getMinecraftRoot().getAbsolutePath() + File.separator
        + ".code-metropolis");
    if (!cmRoot.exists()) {
      cmRoot.mkdir();
    }

    File projectRoot = new File(cmRoot.getAbsolutePath() + File.separator + getCurrentDateString());
    if (!projectRoot.mkdir()) {
      throw new ExecutionException("Failed to create project folder under minecraft root!");
    }
    return projectRoot;
  }

  /**
   * Gets the current date and time, then returns a formatted version of it, that can act as a valid directory name.
   *
   * @return The formatted date and time.
   */
  private String getCurrentDateString() {
    return DATE_FORMATTER.format(new Date());
  }

  public ExecutionOptions getExecutionOptions() {
    return executionOptions;
  }

  public List<CMMetricPanel> getMetricGeneratorPanels() {
    return metricGeneratorPanels;
  }

}
