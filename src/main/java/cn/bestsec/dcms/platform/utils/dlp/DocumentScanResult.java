package cn.bestsec.dcms.platform.utils.dlp;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class DocumentScanResult {
    private String filepath;
    private String keywordnum;
    private Map<String, Risk> risklist;

    public Risk risk(String keyword) {
        for (Risk risk : risklist.values()) {
            if (risk.getKeyword().equals(keyword)) {
                return risk;
            }
        }
        return null;
    }

    public String getFilepath() {
        return filepath;
    }

    public JSONObject toJsonObject() {
        return (JSONObject) JSON.toJSON(this);
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getKeywordnum() {
        return keywordnum;
    }

    public void setKeywordnum(String keywordnum) {
        this.keywordnum = keywordnum;
    }

    public Map<String, Risk> getRisklist() {
        return risklist;
    }

    public void setRisklist(Map<String, Risk> risklist) {
        this.risklist = risklist;
    }

    public static class Risk {
        private List<String> content;
        private String hitcount;
        private String keyword;

        public List<String> getContent() {
            return content;
        }

        public void setContent(List<String> content) {
            this.content = content;
        }

        public String getHitcount() {
            return hitcount;
        }

        public void setHitcount(String hitcount) {
            this.hitcount = hitcount;
        }

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
    }
}
