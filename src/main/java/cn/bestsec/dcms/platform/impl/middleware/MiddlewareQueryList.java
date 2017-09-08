package cn.bestsec.dcms.platform.impl.middleware;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Middleware_QueryListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.MiddlewareACLInfo;
import cn.bestsec.dcms.platform.api.model.MiddlewareInfo;
import cn.bestsec.dcms.platform.api.model.Middleware_QueryListRequest;
import cn.bestsec.dcms.platform.api.model.Middleware_QueryListResponse;
import cn.bestsec.dcms.platform.dao.MiddlewareACLDao;
import cn.bestsec.dcms.platform.dao.MiddlewareDao;
import cn.bestsec.dcms.platform.entity.Middleware;
import cn.bestsec.dcms.platform.entity.MiddlewareAcl;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 获取中间件访问控制列表
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月4日 下午4:58:47
 */
@Component
public class MiddlewareQueryList implements Middleware_QueryListApi {

    @Autowired
    private MiddlewareDao middlewareDao;
    @Autowired
    private MiddlewareACLDao middlewareACLDao;

    @Override
    @Transactional
    public Middleware_QueryListResponse execute(Middleware_QueryListRequest middleware_QueryListRequest)
            throws ApiException {
        Middleware_QueryListResponse resp = new Middleware_QueryListResponse();
        // 获取中间件所有数据
        List<Middleware> middlewareList = middlewareDao.findAll();
        // 响应数据
        List<MiddlewareInfo> middlewareInfos = new ArrayList<MiddlewareInfo>();
        if (middlewareList != null) {
            for (Middleware middleware : middlewareList) {
                MiddlewareInfo middlewareInfo = new MiddlewareInfo();
                middlewareInfo.setMiddlewareId(middleware.getId());
                middlewareInfo.setName(middleware.getName());
                middlewareInfo.setDescription(middleware.getDescription());
                middlewareInfo.setEnable(middleware.getEnable());

                List<MiddlewareAcl> acls = middlewareACLDao.findByMiddleware(middleware);
                if (acls != null) {
                    List<MiddlewareACLInfo> aclInfos = new ArrayList<MiddlewareACLInfo>();
                    for (MiddlewareAcl acl : acls) {
                        MiddlewareACLInfo aclInfo = new MiddlewareACLInfo();
                        aclInfo.setMiddlewareACLId(acl.getId());
                        aclInfo.setIp(acl.getIp());
                        aclInfo.setEnable(acl.getEnable());
                        aclInfo.setPasswdEnable(acl.getPasswdEnable());
                        aclInfo.setPasswd(StringEncrypUtil.decrypt(acl.getPasswd()));
                        aclInfos.add(aclInfo);
                    }
                    middlewareInfo.setMiddlewareUserList(aclInfos);
                }
                middlewareInfos.add(middlewareInfo);
            }
        }

        resp.setMiddlewareList(middlewareInfos);
        return resp;
    }

}
