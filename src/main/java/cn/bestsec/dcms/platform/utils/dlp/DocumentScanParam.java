package cn.bestsec.dcms.platform.utils.dlp;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class DocumentScanParam {
    private String taskid;
    private List<String> keywords;
    private int disturbflag = 1;
    private int casesensitivity = 1;
    private int findstop = 0;
    private int embedflag = 1;
    private String andorflag = "or";
    private int keywordspacing = 4;
    private int keywordlimit = 200;

    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("taskid", taskid);
        jsonObject.put("Keywordnum", String.valueOf(keywords.size()));
        for (int i = 0; i < keywords.size(); i++) {
            jsonObject.put("Keyword" + (i + 1), keywords.get(i));
        }
        jsonObject.put("disturbflag", String.valueOf(disturbflag));
        jsonObject.put("casesensitivity", String.valueOf(casesensitivity));
        jsonObject.put("findstop", String.valueOf(findstop));
        jsonObject.put("embedflag", String.valueOf(embedflag));
        jsonObject.put("and-or-flag", andorflag);
        jsonObject.put("findstop", String.valueOf(findstop));
        jsonObject.put("KeywordSpacing", String.valueOf(keywordspacing));
        jsonObject.put("Keyword limit", String.valueOf(keywordlimit));
        return jsonObject.toJSONString();
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public int getDisturbflag() {
        return disturbflag;
    }

    public void setDisturbflag(int disturbflag) {
        this.disturbflag = disturbflag;
    }

    public int getCasesensitivity() {
        return casesensitivity;
    }

    public void setCasesensitivity(int casesensitivity) {
        this.casesensitivity = casesensitivity;
    }

    public int getFindstop() {
        return findstop;
    }

    public void setFindstop(int findstop) {
        this.findstop = findstop;
    }

    public int getEmbedflag() {
        return embedflag;
    }

    public void setEmbedflag(int embedflag) {
        this.embedflag = embedflag;
    }

    public String getAndorflag() {
        return andorflag;
    }

    public void setAndorflag(String andorflag) {
        this.andorflag = andorflag;
    }

    public int getKeywordspacing() {
        return keywordspacing;
    }

    public void setKeywordspacing(int keywordspacing) {
        this.keywordspacing = keywordspacing;
    }

    public int getKeywordlimit() {
        return keywordlimit;
    }

    public void setKeywordlimit(int keywordlimit) {
        this.keywordlimit = keywordlimit;
    }
}
