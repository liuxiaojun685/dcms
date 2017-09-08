package cn.bestsec.dcms.platform.impl.securePolicy;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_QueryFileAccessPolicyListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.FileAccessPolicyInfo;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryFileAccessPolicyListRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryFileAccessPolicyListResponse;
import cn.bestsec.dcms.platform.dao.FileLevelAccessPolicyDao;
import cn.bestsec.dcms.platform.entity.FileLevelAccessPolicy;

/**
 * 查询文件密级访问控制表
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月30日 下午2:31:05
 */
@Component
public class SecurePolicyQueryFileAccessPolicyList implements SecurePolicy_QueryFileAccessPolicyListApi {

    @Autowired
    private FileLevelAccessPolicyDao fileLevelAccessPolicyDao;

    @Override
    @Transactional
    public SecurePolicy_QueryFileAccessPolicyListResponse execute(
            SecurePolicy_QueryFileAccessPolicyListRequest securePolicy_QueryFileAccessPolicyListRequest)
            throws ApiException {
        SecurePolicy_QueryFileAccessPolicyListResponse resp = new SecurePolicy_QueryFileAccessPolicyListResponse();
        // 用于封装响应数据
        List<FileAccessPolicyInfo> fileAccessPolicyList = new ArrayList<FileAccessPolicyInfo>();
        List<FileLevelAccessPolicy> dataList = fileLevelAccessPolicyDao.findAll();
        for (FileLevelAccessPolicy fileLevelAccessPolicy : dataList) {
            FileAccessPolicyInfo fileAccessPolicyInfo = new FileAccessPolicyInfo();
            fileAccessPolicyInfo.setEnable(fileLevelAccessPolicy.getEnable());
            fileAccessPolicyInfo.setFileLevel(fileLevelAccessPolicy.getFileLevel());
            fileAccessPolicyInfo.setUserLevel(fileLevelAccessPolicy.getUserLevel());
            fileAccessPolicyList.add(fileAccessPolicyInfo);
        }
        resp.setFileAccessPolicyList(fileAccessPolicyList);

        return resp;
    }

}
