package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface WorkFlow_ApprovalApi {
    /**
     * 权限:终端用户
<br>正式定密：流程ID/审批意见/审批状态。决定文件密级/保密期限/定密依据/辅助定密单位，建议签发范围。
<br>密级变更：流程ID/审批意见/审批状态。决定文件密级/保密期限。
<br>文件签发：流程ID/审批意见/审批状态。决定签发范围
<br>文件解密：仅流程ID/审批意见/审批状态。

     */
    WorkFlow_ApprovalResponse execute(WorkFlow_ApprovalRequest workFlow_ApprovalRequest) throws ApiException;
}
