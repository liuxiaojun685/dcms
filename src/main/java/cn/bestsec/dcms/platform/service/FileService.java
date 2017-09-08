package cn.bestsec.dcms.platform.service;

import java.io.File;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.ClassificationInfo;
import cn.bestsec.dcms.platform.api.model.FileInfo;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月6日 下午7:52:32
 */
public interface FileService {
    FileInfo getFileInfo(String fid);

    File filePreclassified(File attachment, ClassificationInfo prop, String uid) throws ApiException;

    File fileClassified(File attachment, ClassificationInfo prop, String uid, String lastFid) throws ApiException;

    File fileIssued(File attachment, ClassificationInfo prop, String uid, String lastFid) throws ApiException;

    File fileDeclassified(File attachment, ClassificationInfo prop, String uid, String lastFid) throws ApiException;

    File fileClassifiedChange(File attachment, ClassificationInfo prop, String uid) throws ApiException;

    File fileRestore(File attachment) throws ApiException;

    cn.bestsec.dcms.platform.entity.File fileUpdate(File attachment, ClassificationInfo prop, String uid, String lastFid)
            throws ApiException;
}
