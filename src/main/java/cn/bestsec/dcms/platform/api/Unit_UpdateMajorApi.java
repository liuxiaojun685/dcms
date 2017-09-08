package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface Unit_UpdateMajorApi {
    /**
     * 权限:安全管理员
<br>主定密单位只能有一个

     */
    Unit_UpdateMajorResponse execute(Unit_UpdateMajorRequest unit_UpdateMajorRequest) throws ApiException;
}
