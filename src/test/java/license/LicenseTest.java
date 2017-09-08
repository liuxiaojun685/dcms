package license;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;

import cn.bestsec.dcms.platform.utils.LicenseConfiguration;
import cn.bestsec.dcms.platform.utils.RSAUtil;
import cn.bestsec.dcms.platform.utils.ServerEnv;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月17日 上午9:22:16
 */
public class LicenseTest {
    private static final String LICENSE_FEATURES = "1317925098";
    private static final String LICENSE_CUSTOMID = "10001"; // 客户编号
    private static final String LICENSE_CUSTOMNAME = "北京航天科技集团公司"; // 客户名称
    private static final int LICENSE_VERSIONSTATE = 2; // 授权状态 1正式版 2试用版
    private static final int LICENSE_ONLINEMAX = 100; // 网络版最大个数
    private static final int LICENSE_OFFLINEMAX = 50; // 单机版最大个数
    private static final int LICENSE_MIDDLEWAREMAX = 3; // 中间件最大连接个数
    private static final String LICENSE_INVALIDTIME = "2017-12-12"; // 授权到期日期

    private static String licenseKeyFile = "license_key.txt";
    private static String licenseFile = "license.txt";

    private static Properties prop = new Properties();

    static {
        try {
            File file = new File(licenseKeyFile);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            prop.load(fis);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean saveKey(Map<String, Object> keyMap) {
        try {
            String pubkey = RSAUtil.getPublicKey(keyMap);
            String prikey = RSAUtil.getPrivateKey(keyMap);
            if (pubkey != null && !pubkey.isEmpty() && prikey != null && !prikey.isEmpty()) {
                return false;
            }
            prop.setProperty("pubKey", pubkey);
            prop.setProperty("priKey", prikey);
            FileOutputStream fos = new FileOutputStream(new File(licenseKeyFile));
            prop.store(fos, "LICENSE KEY PAIR");
            fos.close();
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String loadPublicKey() {
        return prop.getProperty("pubKey");
    }

    public static String loadPrivateKey() {
        return prop.getProperty("priKey");
    }

    // 创建新的秘钥文件propertiesPath，如果有旧的存在则不创建。
    public static boolean createNewKeys() {
        try {
            Map<String, Object> keyMap = RSAUtil.genKeyPair();
            return saveKey(keyMap);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    // 制作LicenseFile
    static String makeLicense(String priKey) {
        try {
            File file = new File(licenseFile);
            if (!file.exists()) {
                file.createNewFile();
            }
            Properties prop = new Properties();
            prop.load(new FileInputStream(file));
            LicenseConfiguration.License lic = new LicenseConfiguration.License();
            lic.features = LICENSE_FEATURES;
            lic.customId = LICENSE_CUSTOMID;
            lic.customName = LICENSE_CUSTOMNAME;
            lic.versionState = LICENSE_VERSIONSTATE;
            lic.onlineMax = LICENSE_ONLINEMAX;
            lic.offlineMax = LICENSE_OFFLINEMAX;
            lic.middlewareMax = LICENSE_MIDDLEWAREMAX;
            lic.invalidTime = new SimpleDateFormat("yyyy-MM-dd").parse(LICENSE_INVALIDTIME);

            String data = JSON.toJSONString(lic);
            System.out.println(data);
            byte[] licenseRaws = RSAUtil.encryptByPrivateKey(data.getBytes(), priKey);
            String licenseText = Base64.encodeBase64String(licenseRaws);
            System.out.println("License：\r\n" + licenseText);
            prop.setProperty("license", licenseText);
            prop.store(new FileOutputStream(file), "LICENSE");
            return licenseText;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(ServerEnv.getHardwareFeature());
        // testSign();
        // createNewKeys();
        String licenseText = makeLicense(loadPrivateKey());

        String json = LicenseConfiguration.getInstance().decodeLicenseToString(licenseText);
        System.out.println(json);
    }

    // 公钥加密——私钥解密
    static void test() throws Exception {
        Map<String, Object> keyMap = RSAUtil.genKeyPair();
        String publicKey = RSAUtil.getPublicKey(keyMap);
        String privateKey = RSAUtil.getPrivateKey(keyMap);
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);

        System.err.println("公钥加密——私钥解密");
        String source = "这是一行没有任何意义的文字，你看完了等于没看，不是吗？";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtil.encryptByPublicKey(data, publicKey);
        System.out.println("加密后文字：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtil.decryptByPrivateKey(encodedData, privateKey);
        String target = new String(decodedData);
        System.out.println("解密后文字: \r\n" + target);
    }

    // 私钥加密——公钥解密
    static void testSign() throws Exception {
        Map<String, Object> keyMap = RSAUtil.genKeyPair();
        String publicKey = RSAUtil.getPublicKey(keyMap);
        String privateKey = RSAUtil.getPrivateKey(keyMap);
        System.err.println("公钥: \n\r" + publicKey);
        System.err.println("私钥： \n\r" + privateKey);

        System.err.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes();
        byte[] encodedData = RSAUtil.encryptByPrivateKey(data, privateKey);
        System.out.println("加密后：\r\n" + new String(encodedData));
        byte[] decodedData = RSAUtil.decryptByPublicKey(encodedData, publicKey);
        String target = new String(decodedData);
        System.out.println("解密后: \r\n" + target);
        System.err.println("私钥签名——公钥验证签名");
        String sign = RSAUtil.sign(encodedData, privateKey);
        System.err.println("签名:\r" + sign);
        boolean status = RSAUtil.verify(encodedData, publicKey, sign);
        System.err.println("验证结果:\r" + status);
    }

}
