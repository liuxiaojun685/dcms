package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface Role_DeleteByIdApi {
    /**
     * 权限:安全管理员
<br>如果该角色没有其他人，不能删除。

     */
    Role_DeleteByIdResponse execute(Role_DeleteByIdRequest role_DeleteByIdRequest) throws ApiException;
}
