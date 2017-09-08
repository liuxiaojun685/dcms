package cn.bestsec.dcms.platform.utils;

import org.apache.commons.codec.binary.Base64;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2017年1月5日 下午4:49:54
 */
public class Base64Utils {

    public static byte[] decode(byte[] binaryData) {
        return Base64.decodeBase64(binaryData);
    }

    public static byte[] encode(byte[] binaryData) {
        return Base64.encodeBase64(binaryData);
    }

    public static byte[] decode(String base64String) {
        return Base64.decodeBase64(base64String);
    }

    public static String encodeString(byte[] binaryData) {
        return Base64.encodeBase64String(binaryData);
    }
}
