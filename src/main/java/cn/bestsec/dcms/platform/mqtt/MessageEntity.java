package cn.bestsec.dcms.platform.mqtt;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月24日 下午2:04:17
 */
public class MessageEntity {
    private String type;
    private Long stime;
    private Long ctime;
    private String msg;
    private String url;

    public MessageEntity() {
    }

    public MessageEntity(String type, Long stime, Long ctime, String msg, String url) {
        this.type = type;
        this.stime = stime;
        this.ctime = ctime;
        this.msg = msg;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getStime() {
        return stime;
    }

    public void setStime(Long stime) {
        this.stime = stime;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
