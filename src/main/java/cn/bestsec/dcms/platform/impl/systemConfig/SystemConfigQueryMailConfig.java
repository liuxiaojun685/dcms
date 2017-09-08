package cn.bestsec.dcms.platform.impl.systemConfig;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.bestsec.dcms.platform.api.SystemConfig_QueryMailConfigApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.ResourceThreshold;
import cn.bestsec.dcms.platform.api.model.StorageThreshold;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryMailConfigRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryMailConfigResponse;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 获取邮件告警配置
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月3日 下午3:06:15
 */
@Component
public class SystemConfigQueryMailConfig implements SystemConfig_QueryMailConfigApi {

    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public SystemConfig_QueryMailConfigResponse execute(
            SystemConfig_QueryMailConfigRequest systemConfig_QueryMailConfigRequest) throws ApiException {
        SystemConfig_QueryMailConfigResponse resp = new SystemConfig_QueryMailConfigResponse();
        SystemConfig systemConfig = systemConfigService.getSystemConfig();

        resp.setMailRecvFileStorageAddr(systemConfig.getMailRecvFileStorageAddr());

        // 文件存储容量告警阈值
        try {
            JSONObject jsonFileStorageThreshold = JSONObject
                    .parseObject(systemConfig.getMailRecvFileStorageThreshold());
            StorageThreshold mailRecvFileStorageThreshold = new StorageThreshold();
            List<Long> fileStorageThresholdFree = new ArrayList<Long>();
            if (jsonFileStorageThreshold != null) {
                JSONArray jsonFileStorageThresholdArray = jsonFileStorageThreshold.getJSONArray("free");
                if (jsonFileStorageThresholdArray != null) {
                    fileStorageThresholdFree = JSON.parseArray(jsonFileStorageThresholdArray.toJSONString(),
                            Long.class);
                }
            }
            mailRecvFileStorageThreshold.setFree(fileStorageThresholdFree);
            resp.setMailRecvFileStorageThreshold(mailRecvFileStorageThreshold);
        } catch (Throwable e) {
        }

        resp.setMailRecvLogStorageAddr(systemConfig.getMailRecvLogStorageAddr());
        try {
            // 日志存储容量告警阈值
            JSONObject jsonLogStorageThreshold = JSONObject.parseObject(systemConfig.getMailRecvLogStorageThreshold());
            StorageThreshold mailRecvLogStorageThreshold = new StorageThreshold();
            List<Long> logStorageThresholdFree = new ArrayList<Long>();
            if (jsonLogStorageThreshold != null) {
                JSONArray jsonLogStorageThresholdArray = jsonLogStorageThreshold.getJSONArray("free");
                if (jsonLogStorageThresholdArray != null) {
                    logStorageThresholdFree = JSON.parseArray(jsonLogStorageThresholdArray.toJSONString(), Long.class);
                }
            }
            mailRecvLogStorageThreshold.setFree(logStorageThresholdFree);
            resp.setMailRecvLogStorageThreshold(mailRecvLogStorageThreshold);
        } catch (Throwable e) {
        }

        resp.setMailRecvResourceAccount(systemConfig.getMailRecvResourceAccount());
        try {
            JSONObject jsonResourceThreshold = JSONObject.parseObject(systemConfig.getMailRecvResourceThreshold());
            ResourceThreshold mailRecvResourceThreshold = new ResourceThreshold();
            List<Long> cpuFree = new ArrayList<Long>();
            List<Long> memFree = new ArrayList<Long>();
            List<Long> hdFree = new ArrayList<Long>();
            if (jsonResourceThreshold != null) {
                // cpu空闲百分比 数组
                JSONArray cpuFreeArray = jsonResourceThreshold.getJSONArray("cpuFree");
                if (cpuFreeArray != null) {
                    cpuFree = JSON.parseArray(cpuFreeArray.toJSONString(), Long.class);
                }

                // 内存剩余字节 数组 下降到其中某值时触发一次告警
                JSONArray memFreeArray = jsonResourceThreshold.getJSONArray("memFree");
                if (memFreeArray != null) {
                    memFree = JSON.parseArray(memFreeArray.toJSONString(), Long.class);
                }

                // 硬盘剩余字节 数组 下降到其中某值时触发一次告警
                JSONArray hdFreeArray = jsonResourceThreshold.getJSONArray("hdFree");
                if (hdFreeArray != null) {
                    hdFree = JSON.parseArray(hdFreeArray.toJSONString(), Long.class);
                }

            }
            mailRecvResourceThreshold.setCpuFree(cpuFree);
            mailRecvResourceThreshold.setHdFree(hdFree);
            mailRecvResourceThreshold.setMemFree(memFree);
            resp.setMailRecvResourceThreshold(mailRecvResourceThreshold);
        } catch (Throwable e) {
        }

        resp.setMailSenderAccount(systemConfig.getMailSenderAccount());
        resp.setMailSenderPasswd(StringEncrypUtil.decrypt(systemConfig.getMailSenderPasswd()));
        resp.setMailSenderSmtpAddr(systemConfig.getMailSenderSmtpAddr());
        resp.setMailSenderSmtpPort(systemConfig.getMailSenderSmtpPort());
        resp.setMailSenderSSLEnable(systemConfig.getMailSenderSslenable());
        return resp;
    }

}
