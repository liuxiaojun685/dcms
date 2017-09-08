package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class File_QueryAnalysisResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<KeywordResultItem> keywordResultList;
    private String description;
    private Integer execState;
    
    public File_QueryAnalysisResponse() {
    }

    public File_QueryAnalysisResponse(List<KeywordResultItem> keywordResultList, String description, Integer execState) {
        this.keywordResultList = keywordResultList;
        this.description = description;
        this.execState = execState;
    }

    public File_QueryAnalysisResponse(Integer code, String msg, List<KeywordResultItem> keywordResultList, String description, Integer execState) {
        this.code = code;
        this.msg = msg;
        this.keywordResultList = keywordResultList;
        this.description = description;
        this.execState = execState;
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
     * 
     */
    public List<KeywordResultItem> getKeywordResultList() {
        return keywordResultList;
    }

    public void setKeywordResultList(List<KeywordResultItem> keywordResultList) {
        this.keywordResultList = keywordResultList;
    }

    /**
     * 描述建议
     */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 执行状态 0未执行 1正在执行 2已完成 3执行失败
     */
    public Integer getExecState() {
        return execState;
    }

    public void setExecState(Integer execState) {
        this.execState = execState;
    }
}
