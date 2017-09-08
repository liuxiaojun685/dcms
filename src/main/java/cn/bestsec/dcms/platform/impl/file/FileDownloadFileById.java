package cn.bestsec.dcms.platform.impl.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.File_DownloadFileByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.File_DownloadFileByIdRequest;
import cn.bestsec.dcms.platform.api.model.File_DownloadFileByIdResponse;
import cn.bestsec.dcms.platform.api.support.TokenWrapper;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.dao.ClientLogDao;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.entity.ClientLog;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.LogArchiveService;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;
import cn.bestsec.dcms.platform.utils.LocationUtils;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2017年1月12日 下午3:55:15
 */
@Component
public class FileDownloadFileById implements File_DownloadFileByIdApi {
    @Autowired
    private FileDao fileDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;
    @Autowired
    private ClientLogDao clientLogDao;
    @Autowired
    private LogArchiveService logArchiveService;

    @Override
    public File_DownloadFileByIdResponse execute(File_DownloadFileByIdRequest file_DownloadFileByIdRequest)
            throws ApiException {
        TokenWrapper token = file_DownloadFileByIdRequest.tokenWrapper();
        User user = file_DownloadFileByIdRequest.tokenWrapper().getUser();

        // 获取参数,判断在数据库中是否有该文件的记录
        String fid = file_DownloadFileByIdRequest.getFid();
        File file = fileDao.findByPkFid(fid);
        if (file == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        // 判断是否有访问权限
        if (!securityPolicyService.canAccessFile(user.getUserLevel(), file.getFileLevel())) {
            throw new ApiException(ErrorCode.permissionDenied);
        }
        // 找到文件的存储位置
        StorageLocation location = file.getStorageLocation();

        String cacheDir = SystemProperties.getInstance().getCacheDir();
        String md5 = file.getFileMd5();
        java.io.File attachment = new java.io.File(cacheDir + java.io.File.separator + md5);
        // 如果缓存文件存在并且md5匹配，则免下载
        if (!attachment.exists()) {
            if (LocationUtils.canDownload(location)) {
                if (!LocationUtils.download(location, md5, cacheDir, attachment.getName())) {
                    throw new ApiException(ErrorCode.fileDownloadFailed);
                }
            }
        }

        // 下载的文件名 文件名+状态
        // String attachmentName = TextUtils.changeString(file.getName(),
        // file.getFileState(), file.getFileLevel());
        File_DownloadFileByIdResponse resp = new File_DownloadFileByIdResponse();
        if (attachment.exists()) {
            resp.setDownload(attachment);
            resp.setDownloadName(file.getName());
        }

        String operateType = FileConsts.downLoadType.attachment.getDescription();
        ClientLog clientLog = new ClientLog(token.getRaw().getClient(), file, token.getUser(),
                System.currentTimeMillis(), file_DownloadFileByIdRequest.httpServletRequest().getRemoteAddr(), "",
                operateType, "", 1, "", file.getName(), "");
        clientLogDao.save(clientLog);
        String desc = logArchiveService.parseClientLogDescription(clientLog.getId(), false);
        clientLog.setOperateDescription(desc);
        clientLogDao.save(clientLog);
        return resp;
    }
}
