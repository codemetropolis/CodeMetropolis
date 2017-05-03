package codemetropolis.toolchain.gui.executors;

import java.io.File;
import java.io.PrintStream;

import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.commons.util.Settings;
import codemetropolis.toolchain.gui.beans.ExecutionException;
import codemetropolis.toolchain.gui.beans.ExecutionOptions;
import codemetropolis.toolchain.rendering.RenderingExecutor;
import codemetropolis.toolchain.rendering.RenderingExecutorArgs;

/**
 * {@link ToolchainExecutor} implementation for the rendering tool.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class RenderingToolExecutor implements ToolchainExecutor {

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(File cmRoot, ExecutionOptions executionOptions, PrintStream out) throws ExecutionException {
    FileLogger.load(Settings.get("rendering_log_file"));

    RenderingExecutorArgs args = assembleArguments(cmRoot, executionOptions);
    RenderingExecutor executor = new RenderingExecutor();
    executor.setPrintStream(out);
    executor.setErrorStream(out);
    executor.setPrefix(Resources.get("rendering_prefix"));
    executor.setErrorPrefix(Resources.get("error_prefix"));
    if (!executor.execute(args)) {
      throw new ExecutionException("Failed to complete rendering step!");
    }
  }

  /**
   * Creates the {@link RenderingExecutorArgs} instance that will be used for the execution of the rendering tool.
   *
   * @param cmRoot The path of the folder used to store the intermediate files in.
   * @param executionOptions The {@link ExecutionOptions} instance.
   * @return The assembled {@link RenderingExecutorArgs} object which will be used for execution.
   */
  private RenderingExecutorArgs assembleArguments(File cmRoot, ExecutionOptions executionOptions) {
    return new RenderingExecutorArgs(
      cmRoot.getAbsolutePath() + File.separator + "placing-results.xml",
      executionOptions.getMinecraftRoot().getAbsolutePath() + File.separator + "saves" + File.separator
        + executionOptions.getProjectName().replace(" +;\\/\"?", ""),
      true,
      executionOptions.getTheme());
  }
  
}
