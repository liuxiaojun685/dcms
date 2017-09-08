package cn.bestsec.dcms.platform.service;

import cn.bestsec.dcms.platform.api.support.TokenWrapper;

public interface ApiSecurityService {
    TokenWrapper queryToken(String token);
    void heartbeat(String token);
    int userOnline(String uid);
    int clientOnline(String cid);
    TokenWrapper installMw(Integer mid, String mName, String mIp, String mMac);
}
