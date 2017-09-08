package cn.bestsec.dcms.platform.impl.group;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Group_AddUserInBatchesApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Group_AddUserInBatchesRequest;
import cn.bestsec.dcms.platform.api.model.Group_AddUserInBatchesResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.GroupConsts;
import cn.bestsec.dcms.platform.consts.ResultType;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.GroupDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.dao.UserToGroupDao;
import cn.bestsec.dcms.platform.entity.Group;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.UserToGroup;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 4.7 组批量添加用户
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月12日 上午10:30:27
 */
@Component
public class GroupAddUserInBatches implements Group_AddUserInBatchesApi {

    @Autowired
    private UserToGroupDao userToGroupDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private GroupDao groupDao;

    @Override
    @Transactional
    public Group_AddUserInBatchesResponse execute(Group_AddUserInBatchesRequest group_AddUserInBatchesRequest)
            throws ApiException {
        // 用户组不能为空
        Group group = groupDao.findByPkGid(group_AddUserInBatchesRequest.getGid());
        if (group == null || group.getGroupState() == GroupConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        // 用户ID
        List<String> uidList = group_AddUserInBatchesRequest.getUidList();
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
            userToGroupDao.flush();
            List<UserToGroup> userToGroups = userToGroupDao.findByGroupAndUser(group, user);
            if (userToGroups.isEmpty()) {
                userToGroupDao.save(new UserToGroup(group, user));

                AdminLogBuilder adminLogBuilder = group_AddUserInBatchesRequest.createAdminLogBuilder();
                adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.group_addUser)
                        .admin(group_AddUserInBatchesRequest.tokenWrapper().getAdmin())
                        .operateDescription(user.getName(), group.getName())
                        .operateResult(ResultType.success.getCode(), ErrorCode.success.getReason())
                        .ip(group_AddUserInBatchesRequest.httpServletRequest().getRemoteAddr());
            }
        }

        return new Group_AddUserInBatchesResponse();
    }

}
