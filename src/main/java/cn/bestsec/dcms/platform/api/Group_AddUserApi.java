package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface Group_AddUserApi {
    /**
     * 权限:系统管理员
<br>用户允许在多个组

     */
    Group_AddUserResponse execute(Group_AddUserRequest group_AddUserRequest) throws ApiException;
}
