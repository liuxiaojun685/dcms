package cn.bestsec.dcms.platform.impl.middleware;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Middleware_ClassifyFileApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.ClassificationInfo;
import cn.bestsec.dcms.platform.api.model.FileInfo;
import cn.bestsec.dcms.platform.api.model.MiddlewareFileAttr;
import cn.bestsec.dcms.platform.api.model.Middleware_ClassifyFileRequest;
import cn.bestsec.dcms.platform.api.model.Middleware_ClassifyFileResponse;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.MarkKeyDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.MarkKey;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.service.FileService;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;
import cn.bestsec.dcms.platform.utils.FileUtils;
import cn.bestsec.dcms.platform.utils.IdUtils;
import cn.bestsec.dcms.platform.utils.LocationUtils;
import cn.bestsec.dcms.platform.utils.Md5Utils;
import cn.bestsec.dcms.platform.utils.SMBUtil;
import cn.bestsec.dcms.platform.utils.SystemProperties;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity;
import cn.bestsec.dcms.platform.utils.classification.ClassificationTool;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年5月15日 下午2:39:43
 */
@Component
public class MiddlewareClassifyFile implements Middleware_ClassifyFileApi {
    static Logger logger = LoggerFactory.getLogger(MiddlewareClassifyFile.class);
    @Autowired
    private FileDao fileDao;
    @Autowired
    private UserDao UserDao;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;
    @Autowired
    private MarkKeyDao markKeyDao;

