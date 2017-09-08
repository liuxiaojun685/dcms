package cn.bestsec.dcms.platform.impl.log;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Log_LogArchiveApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Log_LogArchiveRequest;
import cn.bestsec.dcms.platform.api.model.Log_LogArchiveResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.BackupHistoryConsts;
import cn.bestsec.dcms.platform.service.LogArchiveService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 导出管理员excel
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月7日 下午3:46:55
 */
@Component
public class LogLogArchive implements Log_LogArchiveApi {
    static Logger logger = LoggerFactory.getLogger(LogLogArchive.class);
    @Autowired
    private LogArchiveService logArchiveService;

    @Override
    public Log_LogArchiveResponse execute(Log_LogArchiveRequest request) throws ApiException {
        Date date = new Date();
        long currentTime = date.getTime();
        int num = logArchiveService.logArchive(BackupHistoryConsts.CreateFrom.manual.getCode(), date);
        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(currentTime).operation(AdminLogOp.log_logArchive)
                .admin(request.tokenWrapper().getAdmin()).operateDescription("手动", num + "");
        return new Log_LogArchiveResponse();
    }
}
