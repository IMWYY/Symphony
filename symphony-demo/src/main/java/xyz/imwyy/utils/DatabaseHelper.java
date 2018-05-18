package xyz.imwyy.utils;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * create by stephen on 2018/5/18
 */
public class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static BasicDataSource DATA_SOURCE;

    static {
        Properties props = PropsUtil.loadProps("config.properties");
        String DRIVER = props.getProperty("jdbc.driver");
        String URL = props.getProperty("jdbc.url");
        String USERNAME = props.getProperty("jdbc.username");
        String PASSWORD = props.getProperty("jdbc.password");

        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(DRIVER);
        DATA_SOURCE.setUrl(URL);
        DATA_SOURCE.setUsername(USERNAME);
        DATA_SOURCE.setPassword(PASSWORD);

System.out.println("password: " + PASSWORD);

    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DATA_SOURCE.getConnection();
        } catch (SQLException e) {
            LOGGER.error("get connection failure", e);
        }
        return conn;
    }

}
