package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface File_CommitAnalysisApi {
    /**
     * 权限:终端用户
<br>multipart/form-data for POST 附件参数名file 消息(JSON)参数名body
<br>字段属性要与文件头中匹配。fid作为文件的唯一识别。

     */
    File_CommitAnalysisResponse execute(File_CommitAnalysisRequest file_CommitAnalysisRequest) throws ApiException;
}
