package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface File_QueryExistByIdApi {
    /**
     * 权限:终端用户
<br>如果不同步，用户需要下载新的文件。

     */
    File_QueryExistByIdResponse execute(File_QueryExistByIdRequest file_QueryExistByIdRequest) throws ApiException;
}
