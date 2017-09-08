package cn.bestsec.dcms.platform.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.BackupHistoryConsts;
import cn.bestsec.dcms.platform.consts.ClientLogOp;
import cn.bestsec.dcms.platform.consts.CommonConsts;
import cn.bestsec.dcms.platform.consts.ResultType;
import cn.bestsec.dcms.platform.consts.TimeConsts;
import cn.bestsec.dcms.platform.dao.AdminLogDao;
import cn.bestsec.dcms.platform.dao.ClientLogDao;
import cn.bestsec.dcms.platform.dao.ClientRequestLogDao;
import cn.bestsec.dcms.platform.dao.FileLevelValidPeriodDao;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.AdminLog;
import cn.bestsec.dcms.platform.entity.ClientLog;
import cn.bestsec.dcms.platform.entity.ClientRequestLog;
import cn.bestsec.dcms.platform.entity.FileLevelValidPeriod;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.LogArchiveService;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.DateUtils;
import cn.bestsec.dcms.platform.utils.IdUtils;
import cn.bestsec.dcms.platform.utils.LocationUtils;
import cn.bestsec.dcms.platform.utils.Md5Utils;
import cn.bestsec.dcms.platform.utils.SystemProperties;
import cn.bestsec.dcms.platform.utils.TextUtils;
import cn.bestsec.dcms.platform.utils.export.CSVUtils;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月17日 下午6:05:59
 */
@Service
public class LogArchiveServiceImpl implements LogArchiveService {
    static Logger logger = LoggerFactory.getLogger(LogArchiveServiceImpl.class);

    @Autowired
    private AdminLogDao adminLogDao;
    @Autowired
    private ClientRequestLogDao clientRequestLogDao;
    @Autowired
    private ClientLogDao clientLogDao;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private FileLevelValidPeriodDao fileLevelValidPeriodDao;

    @Override
    @Transactional
    public int logArchive(Integer auto, Date date) throws ApiException {
        long currentTime = date.getTime();
        SystemConfig systemConfig = systemConfigService.getSystemConfig();
        String json = systemConfig.getLogArchiveKeepTime();
        String riskLevel1 = "3";
        String riskLevel2 = "6";
        String riskLevel3 = "12";
        try {
            JSONObject obj = JSONObject.parseObject(json);
            riskLevel1 = obj.getString("riskLevel1");
            riskLevel2 = obj.getString("riskLevel2");
            riskLevel3 = obj.getString("riskLevel3");
        } catch (Exception e) {
        }

        StorageLocation location = systemConfigService.getLogLocation();

        Long timeBefore1 = currentTime - Integer.valueOf(riskLevel1) * TimeConsts.month_in_millis;
        Long timeBefore2 = currentTime - Integer.valueOf(riskLevel2) * TimeConsts.month_in_millis;
        Long timeBefore3 = currentTime - Integer.valueOf(riskLevel3) * TimeConsts.month_in_millis;
        List<AdminLog> adminLogs = adminLogDao.findNeedArchive(timeBefore1, timeBefore2, timeBefore3);
        if (!adminLogs.isEmpty()) {
            File file = exportAdminLog(adminLogs);
            if (LocationUtils.canUpload(location)) {
                if (!LocationUtils.upload(location, file.getPath(), file.getName())) {
                    logger.error("日志文件上传存储失败，请检查配置");
                    throw new ApiException(ErrorCode.fileSaveFailed);
                }
            }
            adminLogDao.delete(adminLogs);
        }

        List<ClientLog> clientLogs = clientLogDao.findNeedArchive(timeBefore1, timeBefore2, timeBefore3);
        if (!clientLogs.isEmpty()) {
            File file = exportClientLog(clientLogs);
            if (LocationUtils.canUpload(location)) {
                if (!LocationUtils.upload(location, file.getPath(), file.getName())) {
                    logger.error("日志文件上传存储失败，请检查配置");
                    throw new ApiException(ErrorCode.fileSaveFailed);
                }
            }
            clientLogDao.delete(clientLogs);
        }
        int num = adminLogs.size() + clientLogs.size();

        if (auto == BackupHistoryConsts.CreateFrom.auto.getCode()) {
            AdminLogBuilder adminLogBuilder = new AdminLogBuilder();
            adminLogBuilder.operateTime(currentTime).operation(AdminLogOp.log_logArchive)
                    .operateDescription("自动", num + "").operateResult(ResultType.success.getCode(), null)
                    .ip("127.0.0.1");
            adminLogDao.save(adminLogBuilder.build());
        }
        return num;
    }

