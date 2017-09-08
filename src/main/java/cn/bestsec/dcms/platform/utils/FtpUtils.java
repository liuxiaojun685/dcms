package cn.bestsec.dcms.platform.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;

public class FtpUtils {
    // private static final Logger log =
    // LoggerFactory.getLogger(AdminLogin.class);

    /**
     * 删除时remoteTargetDir留空 注意ftp没有复制
     */
    public static boolean move(String host, String username, String passwd, String remoteSourceDir,
            String remoteSourceFile, String remoteTargetDir, String newFileName) {
        // move("192.168.0.29", "admin", "123456", null, "ftp-path.txt", null,
        // null);

        FTPClient ftp = new FTPClient();
        FTPClientConfig config = new FTPClientConfig();

        ftp.configure(config);

        try {

            ftp.connect(host);
            // log.info("Connected to " + host + ".");
            // log.info("ftp.getReplyString()");
            boolean login = ftp.login(username, passwd);
            // log.info("登录：" + login);

            ftp.enterLocalPassiveMode();
            // log.info("Remote system is " + ftp.getSystemType());

            ftp.changeWorkingDirectory(remoteSourceDir);
            // log.info("Current directory is " + ftp.printWorkingDirectory());

            if (remoteTargetDir == null) {
                boolean deleteFile = ftp.deleteFile(remoteSourceFile);

                ftp.logout();
                // System.out.println("delete: " + deleteFile);
                return deleteFile;
            } else {
                if (newFileName == null) {
                    newFileName = remoteSourceFile;
                }

                boolean rename = ftp.rename(remoteSourceDir + "/" + remoteSourceFile,
                        remoteTargetDir + "/" + newFileName);

                // System.out.println("rename: " + rename);
                ftp.logout();

                return rename;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    // do nothing
                }
            }
        }

