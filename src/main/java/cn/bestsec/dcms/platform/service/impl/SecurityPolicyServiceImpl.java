package cn.bestsec.dcms.platform.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.MarkKeyInfo;
import cn.bestsec.dcms.platform.api.model.PermissionInfo;
import cn.bestsec.dcms.platform.consts.CommonConsts;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.consts.RoleConsts;
import cn.bestsec.dcms.platform.consts.WorkFlowConsts;
import cn.bestsec.dcms.platform.dao.ClientLevelAccessPolicyDao;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.dao.FileAccessScopeDao;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.FileDrmDao;
import cn.bestsec.dcms.platform.dao.FileLevelAccessPolicyDao;
import cn.bestsec.dcms.platform.dao.FileLevelDecidePolicyDao;
import cn.bestsec.dcms.platform.dao.FileLevelDecideUnitDao;
import cn.bestsec.dcms.platform.dao.LicenseDao;
import cn.bestsec.dcms.platform.dao.MarkKeyDao;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.ClientLevelAccessPolicy;
import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.FileAccessScope;
import cn.bestsec.dcms.platform.entity.FileDrm;
import cn.bestsec.dcms.platform.entity.FileLevelAccessPolicy;
import cn.bestsec.dcms.platform.entity.FileLevelDecidePolicy;
import cn.bestsec.dcms.platform.entity.FileLevelDecideUnit;
import cn.bestsec.dcms.platform.entity.Group;
import cn.bestsec.dcms.platform.entity.License;
import cn.bestsec.dcms.platform.entity.MarkKey;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.UserToDepartment;
import cn.bestsec.dcms.platform.entity.UserToGroup;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.LicenseConfiguration;
import cn.bestsec.dcms.platform.utils.ServerEnv;
import cn.bestsec.dcms.platform.utils.SystemProperties;

@Service
@Transactional
public class SecurityPolicyServiceImpl implements SecurityPolicyService {

    @Autowired
    private ClientLevelAccessPolicyDao clientLevelAccessPolicyDao;
    @Autowired
    private FileLevelAccessPolicyDao fileLevelAccessPolicyDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private FileDrmDao fileDrmDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private FileLevelDecidePolicyDao fileLevelDecidePolicyDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private LicenseDao licenseDao;
    @Autowired
    private FileAccessScopeDao fileAccessScopeDao;
    @Autowired
    private FileLevelDecideUnitDao fileLevelDecideUnitDao;
    @Autowired
    private MarkKeyDao markKeyDao;
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    public boolean isValidLicense() {
        List<License> licenses = licenseDao.findAll();
        if (licenses.isEmpty()) {
            return false;
        }
        String licenseText = licenses.get(0).getLicense();
        try {
            if (licenseText != null && !licenseText.isEmpty()) {
                LicenseConfiguration.License lic = LicenseConfiguration.getInstance().decodeLicense(licenseText);
                if (lic.features != null && lic.features.equals(ServerEnv.getHardwareFeature())
                        && lic.invalidTime != null && lic.invalidTime.getTime() > System.currentTimeMillis()) {
                    return true;
                }
            }
        } catch (Throwable e) {
        }
        return false;
    }

    @Override
    public boolean canAccessClient(int userLevel, int clientLevel) {
        ClientLevelAccessPolicy policy = clientLevelAccessPolicyDao.findByUserLevelAndClientLevel(userLevel,
                clientLevel);
        if (policy == null) {
            return false;
        }
        return (policy.getEnable() == CommonConsts.Bool.yes.getInt()) ? CommonConsts.Bool.yes.getBoolean()
                : CommonConsts.Bool.no.getBoolean();
    }

    @Override
    public boolean canAccessFile(int userLevel, int fileLevel) {
        FileLevelAccessPolicy policy = fileLevelAccessPolicyDao.findByFileLevelAndUserLevel(fileLevel, userLevel);
        if (policy == null) {
            return false;
        }
        return (policy.getEnable() == CommonConsts.Bool.yes.getInt()) ? CommonConsts.Bool.yes.getBoolean()
                : CommonConsts.Bool.no.getBoolean();
    }

    private boolean canAccessFile(String fid, String uid) {
        File file = fileDao.findByPkFid(fid);
        User user = userDao.findByPkUid(uid);
        if (file != null && user != null) {
            return canAccessFile(user.getUserLevel(), file.getFileLevel());
        }
        return false;
    }

