package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface User_UnlockApi {
    /**
     * 权限:系统管理员
<br>解锁后错误登陆次数清零，用户可以尝试进行登陆。

     */
    User_UnlockResponse execute(User_UnlockRequest user_UnlockRequest) throws ApiException;
}
