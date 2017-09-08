package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface WorkFlow_DownloadFileByIdApi {
    /**
     * 权限:终端用户
<br>GET，以普通HTTP参数形式请求，返回application/octet-stream文件
<br>字段属性要与文件头中匹配。fid作为文件的唯一识别。

     */
    WorkFlow_DownloadFileByIdResponse execute(WorkFlow_DownloadFileByIdRequest workFlow_DownloadFileByIdRequest) throws ApiException;
}
