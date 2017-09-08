package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface SecurePolicy_BackupMarkKeyApi {
    /**
     * 权限:安全管理员
<br>GET，以普通HTTP参数形式请求，返回application/octet-stream文件

     */
    SecurePolicy_BackupMarkKeyResponse execute(SecurePolicy_BackupMarkKeyRequest securePolicy_BackupMarkKeyRequest) throws ApiException;
}
