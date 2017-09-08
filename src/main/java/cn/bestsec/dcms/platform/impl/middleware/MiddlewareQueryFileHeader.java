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

import cn.bestsec.dcms.platform.api.Middleware_QueryFileHeaderApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.FileInfo;
import cn.bestsec.dcms.platform.api.model.Middleware_QueryFileHeaderRequest;
import cn.bestsec.dcms.platform.api.model.Middleware_QueryFileHeaderResponse;
import cn.bestsec.dcms.platform.service.FileService;
import cn.bestsec.dcms.platform.utils.FileUtils;
import cn.bestsec.dcms.platform.utils.IdUtils;
import cn.bestsec.dcms.platform.utils.SMBUtil;
import cn.bestsec.dcms.platform.utils.SystemProperties;
import cn.bestsec.dcms.platform.utils.classification.ClassificationEntity;
import cn.bestsec.dcms.platform.utils.classification.ClassificationTool;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年5月15日 下午2:39:43
 */
@Component
public class MiddlewareQueryFileHeader implements Middleware_QueryFileHeaderApi {
    static Logger logger = LoggerFactory.getLogger(MiddlewareQueryFileHeader.class);
    @Autowired
    private FileService fileService;

    @Override
    @Transactional
    public Middleware_QueryFileHeaderResponse execute(Middleware_QueryFileHeaderRequest request) throws ApiException {
        FileInfo fileInfo = null;
        String fid = null;
        int fileTransType = 0;
        if (request.getFileTransType() != null) {
            fileTransType = request.getFileTransType();
        }

        if (fileTransType == 0) {
            // 通过fid方式
            fid = request.getFid();
        } else if (fileTransType == 1) {
            // 通过HTTP上传文件方式
            File attachment = request.getAttachment();
            if (!ClassificationTool.isClassification(attachment)) {
                throw new ApiException(ErrorCode.fileIsUnclassified);
            }
            ClassificationEntity prop = ClassificationTool.readProperties(attachment);
            fid = prop.classificationAttribute.classificationIdentifier;
        } else if (fileTransType == 2) {
            // 通过共享目录上传文件方式
            String shareAddr = request.getShareAddr();
            String shareAccount = request.getShareAccount();
            String sharePasswd = request.getSharePasswd();
            String sharePath = request.getSharePath();
            String cacheDir = SystemProperties.getInstance().getCacheDir() + File.separator + "mw";
            String fileName = new File(sharePath).getName();
            String tmpName = IdUtils.randomId() + ".tmp";
            File tmp = new File(cacheDir, tmpName);
            if (!SMBUtil.smbGet(shareAddr, shareAccount, sharePasswd, "/", sharePath, cacheDir, tmp.getName())) {
                logger.warn("共享文件保存失败，addr=" + shareAddr + " account=" + shareAccount + " passwd=" + sharePasswd
                        + " path=" + sharePath + " savePath=" + tmp.getPath());
            }
            logger.info("共享文件保存成功，savePath=" + tmp.getPath());
            if (!ClassificationTool.isClassification(tmp)) {
                throw new ApiException(ErrorCode.fileIsUnclassified);
            }
            ClassificationEntity prop = ClassificationTool.readProperties(tmp);
            fid = prop.classificationAttribute.classificationIdentifier;
            tmp.delete();
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
            if (!ClassificationTool.isClassification(tmp)) {
                throw new ApiException(ErrorCode.fileIsUnclassified);
            }
            ClassificationEntity prop = ClassificationTool.readProperties(tmp);
            fid = prop.classificationAttribute.classificationIdentifier;
            if (tmp.exists()) {
                tmp.delete();
            }
        }

        if (fid != null) {
            logger.info("文件标识，fid=" + fid);
            if (fid != null && !fid.isEmpty()) {
                fileInfo = fileService.getFileInfo(fid);
            }
        }

        if (fileInfo == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        return new Middleware_QueryFileHeaderResponse(fileInfo);
    }

}
