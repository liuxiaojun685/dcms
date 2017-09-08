package cn.bestsec.dcms.platform.impl.role;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Role_AddApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.RoleLevelInfo;
import cn.bestsec.dcms.platform.api.model.Role_AddRequest;
import cn.bestsec.dcms.platform.api.model.Role_AddResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.RoleConsts;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 添加用户角色
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月27日上午10:29:42
 */
@Component
public class RoleAdd implements Role_AddApi {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;

    @Override
    @Transactional
    public Role_AddResponse execute(Role_AddRequest role_AddRequest) throws ApiException {
        User user = userDao.findByPkUid(role_AddRequest.getUid());
        if (user == null) {
            throw new ApiException(ErrorCode.userNotExist);
        }

        AdminLogBuilder adminLogBuilder = role_AddRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.role_add)
                .admin(role_AddRequest.tokenWrapper().getAdmin()).operateDescription(user.getName(),
                        RoleConsts.Type.parse(role_AddRequest.getRoleType()).getDescription());

        List<User> users = roleDao.findRoleUsers(role_AddRequest.getRoleType());
        if (users.contains(user)) {
            throw new ApiException(ErrorCode.userAlreadyExists);
        }

        Role role = new Role();
        role.setUserByFkUid(user);
        role.setFileLevel(0);
        role.setRoleType(role_AddRequest.getRoleType());
        roleDao.save(role);

        Role_AddResponse resp = new Role_AddResponse();
        resp.setAccount(user.getAccount());
        resp.setLevel(user.getUserLevel());
        resp.setName(user.getName());
        resp.setOnline(UserConsts.userOnline(user));
        resp.setUid(user.getPkUid());
        resp.setRoleId(role.getId());
        resp.setRoleType(role.getRoleType());
        resp.setRoleDispScope("");

        List<RoleLevelInfo> infos = new ArrayList<RoleLevelInfo>();
        for (Integer level : securityPolicyService.getSupportFileLevel(user.getUserLevel())) {
            RoleLevelInfo info = new RoleLevelInfo();
            info.setRoleLevel(level);
            infos.add(info);
        }
        resp.setRoleLevelList(infos);
        return resp;
    }

}
