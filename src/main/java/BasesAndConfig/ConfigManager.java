package BasesAndConfig;

// Small wrapper so the rest of the code doesn't deal with raw property strings/keys.
// Add a getter here whenever a new value gets added to config.properties.
public final class ConfigManager {

    private static final ConfigReader READER = new ConfigReader("config.properties");

    private ConfigManager() {
    }

    public static String baseUrl() {
        return READER.get("base.url");
    }

    public static String browser() {
        return READER.get("browser", "chrome");
    }

    public static boolean headless() {
        return READER.getBoolean("headless");
    }

    public static int implicitWaitSeconds() {
        return READER.getInt("implicit.wait.seconds");
    }

    public static int explicitWaitSeconds() {
        return READER.getInt("explicit.wait.seconds");
    }

    public static int pageLoadTimeoutSeconds() {
        return READER.getInt("page.load.timeout.seconds");
    }
}
