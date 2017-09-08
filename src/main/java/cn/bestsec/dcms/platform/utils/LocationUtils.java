/**
 * 
 */
package cn.bestsec.dcms.platform.utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import cn.bestsec.dcms.platform.consts.Protocol;
import cn.bestsec.dcms.platform.entity.StorageLocation;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time 2017年2月27日 下午4:19:59
 */
public class LocationUtils {
    public static boolean canUpload(StorageLocation sl) {
        String prot = sl.getProtocol();
        if (prot.equalsIgnoreCase(Protocol.local.getName())) {
            return true;
        } else if (prot.equalsIgnoreCase(Protocol.ftps.getName())) {
            return true;
        } else if (prot.equalsIgnoreCase(Protocol.smb.getName())) {
            return true;
        } else if (prot.equalsIgnoreCase(Protocol.nas.getName())) {

        }
        return false;
    }

    public static boolean upload(StorageLocation sl, String localFilePath, String newFileName) {
        String prot = sl.getProtocol();
        if (prot.equalsIgnoreCase(Protocol.local.getName())) {
            return localSave(localFilePath, sl.getFilePath(), newFileName);
        } else if (prot.equalsIgnoreCase(Protocol.ftps.getName())) {
            return FtpUtils.upload(sl.getIp(), sl.getAccount(), StringEncrypUtil.decrypt(sl.getPasswd()),
                    sl.getFilePath(), localFilePath, newFileName);
        } else if (prot.equalsIgnoreCase(Protocol.smb.getName())) {
            return SMBUtil.smbPut(sl.getIp(), sl.getAccount(), StringEncrypUtil.decrypt(sl.getPasswd()), localFilePath,
                    sl.getFilePath(), newFileName);
        } else if (prot.equalsIgnoreCase(Protocol.nas.getName())) {

        }
        return false;
    }

