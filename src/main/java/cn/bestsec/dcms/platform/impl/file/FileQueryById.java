package cn.bestsec.dcms.platform.impl.file;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.File_QueryByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.FileInfo;
import cn.bestsec.dcms.platform.api.model.File_QueryByIdRequest;
import cn.bestsec.dcms.platform.api.model.File_QueryByIdResponse;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.service.FileService;

/**
 * 精确查询文件属性
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月28日下午3:40:41
 */
@Component
public class FileQueryById implements File_QueryByIdApi {
    @Autowired
    private FileDao fileDao;
    @Autowired
    private UserDao UserDao;
    @Autowired
    private FileService fileService;

    @Override
    @Transactional
    public File_QueryByIdResponse execute(File_QueryByIdRequest file_QueryByIdRequest) throws ApiException {
        File_QueryByIdResponse resp = new File_QueryByIdResponse();
        FileInfo file = fileService.getFileInfo(file_QueryByIdRequest.getFid());
        if (file == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        resp.setFileCreateTime(file.getFileCreateTime());
        resp.setFileCreateUserName(file.getFileCreateUserName());
        resp.setFileDecryptTime(file.getFileDecryptTime());
        resp.setFileDispatchRoleName(file.getFileDispatchRoleName());
        resp.setFileDispatchTime(file.getFileDispatchTime());
        resp.setFid(file.getFid());
        resp.setFileLevelChangeTime(file.getFileLevelChangeTime());
        resp.setFileLevelDecideRoleName(file.getFileLevelDecideRoleName());
        resp.setFileLocation(file.getFileLocation());
        resp.setBasis(file.getBasis());
        resp.setBasisDesc(file.getBasisDesc());
        resp.setBasisType(file.getBasisType());
        resp.setDescription(file.getDescription());
        resp.setDocNumber(file.getDocNumber());
        resp.setDuplicationAmount(file.getDuplicationAmount());
        resp.setDurationDescription(file.getDurationDescription());
        resp.setDurationType(file.getDurationType());
        resp.setFileLevel(file.getFileLevel());
        resp.setFileLevelDecideTime(file.getFileLevelDecideTime());
        resp.setFileMajorUnit(file.getFileMajorUnit());
        resp.setFileMinorUnit(file.getFileMinorUnit());
        resp.setFileMd5(file.getFileMd5());
        resp.setFileName(file.getFileName());
        resp.setFileScope(file.getFileScope());
        resp.setFileScopeDesc(file.getFileScopeDesc());
        resp.setFileSize(file.getFileSize());
        resp.setFileState(file.getFileState());
        resp.setFileValidPeriod(file.getFileValidPeriod());
        resp.setHistoryList(file.getHistoryList());
        resp.setIssueNumber(file.getIssueNumber());
        resp.setMarkVersion(file.getMarkVersion());
        resp.setPermission(file.getPermission());
        resp.setUrgency(file.getUrgency());
        resp.setBusiness(file.getBusiness());
        return resp;
    }

}
