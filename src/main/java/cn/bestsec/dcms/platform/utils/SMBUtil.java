package cn.bestsec.dcms.platform.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

/**
 * @author 共享文件访问工具类
 */
public class SMBUtil {
    static Logger logger = LoggerFactory.getLogger(SMBUtil.class);

    /**
     * smbGet("192.168.0.29", "secserver", "123", "/share/test.txt",
     * "/home/besthyhy/x", null);
     *
     * @param host
     * @param username
     * @param passwd
     * @param shareFilePath
     * @param localDir
     * @param newFileName
     *            如果使用服务器上的名称，使用null。
     */
    public static boolean smbGet(String host, String username, String passwd, String shareFileDir, String shareFileName,
            String localDir, String newFileName) {
        // mbGet("192.168.0.29", "secserver", "123","share/test.txt",
        // "/home/besthyhy/x", null);

        InputStream in = null;
        SmbFile remoteFile = null;
        NtlmPasswordAuthentication auth = null;
        try {
            auth = new NtlmPasswordAuthentication(host, username, passwd); // 先登录验证
            String smbUrl = "smb://" + host + "/" + shareFileDir + "/" + shareFileName;
            remoteFile = new SmbFile(smbUrl.replace("\\", "/"), auth);
            if (!remoteFile.exists()) {
                return false;
            }
            new File(localDir).mkdirs();

            if (newFileName == null) {
                newFileName = remoteFile.getName();
            }

            File localFile = new File(localDir + File.separator + newFileName);
            in = new SmbFileInputStream(remoteFile);
            FileUtils.copyInputStreamToFile(in, localFile);
            in.close();
            return true;
        } catch (Throwable e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public static void main(String[] args) {
        // NtlmPasswordAuthentication auth = new
        // NtlmPasswordAuthentication("192.168.0.18", "Hardsun", "8632"); //
        // 先登录验证
        // SmbFile remoteFile;
        // try {
        // remoteFile = new
        // SmbFile("smb://Guest:123456@192.168.0.101/share/1.txt");
        // System.out.println(remoteFile.exists());
        // } catch (MalformedURLException e) {
        // e.printStackTrace();
        // } catch (SmbException e) {
        // e.printStackTrace();
        // }
        smbGet("192.168.0.101", "Guest", "123456", "share", "2.txt", "smb", "2.txt");
    }

    public static boolean existDir(String host, String username, String passwd, String shareFileDir) {

        SmbFile remoteFile = null;
        NtlmPasswordAuthentication auth = null;

        auth = new NtlmPasswordAuthentication(host, username, passwd); // 先登录验证

        try {
            String smbUrl = "smb://" + host + "/" + shareFileDir;
            remoteFile = new SmbFile(smbUrl.replace("\\", "/"), auth);
            if (remoteFile.exists()) {
                return true;
            }
        } catch (MalformedURLException | SmbException e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean existFile(String host, String username, String passwd, String shareFileDir,
            String shareFileName) {

        SmbFile remoteFile = null;
        NtlmPasswordAuthentication auth = null;

        auth = new NtlmPasswordAuthentication(host, username, passwd); // 先登录验证

        try {
            String smbUrl = "smb://" + host + "/" + shareFileDir + "/" + shareFileName;
            remoteFile = new SmbFile(smbUrl.replace("\\", "/"), auth);
            if (remoteFile.exists()) {
                return true;
            }
        } catch (MalformedURLException | SmbException e) {
            e.printStackTrace();
        }

        return false;

    }

    public static boolean smbPut(String host, String username, String passwd, String localFilePath, String shareDir,
            String newFileName) {
        // smbPut("192.168.0.29", "secserver", "123",
        // "/home/besthyhy/x/hello.txt","share", null);

        // smbPut("192.168.0.29", "secserver", "123",
        // "C:/Users/besthyhy/works/jboss/xnjc-codestyle-eclipse-formatter.xml",
        // "share", null);

        OutputStream out = null;

        NtlmPasswordAuthentication auth = null;
        auth = new NtlmPasswordAuthentication(host, username, passwd);

        try {
            File localFile = new File(localFilePath);
            String fileName = localFile.getName();
            if (newFileName == null || newFileName.isEmpty()) {
                newFileName = fileName;
            }
            String smbUrl = "smb://" + host + "/" + shareDir + "/" + newFileName;
            SmbFile remoteFile = new SmbFile(smbUrl.replace("\\", "/"), auth);

            if (remoteFile.exists()) {
                String newDir = shareDir + "/org/" + remoteFile.getName();
                mkdir(host, username, passwd, newDir);

                String[] names = TextUtils.splitFileNameByDot(newFileName);
                if (names.length <= 1) {
                    newFileName = names[0] + "_" + TextUtils.getFileDateFormat().format(new Date());
                } else {
                    newFileName = names[0] + "_" + TextUtils.getFileDateFormat().format(new Date()) + "." + names[1];
                }

                String smbUrl2 = "smb://" + host + "/" + newDir + "/" + newFileName;
                SmbFile remoteFile2 = new SmbFile(smbUrl2.replace("\\", "/"), auth);

                remoteFile.copyTo(remoteFile2);
                remoteFile.delete();
            }

            out = new SmbFileOutputStream(remoteFile);
            FileUtils.copyFile(localFile, out);
            out.close();
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * code 1:复制 2:移动 3：删除
     */
    public static boolean smbMove(String host, String username, String passwd, String remoteSourceDir,
            String remoteSourceFile, String remoteTargetDir, String newFileName, int code) {

        // smbMove("192.168.0.29", "secserver", "123", "share",
        // "hello.txt", "share", "hello2.txt", 3);

        NtlmPasswordAuthentication auth = null;
        auth = new NtlmPasswordAuthentication(host, username, passwd);

        try {
            String smbUrl = "smb://" + host + "/" + remoteSourceDir + "/" + remoteSourceFile;
            SmbFile remoteFile1 = new SmbFile(smbUrl.replace("\\", "/"), auth);

            if (newFileName == null) {
                newFileName = remoteFile1.getName();
            }

            SmbFile remoteFile2 = null;
            if (remoteTargetDir != null) {
                String smbUrl2 = "smb://" + host + "/" + remoteSourceDir + "/" + newFileName;
                remoteFile2 = new SmbFile(smbUrl2.replace("\\", "/"), auth);
            }

            switch (code) {
            case 1:
                remoteFile1.copyTo(remoteFile2);
                return true;

            case 2:
                remoteFile1.copyTo(remoteFile2);

                remoteFile1.delete();
                return true;
            case 3:
                remoteFile1.delete();
                return true;

            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void mkdir(String host, String username, String passwd, String newDirPath) {
        NtlmPasswordAuthentication auth = null;
        auth = new NtlmPasswordAuthentication(host, username, passwd);

        try {
            String smbUrl = "smb://" + host + "/" + newDirPath;
            SmbFile remoteFile1 = new SmbFile(smbUrl.replace("\\", "/"), auth);

            if (!remoteFile1.exists()) {

                remoteFile1.mkdirs();

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (SmbException e) {
            e.printStackTrace();
        }

    }

}
