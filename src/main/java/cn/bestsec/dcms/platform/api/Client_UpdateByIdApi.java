package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface Client_UpdateByIdApi {
    /**
     * 权限:系统管理员
<br>管理员只可以修改描述，其他信息由终端自动录入，只可读。

     */
    Client_UpdateByIdResponse execute(Client_UpdateByIdRequest client_UpdateByIdRequest) throws ApiException;
}
