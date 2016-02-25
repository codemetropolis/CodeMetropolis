package codemetropolis.toolchain.commons.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class FileLogger {
	
	private static final String LOGGER_NAME = "CodeMetropolis.Log";
	private static final String LOG_FILE = "../CodeMetropolis_toolchain.log";
	
	private static Logger logger;  
	private static FileHandler fileHandler;
	
	private FileLogger() {}
	 
	static {
		try {
			logger = Logger.getLogger(LOGGER_NAME);  
			logger.setLevel(Level.ALL);
			logger.setUseParentHandlers(false);
			fileHandler = new FileHandler(LOG_FILE, true); 
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
		} catch (SecurityException | IOException e) {
			System.err.println("An exception has occurred while initializing file logger.");
		}  
	}
	
	public static void logInfo(String message) {
		logger.log(Level.INFO, message);
	}
	
	public static void logWarning(String message, Exception exception) {
		logger.log(Level.WARNING, message, exception);
	}
	 
	public static void logError(String message, Exception exception) {
		logger.log(Level.SEVERE, message, exception);
	}
	 
}
