package codemetropolis.toolchain.rendering;

import org.kohsuke.args4j.Option;

import codemetropolis.toolchain.rendering.model.Themes;

public class CommandLineOptions {
	
	@Option(name="-h", aliases = { "--help" })
	private boolean showHelp = false;
	
	@Option(name="-i", aliases = { "--input" })
	private String inputFile = null;
	
	@Option(name="-w", aliases = { "-world", "--world" })
	private String world = null;

	@Option(name="-s", aliases = { "-ow", "--overwrite", "--silent" })
	private boolean overwriteSilently = false;
	
	@Option(name="-t", aliases = { "-theme", "--theme" })
	private Themes theme = Themes.BASIC; 
		
	public boolean showHelp() {
		return showHelp;
	}
	
	public String getInputFile() {
		return inputFile;
	}

	public String getWorld() {
		return world;
	}

	public boolean overwriteSilently() {
		return overwriteSilently;
	}
	
	public Themes getTheme() {
		return theme;
	}

}