        return false;

    }

    public static void main(String[] args) {
        // move("192.168.0.29", "admin", "123456", "/", "2", "/test", "1");
        upload("192.168.0.29", "admin", "123456", "/",
                "C:/Users/besthyhy/works/jboss/xnjc-codestyle-eclipse-formatter.xml", null);
    }

    public static boolean test(String server, String user, String passwd) {
        // upload("192.168.0.29", "admin", "123456", "test",
        // "/home/besthyhy/x/ftp_test.txt", null);
        FTPClient ftp = new FTPClient();
        FTPClientConfig config = new FTPClientConfig();

        ftp.configure(config);

        try {
            ftp.connect(server);

            boolean login = ftp.login(user, passwd);

            if (login) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean existDir(String host, String username, String passwd, String shareFileDir) {
        // download("192.168.0.29", "admin", "123456", "test",
        // "20170301165549_test", "/home/besthyhy/z", null);

        FTPClient ftp = new FTPClient();
        FTPClientConfig config = new FTPClientConfig();

        ftp.configure(config);

        try {

            ftp.connect(host);
            // log.info("Connected to " + host + ".");
            // log.info("ftp.getReplyString()");
            ftp.login(username, passwd);
            // log.info("登录：" + login);

            ftp.enterLocalPassiveMode();
            // log.info("Remote system is " + ftp.getSystemType());

            ftp.changeWorkingDirectory(shareFileDir);
            // log.info("Current directory is " + ftp.printWorkingDirectory());
            int returnCode = ftp.getReplyCode();
            if (returnCode == 550) {
                return false;
            }
            return true;

        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    // do nothing
                }
            }
        }

        return true;
    }

    public static boolean existFile(String host, String username, String passwd, String shareFileDir,
            String shareFileName) {
        // download("192.168.0.29", "admin", "123456", "test",
        // "20170301165549_test", "/home/besthyhy/z", null);

        FTPClient ftp = new FTPClient();
        FTPClientConfig config = new FTPClientConfig();

        ftp.configure(config);

        try {

            ftp.connect(host);
            // log.info("Connected to " + host + ".");
            // log.info("ftp.getReplyString()");
            ftp.login(username, passwd);
            // log.info("登录：" + login);

            ftp.enterLocalPassiveMode();
            // log.info("Remote system is " + ftp.getSystemType());

            ftp.changeWorkingDirectory(shareFileDir);
            // log.info("Current directory is " + ftp.printWorkingDirectory());

            InputStream inputStream = ftp.retrieveFileStream(shareFileName);
            int returnCode = ftp.getReplyCode();
            ftp.logout();

            if (inputStream == null || returnCode == 550) {
                return false;
            }

            // log.info("下载结果：" + retrieveFile);

            return true;

        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    // do nothing
                }
            }
        }

        return true;

    }

    public static boolean upload(String server, String user, String passwd, String remoteDir, String localFilePath,
            String newFileName) {
        // upload("192.168.0.29", "admin", "123456", "test",
        // "/home/besthyhy/x/ftp_test.txt", null);
        FTPClient ftp = new FTPClient();
        FTPClientConfig config = new FTPClientConfig();

        ftp.configure(config);

        try {

            ftp.connect(server);
            // log.info("Connected to " + server + ".");
            // log.info("ftp.getReplyString()");
            boolean login = ftp.login(user, passwd);
            // log.info("登录：" + login);

            ftp.enterLocalPassiveMode();
            // log.info("Remote system is " + ftp.getSystemType());

            ftp.changeWorkingDirectory(remoteDir);
            // log.info("Current directory is " + ftp.printWorkingDirectory());

            if ("/".equals(remoteDir)) {
                remoteDir = "";
            }
            File file = new File(localFilePath);
            if (newFileName == null || newFileName.isEmpty()) {
                newFileName = file.getName();
            }

            InputStream inputStream = ftp.retrieveFileStream(remoteDir + "/" + file.getName());
            int returnCode = ftp.getReplyCode();
            if (inputStream == null || returnCode == 550) {
                // System.out.println("不存在");
            } else {
                // System.out.println("存在");
                ftp.completePendingCommand();
                String newDir = remoteDir + "/org/" + file.getName();

                ftp.makeDirectory(newDir);

                // System.out.println(remoteDir + "/" + file.getName());
                // System.out.println(newDir + "/" + file.getName());
                // ftp.rename(remoteDir + "/" + file.getName(), newDir + "/" +
                // file.getName());

                String[] names = TextUtils.splitFileNameByDot(newFileName);
                String targetName4Move = null;
                if (names.length <= 1) {
                    targetName4Move = names[0] + "_" + TextUtils.getFileDateFormat().format(new Date());
                } else {
                    targetName4Move = names[0] + "_" + TextUtils.getFileDateFormat().format(new Date()) + "."
                            + names[1];
                }

                boolean rename = ftp.rename(remoteDir + "/" + file.getName(), newDir + "/" + targetName4Move);
                // System.out.println(rename + "");
            }

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(localFilePath));
            boolean storeFile = ftp.storeFile(newFileName, bis);
            bis.close();
            ftp.logout();
            // log.info("上传结果：" + storeFile);

            return storeFile;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    // do nothing
                }
            }
        }

        return false;

    }

    public static boolean download(String host, String username, String passwd, String shareFileDir,
            String shareFileName, String localDir, String newFileName) {
        // download("192.168.0.29", "admin", "123456", "test",
        // "20170301165549_test", "/home/besthyhy/z", null);

        FTPClient ftp = new FTPClient();
        FTPClientConfig config = new FTPClientConfig();

        ftp.configure(config);

        new File(localDir).mkdirs();

        try {

            ftp.connect(host);
            // log.info("Connected to " + host + ".");
            // log.info("ftp.getReplyString()");
            boolean login = ftp.login(username, passwd);
            // log.info("登录：" + login);

            ftp.enterLocalPassiveMode();
            // log.info("Remote system is " + ftp.getSystemType());

            ftp.changeWorkingDirectory(shareFileDir);
            // log.info("Current directory is " + ftp.printWorkingDirectory());

            if (newFileName == null) {
                newFileName = shareFileName;
            }
            OutputStream output;
            output = new FileOutputStream(localDir + "/" + newFileName);
            // get the file from the remote system
            boolean retrieveFile = ftp.retrieveFile(shareFileName, output);
            // close output stream
            output.close();

            ftp.logout();
            // log.info("下载结果：" + retrieveFile);

            return retrieveFile;

        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    // do nothing
                }
            }
        }

        return false;

    }

}
