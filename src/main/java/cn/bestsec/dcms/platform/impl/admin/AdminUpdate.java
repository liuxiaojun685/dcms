package cn.bestsec.dcms.platform.impl.admin;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Admin_UpdateApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Admin_UpdateRequest;
import cn.bestsec.dcms.platform.api.model.Admin_UpdateResponse;
import cn.bestsec.dcms.platform.consts.AdminConsts;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.AdminDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 管理员信息修改
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月23日下午9:59:27
 *
 */
@Component
public class AdminUpdate implements Admin_UpdateApi {

    @Autowired
    private AdminDao adminDao;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;

    @Transactional
    @Override
    public Admin_UpdateResponse execute(Admin_UpdateRequest admin_UpdateRequest) throws ApiException {

        String name = admin_UpdateRequest.getName();
        String passwd = admin_UpdateRequest.getPasswd();
        String oldPasswd = admin_UpdateRequest.getOldPasswd();
        Token token = null;

        if (name != null || passwd != null) {
            token = tokenDao.findByToken(admin_UpdateRequest.getToken());
        }

        if (token != null) {
            Admin admin = token.getAdmin();
            if (name != null) {
                admin.setName(name);
            }
            if (passwd != null) {
                AdminLogBuilder adminLogBuilder = admin_UpdateRequest.createAdminLogBuilder();
                adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.admin_update)
                        .admin(admin_UpdateRequest.tokenWrapper().getAdmin()).operateDescription();
                if (!admin.getPasswd().equals(StringEncrypUtil.encryptNonreversible(oldPasswd))) {
                    throw new ApiException(ErrorCode.accountOrPasswordError);
                }
                if (!securityPolicyService.isValidLoginPasswd(passwd, admin.getAccount())) {
                    throw new ApiException(ErrorCode.invalidUserPasswd);
                }
                admin.setPasswd(StringEncrypUtil.encryptNonreversible(passwd));
            }
            adminDao.save(admin);

            // 该管理员如果是指定的用户，需同步更新用户密码
            if (admin.getCreateFrom() == AdminConsts.From.manual.getCode()) {
                User user = userDao.findByAccount(admin.getAccount());
                user.setPasswd(admin.getPasswd());
                userDao.save(user);
            }
        }

        return new Admin_UpdateResponse();
    }

}
