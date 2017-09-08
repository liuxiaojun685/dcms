package cn.bestsec.dcms.platform.service;

import com.alibaba.fastjson.JSONObject;

public interface ApiPreconditionService {
    /**
     * 初始化Api解析器
     * 
     * @param ramlLocation
     */
    void initApiParser(String ramlLocation);

    /**
     * 是否为有效请求，检查必填字段
     * 
     * @param apiRequest
     * @param apiGroupName
     * @param apiName
     * @return
     */
    boolean isValidRequest(JSONObject apiRequest, String apiGroupName, String apiName);
}
