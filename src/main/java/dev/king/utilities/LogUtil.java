package dev.king.utilities;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogUtil {
	
	private final static Logger logger = Logger.getGlobal();	
	
	
	public static void main(String[] args) {
		LogUtil.debug("For debugging");
		LogUtil.warning("Exceptions and Errors");
		LogUtil.error("Catastrophic failure or crash");
		
	}
	public static void debug(String log) {
		logger.log(Level.INFO, log);
	}

	public static void warning(String log) {
		logger.log(Level.WARNING, log);
	}
	
	public static void error(String log) {
		logger.log(Level.SEVERE, log);
	}
	
}
