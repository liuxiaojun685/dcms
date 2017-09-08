package cn.bestsec.dcms.platform.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bestsec.dcms.platform.api.support.CommonResponseFieldsAware;

public interface ApiExecuteService {
    CommonResponseFieldsAware execute(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String apiGroupName,
            String apiName);
}
