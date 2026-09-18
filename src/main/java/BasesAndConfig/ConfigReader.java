package BasesAndConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Generic, reusable properties file reader.
 * Loads a properties file from the classpath (src/main/resources or src/test/resources)
 * so that no configuration or test data values need to be hard-coded in the code, and no
 * absolute machine-specific path is required (unlike a fixed disk path).
 */
public class ConfigReader {

    private final Properties properties;

    public ConfigReader(String classpathResourceName) {
        this.properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(classpathResourceName)) {
            if (input == null) {
                throw new IllegalStateException("Unable to find resource on classpath: " + classpathResourceName);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load resource: " + classpathResourceName, e);
        }
    }

    public String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Missing property key: " + key);
        }
        return value.trim();
    }

    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue).trim();
    }

    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
