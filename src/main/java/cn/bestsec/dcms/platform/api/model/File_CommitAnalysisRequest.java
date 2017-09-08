package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class File_CommitAnalysisRequest extends CommonRequestFieldsSupport {
    private String token;
    private String analysisId;
    private String fid;
    
    public File_CommitAnalysisRequest() {
    }

    public File_CommitAnalysisRequest(String token, String analysisId, String fid) {
        this.token = token;
        this.analysisId = analysisId;
        this.fid = fid;
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

    /**
     * 文件ID
     */
    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
