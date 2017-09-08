package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface File_ApplyNewFidApi {
    /**
     * 权限:终端用户
<br>终端申请一个系统分配的fid，作为文件的唯一识别，并存到文件头里。

     */
    File_ApplyNewFidResponse execute(File_ApplyNewFidRequest file_ApplyNewFidRequest) throws ApiException;
}
