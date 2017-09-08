package log;

import java.net.NetworkInterface;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2017年1月9日 上午10:46:57
 */
public class LogBackTest {
    static Logger logger = LoggerFactory.getLogger(LogBackTest.class);

    public static void main(String[] args) {
        logger.debug("doing my job");
        logger.info("doing my job");
        
        System.out.println(StringEncrypUtil.encryptNonreversible("dcms@123"));
        try {
            JSONObject obj = JSONObject.parseObject("xx");
            System.out.println(obj);
        }catch (Exception e) {
        }
    }

    private static String getLocalMac(NetworkInterface ni) throws SocketException {
        // TODO Auto-generated method stub
        // 获取网卡，获取地址
        byte[] mac = ni.getHardwareAddress();
        if (mac == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            // 字节转换为整数
            int temp = mac[i] & 0xff;
            String str = Integer.toHexString(temp);
            if (str.length() == 1) {
                sb.append("0" + str);
            } else {
                sb.append(str);
            }
        }
        return sb.toString().toUpperCase();
    }
}
