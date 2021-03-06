package xyz.imwyy.symphony.util;


import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库操作的工具类
 * create by stephen on 2018/6/5
 */
public class DbUtil {

//
//    private static BasicDataSource DATA_SOURCE;
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();
//
//    static {
//        DATA_SOURCE = new BasicDataSource();
//        DATA_SOURCE.setDriverClassName(ConfigContext.getJdbcDriver());
//        DATA_SOURCE.setUrl(ConfigContext.getJdbcUrl());
//        DATA_SOURCE.setUsername(ConfigContext.getJdbcUsername());
//        DATA_SOURCE.setPassword(ConfigContext.getJdbcPassword());
//    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection() {
        Connection conn = null;
        // TODO: 2018/6/6 处理数据源
//        try {
//            conn = DATA_SOURCE.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        return conn;
    }

    /**
     * 开始事务
     */
    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollBackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }
}
