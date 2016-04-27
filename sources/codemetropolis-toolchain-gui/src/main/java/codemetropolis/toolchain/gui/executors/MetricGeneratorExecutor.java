package codemetropolis.toolchain.gui.executors;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import codemetropolis.toolchain.gui.beans.ExecutionException;
import codemetropolis.toolchain.gui.beans.ExecutionOptions;
import codemetropolis.toolchain.gui.utils.Translations;

/**
 * {@link ToolchainExecutor} implementation for the metric generation.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class MetricGeneratorExecutor implements ToolchainExecutor {

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(File cmRoot, ExecutionOptions executionOptions) throws ExecutionException {
    switch (executionOptions.getConverterType()) {
      case SOURCEMETER:
        executeSourceMeter(cmRoot, executionOptions);
        break;
      case SONARQUBE:
        // Will be executed by the converter tool
        break;
      default:
        throw new ExecutionException(Translations.t("gui_err_invalid_converter"));
    }
  }

  /**
   * Executes the SourceMeter exe file with the proper parameters.
   *
   * @param cmRoot The path of the folder used to store the intermediate files in.
   * @param executionOptions The {@link ExecutionOptions} instance.
   * @throws ExecutionException if it fails to run SourceMeter, or if the process returns with an error code.
   */
  private void executeSourceMeter(File cmRoot, ExecutionOptions executionOptions) throws ExecutionException {
    Map<String, Object> params = executionOptions.getMetricGenerationParams();
    File sourceMeterExe = (File) params.get("sourceMeterExe");
    File projectRoot = (File) params.get("projectRoot");

    try {
      File resultsDir = createResultsDir(cmRoot);
      ProcessBuilder processBuilder = new ProcessBuilder(sourceMeterExe.getAbsolutePath(),
        "-projectName=\"" + executionOptions.getProjectName() + "\"",
        "-projectBaseDir=\"" + projectRoot.getAbsolutePath() + "\"",
        "-resultsDir=\"" + resultsDir.getAbsolutePath() + "\"");

      if (processBuilder.start().waitFor() != 0) {
        throw new ExecutionException(Translations.t("gui_err_sm_exec_failed"));
      }
    } catch (IOException | InterruptedException e) {
      throw new ExecutionException(Translations.t("gui_err_sm_run_failed"), e);
    }
  }

  /**
   * Creates a directory for the SourceMeter results in the {@code cmRoot} folder.
   *
   * @param cmRoot The path of the folder used to store the intermediate files in.
   * @return The folder for the SourceMeter results.
   * @throws ExecutionException if it fails to actually create the folder.
   */
  private File createResultsDir(File cmRoot) throws ExecutionException {
    File resultsDir = new File(cmRoot.getAbsolutePath() + File.separator + "source-meter");
    if (!resultsDir.mkdir()) {
      throw new ExecutionException(Translations.t("gui_err_mkdir_failed"));
    }

    return resultsDir;
  }

}
