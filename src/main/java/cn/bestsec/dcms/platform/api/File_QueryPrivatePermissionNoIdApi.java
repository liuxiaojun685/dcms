package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface File_QueryPrivatePermissionNoIdApi {
    /**
     * 权限:终端用户
<br>查询个人对指定文件的可操作权限，决定了终端右键菜单可选项和文件操作等权限。
<br>用户对文件的角色可以是多个，默认权限是叠加的。
<br>fileIsMyCreate表示用户是否是文件起草人，如果是返回结果叠加起草人默认权限。
<br>用户可能是定密责任人或签发人，如果是返回结果叠加对应权限。

     */
    File_QueryPrivatePermissionNoIdResponse execute(File_QueryPrivatePermissionNoIdRequest file_QueryPrivatePermissionNoIdRequest) throws ApiException;
}
