package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface User_ImportApi {
    /**
     * 权限:系统管理员
<br>将文件编成base64流放到fileStream里。

     */
    User_ImportResponse execute(User_ImportRequest user_ImportRequest) throws ApiException;
}
