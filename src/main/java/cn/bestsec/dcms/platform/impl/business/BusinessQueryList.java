package cn.bestsec.dcms.platform.impl.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Business_QueryListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.BusinessInfo;
import cn.bestsec.dcms.platform.api.model.Business_QueryListRequest;
import cn.bestsec.dcms.platform.api.model.Business_QueryListResponse;
import cn.bestsec.dcms.platform.dao.BusinessDao;
import cn.bestsec.dcms.platform.entity.Business;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月20日 上午10:17:22
 */
@Component
public class BusinessQueryList implements Business_QueryListApi {
    @Autowired
    private BusinessDao businessDao;

    @Override
    public Business_QueryListResponse execute(Business_QueryListRequest request) throws ApiException {
        Integer parentId = request.getParentId();
        if (parentId == null || parentId < 0) {
            parentId = 0;
        }
        List<Business> businessList = businessDao.findByFkParentId(parentId);
        List<BusinessInfo> bList = new ArrayList<>();
        for (Business business : businessList) {
            bList.add(new BusinessInfo(business.getId(), business.getName()));
        }
        return new Business_QueryListResponse(bList);
    }

}
