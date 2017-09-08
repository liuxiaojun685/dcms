package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface User_QueryListApi {
    /**
     * 权限:系统管理员和安全管理员

     */
    User_QueryListResponse execute(User_QueryListRequest user_QueryListRequest) throws ApiException;
}
