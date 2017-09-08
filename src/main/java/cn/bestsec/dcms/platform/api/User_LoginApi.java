package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface User_LoginApi {
    /**
     * 权限:不检查
<br>登陆时尽可能的传入详细终端信息，后台需记录数据。
<br>登陆失败时判断remainTimes字段，显示剩余尝试次数。
<br>判断passwdForceChange是否强制修改密码，如果是，强制弹出修改密码界面。

     */
    User_LoginResponse execute(User_LoginRequest user_LoginRequest) throws ApiException;
}
