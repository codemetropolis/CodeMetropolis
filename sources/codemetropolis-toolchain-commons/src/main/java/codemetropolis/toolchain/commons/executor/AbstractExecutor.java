package codemetropolis.toolchain.commons.executor;

import java.io.PrintStream;

import codemetropolis.toolchain.commons.util.FileLogger;

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
		print(true, true, message, args);
	}
	
	protected void print(boolean log, String message, Object... args ) {
		print(true, log, message, args);
	}
	
	protected void print(boolean withPrefix, boolean log, String message, Object... args ) {
		String str = String.format("%s%s\n", withPrefix ? prefix : "", message);
		String paramStr = String.format(str, args);
		printStream.print(paramStr);
		if(log) FileLogger.logInfo(paramStr);
	}
	
	protected void printError(Exception exception, String message, Object... args ) {
		printError(true, true, exception, message, args);
	}
	
	protected void printError(boolean log, Exception exception, String message, Object... args ) {
		printError(true, log, exception, message, args);
	}
	
	protected void printError(boolean withPrefix, boolean log, Exception exception, String message, Object... args ) {
		String str = String.format("%s%s\n", withPrefix ? errorPrefix : "", message);
		String paramStr = String.format(str, args);
		errorStream.print(paramStr);
		if(log) FileLogger.logError(paramStr, exception);
	}
	
	public abstract boolean execute(ExecutorArgs args);
	
}
