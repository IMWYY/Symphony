package xyz.imwyy.symphony.util;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载属性文件，获取属性值的工具类
 * create by stephen on 2018/5/18
 */
public class PropsUtil {

    /**
     * 加载属性文件到Properties
     */
    public static Properties loadProps(String file) {
        Properties props = null;
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
            if (inputStream == null) {
                throw new FileNotFoundException(file + " file is not found");
            }
            props = new Properties();
            props.load(inputStream);
        } catch (IOException e) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return props;
    }

    public static String getString(Properties properties, String key) {
        return getString(properties, key, "");
    }

    public static String getString(Properties properties, String key, String defaultValue) {
        String value = defaultValue;
        if (properties.contains(key)) {
            value = properties.getProperty(key);
        }
        return value;
    }

    public static int getInt(Properties properties, String key) {
        return getInt(properties, key, 0);
    }

    public static int getInt(Properties properties, String key, int defaultValue) {
        int value = defaultValue;
        if (properties.contains(key)) {
            try {
                value = Integer.valueOf(properties.getProperty(key));
            } catch (NumberFormatException e) {
                value = defaultValue;
            }
        }
        return value;
    }

    public static boolean getBoolean(Properties properties, String key) {
        return getBoolean(properties, key, false);
    }

    public static boolean getBoolean(Properties properties, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (properties.contains(key)) {
            try {
                value = Boolean.valueOf(properties.getProperty(key));
            } catch (NumberFormatException e) {
                value = defaultValue;
            }
        }
        return value;
    }
}
