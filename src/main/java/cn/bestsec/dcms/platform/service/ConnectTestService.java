package cn.bestsec.dcms.platform.service;

import java.util.List;

import cn.bestsec.dcms.platform.api.exception.ApiException;

public interface ConnectTestService {

    List<String> checkResult(Integer type) throws ApiException;

}
