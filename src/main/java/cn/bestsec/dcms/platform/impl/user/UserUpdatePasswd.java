
package cn.bestsec.dcms.platform.impl.user;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.User_UpdatePasswdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.User_UpdatePasswdRequest;
import cn.bestsec.dcms.platform.api.model.User_UpdatePasswdResponse;
import cn.bestsec.dcms.platform.consts.AdminConsts;
import cn.bestsec.dcms.platform.consts.ClientLogOp;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.AdminDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;
import cn.bestsec.dcms.platform.utils.ClientLogBuilder;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 用户修改密码
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月23日下午9:43:17
 *
 */
@Component
public class UserUpdatePasswd implements User_UpdatePasswdApi {

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;

    @Override
    @Transactional
    public User_UpdatePasswdResponse execute(User_UpdatePasswdRequest user_UpdatePasswdRequest) throws ApiException {
        Token token = tokenDao.findByToken(user_UpdatePasswdRequest.getToken());
        User user = token.getUser();
        if (user == null || user.getUserState() == UserConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.userNotExist);
        }
        ClientLogBuilder logBuilder = user_UpdatePasswdRequest.createClientLogBuilder();
        logBuilder.operateTime(System.currentTimeMillis()).operation(ClientLogOp.user_updatePasswd).user(user)
                .operateDescription(user.getName());

        if (!user.getPasswd().equals(StringEncrypUtil.encryptNonreversible(user_UpdatePasswdRequest.getOldPasswd()))) {
            throw new ApiException(ErrorCode.accountOrPasswordError);
        }

        if (!securityPolicyService.isValidLoginPasswd(user_UpdatePasswdRequest.getPasswd(), user.getAccount())) {
            throw new ApiException(ErrorCode.invalidUserPasswd);
        }

        user.setPasswd(StringEncrypUtil.encryptNonreversible(user_UpdatePasswdRequest.getPasswd()));
        // 用户密码更新时间
        user.setLastPasswdChangeTime(System.currentTimeMillis());
        userDao.save(user);
        tokenDao.delete(token);

        // 如果用户被分配管理员角色，需更新对应管理员密码
        Admin admin = adminDao.findByAccountAndAdminStateNot(user.getAccount(),
                AdminConsts.AdminState.deleted.getCode());
        if (admin != null) {
            admin.setPasswd(user.getPasswd());
            adminDao.save(admin);
        }

        return new User_UpdatePasswdResponse();
    }

}
