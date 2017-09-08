package cn.bestsec.dcms.platform.impl.admin;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Admin_LogoutApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Admin_LogoutRequest;
import cn.bestsec.dcms.platform.api.model.Admin_LogoutResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 管理员登出
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月23日下午9:48:05
 *
 */
@Component
public class AdminLogout implements Admin_LogoutApi {

    @Autowired
    private TokenDao tokenDao;

    @Transactional
    @Override
    public Admin_LogoutResponse execute(Admin_LogoutRequest admin_LogoutRequest) throws ApiException {
        Admin_LogoutResponse resp = new Admin_LogoutResponse();
        Token token = tokenDao.findByToken(admin_LogoutRequest.getToken());

        AdminLogBuilder adminLogBuilder = admin_LogoutRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.admin_logout)
                .admin(admin_LogoutRequest.tokenWrapper().getAdmin()).operateDescription();

        tokenDao.delete(token);

        return resp;
    }

}
