package cn.bestsec.dcms.platform.impl.user;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.User_UnlockApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.User_UnlockRequest;
import cn.bestsec.dcms.platform.api.model.User_UnlockResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 用户解锁 系统管理员
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月5日 下午2:46:22
 */
@Component
public class UserUnlock implements User_UnlockApi {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional
    public User_UnlockResponse execute(User_UnlockRequest user_UnlockRequest) throws ApiException {
        User_UnlockResponse resp = new User_UnlockResponse();
        // 得到需要解锁的用户
        User user = userDao.findByPkUid(user_UnlockRequest.getUid());
        if (user == null || user.getUserState() == UserConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.userNotExist);
        }

        AdminLogBuilder adminLogBuilder = user_UnlockRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.user_unlock)
                .admin(user_UnlockRequest.tokenWrapper().getAdmin()).operateDescription(user.getName());

        user.setUserState(UserConsts.State.def.getCode());
        // 用户错误次数清零
        user.setErrLoginCount(0);
        // 用户解锁日期
        user.setUnlockTime(System.currentTimeMillis());
        userDao.save(user);

        return resp;
    }

}
