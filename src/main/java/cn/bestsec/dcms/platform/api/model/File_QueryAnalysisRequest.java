package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class File_QueryAnalysisRequest extends CommonRequestFieldsSupport {
    private String token;
    private String analysisId;
    
    public File_QueryAnalysisRequest() {
    }

    public File_QueryAnalysisRequest(String token, String analysisId) {
        this.token = token;
        this.analysisId = analysisId;
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
     * 分析ID
     */
    public String getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(String analysisId) {
        this.analysisId = analysisId;
    }
}
