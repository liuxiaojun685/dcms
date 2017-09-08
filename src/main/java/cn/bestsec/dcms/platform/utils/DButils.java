package cn.bestsec.dcms.platform.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cn.bestsec.dcms.platform.utils.db.DBConf;

/**
 * 数据库连接信息
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月13日 下午1:59:57
 */
public class DButils {
    private static DButils instance;

    static {
        try {
            Class.forName(DBConf.JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DButils getInstance() {
        if (instance == null) {
            instance = new DButils();
        }
        return instance;
    }

    public static Connection getConnection() {
        getInstance();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DBConf.DB_URL, DBConf.USER, DBConf.PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;

    }

}
