package cn.bestsec.dcms.platform.impl.group;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Group_DeleteUserApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Group_DeleteUserRequest;
import cn.bestsec.dcms.platform.api.model.Group_DeleteUserResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.GroupConsts;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.GroupDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.dao.UserToGroupDao;
import cn.bestsec.dcms.platform.entity.Group;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.UserToGroup;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 组移除用户
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月26日下午7:13:03
 */
@Component
public class GroupDeleteUser implements Group_DeleteUserApi {

    @Autowired
    private UserToGroupDao userToGroupDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public Group_DeleteUserResponse execute(Group_DeleteUserRequest group_DeleteUserRequest) throws ApiException {
        Group group = groupDao.findByPkGid(group_DeleteUserRequest.getGid());
        if (group == null || group.getGroupState() == GroupConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        User user = userDao.findByPkUid(group_DeleteUserRequest.getUid());
        if (user == null || user.getUserState() == UserConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.userNotExist);
        }
        List<UserToGroup> userToGroups = userToGroupDao.findByGroupAndUser(group, user);
        if (userToGroups.isEmpty()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        userToGroupDao.delete(userToGroups);

        AdminLogBuilder adminLogBuilder = group_DeleteUserRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.group_deleteUser)
                .admin(group_DeleteUserRequest.tokenWrapper().getAdmin()).operateDescription(user.getName(), group.getName());
        
        return new Group_DeleteUserResponse();
    }

}
