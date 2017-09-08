package cn.bestsec.dcms.platform.impl.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Business_DeleteApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Business_DeleteRequest;
import cn.bestsec.dcms.platform.api.model.Business_DeleteResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.BusinessDao;
import cn.bestsec.dcms.platform.entity.Business;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月20日 上午10:17:22
 */
@Component
public class BusinessDelete implements Business_DeleteApi {
    @Autowired
    private BusinessDao businessDao;

    @Override
    public Business_DeleteResponse execute(Business_DeleteRequest request) throws ApiException {
        Business business = businessDao.findById(request.getBId());
        if (business == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        businessDao.delete(business);

        AdminLogBuilder adminLogBuilder = request.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.business_delete)
                .admin(request.tokenWrapper().getAdmin()).operateDescription(business.getName());
        return new Business_DeleteResponse();
    }

}
