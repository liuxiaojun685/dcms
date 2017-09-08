package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface User_AddApi {
    /**
     * 权限:系统管理员
<br>用户密级可以不设，默认0未分配。
<br>传入did可以直接分配用户到指定部门。

     */
    User_AddResponse execute(User_AddRequest user_AddRequest) throws ApiException;
}
