package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface File_QueryPrivatePermissionByIdApi {
    /**
     * 权限:终端用户
<br>查询个人对指定文件的可操作权限，决定了终端右键菜单可选项和文件操作等权限。
<br>如果系统没有记录这个文件(预定密文件)，返回错误，终端应尝试通过文件属性查用户角色的默认权限。
<br>如果文件未被签发或授权，接口返回用户所属角色(起草人，定密/签发人)的默认权限。
<br>用户对文件的角色可以是多个，默认权限是叠加的。

     */
    File_QueryPrivatePermissionByIdResponse execute(File_QueryPrivatePermissionByIdRequest file_QueryPrivatePermissionByIdRequest) throws ApiException;
}
