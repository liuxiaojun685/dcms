package cn.bestsec.dcms.platform.api.model;


/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class MessageHistoryInfo {
    private Integer msgId;
    private String message;
    private String type;
    private String url;
    private Long createTime;
    
    public MessageHistoryInfo() {
    }

    public MessageHistoryInfo(Integer msgId, String message, String type, String url, Long createTime) {
        this.msgId = msgId;
        this.message = message;
        this.type = type;
        this.url = url;
        this.createTime = createTime;
    }

    /**
     * 消息ID
     */
    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    /**
     * 消息内容
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 消息类型 wf 流程通知 file 文件通知
     */
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 消息链接
     */
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 产生时间
     */
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
