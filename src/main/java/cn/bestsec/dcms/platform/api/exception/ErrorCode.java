package cn.bestsec.dcms.platform.api.exception;

/**
 * 全局统一错误码
 */
public enum ErrorCode {
    success(0, "Success", "操作成功"),
    invalidArgument(1, "Invalid argument", "无效的请求参数"),
    invalidToken(2, "Invalid token", "失效的会话"),
    permissionDenied(3, "Permission denied", "您的权限不足"),
    authenticationFailed(4, "Authentication failed", "身份验证错误"),
    apiNotExist(5, "Api does not exist", "API不存在"),
    targetNotExist(6, "Target does not exist or have been deleted", "数据不存在或已被删除"),
    operationNotPermitted(7, "Operation not permitted", "操作不允许"),
    unsupport(8, "Operation is not support yet", "功能暂不支持"),
    configNotCorrect(9, "Config is not correct", "系统配置不正确"),
    sameConfitAlreadyExist(10, "Same config already exist", "已存在相同的配置"),
    attachmentNotFound(11, "Attachment not found", "没有找到附件"),
    invalidRequest(12, "Invalid argument", "无效的请求"),
    adminNotExist(1001, "Admin does not exist", "管理员不存在"),
    userAlreadyBeAdmin(1002, "User already be admin", "用户已任命为管理员"),
    identifyingCodeError(1003, "Identifying code error", "验证码错误，请确认后重新输入！"),
    pageOutofdate(1004, "Page out of date", "页面已过期，请刷新页面"),
    userAlreadyExists(2001, "User already exists", "用户已存在"),
    userNotExist(2002, "User does not exist", "用户不存在"),
    fileParsingFailed(2003, "File parsing failed", "文件解析异常"),
    adFailed(2004, "AD failed", "AD同步异常"),
    userAlreadyLocked(2005, "User already locked", "用户已锁定"),
    invalidUserPasswd(2006, "Invalid user passwd", "用户密码格式不正确"),
    accountOrPasswordError(2007, "Account or password error", "账户或密码错误，请确认后重新输入！"),
    userhasUnfinishedApproval(2008, "User has Unfinished Approval", "用户存在未办完的审批，不可删除"),
    phoneFormatError(2009, "Phone format error", "电话格式错误,请填写1开头的11位手机号码"),
    mailFormatError(2010, "Mail format error", "邮箱格式错误"),
    clientLoginNoPermission(2011, "Client login no permission", "您无权在此终端登录"),
    clientNotExist(3002, "Client does not exist", "终端不存在"),
    groupAlreadyExists(4001, "Group already exists", "用户组已存在"),
    groupHaveUser(4002, "Group have user", "用户组内存在用户，不可删除"),
    departmentAlreadyExists(5001, "Department already exists", "部门已存在"),
    userAlreadyExistInOtherDepartment(5002, "User already exist in other department", "用户已存在于其他部门"),
    departmentNotExistsRoot(5003, "Department not exists root", "根部门不存在"),
    departmentRootCannotBeDeleted(5004, "Department root cannot be deleted", "根部门不可删除"),
    departmentHaveUserOrGroup(5005, "Department have user or group", "部门内存在用户或组，不可删除"),
    roleAlreadyInWorkflowPolicy(6001, "Role already in workflow policy", "该用户已配置到流程策略"),
    roleIsLastOneInType(6002, "Role is last one in type", "删除该用户角色将会导致该角色类别无其他用户"),
    unitNoAlreadyExists(7001, "Unit No. already exists", "定密单位编号重复"),
    disableMarkKeyFailed(9001, "Can not disable last key", "操作失败，系统至少要有一个启用的密钥，如需更改请直接启用其他密钥"),
    backupMarkKeyFailed(9002, "Backup makr key failed", "备份密钥异常"),
    invalidMarkKeyFile(9003, "Invalid mark key file", "无效的密钥文件"),
    invalidMarkKeyBackupFile(9004, "Invalid mark key backup file", "无效的密钥备份文件"),
    fileUploadFailed(10001, "File upload failed", "文件上传异常"),
    fileDownloadFailed(10002, "File down failed", "文件下载异常"),
    fileIsOutOfDate(10003, "File is out of date", "文件已经过期，请进入控制台重新下载"),
    fileNoPermission(10004, "File no permission", "您无权操作该文件"),
    fileAlreadyExistWorkflow(10005, "File already exist Workflow", "文件已申请流程，请勿直接操作"),
    fileSaveFailed(10006, "File save failed", "文件存储失败，请检查存储配置"),
    fileStateException(10007, "File save failed", "文件管理状态异常，可能是外部单位的文件"),
    invalidLicense(12001, "Invalid license", "无效的授权码"),
    invalidSystemLicense(12002, "Invalid license", "系统未经授权，拒绝登录"),
    backupFailed(15001, "Backup failed", "备份数据库异常"),
    fileIsUnclassified(16001, "Fil is unclassified", "文件是非密标文件"),
    fileIsClassified(16002, "Fil is classified", "文件是密标文件"),
    fileClassifyError(16003, "Fil is classified", "文件标定密失败"),
    workflowAlreadyExists(17001, "Workflow already exists", "流程已存在，请勿重复提交"),
    agentNoPermission(17002, "Agent no permission", "代理人的权限不足"),
    workflowPolicyError(17003, "Workflow policy error", "缺少流程策略配置"),
    unknownError(999, "Unknown Error", "未知错误");

    private int code;
    private String description;
    private String reason;

    private ErrorCode(int code, String description, String reason) {
        this.code = code;
        this.description = description;
        this.reason = reason;
    }
    
    public static ErrorCode parse(Integer code) {
        for (ErrorCode c : values()) {
            if (c.getCode() == code) {
                return c;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
    
    public String getReason() {
        return reason;
    }
}
