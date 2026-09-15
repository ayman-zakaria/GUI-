package com.assessment.gui.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Generic, reusable properties file reader.
 * Loads a properties file once from the classpath and exposes typed accessors,
 * so that no configuration or test data values need to be hard-coded in the code.
 */
public class PropertiesReader {

    private final Properties properties;

    public PropertiesReader(String classpathResourceName) {
        this.properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(classpathResourceName)) {
            if (input == null) {
                throw new IllegalStateException("Unable to find resource: " + classpathResourceName);
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
