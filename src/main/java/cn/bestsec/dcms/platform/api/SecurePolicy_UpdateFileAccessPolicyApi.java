package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface SecurePolicy_UpdateFileAccessPolicyApi {
    /**
     * 权限:安全管理员
<br>表示用户可登陆的终端密级

     */
    SecurePolicy_UpdateFileAccessPolicyResponse execute(SecurePolicy_UpdateFileAccessPolicyRequest securePolicy_UpdateFileAccessPolicyRequest) throws ApiException;
}
