package cn.bestsec.dcms.platform.service;

import java.util.Date;

import cn.bestsec.dcms.platform.api.exception.ApiException;

public interface BackupService {
    boolean backup(int createFrom, String description, Date date) throws ApiException;
}
