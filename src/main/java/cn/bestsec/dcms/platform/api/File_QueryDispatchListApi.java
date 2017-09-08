package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface File_QueryDispatchListApi {
    /**
     * 权限:终端用户
<br>只有已签发过或授权的文件有分发范围。

     */
    File_QueryDispatchListResponse execute(File_QueryDispatchListRequest file_QueryDispatchListRequest) throws ApiException;
}
