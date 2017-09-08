package cn.bestsec.dcms.platform.impl.file;

import java.io.File;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.File_CommitAnalysisApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.File_CommitAnalysisRequest;
import cn.bestsec.dcms.platform.api.model.File_CommitAnalysisResponse;
import cn.bestsec.dcms.platform.dao.FileAnalysisDao;
import cn.bestsec.dcms.platform.dao.MarkKeyDao;
import cn.bestsec.dcms.platform.entity.FileAnalysis;
import cn.bestsec.dcms.platform.entity.MarkKey;
import cn.bestsec.dcms.platform.service.DlpService;
import cn.bestsec.dcms.platform.utils.FileUtils;
import cn.bestsec.dcms.platform.utils.classification.ClassificationTool;

/**
 * 上传原始文件，执行密点分析
 */
@Component
public class FileCommitAnalysis implements File_CommitAnalysisApi {
    @Autowired
    private FileAnalysisDao fileAnalysisDao;
    @Autowired
    private DlpService dlpService;
    @Autowired
    private MarkKeyDao markKeyDao;

    @Override
    @Transactional
    public File_CommitAnalysisResponse execute(File_CommitAnalysisRequest request) throws ApiException {
        File attachment = request.getAttachment();
        if (attachment == null) {
            throw new ApiException(ErrorCode.attachmentNotFound);
        }

        String suffix = FileUtils.getSuffix(request.getAttachmentName());
        File src = new File(attachment.getParentFile(), request.getAnalysisId() + suffix);
        if (ClassificationTool.isClassification(attachment)) {
            // 获取密钥
            MarkKey key = markKeyDao.findEnableKeyByVersion(1);
            ClassificationTool.cancelClassification(attachment, src, key);
        } else {
            attachment.renameTo(src);
        }

        FileAnalysis result = new FileAnalysis(request.getAnalysisId(), request.getFid(), attachment.getName(),
                src.getName(), src.getPath(), "", "", 0);
        fileAnalysisDao.saveAndFlush(result);

        new Thread(new Runnable() {

            @Override
            public void run() {
                dlpService.keywordDetect(request.getAnalysisId());
            }
        }).start();

        return new File_CommitAnalysisResponse();
    }

}
