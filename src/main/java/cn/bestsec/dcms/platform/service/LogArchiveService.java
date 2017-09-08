package cn.bestsec.dcms.platform.service;

import java.util.Date;

import cn.bestsec.dcms.platform.api.exception.ApiException;

public interface LogArchiveService {
    int logArchive(Integer auto, Date date) throws ApiException;

    String parseClientLogDescription(Integer clientLogId, boolean autoDesensity);

    boolean isFileDesensity(Integer fileLevel);
}
