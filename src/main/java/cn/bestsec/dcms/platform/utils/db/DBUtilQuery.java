package cn.bestsec.dcms.platform.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class DBUtilQuery {
    public static List<Map<String, Object>> simpleQuery(String sql) {

        Connection conn = null;
        QueryRunner queryRunner = new QueryRunner();
        List<Map<String, Object>> result = null;
        try {
            Class.forName(DBConf.JDBC_DRIVER);
            conn = DriverManager.getConnection(DBConf.DB_URL, DBConf.USER, DBConf.PASS);

            result = queryRunner.query(conn, sql, new MapListHandler());

            conn.close();
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
