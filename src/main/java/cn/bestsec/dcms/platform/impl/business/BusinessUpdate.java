package cn.bestsec.dcms.platform.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Business_UpdateApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Business_UpdateRequest;
import cn.bestsec.dcms.platform.api.model.Business_UpdateResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.BusinessDao;
import cn.bestsec.dcms.platform.entity.Business;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月20日 上午10:17:22
 */
@Component
public class BusinessUpdate implements Business_UpdateApi {
    @Autowired
    private BusinessDao businessDao;

    @Override
    public Business_UpdateResponse execute(Business_UpdateRequest request) throws ApiException {
        Business business = businessDao.findById(request.getBId());
        if (!business.getName().equals(request.getName())) {
            String oldName = business.getName();
            business.setName(request.getName());
            businessDao.save(business);

            AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
            adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.business_update)
                    .admin(request.tokenWrapper().getAdmin()).operateDescription(oldName, request.getName());
        }
        return new Business_UpdateResponse(business.getId());
    }

}
