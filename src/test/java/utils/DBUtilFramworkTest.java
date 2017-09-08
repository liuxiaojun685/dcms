package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import cn.bestsec.dcms.platform.utils.db.DBConf;

public class DBUtilFramworkTest {

    public static void main(String args[]) {
        Connection conn;
        QueryRunner queryRunner = new QueryRunner();
        try {
            Class.forName(DBConf.JDBC_DRIVER);
            conn =  DriverManager.getConnection(DBConf.DB_URL, DBConf.USER, DBConf.PASS);

            String sql = "SELECT * FROM SystemConfig;";
          
            List<Map<String, Object>> result = queryRunner.query(conn, sql, new MapListHandler());
            System.out.println(result.toString());

        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        
      

    }
}
