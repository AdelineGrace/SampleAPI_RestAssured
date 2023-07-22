package utilities;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerLoad {

	private static Logger logger= LogManager.getLogger();
	
    public static void logInfo(String message) {
    	
    	logger.info(message);
	}
    
    public static void logDebug(String message) {
		logger.debug(message);
	}
	
    public static void logError(String message) {
		logger.error(message);
	}
    
    public static void logWarning(String message) {
		logger.warn(message);
	}
		
    
	
}
