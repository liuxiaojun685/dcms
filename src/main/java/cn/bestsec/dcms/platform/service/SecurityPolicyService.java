package cn.bestsec.dcms.platform.service;

import java.util.List;
import java.util.Set;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.MarkKeyInfo;
import cn.bestsec.dcms.platform.api.model.PermissionInfo;

public interface SecurityPolicyService {
    boolean isValidLicense();

    boolean canAccessClient(int userLevel, int clientLevel);

    boolean canAccessFile(int userLevel, int fileLevel);

    Set<String> getPrivateVarIds(String uid);

    PermissionInfo getPrivatePermissionInfo(String fid, String uid) throws ApiException;

    PermissionInfo getPrivatePermissionInfo(boolean fileIsMyCreate, int fileState, int fileLevel, String uid)
            throws ApiException;

    boolean isPrivileged(String uid, int roleType);

    List<Integer> getSupportFileLevel(int userLevel);

    boolean canCreateWorkflow(String fid, String uid, int workflowType, int fileState, int fileLevel)
            throws ApiException;

    boolean canUploadFile(String fid, String uid, int uploadType, int fileState, int fileLevel) throws ApiException;

    boolean canClassified(String fid, int fileState, int fileLevel, int classifiedType) throws ApiException;

    List<MarkKeyInfo> getMarkKeyList();

    MarkKeyInfo getCurrentMarkKey();

    boolean isValidLoginPasswd(String passwd, String account);

    void childDepartment(String did, List<String> scopes);
}
