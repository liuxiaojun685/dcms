package cn.bestsec.dcms.platform.consts;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月13日 下午2:09:07
 */
public enum AdminLogOp {
    admin_login(101, "登录", "[%s]登录"),
    admin_logout(102, "注销", "注销"),
    admin_update(103, "修改密码", "修改个人密码"),
    user_resetPasswd(204, "重置用户密码", "重置用户[%s]的密码"),
    user_create(205, "用户与组织管理", "创建新用户[%s]"),
    user_update(206, "用户与组织管理", "修改用户[%s]的个人信息"),
    user_moveDepartment(200, "用户与组织管理", "移动用户[%s]到部门[%s]"),
    user_delete(207, "用户与组织管理", "删除用户[%s]"),
    user_import(209, "用户与组织管理", "从文件模板批量导入用户"), //TODO
    user_sync(210, "用户与组织管理", "从AD同步用户"), //TODO
    user_updateLevel(211, "用户与组织管理", "修改用户[%s]的密级 从 %s 到 %s"),
    user_unlock(212, "用户与组织管理", "解锁用户[%s]"),
    client_add(301, "终端管理", "注册终端[%s]"),
    client_delete(302, "终端管理", "删除终端[%s]"),
    client_patchUpload(308, "终端管理", "上传终端补丁[%s]"),
    client_patchDelete(309, "终端管理", "删除补丁[%s]"),
    client_uninstallPwd(311, "终端管理", "修改卸载密码"),
    group_create(401, "用户与组织管理", "创建新用户组[%s]"),
    group_update(402, "用户与组织管理", "修改用户组[%s]的信息"),
    group_delete(403, "用户与组织管理", "删除用户组[%s]"),
    group_addUser(404, "用户与组织管理", "将用户[%s]添加到用户组[%s]"),
    group_deleteUser(405, "用户与组织管理", "将用户[%s]移除出用户组[%s]"),
    department_create(501, "用户与组织管理", "创建新部门[%s]"),
    department_update(502, "用户与组织管理", "修改部门[%s]的信息"),
    department_delete(503, "用户与组织管理", "删除部门[%s]"),
    department_addUser(505, "用户与组织管理", "将用户[%s]添加到部门[%s]"),
    department_deleteUser(506, "用户与组织管理", "将用户[%s]移除出部门[%s]"),
    role_add(601, "用户角色管理", "将用户[%s]设为 %s"),
    role_delete(602, "用户角色管理", "取消[%s]的 %s 角色"),
    role_level(603, "用户角色管理", "修改[%s]的 %s 角色负责密级"),
    role_scope(604, "用户角色管理", "修改[%s]的 %s 角色负责范围"),
    role_addAdmin(605, "用户角色管理", "授权[%s]为 %s"),
    role_deleteAdmin(606, "用户角色管理", "撤销[%s]等的管理员角色"),
    unit_addMinor(701, "定密策略管理", "添加辅助定密单位  %s"),
    unit_deleteMinor(702, "定密策略管理", "删除辅助定密单位  %s"),
    unit_updateMajor(704, "定密策略管理", "修改主定密单位名称为  %s"),
    basis_addClass(805, "定密策略管理", "添加[%s]定密依据分类 %s"),
    basis_addBasis(801, "定密策略管理", "添加[%s]定密依据 %s"),
    basis_addItem(806, "定密策略管理", "添加[%s]定密依据事项[%s] %s"),
    basis_deleteClass(807, "定密策略管理", "删除[%s]定密依据分类 %s"),
    basis_deleteBasis(802, "定密策略管理", "删除[%s]定密依据[%s] %s"),
    basis_deleteItem(808, "定密策略管理", "删除[%s]定密依据事项 %s"),
    basis_updateClass(809, "定密策略管理", "修改[%s]定密依据分类 %s"),
    basis_updateBasis(804, "定密策略管理", "修改[%s]定密依据 %s"),
    basis_updateItem(810, "定密策略管理", "修改[%s]定密依据事项[%s] %s"),
    secure_permissionPolicy(901, "定密策略管理", "修改定密权限策略"),
    secure_secretPeriod(903, "定密策略管理", "修改 %s密级的 默认保密期限 %s 改为 %s"),
    secure_fileAccessPolicy(905, "定密策略管理", "修改文件密级访问控制策略"),
    secure_addWorkflowPolicy(907, "定密策略管理", "新建流程策略 %s密级的 %s"),
    secure_deleteWorkflowPolicy(908, "定密策略管理", "删除流程策略 %s密级的 %s"),
    secure_updateWorkflowPolicy(909, "定密策略管理", "修改流程策略 %s密级的 %s 编辑%s级审批人 编辑后该级审批有%s人"),
    secure_clientAccessPolicy(912, "定密策略管理", "修改终端密级访问控制策略"),
    secure_addMarkKey(917, "定密策略管理", "新建密标密钥[ID:%s]"),
    secure_updateMarkKey(918, "定密策略管理", "修改密标密钥[ID:%s]状态"),
    secure_deleteMarkKey(919, "定密策略管理", "删除密标密钥[ID:%s]"),
    secure_backupMarkKey(920, "定密策略管理", "备份密标密钥"),
    secure_recoverMarkKey(921, "定密策略管理", "还原密标密钥"),
    secure_importMarkKey(922, "定密策略管理", "导入密标密钥[ID:%s]"),
    secure_updateKeywordPolicy(923, "定密策略管理", "修改密点分析关键词策略"),
    config_watermark(1103, "配置管理", "修改水印配置"),
    config_systemLevel(1104, "配置管理", "设置系统最高密级为%s"),
    config_fileLocation(1105, "配置管理", "配置 %s密级 文件上传位置"),
    config_mail(1109, "配置管理", "修改邮件告警配置"),
    config_localAuth(1111, "配置管理", "修改本地认证配置"),
    config_adAuth(1113, "配置管理", "修改AD认证配置"),
    config_timer(1115, "配置管理", "修改定时器配置"),
    config_conntest(1117, "配置管理", "进行系统连接验证测试"),
    config_addTrust(1118, "配置管理", "添加可信应用程序 %s"),
    config_deleteTrust(1119, "配置管理", "删除可信应用程序 %s"),
    config_Location(1122, "配置管理", "配置文件存储位置"),
    config_updateOrganization (1124, "配置管理", "更新组织机构为 %s[%s]"),
    config_updateTrust(1125, "配置管理", "修改可信应用程序 %s"),
    config_desensitization(1126, "配置管理", "配置[%s]密级文件名称进行脱敏处理"),
    config_advancedControlStrategy(1127, "配置管理", "配置高级管控策略"),
    config_logArchiveKeepTime(1129, "配置管理", "配置日志归档保留时间"),
    config_classifiedWhiteList(1135, "配置管理", "配置标密白名单"),
    license_import(1203, "授权许可", "导入系统授权"),
    log_exportAdminLog(1305, "日志导出", "导出管理员操作日志"),
    log_logArchive(1307, "日志归档", "%s日志归档[%s条]"),
    component_update(1402, "组件管理", "%s组件 %s"),
    backup_create(1501, "备份管理", "备份数据库%s"),
    backup_config(1504, "备份管理", "修改备份配置"),
    backup_delete(1505, "备份管理", "删除备份 %s"),
    backup_recover(1506, "备份管理", "还原数据库备份 %s"),
    middleware_add(1602, "集成管理", " %s 增加应用IP[%s]"),
    middleware_delete(1603, "集成管理", " %s 删除应用IP[%s]"),
    middleware_update(1604, "集成管理", " %s 修改应用配置"),
    business_add(1901, "定密策略管理", "添加业务属性 %s"),
    business_delete(1902, "定密策略管理", "删除业务属性 %s"),
    business_update(1903, "定密策略管理", "修改业务属性 %s 为 %s");
    
    private Integer code;
    private String operateType;
    private String format;

    private AdminLogOp(Integer code, String operateType, String format) {
        this.code = code;
        this.operateType = operateType;
        this.format = format;
    }

    public Integer getCode() {
        return code;
    }

    public String getOperateType() {
        return operateType;
    }
    
    public String getFormat() {
        return format;
    }
}
