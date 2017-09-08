package cn.bestsec.dcms.platform.impl.middleware;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Middleware_DeleteACLByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Middleware_DeleteACLByIdRequest;
import cn.bestsec.dcms.platform.api.model.Middleware_DeleteACLByIdResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.MiddlewareACLDao;
import cn.bestsec.dcms.platform.entity.MiddlewareAcl;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 删除一条中间件访问控制策略
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2017年1月3日上午10:44:33
 */
@Component
public class MiddlewareDeleteACLById implements Middleware_DeleteACLByIdApi {
    @Autowired
    private MiddlewareACLDao middlewareACLDao;

    @Override
    @Transactional
    public Middleware_DeleteACLByIdResponse execute(Middleware_DeleteACLByIdRequest middleware_DeleteACLByIdRequest)
            throws ApiException {
        Middleware_DeleteACLByIdResponse resp = new Middleware_DeleteACLByIdResponse();
        MiddlewareAcl middlewareAcl = middlewareACLDao.findById(middleware_DeleteACLByIdRequest.getMiddlewareACLId());
        if (middlewareAcl == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        middlewareACLDao.delete(middlewareAcl);

        AdminLogBuilder adminLogBuilder = middleware_DeleteACLByIdRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.middleware_delete)
                .admin(middleware_DeleteACLByIdRequest.tokenWrapper().getAdmin())
                .operateDescription(middlewareAcl.getMiddleware().getName(), middlewareAcl.getIp());

        return resp;
    }

}
