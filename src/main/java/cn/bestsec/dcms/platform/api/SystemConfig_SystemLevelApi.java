package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface SystemConfig_SystemLevelApi {
    /**
     * 权限:安全管理员。
<br>一次调用生效，不可修改。初次传入level值设定系统密级，后续调用当作查看系统密级接口。

     */
    SystemConfig_SystemLevelResponse execute(SystemConfig_SystemLevelRequest systemConfig_SystemLevelRequest) throws ApiException;
}
