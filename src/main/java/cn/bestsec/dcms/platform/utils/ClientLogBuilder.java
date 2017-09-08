package cn.bestsec.dcms.platform.utils;

import cn.bestsec.dcms.platform.consts.ClientLogOp;
import cn.bestsec.dcms.platform.consts.ResultType;
import cn.bestsec.dcms.platform.entity.Client;
import cn.bestsec.dcms.platform.entity.ClientLog;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.User;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月3日 下午6:12:23
 */
public class ClientLogBuilder {
    private ClientLog log;

    private User user;
    private Client client;
    private long operateTime;
    private ClientLogOp operation;
    private String operateDescription;
    private int operateResult;
    private String ip;
    private String srcName;
    private String desName;
    private File file;

    public ClientLog build() {
        log = new ClientLog();
        log.setUser(user);
        log.setClient(client);
        log.setIp(ip);
        log.setOperateDescription(operateDescription);
        log.setOperateResult(operateResult);
        log.setCreateTime(operateTime);
        log.setOperateType(operation.getOperateType());
        log.setSrcName(srcName);
        log.setDesName(desName);
        log.setFileByFkSrcFid(file);
        return log;
    }

    public ClientLogBuilder user(User user) {
        this.user = user;
        return this;
    }

    public ClientLogBuilder client(Client client) {
        this.client = client;
        return this;
    }

    public ClientLogBuilder operateTime(long operateTime) {
        this.operateTime = operateTime;
        return this;
    }

    public ClientLogBuilder operation(ClientLogOp op) {
        this.operation = op;
        return this;
    }

    public ClientLogBuilder operateDescription(Object... args) {
        this.operateDescription = String.format(operation.getFormat(), args);
        return this;
    }

    public ClientLogBuilder operateResult(int operateResult, String reason) {
        this.operateResult = operateResult;
        if (operateResult == ResultType.failed.getCode()) {
            this.operateDescription = this.operateDescription + ResultType.failed.getDescription() + " " + reason;
        } else if (operateResult == 1) {
            this.operateDescription = this.operateDescription + ResultType.success.getDescription();
        }
        return this;
    }

    public ClientLogBuilder ip(String ip) {
        this.ip = ip;
        return this;
    }

    public ClientLogBuilder srcName(String srcName) {
        this.srcName = srcName;
        return this;
    }

    public ClientLogBuilder desName(String desName) {
        this.desName = desName;
        return this;
    }

    public ClientLogBuilder file(File file) {
        this.file = file;
        return this;
    }
}
