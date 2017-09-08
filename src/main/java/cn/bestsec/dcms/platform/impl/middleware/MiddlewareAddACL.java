package cn.bestsec.dcms.platform.impl.middleware;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Middleware_AddACLApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Middleware_AddACLRequest;
import cn.bestsec.dcms.platform.api.model.Middleware_AddACLResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.MiddlewareACLDao;
import cn.bestsec.dcms.platform.dao.MiddlewareDao;
import cn.bestsec.dcms.platform.entity.Middleware;
import cn.bestsec.dcms.platform.entity.MiddlewareAcl;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 增加访问控制策略
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2017年1月3日上午10:14:43
 */
@Component
public class MiddlewareAddACL implements Middleware_AddACLApi {
    @Autowired
    private MiddlewareACLDao middlewareACLDao;
    @Autowired
    private MiddlewareDao middlewaredDao;

    @Override
    @Transactional
    public Middleware_AddACLResponse execute(Middleware_AddACLRequest middleware_AddACLRequest) throws ApiException {
        MiddlewareAcl middlewareAcl = new MiddlewareAcl();
        Middleware middleware = middlewaredDao.findById(middleware_AddACLRequest.getMiddlewareId());
        if (middleware == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        middlewareAcl.setMiddleware(middleware);
        middlewareAcl.setIp(middleware_AddACLRequest.getIp());
        middlewareAcl.setEnable(middleware_AddACLRequest.getEnable());
        middlewareAcl.setPasswdEnable(middleware_AddACLRequest.getPasswdEnable());
        middlewareAcl.setPasswd(StringEncrypUtil.encrypt(middleware_AddACLRequest.getPasswd()));
        middlewareACLDao.save(middlewareAcl);

        AdminLogBuilder adminLogBuilder = middleware_AddACLRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.middleware_add)
                .admin(middleware_AddACLRequest.tokenWrapper().getAdmin())
                .operateDescription(middleware.getName(), middlewareAcl.getIp());

        Middleware_AddACLResponse resp = new Middleware_AddACLResponse();
        resp.setMiddlewareACLId(middlewareAcl.getId());
        return resp;
    }

}
