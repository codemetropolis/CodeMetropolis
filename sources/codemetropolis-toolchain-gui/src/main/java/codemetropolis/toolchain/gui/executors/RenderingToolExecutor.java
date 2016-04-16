package codemetropolis.toolchain.gui.executors;

import java.io.File;

import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.commons.util.Settings;
import codemetropolis.toolchain.rendering.RenderingExecutor;
import codemetropolis.toolchain.rendering.RenderingExecutorArgs;
import codemetropolis.toolhchain.gui.beans.ExecutionException;
import codemetropolis.toolhchain.gui.beans.ExecutionOptions;

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
  public void execute(File cmRoot, ExecutionOptions executionOptions) throws ExecutionException {
    FileLogger.load(Settings.get("rendering_log_file"));

    RenderingExecutorArgs args = assembleArguments(cmRoot, executionOptions);
    RenderingExecutor executor = new RenderingExecutor();
    executor.setPrefix(Resources.get("rendering_prefix"));
    executor.setErrorPrefix(Resources.get("error_prefix"));
    executor.execute(args);
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
        true);
  }
  
}
