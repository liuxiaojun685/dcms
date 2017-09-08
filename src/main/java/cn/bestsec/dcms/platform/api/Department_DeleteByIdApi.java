package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface Department_DeleteByIdApi {
    /**
     * 权限:系统管理员
<br>部门及其子部门没有用户时可以删除，否则不能删除。

     */
    Department_DeleteByIdResponse execute(Department_DeleteByIdRequest department_DeleteByIdRequest) throws ApiException;
}
