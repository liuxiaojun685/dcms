package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface SecurePolicy_QueryFileAccessPolicyListApi {
    /**
     * 权限:安全管理员
<br>表示用户可操作的文件密级

     */
    SecurePolicy_QueryFileAccessPolicyListResponse execute(SecurePolicy_QueryFileAccessPolicyListRequest securePolicy_QueryFileAccessPolicyListRequest) throws ApiException;
}
