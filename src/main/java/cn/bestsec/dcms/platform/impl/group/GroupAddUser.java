package cn.bestsec.dcms.platform.impl.group;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Group_AddUserApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Group_AddUserRequest;
import cn.bestsec.dcms.platform.api.model.Group_AddUserResponse;
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
 * 组添加用户 只有管理员才可以添加组用户
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月27日 下午5:04:21
 */
@Component
public class GroupAddUser implements Group_AddUserApi {

    @Autowired
    private UserToGroupDao userToGroupDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupDao groupDao;

    @Override
    @Transactional
    public Group_AddUserResponse execute(Group_AddUserRequest group_AddUserRequest) throws ApiException {
        // 获取用户id，组id
        String uid = group_AddUserRequest.getUid();
        String gid = group_AddUserRequest.getGid();
        // 用户必须存在
        User user = userDao.findByPkUid(uid);
        if (user == null || user.getUserState() == UserConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.userNotExist);
        }
        // 用户组不能为空
        Group group = groupDao.findByPkGid(gid);
        if (group == null || group.getGroupState() == GroupConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        // 该用户是否已经在该组里
        List<UserToGroup> userToGroups = userToGroupDao.findByGroupAndUser(group, user);
        if (userToGroups.isEmpty()) {
            userToGroupDao.save(new UserToGroup(group, user));
            
            AdminLogBuilder adminLogBuilder = group_AddUserRequest.createAdminLogBuilder();
            adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.group_addUser)
                    .admin(group_AddUserRequest.tokenWrapper().getAdmin()).operateDescription(user.getName(), group.getName());

        }
        return new Group_AddUserResponse();
    }

}
