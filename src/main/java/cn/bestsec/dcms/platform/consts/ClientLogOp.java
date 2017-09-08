package cn.bestsec.dcms.platform.consts;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月3日 下午6:04:32
 */
public enum ClientLogOp {
    read(1, "阅读", "[%s] 阅读 《%s》 %s"),
    print(2, "打印", "[%s] 打印(%s) 《%s》 %s"),
    edit(3, "编辑", "[%s] 编辑 《%s》 %s"),
    screenshot(4, "截屏", "[%s] 截屏 《%s》 %s"),
    copy(5, "拷贝", "[%s] 拷贝 《%s》 为 《%s》 %s"),
    paste(6, "粘贴", "[%s] 粘贴 《%s》内容 %s"),
    delete(7, "删除", "[%s] 删除 《%s》 %s"),
    saveAs(8, "另存", "[%s] 另存 《%s》 为 《%s》 %s"),
    rename(9, "改名", "[%s] 改名 《%s》 为 《%s》 %s"),
    send(10, "发送", "[%s] 发送(%s) 《%s》 %s"),
    filePreclassified(11, "预定密", "[%s] 预定密 《%s》 %s"),
    fileClassified(12, "正式定密", "[%s] 正式定密 《%s》 %s"),
    fileIssued(13, "文件签发", "[%s] 文件签发 《%s》 %s"),
    fileClassifiedChange(14, "密级变更", "[%s] 密级变更 《%s》 %s"),
    fileDeclassified(15, "文件解密", "[%s] 文件解密 《%s》 %s"),
    fileRestore(16, "密文还原", "[%s] 密文还原 《%s》 %s"),
    requestPreclassified(21, "预定密流程申请", "[%s] 发起 《%s》 的预定密流程申请 %s"),
    requestClassified(22, "正式定密流程申请", "[%s] 发起 《%s》 的正式定密流程申请 %s"),
    requestIssued(23, "文件签发流程申请", "[%s] 发起 《%s》 的文件签发流程申请 %s"),
    requestClassifiedChange(24, "密级变更流程申请", "[%s] 发起 《%s》 的密级变更流程申请 %s"),
    requestDeclassified(25, "文件解密流程申请", "[%s] 发起 《%s》 的文件解密流程申请 %s"),
    requestRestore(26, "密文还原流程申请", "[%s] 发起 《%s》 的密文还原流程申请 %s"),
    approvePreclassified(31, "预定密流程审批", "[%s] 审批%s 《%s》 的预定密流程申请 %s"),
    approveClassified(32, "正式定密流程审批", "[%s] 审批%s 《%s》 的正式定密流程申请 %s"),
    approveIssued(33, "文件签发流程审批", "[%s] 审批%s 《%s》 的文件签发流程申请 %s"),
    approveClassifiedChange(34, "密级变更流程审批", "[%s] 审批%s 《%s》 的密级变更流程申请 %s"),
    approveDeclassified(35, "文件解密流程审批", "[%s] 审批%s 《%s》 的文件解密流程申请 %s"),
    approveRestore(36, "密文还原流程审批", "[%s] 审批%s 《%s》 的密文还原流程申请 %s"),
    user_login(201, "登录", "[%s] 登录"),
    user_logout(202, "注销", "[%s] 注销"),
    user_updatePasswd(203, "修改密码", "[%s] 修改个人密码"),
    client_loadPolicy(303, "策略同步", "[%s] 策略同步"),
    file_download(1010, "附件下载", "[%s] 下载附件 《%s》");
    
    private Integer code;
    private String operateType;
    private String format;

    private ClientLogOp(Integer code, String operateType, String format) {
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
    
    public static ClientLogOp parse(String operateType) {
        for (ClientLogOp val : values()) {
            if (val.getOperateType().equals(operateType)) {
                return val;
            }
        }
        return null;
    }
}
