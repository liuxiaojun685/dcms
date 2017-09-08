package cn.bestsec.dcms.platform.utils.classification;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年4月5日 下午2:43:23
 */
public interface IClassifitionJni2 extends Library {
    IClassifitionJni2 instance = (IClassifitionJni2) Native.loadLibrary("CommSecDoc", IClassifitionJni2.class);
    
    public static String fileEncryptKey = "1C1DE4C0F7245C9152BA495CD8797058";

    /**
     * 查看是否是密标文件。
     * 
     * @param filePath
     *            文件路径，java用默认编码即可，jna会自动转码为$(jna.encoding)编码。
     * @param isWString
     *            是否是LPCWSTR，这里传入0即可。
     * @param ret
     *            是否是密标文件 1是 0否
     * @return
     */
    public boolean XnfsCtrl_IsEncryptFile(String filePath, int isWString, IntByReference ret);

    /**
     * 文件去除密标/密文还原/解绑
     * 
     * @param filePath
     *            文件路径，java用默认编码即可，jna会自动转码为$(jna.encoding)编码。
     * @param isReplace
     *            是否覆盖原文件，1是 0否，如果是targetFilePath需要和filePath一样。
     * @param targetFilePath
     *            目标文件路径，java用默认编码即可，jna会自动转码为$(jna.encoding)编码。
     * @param passwdKey
     *            密钥，目前是固定值1C1DE4C0F7245C9152BA495CD8797058
     * @param keyLen
     *            密钥长度 固定20
     * @return
     */
    public boolean XnfsCtrl_DecryptFile(String filePath, int isReplace, String targetFilePath, String passwdKey,
            int keyLen);

    /**
     * 明文文件加密标
     * 
     * @param filePath
     *            文件路径，java用默认编码即可，jna会自动转码为$(jna.encoding)编码。
     * @param passwdKey
     *            密钥，目前是固定值1C1DE4C0F7245C9152BA495CD8797058
     * @param keyLen
     *            密钥长度 固定20
     * @return
     */
    public boolean XnfsCtrl_EncryptFile(String filePath, String passwdKey, int keyLen);
}
