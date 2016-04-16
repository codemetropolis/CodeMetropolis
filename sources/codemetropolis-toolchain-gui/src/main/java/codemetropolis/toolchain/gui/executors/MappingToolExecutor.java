package codemetropolis.toolchain.gui.executors;

import java.io.File;

import codemetropolis.toolchain.commons.util.FileLogger;
import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.commons.util.Settings;
import codemetropolis.toolchain.mapping.MappingExecutor;
import codemetropolis.toolchain.mapping.MappingExecutorArgs;
import codemetropolis.toolhchain.gui.beans.ExecutionException;
import codemetropolis.toolhchain.gui.beans.ExecutionOptions;

/**
 * {@link ToolchainExecutor} implementation for the mapping tool.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public class MappingToolExecutor implements ToolchainExecutor {

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(File cmRoot, ExecutionOptions executionOptions) throws ExecutionException {
    FileLogger.load(Settings.get("mapping_log_file"));

    MappingExecutorArgs args = assembleArguments(cmRoot, executionOptions);
    MappingExecutor executor = new MappingExecutor();
    executor.setPrefix(Resources.get("mapping_prefix"));
    executor.setErrorPrefix(Resources.get("error_prefix"));
    executor.execute(args);
  }

  /**
   * Creates the {@link MappingExecutorArgs} instance that will be used for the execution of the mapping tool.
   *
   * @param cmRoot The path of the folder used to store the intermediate files in.
   * @param executionOptions The {@link ExecutionOptions} instance.
   * @return The assembled {@link MappingExecutorArgs} object which will be used for execution.
   */
  private MappingExecutorArgs assembleArguments(File cmRoot, ExecutionOptions executionOptions) {
    return new MappingExecutorArgs(
        cmRoot.getAbsolutePath() + File.separator + "converter-results.xml",
        cmRoot.getAbsolutePath() + File.separator + "mapping-results.xml",
        executionOptions.getMappingXml().getAbsolutePath(),
        executionOptions.getScale(),
        executionOptions.isValidate());
  }

}
