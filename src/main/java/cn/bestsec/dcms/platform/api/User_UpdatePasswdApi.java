package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface User_UpdatePasswdApi {
    /**
     * 权限:终端用户
<br>新密码不能与旧密码相同。

     */
    User_UpdatePasswdResponse execute(User_UpdatePasswdRequest user_UpdatePasswdRequest) throws ApiException;
}
