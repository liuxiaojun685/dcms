
package cn.bestsec.dcms.platform.impl.user;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.User_ResetPasswdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.User_ResetPasswdRequest;
import cn.bestsec.dcms.platform.api.model.User_ResetPasswdResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 重置用户密码
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月24日上午11:48:39
 *
 */
@Component
public class UserResetPasswd implements User_ResetPasswdApi {

    @Autowired
    private TokenDao tokenDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public User_ResetPasswdResponse execute(User_ResetPasswdRequest user_ResetPasswdRequest) throws ApiException {
        User user = userDao.findByPkUid(user_ResetPasswdRequest.getUid());
        if (user == null || user.getUserState() == UserConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.userNotExist);
        }

        AdminLogBuilder adminLogBuilder = user_ResetPasswdRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.user_resetPasswd)
                .admin(user_ResetPasswdRequest.tokenWrapper().getAdmin()).operateDescription(user.getName());

        SystemConfig config = systemConfigService.getSystemConfig();
        String localAuthInitPasswd = config.getLocalAuthInitPasswd();
        if (localAuthInitPasswd.isEmpty()) {
            throw new ApiException(ErrorCode.workflowPolicyError);
        }
        localAuthInitPasswd = StringEncrypUtil.decrypt(localAuthInitPasswd);
        user.setPasswd(StringEncrypUtil.encryptNonreversible(localAuthInitPasswd));
        user.setUserState(UserConsts.State.def.getCode());
        user.setPasswdState(UserConsts.PasswdState.init.getCode());
        user.setErrLoginCount(0);
        // 用户修改密码的时间为默认值
        user.setLastPasswdChangeTime(System.currentTimeMillis());
        userDao.save(user);

        List<Token> tokens = user.getTokens();
        tokenDao.delete(tokens);

        return new User_ResetPasswdResponse();
    }

}
