package cn.bestsec.dcms.platform.api.model;

import java.util.List;
import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsSupport;

/**
 * 自动生成的API请求/响应Model类，不要手动修改
 */
public class File_FileTrackResponse extends CommonResponseFieldsSupport {
    private Integer code;
    private String msg;
    private List<FileTrackNode> nodes;
    private List<FileTrackLink> links;
    
    public File_FileTrackResponse() {
    }

    public File_FileTrackResponse(List<FileTrackNode> nodes, List<FileTrackLink> links) {
        this.nodes = nodes;
        this.links = links;
    }

    public File_FileTrackResponse(Integer code, String msg, List<FileTrackNode> nodes, List<FileTrackLink> links) {
        this.code = code;
        this.msg = msg;
        this.nodes = nodes;
        this.links = links;
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
     * 数据节点
     */
    public List<FileTrackNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<FileTrackNode> nodes) {
        this.nodes = nodes;
    }

    /**
     * 节点关系
     */
    public List<FileTrackLink> getLinks() {
        return links;
    }

    public void setLinks(List<FileTrackLink> links) {
        this.links = links;
    }
}
