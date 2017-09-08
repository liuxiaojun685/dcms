package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface File_AuthorizationApi {
    /**
     * 权限:终端用户
<br>有授权权限的用户可以授权。

     */
    File_AuthorizationResponse execute(File_AuthorizationRequest file_AuthorizationRequest) throws ApiException;
}
