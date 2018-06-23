package xyz.imwyy.symphony;

import xyz.imwyy.symphony.util.PropsUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * 属性值
 * create by stephen on 2018/5/18
 */
public class ConfigContext {

    public static final String CONTEXT_CONFIG_FILE = "symphonyContext.xml";

    public static final String CONFIG_FILE = "symphony.properties";
    public static final String JDBC_DRIVER = "symphony.jdbc.driver";
    public static final String JDBC_URL = "symphony.jdbc.url";
    public static final String JDBC_USERNAME = "symphony.jdbc.username";
    public static final String JDBC_PASSWORD = "symphony.jdbc.password";

    public static final String APP_BASE_PACKAGE = "symphony.base_package";
    public static final String APP_JSP_PATH = "symphony.jsp_path";
    public static final String APP_ASSET_PATH = "symphony.asset_path";


    private static Properties CONFIG_PROPS = PropsUtil.loadProps(CONFIG_FILE);

    public static String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigContext.JDBC_DRIVER);
    }

    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigContext.JDBC_URL);
    }

    public static String getJdbcUsername() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigContext.JDBC_USERNAME);
    }

    public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigContext.JDBC_PASSWORD);
    }

    /**
     * 获取应用基础包名
     */
    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigContext.APP_BASE_PACKAGE);
    }

    /**
     * 获取应用JSP路径 有默认值（配置可选）
     */
    public static String getAppJspPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigContext.APP_JSP_PATH, "/");
    }

    /**
     * 获取应用静态资源路径 有默认值（配置可选）
     */
    public static String getAppAssetPath() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigContext.APP_ASSET_PATH, "/asset/");
    }
}
