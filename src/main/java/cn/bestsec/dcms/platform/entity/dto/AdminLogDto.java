package cn.bestsec.dcms.platform.entity.dto;

import java.util.Date;

/**
 * 导出日志数据
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月7日 下午5:39:49
 */
public class AdminLogDto {

    private String acount;
    private String operateType;
    private String operateDescription;
    private Date createTime;
    private String ip;

    public AdminLogDto() {
        super();
    }

    public String getAcount() {
        return acount;
    }

    public String getOperateType() {
        return operateType;
    }

    public String getOperateDescription() {
        return operateDescription;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setAcount(String acount) {
        this.acount = acount;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public void setOperateDescription(String operateDescription) {
        this.operateDescription = operateDescription;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}
