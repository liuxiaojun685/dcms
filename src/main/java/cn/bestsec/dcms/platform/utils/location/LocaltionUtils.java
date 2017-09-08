/**
 * 
 */
package cn.bestsec.dcms.platform.utils.location;

import java.io.File;

import cn.bestsec.dcms.platform.consts.Protocol;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.utils.FtpUtils;
import cn.bestsec.dcms.platform.utils.SMBUtil;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time 2017年4月22日 下午4:04:50
 */
public class LocaltionUtils {
    public static boolean test(StorageLocation sl, String smbRoot) {
        String prot = sl.getProtocol();
        if (prot.equals(Protocol.local.getName())) {

            return true;

        } else if (prot.equals(Protocol.ftps.getName())) {

            return FtpUtils.test(sl.getIp(), sl.getAccount(), StringEncrypUtil.decrypt(sl.getPasswd()));

        } else if (prot.equals(Protocol.smb.getName())) {

            return SMBUtil.existDir(sl.getIp(), sl.getAccount(), StringEncrypUtil.decrypt(sl.getPasswd()), smbRoot);
        } else if (prot.equals(Protocol.nas.getName())) {

        }

        return false;

    }

    public static boolean existDir(StorageLocation sl, String shareFileDir) {
        String prot = sl.getProtocol();
        if (prot.equals(Protocol.local.getName())) {

            return new File(sl.getFilePath()).exists();

        } else if (prot.equals(Protocol.ftps.getName())) {

            return FtpUtils.existDir(sl.getIp(), sl.getAccount(), StringEncrypUtil.decrypt(sl.getPasswd()),
                    shareFileDir);

        } else if (prot.equals(Protocol.smb.getName())) {

            return SMBUtil.existDir(sl.getIp(), sl.getAccount(), StringEncrypUtil.decrypt(sl.getPasswd()),
                    shareFileDir);
        } else if (prot.equals(Protocol.nas.getName())) {

        }

        return false;

    }

    public static boolean existFile(StorageLocation sl, String shareFileDir, String shareFileName) {
        String prot = sl.getProtocol();
        if (prot.equals(Protocol.local.getName())) {

            return new File(sl.getFilePath()).exists();

        } else if (prot.equals(Protocol.ftps.getName())) {

            return FtpUtils.existFile(sl.getIp(), sl.getAccount(), StringEncrypUtil.decrypt(sl.getPasswd()),
                    shareFileDir, shareFileName);

        } else if (prot.equals(Protocol.smb.getName())) {

            return SMBUtil.existFile(sl.getIp(), sl.getAccount(), StringEncrypUtil.decrypt(sl.getPasswd()),
                    shareFileDir, shareFileName);
        } else if (prot.equals(Protocol.nas.getName())) {

        }

        return false;

    }
}
