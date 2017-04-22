package codemetropolis.toolchain.rendering;

import codemetropolis.toolchain.commons.executor.ExecutorArgs;
import codemetropolis.toolchain.rendering.model.Themes;

public class RenderingExecutorArgs extends ExecutorArgs {

	private String inputFile;
	private String worldPath;
	private boolean overwriteSilently;
	private int maxTime;
	private Themes theme;

	public RenderingExecutorArgs(String inputFile, String worldPath) {
		this(inputFile, worldPath, false);
	}

	public RenderingExecutorArgs(String inputFile, String worldPath, boolean overwriteSilently) {
		this(inputFile, worldPath, overwriteSilently, Themes.BASIC, Integer.MAX_VALUE);
	}

	public RenderingExecutorArgs(String inputFile, String worldPath, boolean overwriteSilently, Themes theme) {
		this(inputFile, worldPath, overwriteSilently, theme, Integer.MAX_VALUE);
	}

	public RenderingExecutorArgs(String inputFile, String worldPath, boolean overwriteSilently, Themes theme,
		int maxTime) {

		super();
		this.inputFile = inputFile;
		this.worldPath = worldPath;
		this.overwriteSilently = overwriteSilently;
		this.theme = theme;
		this.maxTime = maxTime;
	}

	public String getInputFile() {
		return inputFile;
	}

	public String getWorldPath() {
		return worldPath;
	}

	public boolean isSilentOverwriteEnabled() {
		return overwriteSilently;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public Themes getTheme() {
		return theme;
	}

}
