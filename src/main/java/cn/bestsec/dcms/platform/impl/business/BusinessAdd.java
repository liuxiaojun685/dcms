package cn.bestsec.dcms.platform.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Business_AddApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Business_AddRequest;
import cn.bestsec.dcms.platform.api.model.Business_AddResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.BusinessDao;
import cn.bestsec.dcms.platform.entity.Business;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月20日 上午10:17:22
 */
@Component
public class BusinessAdd implements Business_AddApi {
    @Autowired
    private BusinessDao businessDao;

    @Override
    public Business_AddResponse execute(Business_AddRequest request) throws ApiException {
        Integer parentId = request.getParentId();
        if (parentId == null || parentId < 0) {
            parentId = 0;
        }
        Business business = new Business(parentId, request.getName());
        businessDao.save(business);

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.business_add)
                .admin(request.tokenWrapper().getAdmin()).operateDescription(request.getName());
        return new Business_AddResponse(business.getId());
    }

}