    private File exportAdminLog(List<AdminLog> logs) {
        LinkedHashMap<String, String> header = new LinkedHashMap<String, String>();
        header.put("acount", "管理员账号");
        header.put("aid", "管理员ID");
        header.put("operateType", "操作类别");
        header.put("operateDescription", "操作详情");
        header.put("operateResult", "操作结果");
        header.put("operateTime", "操作日期");
        header.put("ip", "操作IP");

        List<Map<String, String>> exportData = new ArrayList<>();
        Map<String, String> row = null;
        for (AdminLog log : logs) {
            row = new HashMap<>();
            Admin admin = log.getAdmin();
            String account = "";
            String aid = "";
            if (admin != null) {
                account = admin.getAccount();
                aid = admin.getPkAid();
            }
            row.put("acount", account);
            row.put("aid", aid);
            row.put("operateType", log.getOperateType());
            row.put("operateDescription", log.getOperateDescription());
            row.put("operateResult", log.getOperateResult() == 1 ? "成功" : "失败");
            row.put("operateTime", DateUtils.timeStamp2Date(log.getOperateTime(), null));
            row.put("ip", log.getIp());
            exportData.add(row);
        }
        String path = SystemProperties.getInstance().getCacheDir();
        File file = CSVUtils.createCSVFile(exportData, header, path, IdUtils.randomId());
        String md5 = Md5Utils.getMd5ByFile(file);
        File target = new File(file.getParentFile(), md5 + ".csv");
        file.renameTo(target);
        return target;
    }

    private File exportClientRequestLog(List<ClientRequestLog> logs) {
        LinkedHashMap<String, String> header = new LinkedHashMap<String, String>();
        header.put("acount", "用户账号");
        header.put("uid", "用户ID");
        header.put("operateType", "操作类别");
        header.put("operateDescription", "操作详情");
        header.put("operateResult", "操作结果");
        header.put("operateTime", "操作日期");
        header.put("ip", "操作IP");

        List<Map<String, String>> exportData = new ArrayList<>();
        Map<String, String> row = null;
        for (ClientRequestLog log : logs) {
            row = new HashMap<>();
            User user = log.getUser();
            String account = "";
            String uid = "";
            if (user != null) {
                account = user.getAccount();
                uid = user.getPkUid();
            }
            row.put("acount", account);
            row.put("uid", uid);
            row.put("operateType", log.getOperateType());
            row.put("operateDescription", log.getOperateDescription());
            row.put("operateResult", log.getOperateResult() == 1 ? "成功" : "失败");
            row.put("operateTime", DateUtils.timeStamp2Date(log.getOperateTime(), null));
            row.put("ip", log.getIp());
            exportData.add(row);
        }
        String path = SystemProperties.getInstance().getCacheDir();
        File file = CSVUtils.createCSVFile(exportData, header, path, IdUtils.randomId());
        String md5 = Md5Utils.getMd5ByFile(file);
        File target = new File(file.getParentFile(), md5 + ".csv");
        file.renameTo(target);
        return target;
    }