    @Override
    @Transactional
    public Set<String> getPrivateVarIds(String uid) {
        Set<String> ids = new HashSet<String>();
        User user = userDao.findByPkUid(uid);
        List<UserToDepartment> userToDepartments = user.getUserToDepartments();
        if (userToDepartments != null) {
            for (UserToDepartment userToDepartment : userToDepartments) {
                Department department = userToDepartment.getDepartment();
                String curDid = department.getPkDid();
                ids.add(curDid);
                String parentDid;
                while ((parentDid = departmentDao.findByPkDid(curDid).getFkParentId()) != null
                        && !parentDid.isEmpty()) {
                    ids.add(parentDid);
                    curDid = parentDid;
                }
            }
        }
        List<UserToGroup> userToGroups = user.getUserToGroups();
        if (userToGroups != null) {
            for (UserToGroup userToGroup : userToGroups) {
                Group group = userToGroup.getGroup();
                ids.add(group.getPkGid());
                String curDid = group.getDepartment().getPkDid();
                ids.add(curDid);
                String parentDid;
                while ((parentDid = departmentDao.findByPkDid(curDid).getFkParentId()) != null
                        && !parentDid.isEmpty()) {
                    ids.add(parentDid);
                    curDid = parentDid;
                }
            }
        }
        ids.add(uid);
        return ids;
    }

