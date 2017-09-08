package cn.bestsec.dcms.platform.api.model;

import cn.bestsec.dcms.platform.api.support.CommonRequestFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class User_QueryListRequest extends CommonRequestFieldsSupport {
    private String token;
    private Integer pageNumber;
    private Integer pageSize;
    private String keyword;
    private Integer level;
    private Integer state;
    private Integer onlineState;
    private Integer createFrom;
    private String did;
    private String gid;
    
    public User_QueryListRequest() {
    }

    public User_QueryListRequest(String token, Integer pageNumber, Integer pageSize, String keyword, Integer level, Integer state, Integer onlineState, Integer createFrom, String did, String gid) {
        this.token = token;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.keyword = keyword;
        this.level = level;
        this.state = state;
        this.onlineState = onlineState;
        this.createFrom = createFrom;
        this.did = did;
        this.gid = gid;
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
     * 页号
     */
    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    /**
     * 行数
     */
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 关键词 可以是姓名职位邮箱等
     */
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * 密级 1一般 2重要 3核心
     */
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 用户状态 1已删除 4已锁定
     */
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 在线状态 1在线 2离线
     */
    public Integer getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(Integer onlineState) {
        this.onlineState = onlineState;
    }

    /**
     * 创建方式 1手动创建 2导入创建 3同步创建
     */
    public Integer getCreateFrom() {
        return createFrom;
    }

    public void setCreateFrom(Integer createFrom) {
        this.createFrom = createFrom;
    }

    /**
     * 所属部门ID
     */
    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    /**
     * 所属组ID
     */
    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }
}
