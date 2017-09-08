package cn.bestsec.dcms.platform.impl.log;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.Log_UploadLogApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Log_UploadLogRequest;
import cn.bestsec.dcms.platform.api.model.Log_UploadLogResponse;
import cn.bestsec.dcms.platform.dao.ClientDao;
import cn.bestsec.dcms.platform.dao.ClientLogDao;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.Client;
import cn.bestsec.dcms.platform.entity.ClientLog;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.LogArchiveService;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月15日 下午2:05:10
 */
@Component
public class LogUploadLog implements Log_UploadLogApi {
    static Logger logger = LoggerFactory.getLogger(LogUploadLog.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private ClientDao clientDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private ClientLogDao clientLogDao;
    @Autowired
    private LogArchiveService logArchiveService;

    private static int COL_TIME = 0;
    private static int COL_TYPE = 1;
    private static int COL_WAY = 2;
    private static int COL_RESULT = 3;
    private static int COL_UID = 4;
    private static int COL_CID = 5;
    private static int COL_FID = 6;
    private static int COL_FNAME = 7;
    private static int COL_RESERVE = 8;
    private static int COLNUM = 9;

    /**
     * [time 时间戳ms ] [type 操作类型 阅读 打印 编辑 截屏 复制 粘贴 删除 另存 重命名 发送 预定密 正式定密 文件签发
     * 密级变更 文件解密 文件解绑 ] [way 操作方式补充 有水印 无水印 ] [result 操作结果 ] [uid 用户ID ] [cid
     * 终端ID ] [fid 文件ID ] [fname 源文件名称 ] [reserve 预留 重命名时存放新文件名 ]
     * 
     * 注意：日志每一行代表一条记录，字段间以:分割，空字段填入null，不要出现连续多个::的情况，应为:null:
     * 
     * 样例
     * 1491368648000:打印:有水印:1:8662774:cid-446b523a8c514db08bedb9292d44f1df:04a4390aaf484520bfe4705e0c49546f:DCMS项目客户端需求说明.txt:null
     * 1491643528000:重命名:null:1:8662774:cid-446b523a8c514db08bedb9292d44f1df:04a4390aaf484520bfe4705e0c49546f:DCMS项目客户端需求说明.txt:DCMS需求说明第二版.txt
     * 
     * 日志文件保存在终端本地，默认周期30分钟一次，在登陆成功后客户端计算出30分钟内的随机数延迟第一次上传，目的降低服务器压力。
     */

    @Override
    @Transactional
    public Log_UploadLogResponse execute(Log_UploadLogRequest log_UploadLogRequest) throws ApiException {
        File attachment = log_UploadLogRequest.getAttachment();
        FileInputStream input = null;
        try {
            input = new FileInputStream(attachment);
            List<String> list = IOUtils.readLines(input, "GB2312");
            if (list == null) {
                return new Log_UploadLogResponse();
            }
            for (String line : list) {
                logger.debug("文件操作日志行----" + line);
                String[] items = line.split(":");
                try {
                    String ip = log_UploadLogRequest.httpServletRequest().getRemoteAddr();
                    Long createTime = Long.valueOf(items[COL_TIME]);
                    String fname = parseNull(items[COL_FNAME]);
                    String operateType = parseNull(items[COL_TYPE]);
                    Integer operateResult = 0;
                    String reason = "";
                    if ("1".equals(items[COL_RESULT])) {
                        operateResult = 1;
                    } else {
                        operateResult = 2;
                        reason = items[COL_RESULT];
                    }

                    String operateWay = parseNull(items[COL_WAY]);
                    String reservelog = "";
                    if (items.length > COL_RESERVE) {
                        reservelog = parseNull(items[COL_RESERVE]);
                    }
                    String cid = parseNull(items[COL_CID]);
                    String uid = parseNull(items[COL_UID]);
                    String fid = parseNull(items[COL_FID]);

                    if (filterOperateType(operateType)) {
                        continue;
                    }

                    // 相同的日志行则不保存
                    List<ClientLog> clientLogs = clientLogDao.findBySameAs(cid, uid, fid, createTime, operateType);
                    if (clientLogs != null && !clientLogs.isEmpty()) {
                        continue;
                    }

                    Client client = null;
                    User user = null;
                    cn.bestsec.dcms.platform.entity.File fileByFkSrcFid = null;
                    if (!cid.isEmpty()) {
                        client = clientDao.findByPkCid(cid);
                    }
                    if (!uid.isEmpty()) {
                        user = userDao.findByPkUid(uid);
                    }
                    if (!fid.isEmpty()) {
                        fileByFkSrcFid = fileDao.findByPkFid(fid);
                    }
                    ClientLog clientLog = new ClientLog(client, fileByFkSrcFid, user, createTime, ip, reason,
                            operateType, "", operateResult, operateWay, fname, reservelog);
                    clientLogDao.save(clientLog);
                    String desc = logArchiveService.parseClientLogDescription(clientLog.getId(), false);
                    clientLog.setOperateDescription(desc);
                    clientLogDao.save(clientLog);
                } catch (Throwable e) {
                    logger.warn("未识别的文件操作日志行----" + line);
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        if (input != null) {
            IOUtils.closeQuietly(input);
        }

        return new Log_UploadLogResponse();
    }

    private boolean filterOperateType(String operateType) {
        if (operateType == null) {
            return true;
        }
        if (operateType.contains("流程")) {
            return true;
        }
        return false;
    }

    private String parseNull(String str) {
        if (str == null) {
            return "";
        }
        return str.equals("null") ? "" : str;
    }

}
