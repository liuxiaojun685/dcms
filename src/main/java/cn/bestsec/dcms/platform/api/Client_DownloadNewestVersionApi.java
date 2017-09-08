package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface Client_DownloadNewestVersionApi {
    /**
     * 权限:终端用户
<br>GET，以普通HTTP参数形式请求，返回application/octet-stream文件

     */
    Client_DownloadNewestVersionResponse execute(Client_DownloadNewestVersionRequest client_DownloadNewestVersionRequest) throws ApiException;
}
