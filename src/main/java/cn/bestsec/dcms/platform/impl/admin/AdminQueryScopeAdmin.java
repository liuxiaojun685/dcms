package cn.bestsec.dcms.platform.impl.admin;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Admin_QueryScopeAdminApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.AdminRole;
import cn.bestsec.dcms.platform.api.model.Admin_QueryScopeAdminRequest;
import cn.bestsec.dcms.platform.api.model.Admin_QueryScopeAdminResponse;
import cn.bestsec.dcms.platform.consts.AdminConsts;
import cn.bestsec.dcms.platform.dao.AdminDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.User;

/**
 * 管理员用户列表(成为管理员的所有用户)
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月7日 上午10:36:52
 */
@Component
public class AdminQueryScopeAdmin implements Admin_QueryScopeAdminApi {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AdminDao adminDao;

    @Override
    @Transactional
    public Admin_QueryScopeAdminResponse execute(Admin_QueryScopeAdminRequest admin_QueryScopeAdminRequest)
            throws ApiException {
        List<AdminRole> userList = new ArrayList<>();
        List<Admin> admins = adminDao.findByCreateFromAndAdminStateNot(AdminConsts.From.manual.getCode(),
                AdminConsts.AdminState.deleted.getCode());
        for (Admin admin : admins) {
            User user = userDao.findByAccount(admin.getAccount());
            AdminRole adminRole = new AdminRole();
            adminRole.setAccount(user.getAccount());
            adminRole.setName(user.getName());
            adminRole.setAid(admin.getPkAid());
            adminRole.setParentAdmin(adminDao
                    .findByPkAidAndAdminStateNot(admin.getFkParentId(), AdminConsts.AdminState.deleted.getCode())
                    .getName());
            adminRole.setUid(user.getPkUid());
            userList.add(adminRole);
        }
        Admin_QueryScopeAdminResponse resp = new Admin_QueryScopeAdminResponse();
        resp.setUserList(userList);
        return resp;
    }

}
