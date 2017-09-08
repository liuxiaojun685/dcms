package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface Department_QueryTreeApi {
    /**
     * 权限:任意
<br>组织架构树中默认包含部门节点，hasUser/hasGroup表示是否包含用户/组节点。
<br>同时包含用户/组节点时，自动包含组内用户节点。
<br>fileLevel在包含用户节点的情况下，筛选有访问该密级文件权限的用户，未包含有效用户的组/部门节点被隐藏。
<br>关键词检索优先级最低。

     */
    Department_QueryTreeResponse execute(Department_QueryTreeRequest department_QueryTreeRequest) throws ApiException;
}
