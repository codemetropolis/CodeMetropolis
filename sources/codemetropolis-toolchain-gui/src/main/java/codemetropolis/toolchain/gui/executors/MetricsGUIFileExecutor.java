package codemetropolis.toolchain.gui.executors;

import java.io.File;
import java.io.PrintStream;

import codemetropolis.toolchain.commons.util.Resources;
import codemetropolis.toolchain.gui.beans.ExecutionException;
import codemetropolis.toolchain.gui.beans.ExecutionOptions;
import codemetropolis.toolchain.metrics.MetricsExecutor;
import codemetropolis.toolchain.metrics.MetricsExecutorArgs;

public class MetricsGUIFileExecutor implements ToolchainExecutor{
	
	@Override
	public void execute(File cmRoot, ExecutionOptions executionOptions, PrintStream out) throws ExecutionException {
		MetricsExecutorArgs args = assembleArguments(cmRoot.getAbsolutePath(), executionOptions);
	    MetricsExecutor executor = new MetricsExecutor();
	    executor.setPrintStream(out);
	    executor.setErrorStream(out);
	    executor.setPrefix(Resources.get("metrics_prefix"));
	    executor.setErrorPrefix(Resources.get("error_prefix"));
	    if (!executor.execute(args)) {
	      throw new ExecutionException("Failed to complete creating metrics visualization!");
	    }
	}
	
	private MetricsExecutorArgs assembleArguments(String cmRootString, ExecutionOptions executionOptions){
		File sourceMeterExe = (File) executionOptions.getMetricGenerationParams().get("sourceMeterExe");
		return new MetricsExecutorArgs(
				executionOptions.getConverterType().toString(),
				cmRootString,
				executionOptions.getProjectName(),
				executionOptions.getMinecraftRoot().getAbsolutePath(),
				sourceMeterExe.getParent() + File.separator + "WindowsTools" + File.separator + "MetricHunter.threshold");
	}
}