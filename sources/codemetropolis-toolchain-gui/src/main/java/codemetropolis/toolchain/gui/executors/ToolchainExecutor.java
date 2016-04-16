package codemetropolis.toolchain.gui.executors;

import java.io.File;

import codemetropolis.toolhchain.gui.beans.ExecutionException;
import codemetropolis.toolhchain.gui.beans.ExecutionOptions;

/**
 * Interface class for the specific toolchain executors.
 *
 * @author Adam Bankeszi {@literal <BAAVAGT.SZE>}
 */
public interface ToolchainExecutor {

  /**
   * Executes a tool with the given parameters.
   *
   * @param cmRoot The folder where the intermediate project files will be stored at.
   * @param executionOptions The data required to specify execution parameters.
   * @throws ExecutionException if any error occured during the execution.
   */
  public void execute(File cmRoot, ExecutionOptions executionOptions) throws ExecutionException;

}
