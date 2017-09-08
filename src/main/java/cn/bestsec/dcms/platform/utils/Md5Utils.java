package cn.bestsec.dcms.platform.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

public class Md5Utils {
    /**
     * Compute the MD5 hash code of a string.
     * 
     * @param data
     *            input string.
     * @return
     */
    public static String md5Hex(String data) {
        return DigestUtils.md5Hex(data);
    }

    public static String getMd5ByFile(File file) {
        String value = "err";
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            value = DigestUtils.md5Hex(IOUtils.toByteArray(in));
            IOUtils.closeQuietly(in);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                IOUtils.closeQuietly(in);
            }
        }
        return value;
    }
    


    public static String getMd5ByStreamAnClose(InputStream in) {
        String value = "err";
        try {
            value = DigestUtils.md5Hex(IOUtils.toByteArray(in));
            IOUtils.closeQuietly(in);
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                IOUtils.closeQuietly(in);
            }
        }
        return value;
    }
}
