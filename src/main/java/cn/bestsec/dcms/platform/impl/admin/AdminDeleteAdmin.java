package cn.bestsec.dcms.platform.impl.admin;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Admin_DeleteAdminApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Admin_DeleteAdminRequest;
import cn.bestsec.dcms.platform.api.model.Admin_DeleteAdminResponse;
import cn.bestsec.dcms.platform.consts.AdminConsts;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.AdminDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 移除管理员角色用户
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月6日 下午6:38:45
 */
@Component
public class AdminDeleteAdmin implements Admin_DeleteAdminApi {

    @Autowired
    private AdminDao adminDao;
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public Admin_DeleteAdminResponse execute(Admin_DeleteAdminRequest admin_DeleteAdminRequest) throws ApiException {

        Admin admin = adminDao.findByPkAidAndAdminStateNot(admin_DeleteAdminRequest.getAid(),
                AdminConsts.AdminState.deleted.getCode());
        if (admin == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        // 记录操作日志
        User user = userDao.findByAccount(admin.getAccount());
        AdminLogBuilder adminLogBuilder = admin_DeleteAdminRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.role_deleteAdmin)
                .admin(admin_DeleteAdminRequest.tokenWrapper().getAdmin()).operateDescription(user.getName());

        List<Admin> childAdmins = new ArrayList<>();
        admin.setAdminState(AdminConsts.AdminState.deleted.getCode());
        // 区分该账户已是删除
        // admin.setAccount(admin.getAccount() + "_delete");
        childAdmins.add(admin);
        childsDel(admin, childAdmins);
        adminDao.save(childAdmins);

        return new Admin_DeleteAdminResponse();
    }

    /**
     * 删除时需删除该对象派生出的所有对象
     * 
     * @param admin
     * @param childAdmins
     * @return
     */
    private void childsDel(Admin admin, List<Admin> childAdmins) {
        List<Admin> admins = adminDao.findByFkParentIdAndAdminStateNot(admin.getPkAid(),
                AdminConsts.AdminState.deleted.getCode());
        for (Admin cAdmin : admins) {
            // cAdmin.setAccount(cAdmin.getAccount() + "_delete");
            cAdmin.setAdminState(AdminConsts.AdminState.deleted.getCode());
            childsDel(cAdmin, childAdmins);
        }
        childAdmins.addAll(admins);
    }

}
