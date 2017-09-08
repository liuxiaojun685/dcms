package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface Role_DeleteByIdForceApi {
    /**
     * 权限:安全管理员
<br>不做检查，直接删除角色。

     */
    Role_DeleteByIdForceResponse execute(Role_DeleteByIdForceRequest role_DeleteByIdForceRequest) throws ApiException;
}
