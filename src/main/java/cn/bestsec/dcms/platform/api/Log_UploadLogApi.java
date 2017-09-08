package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface Log_UploadLogApi {
    /**
     * 权限:终端用户
<br>multipart/form-data for POST 附件参数名file 消息(JSON)参数名body

     */
    Log_UploadLogResponse execute(Log_UploadLogRequest log_UploadLogRequest) throws ApiException;
}
