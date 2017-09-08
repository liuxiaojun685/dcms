package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface Department_AddUserApi {
    /**
     * 权限:系统管理员
<br>一个用户只能隶属于一个部门

     */
    Department_AddUserResponse execute(Department_AddUserRequest department_AddUserRequest) throws ApiException;
}
