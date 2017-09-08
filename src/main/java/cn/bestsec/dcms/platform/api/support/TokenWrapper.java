package cn.bestsec.dcms.platform.api.support;

import cn.bestsec.dcms.platform.consts.AdminConsts;
import cn.bestsec.dcms.platform.consts.TokenConsts.UserRole;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * @author 徐泽威 email:xzw@bestsec.cn
 * @time：2016年12月26日 上午11:21:58
 */
public class TokenWrapper {
    private Token raw;
    private Admin admin;
    private User user;

    public TokenWrapper(Token raw) {
        this.raw = raw;
        if (isAdmin()) {
            admin = raw.getAdmin();
        }
        if (isUser()) {
            user = raw.getUser();
        }
    }

    public TokenWrapper(User mw) {
        this.user = mw;
    }

    /**
     * 是否有效
     * 
     * @return
     */
    public boolean isValid() {
        if (raw == null) {
            return false;
        }
        return raw.getHeartbeatTime() + SystemProperties.getInstance().getTokenValidTime() > System.currentTimeMillis();
    }

    /**
     * 获取该token的用户类型
     * 
     * @return
     */
    public UserRole getUserRole() {
        if (raw == null) {
            if (user != null && user.getCreateFrom() == UserConsts.CreateFrom.middleware.getCode()) {
                return UserRole.middleware;
            }
            return UserRole.nocheck;
        } else if (isAdmin()) {
            switch (AdminConsts.Type.parse(raw.getAdmin().getAdminType())) {
            case sysadmin:
                return UserRole.sysadmin;
            case secadmin:
                return UserRole.secadmin;
            case logadmin:
                return UserRole.logadmin;
            default:
                return UserRole.any;
            }
        } else if (isUser()) {
            return UserRole.user;
        } else {
            return UserRole.any;
        }
    }

    public boolean isAdmin() {
        if (raw == null) {
            return false;
        }
        return raw.getAdmin() != null;
    }

    public boolean isUser() {
        if (raw == null) {
            return false;
        }
        return raw.getUser() != null;
    }

    public Token getRaw() {
        return raw;
    }

    public void setRaw(Token raw) {
        this.raw = raw;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
