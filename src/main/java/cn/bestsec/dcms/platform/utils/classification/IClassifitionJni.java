package cn.bestsec.dcms.platform.utils.classification;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年4月5日 下午2:43:23
 */
public interface IClassifitionJni extends Library {
    IClassifitionJni instance = (IClassifitionJni) Native.loadLibrary("CommSecDocEx", IClassifitionJni.class);

    /**
     * 读取文件头，目前文件头内存储的是utf-8编码的数据，所以读出来的可以不用转码。
     * 
     * @param filePath
     *            文件路径，java用默认编码即可，jna会自动转码为$(jna.encoding)编码。
     * @param ptr
     *            文件头指针，指向json string，编码为$(jna.encoding)，需要java手动转码成utf-8
     * @param len
     *            文件头长度，4096即可。
     * @return
     */
    public boolean KfGetJosnFromEncFile(String filePath, Pointer ptr, int len);

    /**
     * 写文件头，必须和js的写文件投保持编码一致，目前均使用utf-8。这样会导致文件路径也是utf-8编码，所以务必保证缓存路径与文件名均不含中文。日后在考虑修复此问题
     * 
     * @param filePath
     *            文件路径，java用默认编码即可，jna会自动转码为$(jna.encoding)编码。
     * @param buffer
     *            文件头json string，java用默认编码即可，jna会自动转码为$(jna.encoding)编码。
     * @param ret
     * @return
     */
    public boolean KfWriteClassificationEntityToFileHead(String filePath, String buffer, IntByReference ret);
}
