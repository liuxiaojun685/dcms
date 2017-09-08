package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface Client_AddApi {
    /**
     * 权限:系统管理员
<br>单机版终端只支持管理员手动录入。

     */
    Client_AddResponse execute(Client_AddRequest client_AddRequest) throws ApiException;
}