    private File exportClientLog(List<ClientLog> logs) {
        LinkedHashMap<String, String> header = new LinkedHashMap<String, String>();
        header.put("acount", "用户账号");
        header.put("uid", "用户ID");
        header.put("fileName", "文件名");
        header.put("fid", "文件ID");
        header.put("operateType", "操作类别");
        header.put("operateDescription", "操作详情");
        header.put("operateResult", "操作结果");
        header.put("operateTime", "操作日期");
        header.put("ip", "操作IP");

        List<Map<String, String>> exportData = new ArrayList<>();
        Map<String, String> row = null;
        for (ClientLog log : logs) {
            row = new HashMap<>();
            User user = log.getUser();
            String account = "";
            String uid = "";
            if (user != null) {
                account = user.getAccount();
                uid = user.getPkUid();
            }
            cn.bestsec.dcms.platform.entity.File src = log.getFileByFkSrcFid();
            String fileName = "";
            String fid = "";
            if (src != null) {
                fileName = src.getName();
                fid = src.getPkFid();
            }
            row.put("acount", account);
            row.put("uid", uid);
            row.put("fileName", fileName);
            row.put("fid", fid);
            row.put("operateType", log.getOperateType());
            row.put("operateDescription", log.getOperateDescription());
            row.put("operateResult", log.getOperateResult() == 1 ? "成功" : "失败");
            row.put("operateTime", DateUtils.timeStamp2Date(log.getCreateTime(), null));
            row.put("ip", log.getIp());
            exportData.add(row);
        }
        String path = SystemProperties.getInstance().getCacheDir();
        File file = CSVUtils.createCSVFile(exportData, header, path, IdUtils.randomId());
        String md5 = Md5Utils.getMd5ByFile(file);
        File target = new File(file.getParentFile(), md5 + ".csv");
        file.renameTo(target);
        return target;
    }

    @Override
    public String parseClientLogDescription(Integer clientLogId, boolean autoDesensity) {
        ClientLog clientLog = clientLogDao.findOne(clientLogId);
        String description = "";
        String userName = clientLog.getUser().getName();
        ClientLogOp operateType = ClientLogOp.parse(clientLog.getOperateType());
        String result = clientLog.getOperateResult() == 1 ? "成功" : "失败";
        if (clientLog.getOperateResult() != 1 && clientLog.getReserve() != null && !clientLog.getReserve().isEmpty()) {
            result += "，原因：" + clientLog.getReserve();
        }
        String fileName = clientLog.getSrcName();
        String fileName2 = clientLog.getDesName();
        cn.bestsec.dcms.platform.entity.File src = clientLog.getFileByFkSrcFid();
        if (src != null) {
            if (fileName == null || fileName.isEmpty()) {
                fileName = src.getName();
            }
            if (autoDesensity && isFileDesensity(src.getFileLevel())) {
                fileName = TextUtils.getDealWithName(fileName);
                fileName2 = TextUtils.getDealWithName(fileName2);
            }
        }
        if (operateType == null) {
            description = clientLog.getOperateDescription();
        } else {
            switch (operateType) {
            case read:
            case edit:
            case screenshot:
            case paste:
            case delete:
            case filePreclassified:
            case fileClassified:
            case fileIssued:
            case fileClassifiedChange:
            case fileDeclassified:
            case fileRestore:
            case file_download:
            case requestPreclassified:
            case requestClassified:
            case requestIssued:
            case requestClassifiedChange:
            case requestDeclassified:
            case requestRestore:
                description = String.format(operateType.getFormat(), userName, fileName, result);
                break;
            case print:
            case send:
            case approvePreclassified:
            case approveClassified:
            case approveIssued:
            case approveClassifiedChange:
            case approveDeclassified:
            case approveRestore:
                description = String.format(operateType.getFormat(), userName, clientLog.getOperateWay(), fileName,
                        result);
                break;
            case copy:
            case saveAs:
            case rename:
                description = String.format(operateType.getFormat(), userName, fileName, fileName2, result);
                break;
            default:
                description = clientLog.getOperateDescription();
                break;
            }
        }
        return description;
    }

    @Override
    public boolean isFileDesensity(Integer fileLevel) {
        FileLevelValidPeriod fileLevelValidPeriod = fileLevelValidPeriodDao.findByfilelevel(fileLevel);
        if (fileLevelValidPeriod.getIsDesensity() == CommonConsts.Bool.yes.getInt()) {
            return true;
        }
        return false;
    }

}
