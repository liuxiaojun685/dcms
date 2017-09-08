package cn.bestsec.dcms.platform.impl.admin;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Admin_QueryDeriveAdminApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.AdminRole;
import cn.bestsec.dcms.platform.api.model.Admin_QueryDeriveAdminRequest;
import cn.bestsec.dcms.platform.api.model.Admin_QueryDeriveAdminResponse;
import cn.bestsec.dcms.platform.consts.AdminConsts;
import cn.bestsec.dcms.platform.dao.AdminDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.User;

/**
 * 派生管理员列表
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月8日 下午3:32:27
 */
@Component
public class AdminQueryDeriveAdmin implements Admin_QueryDeriveAdminApi {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private TokenDao tokenDao;

    @Override
    @Transactional
    public Admin_QueryDeriveAdminResponse execute(Admin_QueryDeriveAdminRequest admin_QueryDeriveAdminRequest)
            throws ApiException {

        Admin loginAdmin = tokenDao.findByToken(admin_QueryDeriveAdminRequest.getToken()).getAdmin();
        List<Admin> sysadmins = null;
        // 内置安全管理员登录
        if (loginAdmin.getCreateFrom() == AdminConsts.From.inner.getCode()) {

            sysadmins = adminDao.findByAdminTypeAndAdminStateAndCreateFrom(admin_QueryDeriveAdminRequest.getAdminType(),
                    AdminConsts.AdminState.undelete.getCode(), AdminConsts.From.manual.getCode());
        }
        // 派生安全管理员登录
        if (loginAdmin.getCreateFrom() == AdminConsts.From.manual.getCode()) {

            sysadmins = adminDao.findByAdminTypeAndFkParentIdAndAdminStateAndCreateFrom(
                    admin_QueryDeriveAdminRequest.getAdminType(), loginAdmin.getPkAid(),
                    AdminConsts.AdminState.undelete.getCode(), AdminConsts.From.manual.getCode());
        }
        List<AdminRole> adminList = new ArrayList<>();
        for (Admin admin : sysadmins) {
            AdminRole adminRole = new AdminRole();
            User user = userDao.findByAccount(admin.getAccount());
            adminRole = new AdminRole();
            adminRole.setAccount(user.getAccount());
            adminRole.setName(user.getName());
            adminRole.setUid(user.getPkUid());
            adminRole.setAid(admin.getPkAid());
            adminRole.setParentAdmin(adminDao
                    .findByPkAidAndAdminStateNot(admin.getFkParentId(), AdminConsts.AdminState.deleted.getCode())
                    .getName());
            adminList.add(adminRole);
        }

        Admin_QueryDeriveAdminResponse resp = new Admin_QueryDeriveAdminResponse();
        resp.setAdminList(adminList);
        return resp;
    }

}
