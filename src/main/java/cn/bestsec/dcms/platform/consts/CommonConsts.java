package cn.bestsec.dcms.platform.consts;

import cn.bestsec.dcms.platform.api.support.TokenWrapper;
import cn.bestsec.dcms.platform.consts.TokenConsts.UserRole;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2016年12月26日 下午12:11:41
 */
public class CommonConsts {
    public enum Bool {
        /**
         * 是
         */
        yes(true, 1, "开启"),
        /**
         * 否
         */
        no(false, 0, "关闭");

        private boolean booleanValue;
        private int intValue;
        private String description;

        private Bool(boolean booleanValue, int intValue, String description) {
            this.booleanValue = booleanValue;
            this.intValue = intValue;
            this.description = description;
        }

        public boolean getBoolean() {
            return booleanValue;
        }

        public int getInt() {
            return intValue;
        }

        public String getDescription() {
            return description;
        }

        public static Bool parse(Integer code) {
            for (Bool c : values()) {
                if (c.getInt() == code) {
                    return c;
                }
            }
            return null;
        }
    }

    public enum Api {
        admin_login("admin", "login", UserRole.nocheck), // 权限:不检查
        admin_logout("admin", "logout", UserRole.anyadmin), // 权限:管理员
        admin_update("admin", "update", UserRole.anyadmin), // 权限:管理员
        admin_addAdmin("admin", "addAdmin", UserRole.secadmin), // 权限:安全管理员
        admin_queryDefaultAdmin("admin", "queryDefaultAdmin", UserRole.secadmin), // 权限:安全管理员
        admin_queryDeriveAdmin("admin", "queryDeriveAdmin", UserRole.secadmin), // 权限:安全管理员
        admin_queryScopeAdmin("admin", "queryScopeAdmin", UserRole.secadmin), // 权限:安全管理员
        admin_deleteAdmin("admin", "deleteAdmin", UserRole.secadmin), // 权限:安全管理员
        admin_updateAdmin("admin", "updateAdmin", UserRole.secadmin), // 权限:安全管理员
        admin_identifyingCode("admin", "identifyingCode", UserRole.nocheck), // 权限:不检查
        user_login("user", "login", UserRole.nocheck), // 权限:不检查
        user_logout("user", "logout", UserRole.user), // 权限:终端用户
        user_updatePasswd("user", "updatePasswd", UserRole.user), // 权限:终端用户
        user_resetPasswd("user", "resetPasswd", UserRole.sysadmin), // 权限:系统管理员
        user_add("user", "add", UserRole.sysadmin), // 权限:系统管理员
        user_updateBase("user", "updateBase", UserRole.sysadmin), // 权限:系统管理员
        user_deleteById("user", "deleteById", UserRole.sysadmin), // 权限:系统管理员
        user_deleteByIds("user", "deleteByIds", UserRole.sysadmin), // 权限:系统管理员
        user_queryList("user", "queryList", UserRole.anyadmin), // 权限:系统管理员和安全管理员
        user_import("user", "import", UserRole.sysadmin), // 权限:系统管理员
        user_syncUserList("user", "syncUserList", UserRole.sysadmin), // 权限:系统管理员
        user_updateLevel("user", "updateLevel", UserRole.secadmin), // 权限:安全管理员
        user_unlock("user", "unlock", UserRole.sysadmin), // 权限:系统管理员
        user_queryPasswdRule("user", "queryPasswdRule", UserRole.any), // 权限:任意
        user_refreshMessage("user", "refreshMessage", UserRole.user), // 权限:终端用户
        user_queryMessageHistory("user", "queryMessageHistory", UserRole.user), // 权限:终端用户
        user_deleteMessageHistory("user", "deleteMessageHistory", UserRole.user), // 权限:终端用户
        user_queryRoleType("user", "queryRoleType", UserRole.user), // 权限:终端用户
        client_queryList("client", "queryList", UserRole.sysadmin), // 权限:系统管理员
        client_queryNewestVersion("client", "queryNewestVersion", UserRole.user), // 权限:终端用户
        client_loadPolicy("client", "loadPolicy", UserRole.any), // 权限:任意
        client_add("client", "add", UserRole.sysadmin), // 权限:系统管理员
        client_deleteById("client", "deleteById", UserRole.sysadmin), // 权限:系统管理员
        client_updateById("client", "updateById", UserRole.sysadmin), // 权限:系统管理员
        client_queryClientPatchList("client", "queryClientPatchList", UserRole.sysadmin), // 权限:系统管理员
        client_addClientPatch("client", "addClientPatch", UserRole.sysadmin), // 权限:系统管理员
        client_deleteClientPatch("client", "deleteClientPatch", UserRole.sysadmin), // 权限:系统管理员
        client_queryClientUninstallPasswdList("client", "queryClientUninstallPasswdList", UserRole.sysadmin), // 权限:系统管理员
        client_changeClientUninstallPasswd("client", "changeClientUninstallPasswd", UserRole.sysadmin), // 权限:系统管理员
        client_deleteInBatches("client", "deleteInBatches", UserRole.sysadmin), // 权限:系统管理员
        client_downloadNewestVersion("client", "downloadNewestVersion", UserRole.user), // 权限:终端用户
        client_loadWatermarkConfig("client", "loadWatermarkConfig", UserRole.user), // 权限：终端用户
        client_showWatermarkByLevel("client", "showWatermarkByLevel", UserRole.user), // 权限：终端用户
        group_add("group", "add", UserRole.sysadmin), // 权限:系统管理员
        group_update("group", "update", UserRole.sysadmin), // 权限:系统管理员
        group_delete("group", "delete", UserRole.sysadmin), // 权限:系统管理员
        group_addUser("group", "addUser", UserRole.sysadmin), // 权限:系统管理员
        group_deleteUser("group", "deleteUser", UserRole.sysadmin), // 权限:系统管理员
        group_queryById("group", "queryById", UserRole.sysadmin), // 权限:系统管理员
        group_queryList("group", "queryList", UserRole.sysadmin), // 权限:系统管理员
        group_addUserInBatches("group", "addUserInBatches", UserRole.sysadmin), // 权限:系统管理员
        group_deleteUserInBatches("group", "deleteUserInBatches", UserRole.sysadmin), // 权限:系统管理员
        department_add("department", "add", UserRole.sysadmin), // 权限:系统管理员
        department_updateById("department", "updateById", UserRole.sysadmin), // 权限:系统管理员
        department_deleteById("department", "deleteById", UserRole.sysadmin), // 权限:系统管理员
        department_queryTree("department", "queryTree", UserRole.any), // 权限:任意
        department_addUser("department", "addUser", UserRole.sysadmin), // 权限:系统管理员
        department_deleteUser("department", "deleteUser", UserRole.sysadmin), // 权限:系统管理员
        department_queryList("department", "queryList", UserRole.sysadmin), // 权限:系统管理员
        department_queryById("department", "queryById", UserRole.sysadmin), // 权限:系统管理员
        role_add("role", "add", UserRole.secadmin), // 权限:安全管理员
        role_deleteById("role", "deleteById", UserRole.secadmin), // 权限:安全管理员
        role_queryTree("role", "queryTree", UserRole.secadmin), // 权限:安全管理员
        role_queryList("role", "queryList", UserRole.secadmin), // 权限:安全管理员
        role_deleteByIdForce("role", "deleteByIdForce", UserRole.secadmin), // 权限:安全管理员
        role_updateScope("role", "updateScope", UserRole.secadmin), // 权限:安全管理员
        role_queryScope("role", "queryScope", UserRole.secadmin), // 权限:安全管理员
        role_updateLevel("role", "updateLevel", UserRole.secadmin), // 权限:安全管理员
        role_checkRoleScope("role", "checkRoleScope", UserRole.secadmin), // 权限:安全管理员
        unit_addMinor("unit", "addMinor", UserRole.secadmin), // 权限:安全管理员
        unit_deleteMinor("unit", "deleteMinor", UserRole.secadmin), // 权限:安全管理员
        unit_queryMinorList("unit", "queryMinorList", UserRole.any), // 权限:任意
        unit_updateMajor("unit", "updateMajor", UserRole.secadmin), // 权限:安全管理员
        unit_queryMajorList("unit", "queryMajorList", UserRole.any), // 权限:任意
        basis_addBasisClass("basis", "addBasisClass", UserRole.secadmin), // 权限:安全管理员
        basis_addBasis("basis", "addBasis", UserRole.secadmin), // 权限:安全管理员
        basis_addBasisItem("basis", "addBasisItem", UserRole.secadmin), // 权限:安全管理员
        basis_deleteBasisClass("basis", "deleteBasisClass", UserRole.secadmin), // 权限:安全管理员
        basis_deleteBasis("basis", "deleteBasis", UserRole.secadmin), // 权限:安全管理员
        basis_deleteBasisItem("basis", "deleteBasisItem", UserRole.secadmin), // 权限:安全管理员
        basis_updateBasisClass("basis", "updateBasisClass", UserRole.secadmin), // 权限:安全管理员
        basis_updateBasis("basis", "updateBasis", UserRole.secadmin), // 权限:安全管理员
        basis_updateBasisItem("basis", "updateBasisItem", UserRole.secadmin), // 权限:安全管理员
        basis_queryBasisClassList("basis", "queryBasisClassList", UserRole.any), // 权限:任意
        basis_queryBasisList("basis", "queryBasisList", UserRole.any), // 权限:任意
        basis_queryBasisItemList("basis", "queryBasisItemList", UserRole.any), // 权限:任意
        basis_queryBasisTree("basis", "queryBasisTree", UserRole.any), // 权限:任意
        business_add("business", "add", UserRole.secadmin), // 权限:安全管理员
        business_delete("business", "delete", UserRole.secadmin), // 权限:安全管理员
        business_update("business", "update", UserRole.secadmin), // 权限:安全管理员
        business_queryList("business", "queryList", UserRole.any), // 权限:任意
        business_queryTree("business", "queryTree", UserRole.any), // 权限:任意
        securePolicy_updatePermissionPolicy("securePolicy", "updatePermissionPolicy", UserRole.secadmin), // 权限:安全管理员
        securePolicy_queryPermissionPolicyList("securePolicy", "queryPermissionPolicyList", UserRole.secadmin), // 权限:安全管理员
        securePolicy_updateValidPeriod("securePolicy", "updateValidPeriod", UserRole.secadmin), // 权限:安全管理员
        securePolicy_queryValidPeriodList("securePolicy", "queryValidPeriodList", UserRole.secadmin), // 权限:安全管理员
        securePolicy_updateFileAccessPolicy("securePolicy", "updateFileAccessPolicy", UserRole.secadmin), // 权限:安全管理员
        securePolicy_queryFileAccessPolicyList("securePolicy", "queryFileAccessPolicyList", UserRole.secadmin), // 权限:安全管理员
        securePolicy_addWorkFlow("securePolicy", "addWorkFlow", UserRole.secadmin), // 权限:安全管理员
        securePolicy_deleteWorkFlow("securePolicy", "deleteWorkFlow", UserRole.secadmin), // 权限:安全管理员
        securePolicy_updateWorkFlow("securePolicy", "updateWorkFlow", UserRole.secadmin), // 权限:安全管理员
        securePolicy_queryWorkFlow("securePolicy", "queryWorkFlow", UserRole.secadmin), // 权限:安全管理员
        securePolicy_queryValidPeriod("securePolicy", "queryValidPeriod", UserRole.any), // 权限:任意
        securePolicy_updateClientAccessPolicy("securePolicy", "updateClientAccessPolicy", UserRole.secadmin), // 权限:安全管理员
        securePolicy_queryClientAccessPolicyList("securePolicy", "queryClientAccessPolicyList", UserRole.secadmin), // 权限:安全管理员
        securePolicy_queryPermissionPolicy("securePolicy", "queryPermissionPolicy", UserRole.user), // 权限:终端用户
        securePolicy_queryWorkFlowById("securePolicy", "queryWorkFlowById", UserRole.secadmin), // 权限:安全管理员
        securePolicy_queryMarkKeyList("securePolicy", "queryMarkKeyList", UserRole.secadmin), // 权限:安全管理员
        securePolicy_addMarkKey("securePolicy", "addMarkKey", UserRole.secadmin), // 权限:安全管理员
        securePolicy_updateMarkKey("securePolicy", "updateMarkKey", UserRole.secadmin), // 权限:安全管理员
        securePolicy_deleteMarkKey("securePolicy", "deleteMarkKey", UserRole.secadmin), // 权限:安全管理员
        securePolicy_queryMarkKeyHistory("securePolicy", "queryMarkKeyHistory", UserRole.secadmin), // 权限:安全管理员
        securePolicy_backupMarkKey("securePolicy", "backupMarkKey", UserRole.secadmin), // 权限:安全管理员
        securePolicy_recoverMarkKey("securePolicy", "recoverMarkKey", UserRole.secadmin), // 权限:安全管理员
        securePolicy_importMarkKey("securePolicy", "importMarkKey", UserRole.secadmin), // 权限:安全管理员
        securePolicy_checkWorkFlow("securePolicy", "checkWorkFlow", UserRole.secadmin), // 权限:安全管理员
        securePolicy_queryKeywordPolicy("securePolicy", "queryKeywordPolicy", UserRole.secadmin), // 权限:安全管理员
        securePolicy_updateKeywordPolicy("securePolicy", "updateKeywordPolicy", UserRole.secadmin), // 权限:安全管理员
        file_queryPrivateList("file", "queryPrivateList", UserRole.user), // 权限:终端用户
        file_queryList("file", "queryList", UserRole.secadmin), // 权限:安全管理员
        file_queryById("file", "queryById", UserRole.any), // 权限:任意
        file_authorization("file", "authorization", UserRole.user), // 权限:终端用户
        file_queryDispatchList("file", "queryDispatchList", UserRole.user), // 权限:终端用户
        file_queryPrivatePermissionById("file", "queryPrivatePermissionById", UserRole.user), // 权限:终端用户
        file_queryPrivatePermissionNoId("file", "queryPrivatePermissionNoId", UserRole.user), // 权限:终端用户
        file_applyNewFid("file", "applyNewFid", UserRole.user), // 权限:终端用户
        file_uploadFileById("file", "uploadFileById", UserRole.any), // 权限:终端用户
        file_downloadFileById("file", "downloadFileById", UserRole.user), // 权限:终端用户
        file_queryExistById("file", "queryExistById", UserRole.user), // 权限:终端用户
        file_canUpload("file", "canUpload", UserRole.user), // 权限:终端用户
        file_fileTrack("file", "fileTrack", UserRole.user), // 权限:终端用户
        file_commitAnalysis("file", "commitAnalysis", UserRole.user), // 权限:终端用户
        file_queryAnalysis("file", "queryAnalysis", UserRole.user), // 权限:终端用户
        systemConfig_queryEnvironment("systemConfig", "queryEnvironment", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_queryWatermarkConfig("systemConfig", "queryWatermarkConfig", UserRole.any), // 权限:任意
        systemConfig_updateWatermarkConfig("systemConfig", "updateWatermarkConfig", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_systemLevel("systemConfig", "systemLevel", UserRole.anyadmin), // 权限:任意管理员。
        systemConfig_updateFileLocation("systemConfig", "updateFileLocation", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_queryFileLocation("systemConfig", "queryFileLocation", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_updateLogLocation("systemConfig", "updateLogLocation", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_queryLogLocation("systemConfig", "queryLogLocation", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_updateMailConfig("systemConfig", "updateMailConfig", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_queryMailConfig("systemConfig", "queryMailConfig", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_updateLocalAuthConfig("systemConfig", "updateLocalAuthConfig", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_queryLocalAuthConfig("systemConfig", "queryLocalAuthConfig", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_updateAdAuthConfig("systemConfig", "updateAdAuthConfig", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_queryAdAuthConfig("systemConfig", "queryAdAuthConfig", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_updateTimerConfig("systemConfig", "updateTimerConfig", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_queryTimerConfig("systemConfig", "queryTimerConfig", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_connectTest("systemConfig", "connectTest", UserRole.anyadmin), // 权限:管理员
        systemConfig_addTrustedApp("systemConfig", "addTrustedApp", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_deleteTrustedAppById("systemConfig", "deleteTrustedAppById", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_queryTrustedAppList("systemConfig", "queryTrustedAppList", UserRole.any), // 权限:任意
        systemConfig_queryLocationList("systemConfig", "queryLocationList", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_updateLocation("systemConfig", "updateLocation", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_updateOrganization("systemConfig", "updateOrganization", UserRole.secadmin), // 权限:安全管理员
        systemConfig_queryOrganization("systemConfig", "queryOrganization", UserRole.secadmin), // 权限:安全管理员
        systemConfig_updateTrustedApp("systemConfig", "updateTrustedApp", UserRole.sysadmin), // 权限：系统管理员
        systemConfig_desensitization("systemConfig", "desensitization", UserRole.sysadmin), // 权限：系统管理员
        systemConfig_queryDesensity("systemConfig", "queryDesensity", UserRole.sysadmin), // 权限：系统管理员
        systemConfig_queryLogArchiveKeepTime("systemConfig", "queryLogArchiveKeepTime", UserRole.sysadmin), // 权限：系统管理员
        systemConfig_updateLogArchiveKeepTime("systemConfig", "updateLogArchiveKeepTime", UserRole.sysadmin), // 权限：系统管理员
        systemConfig_queryOtherSecurityConfig("systemConfig", "queryOtherSecurityConfig", UserRole.secadmin), // 权限:安全管理员
        systemConfig_updateOtherSecurityConfig("systemConfig", "updateOtherSecurityConfig", UserRole.secadmin), // 权限:安全管理员
        systemConfig_updateAdvancedControlStrategy("systemConfig", "updateAdvancedControlStrategy", UserRole.secadmin), // 权限:安全管理员
        systemConfig_queryAdvancedControlStrategy("systemConfig", "queryAdvancedControlStrategy", UserRole.secadmin), // 权限:安全管理员
        systemConfig_queryClassifiedWhiteList("systemConfig", "queryClassifiedWhiteList", UserRole.sysadmin), // 权限:系统管理员
        systemConfig_updateClassifiedWhiteList("systemConfig", "updateClassifiedWhiteList", UserRole.sysadmin), // 权限:系统管理员
        license_query("license", "query", UserRole.nocheck), // 权限:不检查
        license_export("license", "export", UserRole.nocheck), // 权限:不检查
        license_import("license", "import", UserRole.nocheck), // 权限:不检查
        license_importFile("license", "importFile", UserRole.nocheck), // 权限:系统管理员
        log_queryAdminLog("log", "queryAdminLog", UserRole.logadmin), // 权限:日志审计员
        log_queryClientRequestLog("log", "queryClientRequestLog", UserRole.secadmin), // 权限:安全管理员
        log_queryClientLog("log", "queryClientLog", UserRole.secadmin), // 权限:安全管理员
        log_uploadLog("log", "uploadLog", UserRole.user), // 权限:终端用户
        log_exportAdminLog("log", "exportAdminLog", UserRole.logadmin), // 权限:安全审计员
        log_queryFileLevelManagementLog("log", "queryFileLevelManagementLog", UserRole.secadmin), // 权限:安全管理员
        log_logArchive("log", "logArchive", UserRole.logadmin), // 权限:安全审计员
        component_query("component", "query", UserRole.sysadmin), // 权限:系统管理员
        component_updateById("component", "updateById", UserRole.sysadmin), // 权限:系统管理员
        backup_add("backup", "add", UserRole.sysadmin), // 权限:系统管理员
        backup_queryList("backup", "queryList", UserRole.sysadmin), // 权限:系统管理员
        backup_queryConfig("backup", "queryConfig", UserRole.sysadmin), // 权限:系统管理员
        backup_updateConfig("backup", "updateConfig", UserRole.sysadmin), // 权限:系统管理员
        backup_deleteById("backup", "deleteById", UserRole.sysadmin), // 权限:系统管理员
        backup_recover("backup", "recover", UserRole.sysadmin), // 权限:系统管理员
        middleware_queryList("middleware", "queryList", UserRole.sysadmin), // 权限:系统管理员
        middleware_addACL("middleware", "addACL", UserRole.sysadmin), // 权限:系统管理员
        middleware_deleteACLById("middleware", "deleteACLById", UserRole.sysadmin), // 权限:系统管理员
        middleware_updateACLById("middleware", "updateACLById", UserRole.sysadmin), // 权限:系统管理员
        middleware_queryFileHeaderd("middleware", "queryFileHeader", UserRole.middleware), // 权限:中间件对接
        middleware_classifyFile("middleware", "classifyFile", UserRole.middleware), // 权限:中间件对接
        middleware_uploadCirculationLog("middleware", "uploadCirculationLog", UserRole.middleware), // 权限:中间件对接
        middleware_userRegister("middleware", "userRegister", UserRole.middleware), // 权限:中间件对接
        workFlow_create("workFlow", "create", UserRole.user), // 权限:终端用户，只有定密责任人可以提签发流程
        workFlow_approval("workFlow", "approval", UserRole.user), // 权限:终端用户
        workFlow_queryUnfinished("workFlow", "queryUnfinished", UserRole.user), // 权限:终端用户
        workFlow_addAgent("workFlow", "addAgent", UserRole.user), // 权限:终端用户
        workFlow_deleteAgentById("workFlow", "deleteAgentById", UserRole.user), // 权限:终端用户
        workFlow_updateAgentById("workFlow", "updateAgentById", UserRole.user), // 权限:终端用户
        workFlow_queryAgent("workFlow", "queryAgent", UserRole.user), // 权限:终端用户
        workFlow_queryFinished("workFlow", "queryFinished", UserRole.user), // 权限:终端用户
        workFlow_queryMyRequest("workFlow", "queryMyRequest", UserRole.user), // 权限:终端用户
        workFlow_queryById("workFlow", "queryById", UserRole.any), // 权限:任意
        workFlow_queryList("workFlow", "queryList", UserRole.secadmin), // 权限:安全管理员
        workFlow_queryApproverSelectList("workFlow", "queryApproverSelectList", UserRole.user), // 权限:终端用户
        workFlow_canCreate("workFlow", "canCreate", UserRole.user), // 权限:终端用户
        workFlow_downloadFileById("workFlow", "downloadFileById", UserRole.user), // 权限:终端用户
        statistics_adminUsage("statistics", "adminUsage", UserRole.logadmin), // 权限:日志审计员
        statistics_userOnline("statistics", "userOnline", UserRole.sysadmin), // 权限:系统管理员
        statistics_environment("statistics", "environment", UserRole.sysadmin), // 权限:系统管理员
        statistics_clientOnline("statistics", "clientOnline", UserRole.sysadmin), // 权限:系统管理员
        statistics_queryFileList("statistics", "queryFileList", UserRole.secadmin), // 权限:安全管理员
        statistics_queryWorkFlowList("statistics", "queryWorkFlowList", UserRole.secadmin), // 权限:安全管理员
        statistics_fileTrend("statistics", "fileTrend", UserRole.secadmin), // 权限:安全管理员
        statistics_fileManyTrend("statistics", "fileManyTrend", UserRole.secadmin), // 权限:安全管理员
        statistics_fileInLevel("statistics", "fileInLevel", UserRole.user), // 权限:终端用户
        statistics_fileInState("statistics", "fileInState", UserRole.user); // 权限:终端用户
        private String apiGroupName;
        private String apiName;
        private UserRole permission;

        private Api(String apiGroupName, String apiName, UserRole permission) {
            this.apiGroupName = apiGroupName;
            this.apiName = apiName;
            this.permission = permission;
        }

        public static Api parse(String apiGroupName, String apiName) {
            for (Api api : values()) {
                if (api.getApiGroupName().equals(apiGroupName) && api.getApiName().equals(apiName)) {
                    return api;
                }
            }
            return null;
        }

        public boolean hasPermission(TokenWrapper tokenWrapper) {
            UserRole userRoleInToken = tokenWrapper.getUserRole();
            switch (permission) {
            case any:
                return true;
            case anyadmin:
                return userRoleInToken == UserRole.sysadmin || userRoleInToken == UserRole.secadmin
                        || userRoleInToken == UserRole.logadmin;
            case sysadmin:
                return userRoleInToken == UserRole.sysadmin;
            case secadmin:
                return userRoleInToken == UserRole.secadmin;
            case logadmin:
                return userRoleInToken == UserRole.logadmin;
            case user:
                return userRoleInToken == UserRole.user;
            default:
                return false;
            }
        }

        public String getApiGroupName() {
            return apiGroupName;
        }

        public String getApiName() {
            return apiName;
        }

        public UserRole getPermission() {
            return permission;
        }
    }
}
