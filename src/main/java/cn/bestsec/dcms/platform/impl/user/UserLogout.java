package cn.bestsec.dcms.platform.impl.user;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.User_LogoutApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.User_LogoutRequest;
import cn.bestsec.dcms.platform.api.model.User_LogoutResponse;
import cn.bestsec.dcms.platform.consts.ClientLogOp;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.utils.ClientLogBuilder;

/**
 * 用户注销接口
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月23日 下午4:08:38
 */
@Component
public class UserLogout implements User_LogoutApi {

    @Autowired
    private TokenDao tokenDao;

    @Override
    @Transactional
    public User_LogoutResponse execute(User_LogoutRequest user_LogoutRequest) throws ApiException {
        Token token = tokenDao.findByToken(user_LogoutRequest.getToken());
        // 查找该用户的token值
        tokenDao.delete(token);
        User user = user_LogoutRequest.tokenWrapper().getUser();
        ClientLogBuilder logBuilder = user_LogoutRequest.createClientLogBuilder();
        logBuilder.operateTime(System.currentTimeMillis()).operation(ClientLogOp.user_logout).user(user)
                .operateDescription(user.getName());
        return new User_LogoutResponse();
    }

}
