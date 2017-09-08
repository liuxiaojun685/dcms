package cn.bestsec.dcms.platform.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author bada email:bada@bestsec.cn
 * @time：2017年1月23日 下午1:52:54
 */
public class SystemProperties {
    private static SystemProperties instance;

    private String versionBranch;
    private int versionMajor;
    private int versionCode;

    private String cacheDir;
    private String mqttTopic;
    private String mqttBroker;
    private String mqttId;

    private long tokenValidTime;
    private long userOfflineTime;

    private boolean micpEnable;
    private String micpHost;

    private boolean scopeDefaultAccess;
    private long dlpKeywordTimeout;
    private boolean identifyingCodeEnable;

    public SystemProperties() {
        Properties prop = new Properties();
        try {
            prop.load(SystemProperties.class.getClassLoader().getResourceAsStream("system.properties"));
            versionBranch = prop.getProperty("branch", "BASE");
            versionMajor = Integer.valueOf(prop.getProperty("major", "1"));
            versionCode = Integer.valueOf(prop.getProperty("code", "0"));
            cacheDir = prop.getProperty("cacheDir");
            FileUtils.newFolder(cacheDir);
            mqttTopic = prop.getProperty("mqttTopic");
            mqttBroker = prop.getProperty("mqttBroker");
            mqttId = prop.getProperty("mqttId");
            tokenValidTime = Long.valueOf(prop.getProperty("token_valid_time", "3600")) * 1000;
            userOfflineTime = Long.valueOf(prop.getProperty("user_offline_time", "60")) * 1000;
            micpEnable = prop.getProperty("micp_enable", "false").equals("true");
            micpHost = prop.getProperty("micp_host", "http://localhost:8080");
            scopeDefaultAccess = prop.getProperty("scope_default_access", "true").equals("true");
            dlpKeywordTimeout = Long.valueOf(prop.getProperty("dlp_keyword_timeout", "60000"));
            identifyingCodeEnable = prop.getProperty("identifying_code_enable", "true").equals("true");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SystemProperties getInstance() {
        if (instance == null) {
            instance = new SystemProperties();
        }
        return instance;
    }

    public String getVersionBranch() {
        return versionBranch;
    }

    public int getVersionMajor() {
        return versionMajor;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getCacheDir() {
        return cacheDir;
    }

    public String getMqttTopic() {
        return mqttTopic;
    }

    public String getMqttBroker() {
        return mqttBroker;
    }

    public String getMqttId() {
        return mqttId;
    }

    public long getTokenValidTime() {
        return tokenValidTime;
    }

    public long getUserOfflineTime() {
        return userOfflineTime;
    }

    public boolean isMicpEnable() {
        return micpEnable;
    }

    public String getMicpHost() {
        return micpHost;
    }

    public boolean isScopeDefaultAccess() {
        return scopeDefaultAccess;
    }

    public Long getDlpKeywordTimeout() {
        return dlpKeywordTimeout;
    }

    public boolean isIdentifyingCodeEnable() {
        return identifyingCodeEnable;
    }

}