    @Override
    @Transactional
    public PermissionInfo getPrivatePermissionInfo(String fid, String uid) throws ApiException {
        // 获取操作的文件
        File file = fileDao.findByPkFid(fid);
        if (file == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        if (!canAccessFile(fid, uid)) {
            throw new ApiException(ErrorCode.permissionDenied);
        }

        Set<String> varIds = getPrivateVarIds(uid);
        // 根据文件id和varid查询用户对文件的权限
        Specification<FileDrm> spec = new Specification<FileDrm>() {

            @Override
            public Predicate toPredicate(Root<FileDrm> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate fId = cb.equal(root.get("fkFid").as(String.class), file.getPkFid());
                In<String> in = cb.in(root.get("fkVarId").as(String.class));
                for (String fkVarId : varIds) {
                    in.value(fkVarId);
                }
                return cb.and(fId, in);
            }
        };
        List<FileDrm> fileDrmAll = fileDrmDao.findAll(spec);
        // 设置返回的权限信息默认值为否
        PermissionInfo permission = new PermissionInfo();
        permission.setContentCopy(0);
        permission.setContentModify(0);
        permission.setContentPrint(0);
        permission.setContentPrintHideWater(0);
        permission.setContentRead(0);
        permission.setContentScreenShot(0);
        permission.setFileAuthorization(0);
        permission.setFileCopy(0);
        permission.setFileDecrypt(0);
        permission.setFileDispatch(0);
        permission.setFileLevelChange(0);
        permission.setFileLevelDecide(0);
        permission.setFileSaveCopy(0);
        permission.setFileUnbunding(0);
        if (fileDrmAll != null && fileDrmAll.size() > 0) {
            for (FileDrm fileDrm : fileDrmAll) {
                permission.setContentCopy(fileDrm.getContentCopy() | permission.getContentCopy());
                permission.setContentModify(fileDrm.getContentModify() | permission.getContentModify());
                permission.setContentPrint(fileDrm.getContentPrint() | permission.getContentPrint());
                permission.setContentPrintHideWater(
                        fileDrm.getContentPrintHideWater() | permission.getContentPrintHideWater());
                permission.setContentRead(fileDrm.getContentRead() | permission.getContentRead());
                permission.setContentScreenShot(fileDrm.getContentScreenShot() | permission.getContentScreenShot());
                permission.setFileAuthorization(fileDrm.getFileAuthorization() | permission.getFileAuthorization());
                permission.setFileCopy(fileDrm.getFileCopy() | permission.getFileCopy());
                permission.setFileDecrypt(fileDrm.getFileDecrypt() | permission.getFileDecrypt());
                permission.setFileDispatch(fileDrm.getFileDispatch() | permission.getFileDispatch());
                permission.setFileLevelChange(fileDrm.getFileLevelChange() | permission.getFileLevelChange());
                permission.setFileLevelDecide(fileDrm.getFileLevelDecide() | permission.getFileLevelDecide());
                permission.setFileSaveCopy(fileDrm.getFileSaveCopy() | permission.getFileSaveCopy());
                permission.setFileUnbunding(fileDrm.getFileUnbunding() | permission.getFileUnbunding());
            }
        }

        // 判断是否作为分发使用人
        FileLevelDecideUnit majorUnit = fileLevelDecideUnitDao
                .findByMajor(FileConsts.ToFileLevelDecideUnitType.main.getCode());
        List<FileAccessScope> scope = fileAccessScopeDao.findByFkFidAndUnitNoAndFkUid(fid, majorUnit.getUnitNo(), uid);
        // FileAccessScope中有uid或者 文件没选范围也没有范围描述，表示用户作为分发使用人
        if (!scope.isEmpty() || (scope.isEmpty()
                && (!SystemProperties.getInstance().isScopeDefaultAccess() && file.getFileDispatchExpect() == null
                        || file.getFileDispatchExpect().isEmpty()))) {
            permission.setContentCopy(file.getContentCopy() | permission.getContentCopy());
            permission.setContentModify(file.getContentModify() | permission.getContentModify());
            permission.setContentPrint(file.getContentPrint() | permission.getContentPrint());
            permission
                    .setContentPrintHideWater(file.getContentPrintHideWater() | permission.getContentPrintHideWater());
            permission.setContentRead(file.getContentRead() | permission.getContentRead());
            permission.setContentScreenShot(file.getContentScreenShot() | permission.getContentScreenShot());
            permission.setFileCopy(file.getFileCopy() | permission.getFileCopy());
            permission.setFileSaveCopy(file.getFileSaveCopy() | permission.getFileSaveCopy());
        }

        // 叠加用户所属角色(起草人，定密/签发人)的默认权限。
        List<Integer> roleTypes = roleDao.findUserAllRoleTypeByFileLevel(1 << file.getFileLevel(), uid);
        List<Integer> roleTypes2 = roleDao.findUserAllRoleTypeAgentByFileLevel(1 << file.getFileLevel(), uid,
                System.currentTimeMillis());
        roleTypes.addAll(roleTypes2);
        User creater = file.getUserByFkFileCreateUid();
        if (creater != null && creater.getPkUid().equals(uid)) {
            roleTypes.add(RoleConsts.Type.draftsman.getCode());
        }

        if (!roleTypes.isEmpty()) {
            Specification<FileLevelDecidePolicy> speci = new Specification<FileLevelDecidePolicy>() {

                @Override
                public Predicate toPredicate(Root<FileLevelDecidePolicy> root, CriteriaQuery<?> query,
                        CriteriaBuilder cb) {
                    Predicate fileState = cb.equal(root.get("fileState").as(Integer.class), file.getFileState());
                    Predicate fileLevel = cb.equal(root.get("fileLevel").as(Integer.class), file.getFileLevel());
                    Predicate predicate = cb.and(fileState, fileLevel);
                    In<Integer> in = cb.in(root.get("roleType").as(Integer.class));
                    for (Integer roleType : roleTypes) {
                        in.value(roleType);
                    }
                    return cb.and(predicate, in);
                }
            };

            List<FileLevelDecidePolicy> decidePolicyAll = fileLevelDecidePolicyDao.findAll(speci);
            if (decidePolicyAll != null && decidePolicyAll.size() > 0) {
                for (FileLevelDecidePolicy policy : decidePolicyAll) {
                    permission.setContentCopy(policy.getContentCopy() | permission.getContentCopy());
                    permission.setContentModify(policy.getContentModify() | permission.getContentModify());
                    permission.setContentPrint(policy.getContentPrint() | permission.getContentPrint());
                    permission.setContentPrintHideWater(
                            policy.getContentPrintHideWater() | permission.getContentPrintHideWater());
                    permission.setContentRead(policy.getContentRead() | permission.getContentRead());
                    permission.setContentScreenShot(policy.getContentScreenShot() | permission.getContentScreenShot());
                    permission.setFileAuthorization(policy.getFileAuthorization() | permission.getFileAuthorization());
                    permission.setFileCopy(policy.getFileCopy() | permission.getFileCopy());
                    permission.setFileDecrypt(policy.getFileDecrypt() | permission.getFileDecrypt());
                    permission.setFileDispatch(policy.getFileDispatch() | permission.getFileDispatch());
                    permission.setFileLevelChange(policy.getFileLevelChange() | permission.getFileLevelChange());
                    permission.setFileLevelDecide(policy.getFileLevelDecide() | permission.getFileLevelDecide());
                    permission.setFileSaveCopy(policy.getFileSaveCopy() | permission.getFileSaveCopy());
                    permission.setFileUnbunding(policy.getFileUnbunding() | permission.getFileUnbunding());
                }
            }
        }
        return permission;
    }

    @Override
    @Transactional
    public PermissionInfo getPrivatePermissionInfo(boolean fileIsMyCreate, int fileState, int fileLevel, String uid)
            throws ApiException {
        User user = userDao.findByPkUid(uid);
        if (user == null) {
            throw new ApiException(ErrorCode.userNotExist);
        }

        if (!canAccessFile(user.getUserLevel(), fileLevel)) {
            throw new ApiException(ErrorCode.permissionDenied);
        }

        // 设置返回的权限信息默认值为否
        PermissionInfo permission = new PermissionInfo();
        permission.setContentCopy(0);
        permission.setContentModify(0);
        permission.setContentPrint(0);
        permission.setContentPrintHideWater(0);
        permission.setContentRead(0);
        permission.setContentScreenShot(0);
        permission.setFileAuthorization(0);
        permission.setFileCopy(0);
        permission.setFileDecrypt(0);
        permission.setFileDispatch(0);
        permission.setFileLevelChange(0);
        permission.setFileLevelDecide(0);
        permission.setFileSaveCopy(0);
        permission.setFileUnbunding(0);
        // 获取用户的角色，用户对文件的角色可以是多个
        // 用户的角色类型,作为用户和代理人
        List<Integer> roleTypes = roleDao.findByUserByFkUid(user.getPkUid());
        List<Integer> roleTypes2 = roleDao.findByUserByFkAgentUid(System.currentTimeMillis(), user.getPkUid());
        roleTypes.addAll(roleTypes2);
        // 用于查询的角色类型
        if (fileIsMyCreate) {
            roleTypes.add(RoleConsts.Type.draftsman.getCode());
        }

        if (!roleTypes.isEmpty()) {
            Specification<FileLevelDecidePolicy> spec = new Specification<FileLevelDecidePolicy>() {

                @Override
                public Predicate toPredicate(Root<FileLevelDecidePolicy> root, CriteriaQuery<?> query,
                        CriteriaBuilder cb) {
                    Predicate pState = cb.equal(root.get("fileState").as(Integer.class), fileState);
                    Predicate pLevel = cb.equal(root.get("fileLevel").as(Integer.class), fileLevel);
                    Predicate predicate = cb.and(pState, pLevel);
                    In<Integer> in = cb.in(root.get("roleType").as(Integer.class));
                    for (Integer roleType : roleTypes) {
                        in.value(roleType);
                    }

                    return cb.and(predicate, in);
                }
            };

            List<FileLevelDecidePolicy> decidePolicyAll = fileLevelDecidePolicyDao.findAll(spec);
            if (decidePolicyAll != null && decidePolicyAll.size() > 0) {
                for (FileLevelDecidePolicy policy : decidePolicyAll) {
                    permission.setContentCopy(policy.getContentCopy() | permission.getContentCopy());
                    permission.setContentModify(policy.getContentModify() | permission.getContentModify());
                    permission.setContentPrint(policy.getContentPrint() | permission.getContentPrint());
                    permission.setContentPrintHideWater(
                            policy.getContentPrintHideWater() | permission.getContentPrintHideWater());
                    permission.setContentRead(policy.getContentRead() | permission.getContentRead());
                    permission.setContentScreenShot(policy.getContentScreenShot() | permission.getContentScreenShot());
                    permission.setFileAuthorization(policy.getFileAuthorization() | permission.getFileAuthorization());
                    permission.setFileCopy(policy.getFileCopy() | permission.getFileCopy());
                    permission.setFileDecrypt(policy.getFileDecrypt() | permission.getFileDecrypt());
                    permission.setFileDispatch(policy.getFileDispatch() | permission.getFileDispatch());
                    permission.setFileLevelChange(policy.getFileLevelChange() | permission.getFileLevelChange());
                    permission.setFileLevelDecide(policy.getFileLevelDecide() | permission.getFileLevelDecide());
                    permission.setFileSaveCopy(policy.getFileSaveCopy() | permission.getFileSaveCopy());
                    permission.setFileUnbunding(policy.getFileUnbunding() | permission.getFileUnbunding());
                }
            }
        }
        return permission;
    }

    @Override
    public boolean isPrivileged(String uid, int roleType) {
        List<Role> roles = roleDao.findUserAllRoleByRoleType(roleType, uid);
        List<Role> roles2 = roleDao.findUserAllRoleByRoleTypeAgent(roleType, uid, System.currentTimeMillis());
        roles.addAll(roles2);
        return roles != null && roles.size() > 0;
    }

    @Override
    public List<Integer> getSupportFileLevel(int userLevel) {
        return fileLevelAccessPolicyDao.findSupportFileLevel(userLevel);
    }

    @Override
    public boolean canCreateWorkflow(String fid, String uid, int workflowType, int fileState, int fileLevel)
            throws ApiException {
        PermissionInfo permission = null;
        File file = fileDao.findByPkFid(fid);
        if (file != null && file.getOutOfDate() != 0) {
            throw new ApiException(ErrorCode.fileIsOutOfDate);
        }
        if (file == null) {
            permission = getPrivatePermissionInfo(true, fileState, fileLevel, uid);
        } else {
            permission = getPrivatePermissionInfo(fid, uid);
        }
        if (permission == null) {
            return false;
        }
        if (workflowType == WorkFlowConsts.Type.makeSecret.getCode()) {
            return (permission.getFileLevelDecide() & 2) != 0;
        } else if (workflowType == WorkFlowConsts.Type.changeSecret.getCode()) {
            return (permission.getFileLevelChange() & 2) != 0;
        } else if (workflowType == WorkFlowConsts.Type.dispatch.getCode()) {
            return (permission.getFileDispatch() & 2) != 0;
        } else if (workflowType == WorkFlowConsts.Type.unSecret.getCode()) {
            return (permission.getFileDecrypt() & 2) != 0;
        } else if (workflowType == WorkFlowConsts.Type.restore.getCode()) {
            return (permission.getFileUnbunding() & 2) != 0;
        }
        return false;
    }

    @Override
    public boolean canUploadFile(String fid, String uid, int uploadType, int fileState, int fileLevel)
            throws ApiException {
        PermissionInfo permission = null;
        if (uploadType == FileConsts.UploadType.pre.getCode()) {
            if (fileState == 0 || fileState == 1) {
                User user = userDao.findByPkUid(uid);
                if (user == null) {
                    throw new ApiException(ErrorCode.fileNoPermission);
                }
                if (!canAccessFile(user.getUserLevel(), fileLevel)) {
                    throw new ApiException(ErrorCode.permissionDenied);
                }
                return true;
            }
            return false;
        }
        File file = fileDao.findByPkFid(fid);
        if (file != null && file.getOutOfDate() != 0) {
            throw new ApiException(ErrorCode.fileIsOutOfDate);
        }
        if (file == null) {
            permission = getPrivatePermissionInfo(false, fileState, fileLevel, uid);
        } else {
            permission = getPrivatePermissionInfo(fid, uid);
        }
        if (permission == null) {
            return false;
        }
        if (uploadType == FileConsts.UploadType.makeSecret.getCode()) {
            return (permission.getFileLevelDecide() & 1) != 0;
        } else if (uploadType == FileConsts.UploadType.changeSecret.getCode()) {
            return (permission.getFileLevelChange() & 1) != 0;
        } else if (uploadType == FileConsts.UploadType.dispatch.getCode()) {
            return (permission.getFileDispatch() & 1) != 0;
        } else if (uploadType == FileConsts.UploadType.unSecret.getCode()) {
            return (permission.getFileDecrypt() & 1) != 0;
        } else if (uploadType == FileConsts.UploadType.update.getCode()) {
            return true;
        } else if (uploadType == FileConsts.UploadType.restore.getCode()) {
            return (permission.getFileUnbunding() & 1) != 0;
        }
        return false;
    }

    @Override
    public boolean canClassified(String fid, int fileState, int fileLevel, int opType) throws ApiException {
        if (opType == FileConsts.UploadType.pre.getCode()) {
            if (fileState == 0 || fileState == 1) {
                return true;
            }
            return false;
        }
        File file = fileDao.findByPkFid(fid);
        if (file != null && file.getOutOfDate() != 0) {
            throw new ApiException(ErrorCode.fileIsOutOfDate);
        }
        PermissionInfo permission = new PermissionInfo();
        permission.setFileAuthorization(0);
        permission.setFileDecrypt(0);
        permission.setFileDispatch(0);
        permission.setFileLevelChange(0);
        permission.setFileLevelDecide(0);
        permission.setFileUnbunding(0);
        List<FileLevelDecidePolicy> decidePolicys = fileLevelDecidePolicyDao.findByFileStateAndFileLevel(fileState,
                fileLevel);
        if (decidePolicys != null && decidePolicys.size() > 0) {
            for (FileLevelDecidePolicy policy : decidePolicys) {
                permission.setFileAuthorization(policy.getFileAuthorization() | permission.getFileAuthorization());
                permission.setFileDecrypt(policy.getFileDecrypt() | permission.getFileDecrypt());
                permission.setFileDispatch(policy.getFileDispatch() | permission.getFileDispatch());
                permission.setFileLevelChange(policy.getFileLevelChange() | permission.getFileLevelChange());
                permission.setFileLevelDecide(policy.getFileLevelDecide() | permission.getFileLevelDecide());
                permission.setFileUnbunding(policy.getFileUnbunding() | permission.getFileUnbunding());
            }
        }
        // 操作类型 0去除密标 1预定密 2正式定密 3文件签发 4密级变更 5文件解密
        if (opType == 2) {
            return (permission.getFileLevelDecide() & 1) != 0;
        } else if (opType == 4) {
            return (permission.getFileLevelChange() & 1) != 0;
        } else if (opType == 3) {
            return (permission.getFileDispatch() & 1) != 0;
        } else if (opType == 5) {
            return (permission.getFileDecrypt() & 1) != 0;
        } else if (opType == 0) {
            return true;
        } else if (opType == 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<MarkKeyInfo> getMarkKeyList() {
        List<MarkKey> keys = markKeyDao.findEnableKey();
        List<MarkKeyInfo> resp = new ArrayList<>();
        for (MarkKey key : keys) {
            resp.add(new MarkKeyInfo(key.getPubKey(), key.getPriKey(), key.getMarkVersion()));
        }
        return resp;
    }

    @Override
    public MarkKeyInfo getCurrentMarkKey() {
        MarkKey key = markKeyDao.findEnableKeyByVersion(1);
        return new MarkKeyInfo(key.getPubKey(), key.getPriKey(), key.getMarkVersion());
    }

    @Override
    public boolean isValidLoginPasswd(String passwd, String account) {
        if (passwd == null) {
            return false;
        }

        SystemConfig config;
        try {
            config = systemConfigService.getSystemConfig();
        } catch (ApiException e) {
            return false;
        }
        // 最短
        if (config.getLocalAuthPasswdMin() > passwd.length()) {
            return false;
        }
        // 最长
        if (config.getLocalAuthPasswdMax() < passwd.length()) {
            return false;
        }
        // 包含数字
        if (config.getLocalAuthPasswdContainsNumber() == CommonConsts.Bool.yes.getInt()) {
            if (!passwd.matches(".*\\d+.*")) {
                return false;
            }
        }
        // 包含字母大小限
        if (config.getLocalAuthPasswdContainsLetter() == CommonConsts.Bool.yes.getInt()) {
            if (!passwd.matches(".*[a-z]+.*") || !passwd.matches(".*[A-Z]+.*")) {
                return false;
            }
        }
        // 包含特殊字符
        if (config.getLocalAuthPasswdContainsSpecial() == CommonConsts.Bool.yes.getInt()) {
            if (!passwd.matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*")) {
                return false;
            }
        }
        // 不能包含空格
        // if (passwd.matches(".*[^\\S]+.*")) {
        // return false;
        // }

        // 不可与账户相同
        if (account != null && account.equals(passwd)) {
            return false;
        }

        // 不可为连续相同字符
        if (passwd.matches("(.)\1{" + (passwd.length() - 1) + "}")) {
            return false;
        }

        return true;
    }

    // 得到所有子部门
    @Override
    public void childDepartment(String did, List<String> scopeAll) {
        List<Department> findByFkParentId = departmentDao.findByFkParentId(did);
        for (Department department : findByFkParentId) {
            childDepartment(department.getPkDid(), scopeAll);
        }
        scopeAll.add(did);

    }

}
