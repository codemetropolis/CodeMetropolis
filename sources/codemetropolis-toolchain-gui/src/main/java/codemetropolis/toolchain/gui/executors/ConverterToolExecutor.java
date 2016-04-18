package codemetropolis.toolchain.gui.executors;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.commons.util.Settings;
import codemetropolis.toolchain.converter.ConverterExecutor;
import codemetropolis.toolchain.converter.ConverterExecutorArgs;
import codemetropolis.toolchain.gui.beans.ExecutionException;
import codemetropolis.toolchain.gui.beans.ExecutionOptions;

/**
 * {@link ToolchainExecutor} implementation for the converter tool.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class ConverterToolExecutor implements ToolchainExecutor {

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(File cmRoot, ExecutionOptions executionOptions) throws ExecutionException {
    FileLogger.load(Settings.get("converter_log_file"));

    ConverterExecutorArgs args = assembleArguments(cmRoot, executionOptions);
    ConverterExecutor executor = new ConverterExecutor();
    executor.setPrefix(Resources.get("converter_prefix"));
    executor.setErrorPrefix(Resources.get("error_prefix"));
    executor.execute(args);
  }

  /**
   * Converts the parameters from UI to match the names and types used by the {@link ConverterExecutor}.
   *
   * @param executionOptions The {@link ExecutionOptions} instance.
   * @return The converted parameter map.
   */
  private Map<String, String> convertParams(ExecutionOptions executionOptions) {
    Map<String, String> params = new HashMap<String, String>();
    Map<String, Object> executionParams = executionOptions.getMetricGenerationParams();

    switch (executionOptions.getConverterType()) {
      case SONARQUBE:
        boolean splitDirs = (boolean) executionParams.get("splitDirs");
        params.put("username", executionParams.get("username").toString());
        params.put("password", executionParams.get("password").toString());
        params.put("projects", executionParams.get("projects").toString());
        params.put("splitDirs", splitDirs ? "true" : "false");
        break;
      default:
        break;
    }

    return params;
  }

  /**
   * Creates the {@link ConverterExecutorArgs} instance that will be used for the execution of the converter tool.
   *
   * @param cmRoot The path of the folder used to store the intermediate files in.
   * @param executionOptions The {@link ExecutionOptions} instance.
   * @return The assembled {@link ConverterExecutorArgs} object which will be used for execution.
   * @throws ExecutionException if the {@link codemetropolis.toolchain.converter.control.ConverterType} in the
   *           {@code executionOptions} is unhandled.
   */
  private ConverterExecutorArgs assembleArguments(File cmRoot, ExecutionOptions executionOptions)
      throws ExecutionException {

    String source = "";
    switch (executionOptions.getConverterType()) {
      case SOURCEMETER:
        source = findSourceMeterGraphPath(cmRoot);
        break;
      case SONARQUBE:
        source = executionOptions.getMetricGenerationParams().get("url").toString();
        break;
      default:
        throw new ExecutionException("Unhandled metric source!");
    }

    return new ConverterExecutorArgs(
        executionOptions.getConverterType(),
        source,
        cmRoot.getAbsolutePath() + File.separator + "converter-results.xml",
        convertParams(executionOptions));
  }

  /**
   * Attempts to find the graph file in the source-meter folder. Traversed through
   * source-meter/<project>/<language>/<timestamp>/ folders to find the actual contents.
   *
   * @param cmRoot The path of the folder used to store the intermediate files in.
   * @return The path of the graph file, if found.
   * @throws ExecutionException if the graph file was not found.
   */
  private String findSourceMeterGraphPath(File cmRoot) throws ExecutionException {
    File project = new File(cmRoot.getAbsolutePath() + File.separator + "source-meter").listFiles()[0];
    File contents = project.listFiles()[0].listFiles()[0];

    File graph = new File(contents.getAbsolutePath() + File.separator + project.getName() + ".graph");
    if (graph.exists() && graph.isFile() && graph.canRead()) {
      return graph.getAbsolutePath();
    } else {
      throw new ExecutionException("SourceMeter graph file not found at the expected location!");
    }
  }

}
