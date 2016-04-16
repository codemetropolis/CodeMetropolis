package codemetropolis.toolchain.gui.executors;

import java.io.File;

import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.commons.util.Settings;
import codemetropolis.toolchain.gui.beans.ExecutionException;
import codemetropolis.toolchain.gui.beans.ExecutionOptions;
import codemetropolis.toolchain.placing.PlacingExecutor;
import codemetropolis.toolchain.placing.PlacingExecutorArgs;

/**
 * {@link ToolchainExecutor} implementation for the placing tool.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class PlacingToolExecutor implements ToolchainExecutor {

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(File cmRoot, ExecutionOptions executionOptions) throws ExecutionException {
    FileLogger.load(Settings.get("placing_log_file"));

    PlacingExecutorArgs args = assembleArguments(cmRoot, executionOptions);
    PlacingExecutor executor = new PlacingExecutor();
    executor.setPrefix(Resources.get("placing_prefix"));
    executor.setErrorPrefix(Resources.get("error_prefix"));
    executor.execute(args);
  }

  /**
   * Creates the {@link PlacingExecutorArgs} instance that will be used for the execution of the placing tool.
   *
   * @param cmRoot The path of the folder used to store the intermediate files in.
   * @param executionOptions The {@link ExecutionOptions} instance.
   * @return The assembled {@link PlacingExecutorArgs} object which will be used for execution.
   */
  private PlacingExecutorArgs assembleArguments(File cmRoot, ExecutionOptions executionOptions) {
    return new PlacingExecutorArgs(
        cmRoot.getAbsolutePath() + File.separator + "mapping-results.xml",
        cmRoot.getAbsolutePath() + File.separator + "placing-results.xml",
        executionOptions.getLayoutAlgorithm().toString(),
        executionOptions.isShowMap());
  }

}
