package codemetropolis.toolchain.metrics;

import codemetropolis.toolchain.commons.executor.ExecutorArgs;

/**
 * MetricsExecutorArgs class for the MetricsExecutor.
 * This class is the instance which the MetricsExecutor uses.
 * 
 * @author Dora Borsos {@literal <BODWACT.SZE>}
 */

public class MetricsExecutorArgs extends ExecutorArgs {
	
	private String conversionType;
	private String projectName;
	private String cmPath;
	private String minecraftPath;
	private String thresHoldPath;

	public MetricsExecutorArgs(String conversionType, String cmRootString, String projectName, String minecraftRootString, String thresHoldPath) {
		super();
		this.conversionType = conversionType;
		this.cmPath = cmRootString;
		this.projectName = projectName;
		this.minecraftPath = minecraftRootString;
		this.thresHoldPath = thresHoldPath;
	}

	public String getType() {
		return conversionType;
	}
	
	public String getCmRoot() {
		return cmPath;
	}
	
	public String getProjectName() {
		return projectName;
	}

	public String getMinecraftRoot(){
		return minecraftPath;
	}
	
	public String getThresholdPath() {
		return thresHoldPath;
	}

	public void setThresholdPath(String thresHoldPath) {
		this.thresHoldPath = thresHoldPath;
	}
	
}
