package cn.bestsec.dcms.platform.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月8日 上午10:11:47
 */
public class StringEncrypUtil {
    /**
     * 加密
     * 
     * @param str
     * @return
     */
    public static String encrypt(String str) {
        if (str == null) {
            str = "";
        }
        return Base64.encodeBase64String(str.getBytes());
    }

    /**
     * 解密
     * 
     * @param str
     * @return
     */
    public static String decrypt(String str) {
        if (str == null) {
            str = "";
        }
        return new String(Base64.decodeBase64(str));
    }

    public static String encryptNonreversible(String str) {
        if (str == null) {
            str = "";
        }
        return Md5Utils.md5Hex(str);
    }

}
