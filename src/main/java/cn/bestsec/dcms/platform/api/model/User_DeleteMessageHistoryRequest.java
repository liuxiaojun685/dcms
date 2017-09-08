package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class User_DeleteMessageHistoryRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer msgId;
    
    public User_DeleteMessageHistoryRequest() {
    }

    public User_DeleteMessageHistoryRequest(String token, Integer msgId) {
        this.token = token;
        this.msgId = msgId;
    }

    /**
     * 
     */
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}
