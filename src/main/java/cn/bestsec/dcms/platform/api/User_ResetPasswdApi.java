package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface User_ResetPasswdApi {
    /**
     * 权限:系统管理员
<br>重置后，该用户密码恢复默认未修改状态，错误登陆次数清零。

     */
    User_ResetPasswdResponse execute(User_ResetPasswdRequest user_ResetPasswdRequest) throws ApiException;
}
