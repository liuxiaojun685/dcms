package cn.bestsec.dcms.platform.impl.group;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Group_DeleteUserInBatchesApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Group_DeleteUserInBatchesRequest;
import cn.bestsec.dcms.platform.api.model.Group_DeleteUserInBatchesResponse;
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
 * 4.8 组批量移除用户
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月12日 上午11:05:16
 */
@Component
public class GroupDeleteUserInBatches implements Group_DeleteUserInBatchesApi {

    @Autowired
    private UserToGroupDao userToGroupDao;
    @Autowired
    private GroupDao groupDao;
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public Group_DeleteUserInBatchesResponse execute(Group_DeleteUserInBatchesRequest group_DeleteUserInBatchesRequest)
            throws ApiException {
        // 用户组不能为空
        Group group = groupDao.findByPkGid(group_DeleteUserInBatchesRequest.getGid());
        if (group == null || group.getGroupState() == GroupConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        // 用户ID
        List<String> uidList = group_DeleteUserInBatchesRequest.getUidList();
        List<User> userList = new ArrayList<User>();

        for (String uid : uidList) {
            User user = userDao.findByPkUid(uid);
            if (user == null || user.getUserState() == UserConsts.State.deleted.getCode()) {
                throw new ApiException(ErrorCode.userNotExist);
            }
            userList.add(user);
        }

        for (User user : userList) {

            // 该用户是否已经在该组里
            List<UserToGroup> userToGroups = userToGroupDao.findByGroupAndUser(group, user);
            if (!userToGroups.isEmpty()) {
                userToGroupDao.delete(userToGroups);

                AdminLogBuilder adminLogBuilder = group_DeleteUserInBatchesRequest.createAdminLogBuilder();
                adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.group_deleteUser)
                        .admin(group_DeleteUserInBatchesRequest.tokenWrapper().getAdmin()).operateDescription(user.getName(), group.getName());
            }
        }

        return new Group_DeleteUserInBatchesResponse();
    }

}
