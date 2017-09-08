package cn.bestsec.dcms.platform.impl.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Client_LoadPolicyApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Client_LoadPolicyRequest;
import cn.bestsec.dcms.platform.api.model.Client_LoadPolicyResponse;
import cn.bestsec.dcms.platform.api.model.TrustedAppInfo;
import cn.bestsec.dcms.platform.consts.ClientConsts.PolicyType;
import cn.bestsec.dcms.platform.dao.ClientUninstallPasswdDao;
import cn.bestsec.dcms.platform.dao.FileAccessScopeDao;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.FileDrmDao;
import cn.bestsec.dcms.platform.dao.MarkKeyDao;
import cn.bestsec.dcms.platform.dao.TrustedApplicationDao;
import cn.bestsec.dcms.platform.dao.WorkflowTrackDao;
import cn.bestsec.dcms.platform.entity.ClientUninstallPasswd;
import cn.bestsec.dcms.platform.entity.FileAccessScope;
import cn.bestsec.dcms.platform.entity.FileDrm;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.entity.TrustedApplication;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.WorkflowTrack;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * 同步策略到本地（待定）
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月26日 下午2:49:57
 */
@Component
public class ClientLoadPolicy implements Client_LoadPolicyApi {

    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private ClientUninstallPasswdDao clientUninstallPasswdDao;
    @Autowired
    private TrustedApplicationDao trustedApplicationDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;
    @Autowired
    private FileDrmDao fileDrmDao;
    @Autowired
    private FileAccessScopeDao fileAccessScopeDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private WorkflowTrackDao workFlowTrackDao;
    @Autowired
    private MarkKeyDao markKeyDao;

    @Override
    @Transactional
    public Client_LoadPolicyResponse execute(Client_LoadPolicyRequest client_LoadPolicyRequest) throws ApiException {
        // 获取配置信息
        SystemConfig systemConfig = systemConfigService.getSystemConfig();

        PolicyType policyType = PolicyType.all;
        if (client_LoadPolicyRequest.getPolicyType() != null) {
            policyType = PolicyType.valueOf(client_LoadPolicyRequest.getPolicyType());
        }
        User user = client_LoadPolicyRequest.tokenWrapper().getUser();
        Client_LoadPolicyResponse resp = new Client_LoadPolicyResponse();

        // 可信应用
        if (policyType == PolicyType.trustApp || policyType == PolicyType.all) {
            List<TrustedApplication> trustApps = trustedApplicationDao.findAll();
            List<TrustedAppInfo> trustAppList = new ArrayList<>();
            for (TrustedApplication trustApp : trustApps) {
                trustAppList.add(new TrustedAppInfo(trustApp.getId(), trustApp.getProcessName(),
                        trustApp.getDescription(), trustApp.getExtensionName()));
            }
            resp.setTrustAppList(trustAppList);
        }

        // 可访问文件
        if (policyType == PolicyType.accessFile || policyType == PolicyType.all) {
            Set<String> fileIdList = new HashSet<String>();
            Set<String> varIds = securityPolicyService.getPrivateVarIds(user.getPkUid());
            Specification<FileDrm> varSpec = new Specification<FileDrm>() {

                @Override
                public Predicate toPredicate(Root<FileDrm> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    if (varIds != null && varIds.size() > 0) {

                        In<String> in = cb.in(root.get("fkVarId").as(String.class));
                        for (String varId : varIds) {
                            in.value(varId);
                        }
                        list.add(in);
                    }
                    Predicate[] p = new Predicate[list.size()];
                    return cb.and(list.toArray(p));
                }
            };
            if (varIds != null && varIds.size() > 0) {
                List<FileDrm> drms = fileDrmDao.findAll(varSpec);
                if (drms != null) {
                    for (FileDrm drm : drms) {
                        String fid = drm.getFkFid();
                        fileIdList.add(fid);
                    }
                }
            }

            List<FileAccessScope> scopes = fileAccessScopeDao.findByFkUid(user.getPkUid());
            for (FileAccessScope scope : scopes) {
                fileIdList.add(scope.getFkFid());
            }

            // 不限定知悉范围的
            if (SystemProperties.getInstance().isScopeDefaultAccess()) {
                List<String> fids = fileDao.findFileNoScopes();
                if (fids != null && !fids.isEmpty()) {
                    fileIdList.addAll(fids);
                }
            } else {
                List<String> fids = fileDao.findByScopes();
                if (fids != null && !fids.isEmpty()) {
                    fileIdList.addAll(fids);
                }
            }
            // 如果该用户为流程审批人，找到该用户所有的审批文件
            List<WorkflowTrack> tracks = workFlowTrackDao.findByUser(user);
            for (WorkflowTrack workflowTrack : tracks) {
                fileIdList.add(workflowTrack.getWorkflow().getFkSrcFid());
            }
            List<String> accessFileList = new ArrayList<>();
            if (!fileIdList.isEmpty()) {

                for (String f : fileIdList) {
                    accessFileList.add(f);
                }
            }
            resp.setAccessFileList(accessFileList);
        }

        // 标志密钥策略
        if (policyType == PolicyType.markKey || policyType == PolicyType.all) {
            resp.setMarkKeyList(securityPolicyService.getMarkKeyList());
            resp.setCurrentMarkKey(securityPolicyService.getCurrentMarkKey());
        }

        if (policyType == PolicyType.classExtension || policyType == PolicyType.all) {
            Set<String> classExtensions = new HashSet<>();
            List<TrustedApplication> trustApps = trustedApplicationDao.findAll();
            for (TrustedApplication app : trustApps) {
                String extStr = app.getExtensionName();
                String[] exts = extStr.split("\\|");
                for (String ext : exts) {
                    if (!ext.isEmpty()) {
                        classExtensions.add(ext.substring(ext.lastIndexOf(".") + 1));
                    }
                }
            }
            StringBuilder classExtension = new StringBuilder();
            Iterator<String> it = classExtensions.iterator();
            while (it.hasNext()) {
                classExtension.append(it.next());
                if (it.hasNext()) {
                    classExtension.append("|");
                }
            }
            resp.setClassExtension(classExtension.toString());
        }

        if (policyType == PolicyType.otherPolicy || policyType == PolicyType.all) {
            List<ClientUninstallPasswd> passwds = clientUninstallPasswdDao
                    .findAll(new Sort(Direction.DESC, "createTime"));

            resp.setSystemTopLevel(systemConfig.getSystemTopLevel());
            if (!passwds.isEmpty()) {
                resp.setUninstallPasswd(StringEncrypUtil.decrypt(passwds.get(0).getPasswd()));
            }
            resp.setPreclassifiedForce(systemConfig.getPreclassifiedForce());
        }

        if (policyType == PolicyType.levelAccessPolicy || policyType == PolicyType.all) {
            // 根据用户密级得到可访问的文件密级列表
            List<Integer> supportFileLevel = securityPolicyService.getSupportFileLevel(user.getUserLevel());
            resp.setFileLevelAccessPolicy(supportFileLevel);
        }

        if (policyType == PolicyType.classifiedWhiteList || policyType == PolicyType.all) {
            resp.setClassifiedWhiteList(systemConfig.getClassifiedWhiteList());
        }
        // UserActionLogBuilder logBuilder =
        // client_LoadPolicyRequest.createUserActionLogBuilder();
        // logBuilder.operateTime(System.currentTimeMillis()).operation(UserActionOp.client_loadPolicy).user(user)
        // .operateDescription();
        return resp;
    }

}
