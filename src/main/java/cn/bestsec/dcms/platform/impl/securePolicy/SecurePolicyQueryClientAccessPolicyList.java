package cn.bestsec.dcms.platform.impl.securePolicy;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_QueryClientAccessPolicyListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.ClientAccessPolicyInfo;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryClientAccessPolicyListRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryClientAccessPolicyListResponse;
import cn.bestsec.dcms.platform.dao.ClientLevelAccessPolicyDao;
import cn.bestsec.dcms.platform.entity.ClientLevelAccessPolicy;

/**
 * 查询终端密级访问控制表 安全管理员
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月30日 下午2:59:35
 */
@Component
public class SecurePolicyQueryClientAccessPolicyList implements SecurePolicy_QueryClientAccessPolicyListApi {

    @Autowired
    private ClientLevelAccessPolicyDao clientLevelAccessPolicyDao;

    @Override
    @Transactional
    public SecurePolicy_QueryClientAccessPolicyListResponse execute(
            SecurePolicy_QueryClientAccessPolicyListRequest securePolicy_QueryClientAccessPolicyListRequest)
            throws ApiException {
        SecurePolicy_QueryClientAccessPolicyListResponse resp = new SecurePolicy_QueryClientAccessPolicyListResponse();
        // 用于封装返回响应数据
        ClientAccessPolicyInfo clientAccessPolicyInfo = null;
        List<ClientAccessPolicyInfo> fileAccessPolicyList = new ArrayList<ClientAccessPolicyInfo>();
        List<ClientLevelAccessPolicy> dataList = clientLevelAccessPolicyDao.findAll();
        for (ClientLevelAccessPolicy clientLevelAccessPolicy : dataList) {
            clientAccessPolicyInfo = new ClientAccessPolicyInfo();
            clientAccessPolicyInfo.setClientLevel(clientLevelAccessPolicy.getClientLevel());
            clientAccessPolicyInfo.setEnable(clientLevelAccessPolicy.getEnable());
            clientAccessPolicyInfo.setUserLevel(clientLevelAccessPolicy.getUserLevel());
            fileAccessPolicyList.add(clientAccessPolicyInfo);
        }
        resp.setFileAccessPolicyList(fileAccessPolicyList);
        return resp;
    }

}
