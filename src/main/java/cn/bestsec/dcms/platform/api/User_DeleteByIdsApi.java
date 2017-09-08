package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface User_DeleteByIdsApi {
    /**
     * 权限:系统管理员
<br>如果用户有未完成的审批，不能删除。
<br>如果用户作为责任人/特权人/代理人，不能删除。
<br>根据uid删除用户信息及用户与部门组的联系一并删除，

     */
    User_DeleteByIdsResponse execute(User_DeleteByIdsRequest user_DeleteByIdsRequest) throws ApiException;
}
