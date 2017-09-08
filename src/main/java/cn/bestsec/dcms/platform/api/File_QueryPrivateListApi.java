package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface File_QueryPrivateListApi {
    /**
     * 权限:终端用户
<br>文件收件箱：作为分发使用人的已签发文件和作为定密责任人（有定密历史）的文件,作为签发人的文件
<br>文件拟稿箱：作为文件起草人的所有文件
<br>待解密：作为定密责任人的即将到或超过解密日期的正式定密/密级变更/文件签发文件
<br>已解密：作为定密责任人的已解密的文件
<br>负责范围内文件：作为定密责任人可视范围内的所有文件

     */
    File_QueryPrivateListResponse execute(File_QueryPrivateListRequest file_QueryPrivateListRequest) throws ApiException;
}
