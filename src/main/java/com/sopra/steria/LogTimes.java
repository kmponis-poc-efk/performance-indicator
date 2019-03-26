package com.sopra.steria;

// import org.fluentd.logger.FluentLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public final class LogTimes {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LogTimes.class);
    // private static final FluentLogger FLUENTD_LOG = FluentLogger.getLogger("com.sopra.steria");

    public final static Map<String, Long> methodTimeMap = new HashMap<>();

    public static void startTiming(String codeDevision) {
        if (null != codeDevision && !methodTimeMap.containsKey(codeDevision)) {
            methodTimeMap.put(codeDevision, System.currentTimeMillis());
        }
    }

    public static void endTiming(String codeDevision) {
        displayLogs(codeDevision, null, null);
    }

    public static void endTiming(String codeDevision, String loggingLevel) {
        displayLogs(codeDevision, loggingLevel, null);
    }

    public static void endTiming(String codeDevision, String loggingLevel, String description) {
        displayLogs(codeDevision, loggingLevel, description);
    }

    private static void displayLogs(String codeDevision, String loggingLevel, String description) {
        long time = 0;
        if (null != codeDevision && methodTimeMap.containsKey(codeDevision) && null != methodTimeMap.get(codeDevision)) {
            if (null == loggingLevel || "debug".equalsIgnoreCase(loggingLevel)) {
                time = System.currentTimeMillis() - methodTimeMap.get(codeDevision);
                LOGGER.debug("*********** Performance of '" + codeDevision + "' is: " + time + " ms");
                if (null != description) {
                    LOGGER.debug("*********** Description of '" + codeDevision + "' is: " + description);
                }
            } else if ("info".equalsIgnoreCase(loggingLevel)) {
                time = System.currentTimeMillis() - methodTimeMap.get(codeDevision);
                LOGGER.info("*********** Performance of '" + codeDevision + "' is: " + time + " ms");
                if (null != description) {
                    LOGGER.info("*********** Description of '" + codeDevision + "' is: " + description);
                }
            } else if ("warn".equalsIgnoreCase(loggingLevel)) {
                time = System.currentTimeMillis() - methodTimeMap.get(codeDevision);
                LOGGER.warn("*********** Performance of '" + codeDevision + "' is: " + time + " ms");
                if (null != description) {
                    LOGGER.warn("*********** Description of '" + codeDevision + "' is: " + description);
                }
            } else if ("error".equalsIgnoreCase(loggingLevel)) {
                time = System.currentTimeMillis() - methodTimeMap.get(codeDevision);
                LOGGER.error("*********** Performance of '" + codeDevision + "' is: " + time + " ms");
                if (null != description) {
                    LOGGER.error("*********** Description of '" + codeDevision + "' is: " + description);
                }
            }
            // FLUENTD_LOG.log("LogTimes", "Performance of '" + codeDevision + "'", time + " ms"); 
            methodTimeMap.remove(codeDevision);
        }
    }
}
