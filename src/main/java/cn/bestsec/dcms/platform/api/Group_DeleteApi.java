package cn.bestsec.dcms.platform.api;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.*;

/**
 * 自动生成的API接口类，不要手动修改
 */
public interface Group_DeleteApi {
    /**
     * 权限:系统管理员
组内没有用户时可以删除，否则不能删除。

     */
    Group_DeleteResponse execute(Group_DeleteRequest group_DeleteRequest) throws ApiException;
}
