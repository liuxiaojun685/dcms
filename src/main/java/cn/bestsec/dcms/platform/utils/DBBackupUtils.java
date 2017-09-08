package cn.bestsec.dcms.platform.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bestsec.dcms.platform.utils.db.DBConf;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2017年1月5日 下午6:00:00
 */
public class DBBackupUtils {
    private static final Logger logger = LoggerFactory.getLogger(DBBackupUtils.class);

    private static boolean backup(File backupFile, String datebaseName, String username, String passwd, String address,
            String port) {
        Runtime runtime = Runtime.getRuntime();
        PrintStream ps;
        try {
            Process process = runtime.exec("mysqldump --databases " + datebaseName + " --add-drop-database -u"
                    + username + " -p" + passwd + " -h " + address + " -P " + port);
            ps = new PrintStream(backupFile);
            InputStream in = process.getInputStream();
            int ch;
            while ((ch = in.read()) != -1) {
                ps.write(ch);
            }
            InputStream err = process.getErrorStream();
            while ((ch = err.read()) != -1) {
                // System.out.write(ch);
            }
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean recover(File backupFile, String datebaseName, String username, String passwd, String address,
            String port) {
        Runtime runtime = Runtime.getRuntime();
        Process process;
        try {
            // TODO 需要改为远程服务器
            process = runtime
                    .exec("mysql -u" + username + " -p" + passwd + " --default-character-set=utf8 " + datebaseName);
            OutputStream outputStream = process.getOutputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(backupFile)));
            String str = null;
            StringBuffer sb = new StringBuffer();
            while ((str = br.readLine()) != null) {
                sb.append(str + "\r\n");
            }
            str = sb.toString();
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8");
            writer.write(str);
            writer.flush();
            outputStream.close();
            br.close();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean backup(File backupFile) {
        String datebaseName;
        String username;
        String passwd;
        String address;
        String port = "3306";
        try {

            username = DBConf.USER;
            passwd = DBConf.PASS;
            String[] arr = DBConf.DB_URL.split("/");
            if (!"mysql".equals(arr[0].split(":")[1])) {
                logger.warn(arr[1] + " 暂不支持备份功能");
                return false;
            }
            String[] addrport = arr[2].split(":");
            address = addrport[0];
            if (addrport.length > 1) {
                port = addrport[1];
            }
            datebaseName = arr[3].split("[?]")[0];

            return backup(backupFile, datebaseName, username, passwd, address, port);
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.warn("jdbc.properties配置有误");
            return false;
        }
    }

    public static boolean recover(File backupFile) {
        String datebaseName;
        String username;
        String passwd;
        String address;
        String port = "3306";
        try {
            username = DBConf.USER;
            passwd = DBConf.PASS;
            String[] arr = DBConf.DB_URL.split("/");
            if (!"mysql".equals(arr[0].split(":")[1])) {
                logger.warn(arr[1] + " 暂不支持还原功能");
                return false;
            }
            String[] addrport = arr[2].split(":");
            address = addrport[0];
            if (addrport.length > 1) {
                port = addrport[1];
            }
            datebaseName = arr[3].split("[?]")[0];

            return recover(backupFile, datebaseName, username, passwd, address, port);
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.warn("jdbc.properties配置有误");
            return false;
        }
    }

    public static String dbInfo() {
        String dB_URL = DBConf.DB_URL;
        String[] arr = dB_URL.split(":");
        return arr[1];
    }

}
