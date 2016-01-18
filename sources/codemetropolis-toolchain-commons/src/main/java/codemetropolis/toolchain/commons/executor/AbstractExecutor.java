package codemetropolis.toolchain.commons.executor;

import java.io.PrintStream;

public abstract class AbstractExecutor {

	protected PrintStream printStream = System.out;
	protected PrintStream errorStream = System.err;
	
	public void setPrintStream(PrintStream printStream) {
		this.printStream = printStream;
	}
	
	public void setErrorStream(PrintStream errorStream) {
		this.errorStream = errorStream;
	}
	
	public abstract void execute(ExecutorArgs args);
	
}
