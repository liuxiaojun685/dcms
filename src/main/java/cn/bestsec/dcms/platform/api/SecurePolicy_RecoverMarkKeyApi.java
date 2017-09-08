package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface SecurePolicy_RecoverMarkKeyApi {
    /**
     * 权限:安全管理员
<br>multipart/form-data for POST 附件参数名file 消息(JSON)参数名body

     */
    SecurePolicy_RecoverMarkKeyResponse execute(SecurePolicy_RecoverMarkKeyRequest securePolicy_RecoverMarkKeyRequest) throws ApiException;
}