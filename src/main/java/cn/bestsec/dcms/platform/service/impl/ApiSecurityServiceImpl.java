package cn.bestsec.dcms.platform.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.support.TokenWrapper;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.ClientDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.Client;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.ApiSecurityService;
import cn.bestsec.dcms.platform.utils.IdUtils;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * @author 徐泽威 email:xzw@bestsec.cn
 * @time：2016年12月26日 下午5:51:30
 */
@Service
public class ApiSecurityServiceImpl implements ApiSecurityService {
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ClientDao clientDao;

    @Override
    public TokenWrapper queryToken(String token) {
        return new TokenWrapper(tokenDao.findByToken(token));
    }

    @Transactional
    @Override
    public void heartbeat(String token) {
        Long currentTime = System.currentTimeMillis();
        Token tokenObj = tokenDao.findByToken(token);
        if (tokenObj != null) {
            // 计算失效时间 +30分钟
            tokenObj.setHeartbeatTime(currentTime);
            tokenDao.save(tokenObj);
            if (new TokenWrapper(tokenObj).isUser()) {
                User user = tokenObj.getUser();
                user.setHeartbeatTime(currentTime);
                userDao.save(user);
                Client client = tokenObj.getClient();
                if (client != null) {
                    client.setHeartbeatTime(currentTime);
                    clientDao.save(client);
                }
            }
        }
    }

    @Override
    public int userOnline(String uid) {
        Long currentTime = System.currentTimeMillis();
        User user = userDao.findByPkUid(uid);
        if (user == null || user.getHeartbeatTime() == null) {
            return 2;
        }
        return (user.getHeartbeatTime() + SystemProperties.getInstance().getUserOfflineTime()) > currentTime ? 1 : 2;
    }

    @Override
    public int clientOnline(String cid) {
        Long currentTime = System.currentTimeMillis();
        Client client = clientDao.findByPkCid(cid);
        if (client == null || client.getHeartbeatTime() == null) {
            return 2;
        }
        return (client.getHeartbeatTime() + SystemProperties.getInstance().getUserOfflineTime()) > currentTime ? 1 : 2;
    }

    @Override
    public TokenWrapper installMw(Integer mid, String mName, String mIp, String mMac) {
        long currentTime = System.currentTimeMillis();
        User user = null;
        if (mid == null || mid == 0) {
            user = userDao.findMWUserByIp(mIp);
        } else {
            user = userDao.findMWUserByMid("" + mid);
        }
        if (user == null) {
            user = new User();
            user.setPhone("" + mid);
            user.setName(mName);
            user.setCreateFrom(UserConsts.CreateFrom.middleware.getCode());
            user.setCreateTime(currentTime);
            user.setUserLevel(UserConsts.Level.core.getCode());
            user.setPkUid("" + IdUtils.randowUid());
            user.setAccount("#MW#" + mid + "#");
            user.setUserState(UserConsts.State.deleted.getCode());
        }
        user.setHeartbeatTime(currentTime);
        user.setLastLoginIp(mIp);
        user.setMail(mMac);
        userDao.save(user);
        return new TokenWrapper(user);
    }
}
