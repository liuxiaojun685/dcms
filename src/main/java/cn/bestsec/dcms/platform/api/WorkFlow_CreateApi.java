package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface WorkFlow_CreateApi {
    /**
     * 权限:终端用户
<br>multipart/form-data for POST 附件参数名file 消息(JSON)参数名body
<br>正式定密：文件ID/文件密级/保密期限/定密依据/辅助定密单位/签发范围/申请理由。
<br>密级变更：文件ID/文件密级/保密期限/申请理由。
<br>文件签发：文件ID/签发范围/申请理由。
<br>文件解密：文件ID/申请理由。

     */
    WorkFlow_CreateResponse execute(WorkFlow_CreateRequest workFlow_CreateRequest) throws ApiException;
}
