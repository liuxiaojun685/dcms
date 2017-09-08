package cn.bestsec.dcms.platform.impl.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Business_QueryTreeApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.BusinessNode;
import cn.bestsec.dcms.platform.api.model.Business_QueryTreeRequest;
import cn.bestsec.dcms.platform.api.model.Business_QueryTreeResponse;
import cn.bestsec.dcms.platform.dao.BusinessDao;
import cn.bestsec.dcms.platform.entity.Business;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月20日 上午10:17:22
 */
@Component
public class BusinessQueryTree implements Business_QueryTreeApi {
    @Autowired
    private BusinessDao businessDao;

    @Override
    public Business_QueryTreeResponse execute(Business_QueryTreeRequest request) throws ApiException {
        return new Business_QueryTreeResponse(installChildNode(0));
    }

    private List<BusinessNode> installChildNode(Integer parentId) {
        List<BusinessNode> nodes = new ArrayList<>();
        List<Business> businessList = businessDao.findByFkParentId(parentId);
        for (Business business : businessList) {
            List<BusinessNode> childs = installChildNode(business.getId());
            nodes.add(new BusinessNode(business.getId(), business.getName(), childs));
        }
        return nodes;
    }

}