    private static boolean localSave(String localFilePath, String saveDir, String saveName) {
        File save = new File(saveDir + File.separator + saveName);
        try {
            if (save.exists()) {
                String orgFile = saveDir + File.separator + "org" + File.separator
                        + TextUtils.getFileDateFormat().format(new Date()) + "_" + save.getName();
                FileUtils.moveFile(save, new File(orgFile));
            }
            FileUtils.copyFile(new File(localFilePath), save);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean canDownload(StorageLocation sl) {
        String prot = sl.getProtocol();
        if (prot.equals(Protocol.local.getName())) {
            return true;
        } else if (prot.equals(Protocol.ftps.getName())) {
            return true;
        } else if (prot.equals(Protocol.smb.getName())) {
            return true;
        } else if (prot.equals(Protocol.nas.getName())) {

        }
        return false;
    }

    public static boolean download(StorageLocation sl, String remoteFileName, String localDir, String newFileName) {
        String prot = sl.getProtocol();
        if (prot.equals(Protocol.local.getName())) {
            try {
                FileUtils.copyFile(new File(sl.getFilePath() + File.separator + remoteFileName),
                        new File(localDir + File.separator + newFileName));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        } else if (prot.equals(Protocol.ftps.getName())) {
            return FtpUtils.download(sl.getIp(), sl.getAccount(), StringEncrypUtil.decrypt(sl.getPasswd()),
                    sl.getFilePath(), remoteFileName, localDir, newFileName);
        } else if (prot.equals(Protocol.smb.getName())) {
            return SMBUtil.smbGet(sl.getIp(), sl.getAccount(), StringEncrypUtil.decrypt(sl.getPasswd()),
                    sl.getFilePath(), remoteFileName, localDir, newFileName);
        } else if (prot.equals(Protocol.nas.getName())) {

        }
        return false;
    }

    public static boolean canDelete(StorageLocation sl) {
        String prot = sl.getProtocol();
        if (prot.equals(Protocol.local.getName())) {
            return true;
        } else if (prot.equals(Protocol.ftps.getName())) {
            return true;
        } else if (prot.equals(Protocol.smb.getName())) {
            return true;
        } else if (prot.equals(Protocol.nas.getName())) {

        }
        return false;
    }

    /**
     * 远程传名称，本地传路径
     * 
     * @param remoteSourceFile
     */
    public static boolean delete(StorageLocation sl, String remoteFileName) {
        String prot = sl.getProtocol();
        if (prot.equals(Protocol.local.getName())) {
            File file = new File(sl.getFilePath() + File.separator + remoteFileName);
            if (file.exists()) {
                return file.delete();
            }
            return true;
        } else if (prot.equals(Protocol.ftps.getName())) {
            return FtpUtils.move(sl.getIp(), sl.getAccount(), StringEncrypUtil.decrypt(sl.getPasswd()),
                    sl.getFilePath(), remoteFileName, null, null);
        } else if (prot.equals(Protocol.smb.getName())) {
            return SMBUtil.smbMove(sl.getIp(), sl.getAccount(), StringEncrypUtil.decrypt(sl.getPasswd()),
                    sl.getFilePath(), remoteFileName, null, null, 3);
        } else if (prot.equals(Protocol.nas.getName())) {

        }
        return false;
    }

    public static boolean canCopy(StorageLocation sl) {
        String prot = sl.getProtocol();
        if (prot.equals(Protocol.local.getName())) {
            return true;
        } else if (prot.equals(Protocol.ftps.getName())) {
            return false;
        } else if (prot.equals(Protocol.smb.getName())) {
            return true;
        } else if (prot.equals(Protocol.nas.getName())) {

        }
        return false;

    }

    /**
     * 远程传名称，本地传路径
     * 
     * @param remoteSourceFile
     * @param remoteTargetFile
     */
    public static boolean copy(StorageLocation sl, String remoteFileName, String remoteTargetFile) {
        String prot = sl.getProtocol();
        if (prot.equals(Protocol.local.getName())) {
            FileUtils.copyFile(sl.getFilePath() + File.separator + remoteFileName, remoteTargetFile);
            return true;
        } else if (prot.equals(Protocol.ftps.getName())) {
            // none
        } else if (prot.equals(Protocol.smb.getName())) {
            return SMBUtil.smbMove(sl.getIp(), sl.getAccount(), StringEncrypUtil.decrypt(sl.getPasswd()),
                    sl.getFilePath(), remoteFileName, sl.getFilePath(), remoteTargetFile, 1);
        } else if (prot.equals(Protocol.nas.getName())) {

        }

        return false;
    }

    public static boolean canMove(StorageLocation sl) {
        String prot = sl.getProtocol();
        if (prot.equals(Protocol.local.getName())) {
            return true;
        } else if (prot.equals(Protocol.ftps.getName())) {
            return true;
        } else if (prot.equals(Protocol.smb.getName())) {
            return true;
        } else if (prot.equals(Protocol.nas.getName())) {

        }
        return false;
    }

    /**
     * 远程传名称，本地传路径
     * 
     * @param remoteSourceFile
     * @param remoteTargetFile
     */
    public static boolean move(StorageLocation sl, String remoteFileName, String remoteTargetFile) {
        String prot = sl.getProtocol();
        if (prot.equals(Protocol.local.getName())) {
            FileUtils.moveFile(sl.getFilePath() + File.separator + remoteFileName, remoteTargetFile);
            return true;
        } else if (prot.equals(Protocol.ftps.getName())) {
            return FtpUtils.move(sl.getIp(), sl.getAccount(), StringEncrypUtil.decrypt(sl.getPasswd()),
                    sl.getFilePath(), remoteFileName, sl.getFilePath(), remoteTargetFile);
        } else if (prot.equals(Protocol.smb.getName())) {
            return SMBUtil.smbMove(sl.getIp(), sl.getAccount(), StringEncrypUtil.decrypt(sl.getPasswd()),
                    sl.getFilePath(), remoteFileName, sl.getFilePath(), remoteTargetFile, 2);
        } else if (prot.equals(Protocol.nas.getName())) {

        }
        return false;
    }

    public static String getTotalSpace(StorageLocation sl) {
        try {
            String prot = sl.getProtocol();
            if (prot.equals(Protocol.local.getName())) {
                return ServerEnv.convertSizeDisp(new File(sl.getFilePath()).getTotalSpace());
            } else if (prot.equals(Protocol.ftps.getName())) {
            } else if (prot.equals(Protocol.smb.getName())) {
            } else if (prot.equals(Protocol.nas.getName())) {
            }
            return "未知";
        } catch (Throwable e) {
        }
        return "存储位置异常";
    }

    public static String getFreeSpace(StorageLocation sl) {
        try {
            String prot = sl.getProtocol();
            if (prot.equals(Protocol.local.getName())) {
                return ServerEnv.convertSizeDisp(new File(sl.getFilePath()).getFreeSpace());
            } else if (prot.equals(Protocol.ftps.getName())) {
            } else if (prot.equals(Protocol.smb.getName())) {
            } else if (prot.equals(Protocol.nas.getName())) {
            }
            return "未知";
        } catch (Throwable e) {
        }
        return "存储位置异常";
    }

    public static String toUrl(StorageLocation sl) {
        String protocol = sl.getProtocol();
        String domain = sl.getDomainName();
        String ip = sl.getIp();
        String port = sl.getPort();
        String path = sl.getFilePath();
        String account = sl.getAccount();
        String passwd = StringEncrypUtil.decrypt(sl.getPasswd());
        if (protocol.equals(Protocol.local.getName())) {
            return sl.getFilePath();
        } else if (protocol.equals(Protocol.ftps.getName())) {
            StringBuilder builder = new StringBuilder("sftp://");
            if (account != null && !account.isEmpty()) {
                builder.append(account + ":" + passwd + "@");
            }
            String host = ip;
            if (domain != null && !domain.isEmpty()) {
                host = domain;
            }
            builder.append(host);
            if (port != null && !port.isEmpty()) {
                builder.append(":" + port);
            }
            builder.append(path);
            return builder.toString();
        } else if (protocol.equals(Protocol.smb.getName())) {
            StringBuilder builder = new StringBuilder("smb://");
            if (account != null && !account.isEmpty()) {
                builder.append(account + ":" + passwd + "@");
            }
            String host = ip;
            if (domain != null && !domain.isEmpty()) {
                host = domain;
            }
            builder.append(host);
            if (port != null && !port.isEmpty()) {
                builder.append(":" + port);
            }
            builder.append(path);
            return builder.toString();
        } else if (protocol.equals(Protocol.nas.getName())) {
            return "";
        }
        return null;
    }

}
