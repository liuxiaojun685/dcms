package cn.bestsec.dcms.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.consts.CommonConsts;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.MailService;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.EmailUtils;
import cn.bestsec.dcms.platform.utils.ServerEnv;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;
import cn.bestsec.dcms.platform.utils.StringHelper;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月22日 下午5:16:11
 */
@Service
public class MailServiceImpl implements MailService {

    private static String alarmEmailModel = "邮件为自动发送邮件\n\n" + "DCMS系统资源告警！！！！\n\n";

    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public void checkResource() throws ApiException {
        SystemConfig systemConfig = systemConfigService.getSystemConfig();

        // 查系统资源
        double curentCpuRatio = ServerEnv.getCpuRatioValue(); // CPU使用率0-100
        double curentMemFree = ServerEnv.getMemRatioValue(); // 内存空闲率0-100
        double curentHdFree = ServerEnv.getHdRatioValue(); // 硬盘空闲率0-100

        try {
            boolean needAlarm = false;

            JSONObject jsonResourceThreshold = JSONObject.parseObject(systemConfig.getMailRecvResourceThreshold());
            if (jsonResourceThreshold != null) {
                // cpu空闲百分比 数组
                JSONArray cpuFreeArray = jsonResourceThreshold.getJSONArray("cpuFree");
                List<Long> cpuFree = new ArrayList<Long>();
                if (cpuFreeArray != null) {
                    cpuFree = JSON.parseArray(cpuFreeArray.toJSONString(), Long.class);
                    if (cpuFree != null && !cpuFree.isEmpty() && curentCpuRatio >= cpuFree.get(0)) {
                        needAlarm = true;
                    }
                }

                // 内存剩余字节 数组 下降到其中某值时触发一次告警
                JSONArray memFreeArray = jsonResourceThreshold.getJSONArray("memFree");
                List<Long> memFree = new ArrayList<Long>();
                if (memFreeArray != null) {
                    memFree = JSON.parseArray(memFreeArray.toJSONString(), Long.class);
                    if (memFree != null && !memFree.isEmpty() && curentMemFree <= memFree.get(0)) {
                        needAlarm = true;
                    }
                }

                // 硬盘剩余字节 数组 下降到其中某值时触发一次告警
                JSONArray hdFreeArray = jsonResourceThreshold.getJSONArray("hdFree");
                List<Long> hdFree = new ArrayList<Long>();
                if (hdFreeArray != null) {
                    hdFree = JSON.parseArray(hdFreeArray.toJSONString(), Long.class);
                    if (hdFree != null && !hdFree.isEmpty() && curentHdFree <= hdFree.get(0)) {
                        needAlarm = true;
                    }
                }

                if (needAlarm) {
                    StringBuilder builder = new StringBuilder(alarmEmailModel);
                    builder.append("\n  --CPU使用率:" + curentCpuRatio + "%");
                    builder.append("\n  --内存空闲率:" + curentMemFree + "%");
                    builder.append("\n  --硬盘空闲率:" + curentHdFree + "%");
                    sendAlarmEmail(builder.toString());
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void sendAlarmEmail(String content) throws ApiException {
        SystemConfig systemConfig = systemConfigService.getSystemConfig();

        String smtpAddr = systemConfig.getMailSenderSmtpAddr();
        String smtpPort = systemConfig.getMailSenderSmtpPort();
        boolean sslEnable = false;
        if (systemConfig.getMailSenderSslenable() != null) {
            sslEnable = CommonConsts.Bool.parse(systemConfig.getMailSenderSslenable()).getBoolean();
        }
        String account = systemConfig.getMailSenderAccount();
        String passwd = StringEncrypUtil.decrypt(systemConfig.getMailSenderPasswd());
        try {
            if (StringHelper.isEmail(account)) {
                EmailUtils.sendSimpleEmail(new EmailUtils.Sender(smtpAddr, smtpPort, account, passwd, sslEnable),
                        "DCMS系统资源告警", content, systemConfig.getMailRecvResourceAccount());
            }
        } catch (Throwable e) {
        }
    }

}
