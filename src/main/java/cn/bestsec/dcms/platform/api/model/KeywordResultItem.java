package cn.bestsec.dcms.platform.api.model;

import java.util.List;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class KeywordResultItem {
    private String keyword;
    private String level;
    private String hitCount;
    private List<String> content;
    
    public KeywordResultItem() {
    }

    public KeywordResultItem(String keyword, String level, String hitCount, List<String> content) {
        this.keyword = keyword;
        this.level = level;
        this.hitCount = hitCount;
        this.content = content;
    }

    /**
     * 关键词
     */
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * 密级
     */
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 命中次数
     */
    public String getHitCount() {
        return hitCount;
    }

    public void setHitCount(String hitCount) {
        this.hitCount = hitCount;
    }

    /**
     * 内容
     */
    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
