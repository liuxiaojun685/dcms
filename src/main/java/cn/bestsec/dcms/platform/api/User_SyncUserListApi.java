package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface User_SyncUserListApi {
    /**
     * 权限:系统管理员
<br>需要考虑AD用户表和本地用户表的对应关系，后期在做。

     */
    User_SyncUserListResponse execute(User_SyncUserListRequest user_SyncUserListRequest) throws ApiException;
}
