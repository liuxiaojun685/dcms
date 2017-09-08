package cn.bestsec.dcms.platform.impl.user;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.User_UpdateLevelApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.User_UpdateLevelRequest;
import cn.bestsec.dcms.platform.api.model.User_UpdateLevelResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 修改用户密级，安全管理员
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月27日 下午6:34:09
 */
@Component
public class UserUpdateLevel implements User_UpdateLevelApi {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public User_UpdateLevelResponse execute(User_UpdateLevelRequest user_UpdateLevelRequest) throws ApiException {
        // 获取需要被修改的用户
        User user = userDao.findByPkUid(user_UpdateLevelRequest.getUid());
        if (user == null || user.getUserState() == UserConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.userNotExist);
        }

        AdminLogBuilder adminLogBuilder = user_UpdateLevelRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.user_updateLevel)
                .admin(user_UpdateLevelRequest.tokenWrapper().getAdmin()).operateDescription(user.getName(),
                        UserConsts.Level.parse(user.getUserLevel()).getUserLevel(),
                        UserConsts.Level.parse(user_UpdateLevelRequest.getLevel()).getUserLevel());

        // 如果该用户被配置到流程策略，不能修改
        List<Role> rolesForFkUid = user.getRolesForFkUid();
        if (rolesForFkUid == null) {
            rolesForFkUid = new ArrayList<>();
        }
        List<Role> rolesForFkAgent = user.getRolesForFkAgentUid();
        if (rolesForFkAgent != null && rolesForFkAgent.size() > 0) {
            for (Role role : rolesForFkAgent) {
                rolesForFkUid.add(role);
            }
        }

        if (rolesForFkUid != null && rolesForFkUid.size() > 0) {
            throw new ApiException(ErrorCode.roleAlreadyInWorkflowPolicy);
        }
        user.setUserLevel(user_UpdateLevelRequest.getLevel());
        userDao.save(user);
        return new User_UpdateLevelResponse();
    }

}
