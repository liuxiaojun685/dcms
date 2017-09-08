package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class SecurePolicy_UpdateKeywordPolicyRequest extends CommonRequestFieldsSupport {
    private String token;
    private String keyword0;
    private String keyword1;
    private String keyword2;
    private String keyword3;
    private String keyword4;
    
    public SecurePolicy_UpdateKeywordPolicyRequest() {
    }

    public SecurePolicy_UpdateKeywordPolicyRequest(String token, String keyword0, String keyword1, String keyword2, String keyword3, String keyword4) {
        this.token = token;
        this.keyword0 = keyword0;
        this.keyword1 = keyword1;
        this.keyword2 = keyword2;
        this.keyword3 = keyword3;
        this.keyword4 = keyword4;
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
     * 公开关键词
     */
    public String getKeyword0() {
        return keyword0;
    }

    public void setKeyword0(String keyword0) {
        this.keyword0 = keyword0;
    }

    /**
     * 内部关键词
     */
    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    /**
     * 秘密关键词
     */
    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
    }

    /**
     * 机密关键词
     */
    public String getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(String keyword3) {
        this.keyword3 = keyword3;
    }

    /**
     * 绝密关键词
     */
    public String getKeyword4() {
        return keyword4;
    }

    public void setKeyword4(String keyword4) {
        this.keyword4 = keyword4;
    }
}
