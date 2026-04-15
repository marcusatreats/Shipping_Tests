package org.google.shipping.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads test configuration from:
 * 1. System properties (e.g. -Dbase.url=https://... via Maven)
 * 2. test.properties file on the classpath (fallback)
 */
public class ConfigManager {

    private static final Properties props = new Properties();

    static {
        try (InputStream in = ConfigManager.class
                .getClassLoader()
                .getResourceAsStream("config/test.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test.properties", e);
        }
    }

    /**
     * Get a config value. System property takes priority over test.properties.
     * Useful for overriding via Maven: -Dbase.url=https://staging.example.com
     */
    public static String get(String key) {
        return System.getProperty(key, props.getProperty(key));
    }

    /**
     * Get a config value with a default fallback.
     */
    public static String get(String key, String defaultValue) {
        return System.getProperty(key, props.getProperty(key, defaultValue));
    }

    /**
     * Get a boolean config value.
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String val = get(key);
        return val != null ? Boolean.parseBoolean(val) : defaultValue;
    }
}