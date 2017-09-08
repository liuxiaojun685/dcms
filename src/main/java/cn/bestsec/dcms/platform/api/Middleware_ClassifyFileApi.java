package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface Middleware_ClassifyFileApi {
    /**
     * 权限:中间件对接
<br>应用系统对文件进行标定密动作，如文件预定密或文件去除密标。
<br>
<br>通过http上传文件可以使用下面的形式。
<br>multipart/form-data for POST 附件参数名file 消息(JSON)参数名body 附件名参数名rfilename
<br>文件流放入file表单项，数据以json字符串形式放入body表单项，文件名放入rfilename表单项。
<br>
<br>通过其他方式上传文件可以使用下面的形式。
<br>application/json for POST

     */
    Middleware_ClassifyFileResponse execute(Middleware_ClassifyFileRequest middleware_ClassifyFileRequest) throws ApiException;
}
