package cn.bestsec.dcms.platform.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONObject;

public class LicenseConfiguration {
    private static LicenseConfiguration instance;
    private String publicKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    static synchronized public LicenseConfiguration getInstance() {
        if (instance == null) {
            instance = new LicenseConfiguration();
        }
        return instance;
    }

    protected LicenseConfiguration() {
        InputStream fin = LicenseConfiguration.class.getClassLoader().getResourceAsStream("license.properties");
        Properties properties = new Properties();
        try {
            properties.load(fin);
            this.publicKey = PropertiesHelper.getString("pubKey", properties, "");
        } catch (Throwable e) {
        } finally {
            try {
                fin.close();
            } catch (IOException e) {
            }
        }
    }

    public static class License {
        public String features;
        public String customId;
        public String customName;
        public int versionState;
        public int onlineMax;
        public int offlineMax;
        public int middlewareMax;
        public java.util.Date invalidTime;
    }

    public String decodeLicenseToString(String licenseText) throws Exception {
        byte[] licenseRaws = Base64.decodeBase64(licenseText);
        return new String(RSAUtil.decryptByPublicKey(licenseRaws, publicKey));
    }

    public License decodeLicense(String licenseText) throws Exception {
        String lic = decodeLicenseToString(licenseText);
        return JSONObject.parseObject(lic, License.class);
    }

    public static Integer[] toIntArray(String[] str) {
        Integer[] num = new Integer[str.length];
        for (int i = 0; i < num.length; i++) {
            num[i] = Integer.parseInt(str[i]);
        }
        return num;
    }

    public static void main(String[] args) throws Exception {
        LicenseConfiguration sc = LicenseConfiguration.getInstance();
        System.out.println(sc.getPublicKey());
    }
}