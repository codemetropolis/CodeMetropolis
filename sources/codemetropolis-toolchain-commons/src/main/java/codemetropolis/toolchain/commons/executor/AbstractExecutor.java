package codemetropolis.toolchain.commons.executor;

import java.io.PrintStream;

public abstract class AbstractExecutor {

	private PrintStream printStream = System.out;
	private PrintStream errorStream = System.err;
	private String prefix = "";
	private String errorPrefix = "";
	
	public void setPrintStream(PrintStream printStream) {
		this.printStream = printStream;
	}
	
	public void setErrorStream(PrintStream errorStream) {
		this.errorStream = errorStream;
	}
	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setErrorPrefix(String errorPrefix) {
		this.errorPrefix = errorPrefix;
	}
	
	protected void print(String message, Object... args ) {
		print(true, message, args);
	}
	
	protected void print(boolean withPrefix, String message, Object... args ) {
		String str = String.format("%s%s\n", withPrefix ? prefix : "", message);
		printStream.printf(str, args);
	}
	
	protected void printError(String message, Object... args ) {
		printError(true, message, args);
	}
	
	protected void printError(boolean withPrefix, String message, Object... args ) {
		String str = String.format("%s%s\n", withPrefix ? errorPrefix : "", message);
		errorStream.printf(str, args);
	}
	
	public abstract void execute(ExecutorArgs args);
	
}
