package BasesAndConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Thin static wrapper around log4j2 so callers don't need to fetch their own Logger
 * instance; the logger name is derived from the calling class automatically.
 */
public class LogUtil {

    private LogUtil() {
    }

    private static Logger logger() {
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[3].getClassName());
    }

    public static void info(String message) {
        logger().info(message);
    }

    public static void warn(String message) {
        logger().warn(message);
    }

    public static void error(String message) {
        logger().error(message);
    }

    public static void debug(String message) {
        logger().debug(message);
    }
}
