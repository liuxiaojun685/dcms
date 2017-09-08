package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface Client_AddClientPatchApi {
    /**
     * 权限:系统管理员
<br>multipart/form-data for POST 附件参数名file 消息(JSON)参数名body

     */
    Client_AddClientPatchResponse execute(Client_AddClientPatchRequest client_AddClientPatchRequest) throws ApiException;
}
