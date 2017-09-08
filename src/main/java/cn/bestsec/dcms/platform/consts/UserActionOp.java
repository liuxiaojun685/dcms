package cn.bestsec.dcms.platform.consts;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月3日 下午6:04:32
 */
public enum UserActionOp {
    user_login(201, "登录", "登录"), 
    user_logout(202, "注销", "注销"),
    user_updatePasswd(203, "修改密码", "修改个人密码"),
    client_loadPolicy(303, "策略同步", "策略同步"),
    file_download(1010, "附件下载", "下载文件 [%s]");

    private Integer code;
    private String operateType;
    private String format;

    private UserActionOp(Integer code, String operateType, String format) {
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
