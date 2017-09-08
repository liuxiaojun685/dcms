package cn.bestsec.dcms.platform.impl.admin;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Admin_AddAdminApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Admin_AddAdminRequest;
import cn.bestsec.dcms.platform.api.model.Admin_AddAdminResponse;
import cn.bestsec.dcms.platform.consts.AdminConsts;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.AdminDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.IdUtils;

/**
 * 添加用户管理员
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月6日 下午2:08:54
 */
@Component
public class AdminAddAdmin implements Admin_AddAdminApi {

    @Autowired
    private UserDao userDao;
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private TokenDao tokenDao;

    @Override
    @Transactional
    public Admin_AddAdminResponse execute(Admin_AddAdminRequest admin_AddAdminRequest) throws ApiException {

        String token = admin_AddAdminRequest.getToken();

        List<String> userIdList = admin_AddAdminRequest.getUserIdList();
        List<Admin> admins = new ArrayList<>();
        long currentTimeMillis = System.currentTimeMillis();
        // 记录需要添加的名字
        StringBuffer userName = new StringBuffer("");
        for (String userId : userIdList) {
            User user = userDao.findByPkUid(userId);
            Admin admin = new Admin();
            admin.setAccount(user.getAccount());
            admin.setPasswd(user.getPasswd());
            admin.setName(user.getName());

            userName.append(user.getName() + "|");
            admin.setFkParentId(tokenDao.findByToken(token).getAdmin().getPkAid());
            admin.setAdminType(admin_AddAdminRequest.getAdminType());
            admin.setCreateFrom(AdminConsts.From.manual.getCode());
            admin.setDerive(AdminConsts.Derive.no.getCode());
            admin.setCreateTime(currentTimeMillis);
            admin.setPkAid(IdUtils.randomId(IdUtils.prefix_admin_id));
            admins.add(admin);
        }
        adminDao.save(admins);
        // 任命的管理员类型
        String amindType = "";
        if (admin_AddAdminRequest.getAdminType() == AdminConsts.Type.sysadmin.getCode()) {
            amindType = AdminConsts.Type.sysadmin.getDescription();
        } else if (admin_AddAdminRequest.getAdminType() == AdminConsts.Type.secadmin.getCode()) {
            amindType = AdminConsts.Type.secadmin.getDescription();
        } else {
            amindType = AdminConsts.Type.logadmin.getDescription();
        }
        // 记录操作日志
        AdminLogBuilder adminLogBuilder = admin_AddAdminRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.role_addAdmin)
                .admin(admin_AddAdminRequest.tokenWrapper().getAdmin()).operateDescription(userName, amindType);
        return new Admin_AddAdminResponse();
    }

}
