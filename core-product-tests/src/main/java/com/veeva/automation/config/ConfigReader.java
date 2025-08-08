package com.veeva.automation.config;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties props = new Properties();
    static {
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            props.load(is);
        } catch (Exception e) { e.printStackTrace(); }
    }
    public static String get(String key){ return props.getProperty(key); }
    public static int getInt(String key){ return Integer.parseInt(get(key)); }
    public static boolean getBool(String key){ return Boolean.parseBoolean(get(key)); }
}