    @Override
    @Transactional
    public Middleware_ClassifyFileResponse execute(Middleware_ClassifyFileRequest request) throws ApiException {
        String fileName = null;
        File src = null;
        File target = null;

        // 操作类型 0去除密标 1预定密 2正式定密 3文件签发 4密级变更 5文件解密
        int opType = request.getOpType();

        // 文件传输类型 0通过fid免传输文件 1通过http上传文件 2通过共享目录方式上传文件 3URL下载
        int fileTransType = 0;
        if (request.getFileTransType() != null) {
            fileTransType = request.getFileTransType();
        }

        // 将远程文件存储到本地缓存mw目录，文件名为md5
        if (fileTransType == 0) {
            // 通过fid方式
            String fid = request.getFid();
            if (fid != null && !fid.isEmpty()) {
                cn.bestsec.dcms.platform.entity.File file = fileDao.findByPkFid(fid);
                if (file == null) {
                    throw new ApiException(ErrorCode.targetNotExist);
                }
                StorageLocation location = file.getStorageLocation();
                String cacheDir = SystemProperties.getInstance().getCacheDir() + java.io.File.separator + "mw";
                java.io.File attachment = new java.io.File(cacheDir + java.io.File.separator + file.getFileMd5());
                // 如果缓存文件存在并且md5匹配，则免下载
                if (!attachment.exists()) {
                    if (LocationUtils.canDownload(location)) {
                        if (!LocationUtils.download(location, file.getFileMd5(), cacheDir, attachment.getName())) {
                            throw new ApiException(ErrorCode.fileDownloadFailed);
                        }
                    }
                }
                fileName = file.getName();
                src = attachment;
            } else {
                throw new ApiException(ErrorCode.targetNotExist);
            }
        } else if (fileTransType == 1) {
            // 通过HTTP上传文件方式
            fileName = request.getAttachmentName();
            src = request.getAttachment();
        } else if (fileTransType == 2) {
            // 通过共享目录上传文件方式
            String shareAddr = request.getShareAddr();
            String shareAccount = request.getShareAccount();
            String sharePasswd = request.getSharePasswd();
            String sharePath = request.getSharePath();
            String cacheDir = SystemProperties.getInstance().getCacheDir() + File.separator + "mw";
            fileName = new File(sharePath).getName();
            String tmpName = IdUtils.randomId() + ".tmp";
            File tmp = new File(cacheDir, tmpName);
            if (!SMBUtil.smbGet(shareAddr, shareAccount, sharePasswd, new File(sharePath).getParent(), fileName,
                    cacheDir, tmp.getName())) {
                logger.warn("共享文件保存失败，addr=" + shareAddr + " account=" + shareAccount + " passwd=" + sharePasswd
                        + " path=" + sharePath + " savePath=" + tmp.getPath());
                throw new ApiException(ErrorCode.fileUploadFailed);
            }
            logger.info("共享文件保存成功，savePath=" + tmp.getPath());
            String md5 = Md5Utils.getMd5ByFile(tmp);
            src = new File(cacheDir, md5);
            tmp.renameTo(src);
            if (tmp.exists()) {
                tmp.delete();
            }
        } else if (fileTransType == 3) {
            // 通过URL下载方式
            String cacheDir = SystemProperties.getInstance().getCacheDir() + File.separator + "mw";
            String tmpName = IdUtils.randomId() + ".tmp";
            File tmp = new File(cacheDir, tmpName);

            String url = request.getUrl();
            try {
                url = URLDecoder.decode(request.getUrl(), "utf-8");
                FileUtils.copyURLToFile(new URL(url), tmp);
            } catch (MalformedURLException e) {
                logger.warn("URL文件保存失败，url=" + url);
                throw new ApiException(ErrorCode.fileUploadFailed);
            } catch (IOException e) {
                logger.warn("URL文件保存失败，url=" + url + ", target=" + tmp.getAbsolutePath());
                throw new ApiException(ErrorCode.fileUploadFailed);
            }

            logger.info("URL文件保存成功，savePath=" + tmp.getPath());
            String md5 = Md5Utils.getMd5ByFile(tmp);
            src = new File(cacheDir, md5);
            tmp.renameTo(src);
            if (tmp.exists()) {
                tmp.delete();
            }
        } else {
            throw new ApiException(ErrorCode.invalidArgument);
        }

        if (src == null || !src.exists()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        // 对上传的文件进行相应操作
        if (opType == 0) {
            // 去除密标
            if (!ClassificationTool.isClassification(src)) {
                throw new ApiException(ErrorCode.fileIsUnclassified);
            }
            ClassificationEntity header = ClassificationTool.readProperties(src);
            String fid = "";
            if (header != null && header.classificationAttribute != null) {
                fid = ClassificationTool.readProperties(src).classificationAttribute.classificationIdentifier;
            }
            java.io.File tmp = new java.io.File(src.getParent(), IdUtils.randomId() + ".tmp");
            logger.info("去除密标，src=" + src.getPath() + " target=" + tmp.getPath() + " fid=" + fid);
            // 获取密钥
            MarkKey key = markKeyDao.findEnableKeyByVersion(1);
            ClassificationTool.cancelClassification(src, tmp, key);
            target = new java.io.File(tmp.getParentFile(), Md5Utils.getMd5ByFile(tmp));
            tmp.renameTo(target);
            if (tmp.exists()) {
                tmp.delete();
            }

        } else if (opType == 1) {
            // 预定密
            String fid = IdUtils.randomId();
            MiddlewareFileAttr attr = request.getFileAttr();
            if (attr == null) {
                throw new ApiException(ErrorCode.invalidArgument);
            }

            ClassificationEntity entity = ClassificationTool.readProperties(src);

            // classificationStatus.value=0或1，允许预定密
            if (!securityPolicyService.canClassified(entity.classificationAttribute.classificationIdentifier,
                    entity.classificationAttribute.classificationStatus.value,
                    entity.classificationAttribute.classificationLevel, opType)) {
                throw new ApiException(ErrorCode.operationNotPermitted);
            }
            ClassificationInfo prop = new ClassificationInfo(fid, fileName, attr.getUrgency(), attr.getFileLevel(),
                    attr.getFileValidPeriod(), attr.getDurationType(), attr.getDurationDescription(),
                    attr.getFileDecryptTime(), attr.getFileScope(), attr.getFileScopeDesc(), attr.getBasis(),
                    attr.getBasisType(), attr.getBasisDesc(), attr.getIssueNumber(), attr.getDocNumber(),
                    attr.getDuplicationAmount(), attr.getFileMajorUnit(), attr.getFileMinorUnit(), attr.getPermission(),
                    attr.getMarkVersion(), attr.getDescription(), attr.getBusiness());
            String uid = request.tokenWrapper().getUser().getPkUid();
            if (attr.getUid() != null && !attr.getUid().isEmpty() && !attr.getUid().equals("0")) {
                uid = attr.getUid();
            }
            target = fileService.filePreclassified(src, prop, uid);
            logger.info("中间件调用 加密标(预定密)，src=" + src.getPath() + " target=" + target.getPath() + " fid=" + fid);

        } else if (opType >= 2 && opType <= 5) {
            if (!ClassificationTool.isClassification(src)) {
                throw new ApiException(ErrorCode.fileIsUnclassified);
            }

            MiddlewareFileAttr attr = request.getFileAttr();
            if (attr == null) {
                throw new ApiException(ErrorCode.invalidArgument);
            }

            String fid = ClassificationTool.readProperties(src).classificationAttribute.classificationIdentifier;
            FileInfo info = fileService.getFileInfo(fid);
            if (info == null) {
                throw new ApiException(ErrorCode.fileStateException);
            }
            if (!securityPolicyService.canClassified(fid, info.getFileState(), info.getFileLevel(), opType)) {
                throw new ApiException(ErrorCode.operationNotPermitted);
            }
            ClassificationInfo prop = new ClassificationInfo(fid, fileName, attr.getUrgency(), attr.getFileLevel(),
                    attr.getFileValidPeriod(), attr.getDurationType(), attr.getDurationDescription(),
                    attr.getFileDecryptTime(), attr.getFileScope(), attr.getFileScopeDesc(), attr.getBasis(),
                    attr.getBasisType(), attr.getBasisDesc(), attr.getIssueNumber(), attr.getDocNumber(),
                    attr.getDuplicationAmount(), attr.getFileMajorUnit(), attr.getFileMinorUnit(), attr.getPermission(),
                    attr.getMarkVersion(), attr.getDescription(), attr.getBusiness());
            String uid = request.tokenWrapper().getUser().getPkUid();
            if (attr.getUid() != null && !attr.getUid().isEmpty() && !attr.getUid().equals("0")) {
                uid = attr.getUid();
            }

            if (opType == 2) {
                if (info.getFileState() != FileConsts.State.makeSecret.getCode()) {
                    prop.setFid(IdUtils.randomId());
                }
                target = fileService.fileClassified(src, prop, uid, fid);
                logger.info("中间件调用 正式定密，src=" + src.getPath() + " target=" + target.getPath() + " fid=" + fid);
            } else if (opType == 3) {
                if (info.getFileState() != FileConsts.State.dispatch.getCode()) {
                    prop.setFid(IdUtils.randomId());
                }
                target = fileService.fileIssued(src, prop, uid, fid);
                logger.info("中间件调用 文件签发，src=" + src.getPath() + " target=" + target.getPath() + " fid=" + fid);
            } else if (opType == 4) {
                target = fileService.fileClassifiedChange(src, prop, uid);
                logger.info("中间件调用 密级变更，src=" + src.getPath() + " target=" + target.getPath() + " fid=" + fid);
            } else if (opType == 5) {
                if (info.getFileState() != FileConsts.State.unSecret.getCode()) {
                    prop.setFid(IdUtils.randomId());
                }
                target = fileService.fileDeclassified(src, prop, uid, fid);
                logger.info("中间件调用 文件解密，src=" + src.getPath() + " target=" + target.getPath() + " fid=" + fid);
            }

        }

        // 目标文件返回方式
        String returnUrl = "";
        Integer fileReturnType = request.getFileReturnType();
        if (fileReturnType == null || fileReturnType == 0) {
            // 不返回
        } else {
            if (target == null || !target.exists()) {
                throw new ApiException(ErrorCode.fileClassifyError);
            }
            if (fileReturnType == 1) {
                // 提供目标文件的http下载地址
                String webappsPath;
                try {
                    webappsPath = request.httpServletRequest().getServletContext().getResource("/").getPath();
                    File download = new File(webappsPath + File.separator + "mwdown", target.getName());
                    if (!download.exists()) {
                        FileUtils.copyFile(target.getPath(), download.getPath());
                    }
                    returnUrl = "/dcms/mwdown/" + download.getName();
                    logger.info("提供下载，local=" + download.getPath() + " url=" + returnUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else if (fileReturnType == 2) {
                // 目标文件保存到客户提供的共享目录
                String shareAddr = request.getShareAddr();
                String shareAccount = request.getShareAccount();
                String sharePasswd = request.getSharePasswd();
                String sharePath = request.getSharePath();
                String shareParent = new File(sharePath).getParent();

                if (!SMBUtil.existFile(shareAddr, shareAccount, sharePasswd, shareParent, target.getName())) {
                    SMBUtil.smbPut(shareAddr, shareAccount, sharePasswd, target.getPath(), shareParent,
                            target.getName());
                }
                returnUrl = shareParent + File.separator + target.getName();
            }
        }

        return new Middleware_ClassifyFileResponse(returnUrl, fileName);
    }

}
