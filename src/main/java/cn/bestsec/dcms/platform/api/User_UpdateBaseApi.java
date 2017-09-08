package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface User_UpdateBaseApi {
    /**
     * 权限:系统管理员
<br>根据uid修改用户的基本信息

     */
    User_UpdateBaseResponse execute(User_UpdateBaseRequest user_UpdateBaseRequest) throws ApiException;
}
