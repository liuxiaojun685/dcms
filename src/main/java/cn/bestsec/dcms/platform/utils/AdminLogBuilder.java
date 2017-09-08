package cn.bestsec.dcms.platform.utils;

import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.ResultType;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.AdminLog;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月15日 上午8:58:28
 */
public class AdminLogBuilder {
    private AdminLog adminLog;

    private Admin admin;
    private long operateTime;
    private AdminLogOp operation;
    private String operateDescription;
    private int operateResult;
    private String ip;
    
    public AdminLog build() {
        adminLog = new AdminLog();
        adminLog.setAdmin(admin);
        adminLog.setOperateTime(operateTime);
        adminLog.setOperateType(operation.getOperateType());
        adminLog.setOperateDescription(operateDescription);
        adminLog.setOperateResult(operateResult);
        adminLog.setIp(ip);
        adminLog.setCreateTime(System.currentTimeMillis());
        return adminLog;
    }

    public AdminLogBuilder admin(Admin admin) {
        this.admin = admin;
        return this;
    }

    public AdminLogBuilder operateTime(long operateTime) {
        this.operateTime = operateTime;
        return this;
    }

    public AdminLogBuilder operation(AdminLogOp op) {
        this.operation = op;
        return this;
    }

    public AdminLogBuilder operateDescription(Object... args) {
        String prefix = "";
        if (admin != null) {
            prefix = "[" + admin.getAccount() + "]";
        }
        this.operateDescription = prefix + String.format(operation.getFormat(), args);
        return this;
    }
    
    public AdminLogBuilder operateResult(int operateResult, String reason) {
        this.operateResult = operateResult;
        if (operateResult == ResultType.failed.getCode()) {
            this.operateDescription = this.operateDescription + ResultType.failed.getDescription() + " " + reason;
        } else if (operateResult == 1) {
            this.operateDescription = this.operateDescription + ResultType.success.getDescription();
        }
        return this;
    }

    public AdminLogBuilder ip(String ip) {
        this.ip = ip;
        return this;
    }

}
