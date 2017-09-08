package cn.bestsec.dcms.platform.impl.securePolicy;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_QueryPermissionPolicyListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.PermissionInfo;
import cn.bestsec.dcms.platform.api.model.PermissionPolicyInfo;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryPermissionPolicyListRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryPermissionPolicyListResponse;
import cn.bestsec.dcms.platform.dao.FileLevelDecidePolicyDao;
import cn.bestsec.dcms.platform.entity.FileLevelDecidePolicy;

/**
 * 查询定密策略
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月30日 下午12:04:41
 */
@Component
public class SecurePolicyQueryPermissionPolicyList implements SecurePolicy_QueryPermissionPolicyListApi {

    @Autowired
    private FileLevelDecidePolicyDao fileLevelDecidePolicyDao;

    @Override
    @Transactional
    public SecurePolicy_QueryPermissionPolicyListResponse execute(
            SecurePolicy_QueryPermissionPolicyListRequest securePolicy_QueryPermissionPolicyListRequest)
            throws ApiException {
        SecurePolicy_QueryPermissionPolicyListResponse resp = new SecurePolicy_QueryPermissionPolicyListResponse();
        Integer fileState = securePolicy_QueryPermissionPolicyListRequest.getFileState();
        Integer fileLevel = securePolicy_QueryPermissionPolicyListRequest.getFileLevel();
        Integer roleType = securePolicy_QueryPermissionPolicyListRequest.getRoleType();

        // 传入参数,动态查询
        Specification<FileLevelDecidePolicy> spec = new Specification<FileLevelDecidePolicy>() {
            @Override
            public Predicate toPredicate(Root<FileLevelDecidePolicy> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();

                if (fileState != null) {
                    list.add(cb.equal(root.get("fileState").as(Integer.class), fileState));
                }

                if (fileLevel != null) {
                    list.add(cb.equal(root.get("fileLevel").as(Integer.class), fileLevel));
                }

                if (roleType != null) {
                    list.add(cb.equal(root.get("roleType").as(Integer.class), roleType));
                }
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        // 得到查询的参数

        List<FileLevelDecidePolicy> findAll = fileLevelDecidePolicyDao.findAll(spec);
        List<PermissionPolicyInfo> policyList = new ArrayList<PermissionPolicyInfo>();
        PermissionPolicyInfo permissionPolicyInfo = null;
        for (FileLevelDecidePolicy fileLevelDecidePolicy : findAll) {
            permissionPolicyInfo = new PermissionPolicyInfo();
            permissionPolicyInfo.setFileLevel(fileLevelDecidePolicy.getFileLevel());
            permissionPolicyInfo.setFileState(fileLevelDecidePolicy.getFileState());
            permissionPolicyInfo.setRoleType(fileLevelDecidePolicy.getRoleType());

            // 定密策略信息
            PermissionInfo permissionInfo = new PermissionInfo();
            permissionInfo.setContentCopy(fileLevelDecidePolicy.getContentCopy());
            permissionInfo.setFileAuthorization(fileLevelDecidePolicy.getFileAuthorization());
            permissionInfo.setFileCopy(fileLevelDecidePolicy.getFileCopy());
            permissionInfo.setFileDispatch(fileLevelDecidePolicy.getFileDispatch());
            permissionInfo.setFileDecrypt(fileLevelDecidePolicy.getFileDecrypt());
            permissionInfo.setFileLevelChange(fileLevelDecidePolicy.getFileLevelChange());
            permissionInfo.setFileLevelDecide(fileLevelDecidePolicy.getFileLevelDecide());
            permissionInfo.setFileSaveCopy(fileLevelDecidePolicy.getFileSaveCopy());
            permissionInfo.setFileUnbunding(fileLevelDecidePolicy.getFileUnbunding());
            permissionInfo.setContentModify(fileLevelDecidePolicy.getContentModify());
            permissionInfo.setContentPrint(fileLevelDecidePolicy.getContentPrint());
            permissionInfo.setContentPrintHideWater(fileLevelDecidePolicy.getContentPrintHideWater());
            permissionInfo.setContentRead(fileLevelDecidePolicy.getContentRead());
            permissionInfo.setContentScreenShot(fileLevelDecidePolicy.getContentScreenShot());
            permissionPolicyInfo.setPermission(permissionInfo);
            policyList.add(permissionPolicyInfo);
        }
        resp.setPolicyList(policyList);
        return resp;
    }

}
