package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class User_QueryPasswdRuleResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private Integer minLength;
    private Integer maxLength;
    private Integer containsNumber;
    private Integer containsLetter;
    private Integer containsSpecial;
    
    public User_QueryPasswdRuleResponse() {
    }

    public User_QueryPasswdRuleResponse(Integer minLength, Integer maxLength, Integer containsNumber, Integer containsLetter, Integer containsSpecial) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.containsNumber = containsNumber;
        this.containsLetter = containsLetter;
        this.containsSpecial = containsSpecial;
    }

    public User_QueryPasswdRuleResponse(Integer code, String msg, Integer minLength, Integer maxLength, Integer containsNumber, Integer containsLetter, Integer containsSpecial) {
        this.code = code;
        this.msg = msg;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.containsNumber = containsNumber;
        this.containsLetter = containsLetter;
        this.containsSpecial = containsSpecial;
    }

    /**
     * 错误码
     */
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 错误提示
     */
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 密码最短字符数
     */
    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    /**
     * 密码最长字符数
     */
    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * 密码是否必须包含数字
     */
    public Integer getContainsNumber() {
        return containsNumber;
    }

    public void setContainsNumber(Integer containsNumber) {
        this.containsNumber = containsNumber;
    }

    /**
     * 密码是否必须包含字母大小写
     */
    public Integer getContainsLetter() {
        return containsLetter;
    }

    public void setContainsLetter(Integer containsLetter) {
        this.containsLetter = containsLetter;
    }

    /**
     * 密码是否必须包含特殊字符
     */
    public Integer getContainsSpecial() {
        return containsSpecial;
    }

    public void setContainsSpecial(Integer containsSpecial) {
        this.containsSpecial = containsSpecial;
    }
}
