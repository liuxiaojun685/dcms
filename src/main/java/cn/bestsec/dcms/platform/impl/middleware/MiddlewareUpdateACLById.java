package cn.bestsec.dcms.platform.impl.middleware;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Middleware_UpdateACLByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Middleware_UpdateACLByIdRequest;
import cn.bestsec.dcms.platform.api.model.Middleware_UpdateACLByIdResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.MiddlewareACLDao;
import cn.bestsec.dcms.platform.entity.MiddlewareAcl;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 修改中间件访问控制
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月4日 下午5:10:36
 */
@Component
public class MiddlewareUpdateACLById implements Middleware_UpdateACLByIdApi {

    @Autowired
    private MiddlewareACLDao middlewareACLDao;

    @Override
    @Transactional
    public Middleware_UpdateACLByIdResponse execute(Middleware_UpdateACLByIdRequest middleware_UpdateACLByIdRequest)
            throws ApiException {
        Middleware_UpdateACLByIdResponse resp = new Middleware_UpdateACLByIdResponse();
        // 查找需要修改的中间件访问控制
        MiddlewareAcl middlewareAcl = middlewareACLDao.findById(middleware_UpdateACLByIdRequest.getMiddlewareACLId());
        if (middlewareAcl == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        Integer enable = middleware_UpdateACLByIdRequest.getEnable();
        if (enable != null) {
            middlewareAcl.setEnable(enable);
        }
        String ip = middleware_UpdateACLByIdRequest.getIp();
        if (ip != null) {
            middlewareAcl.setIp(ip);
        }
        String passwd = middleware_UpdateACLByIdRequest.getPasswd();
        if (passwd != null) {
            middlewareAcl.setPasswd(StringEncrypUtil.encrypt(passwd));
        }
        Integer passwdEnable = middleware_UpdateACLByIdRequest.getPasswdEnable();
        if (passwdEnable != null) {
            middlewareAcl.setPasswdEnable(passwdEnable);
        }
        middlewareACLDao.save(middlewareAcl);

        AdminLogBuilder adminLogBuilder = middleware_UpdateACLByIdRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.middleware_update)
                .admin(middleware_UpdateACLByIdRequest.tokenWrapper().getAdmin())
                .operateDescription(middlewareAcl.getMiddleware().getName());

        return resp;
    }

}
