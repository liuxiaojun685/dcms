package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface Admin_UpdateApi {
    /**
     * 权限:管理员
<br>请求修改密码并成功后，需要重新登陆。

     */
    Admin_UpdateResponse execute(Admin_UpdateRequest admin_UpdateRequest) throws ApiException;
}
