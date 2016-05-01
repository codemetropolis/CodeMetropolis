package codemetropolis.toolchain.gui.executors;

import java.io.File;
import java.io.PrintStream;

import codemetropolis.toolchain.gui.beans.ExecutionException;
import codemetropolis.toolchain.gui.beans.ExecutionOptions;

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
   * @param out The {@link java.io.PrintStream} for the logs of the executors.
   * @throws ExecutionException if any error occured during the execution.
   */
  public void execute(File cmRoot, ExecutionOptions executionOptions, PrintStream out) throws ExecutionException;

}
