package cn.bestsec.dcms.platform.impl.file;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.File_QueryDispatchListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.DRMInfo;
import cn.bestsec.dcms.platform.api.model.File_QueryDispatchListRequest;
import cn.bestsec.dcms.platform.api.model.File_QueryDispatchListResponse;
import cn.bestsec.dcms.platform.dao.FileDrmDao;
import cn.bestsec.dcms.platform.entity.FileDrm;

/**
 * 查询分发范围及权限
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月28日下午4:27:52
 */
@Component
public class FileQueryDispatchList implements File_QueryDispatchListApi {
    @Autowired
    private FileDrmDao fileDrmDao;

    @Override
    @Transactional
    public File_QueryDispatchListResponse execute(File_QueryDispatchListRequest file_QueryDispatchListRequest)
            throws ApiException {
        File_QueryDispatchListResponse resp = new File_QueryDispatchListResponse();
        List<FileDrm> fileDrms = fileDrmDao.findByFkFid(file_QueryDispatchListRequest.getFid());
        if (fileDrms == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        List<DRMInfo> drmInfos = new ArrayList<>();
        DRMInfo drmInfo = null;
        for (FileDrm fileDrm : fileDrms) {
            drmInfo = new DRMInfo();
            drmInfo.setContentCopy(fileDrm.getContentCopy());
            drmInfo.setFileCopy(fileDrm.getFileCopy());
            drmInfo.setFileSaveCopy(fileDrm.getFileSaveCopy());
            drmInfo.setContentModify(fileDrm.getContentModify());
            drmInfo.setContentPrint(fileDrm.getContentPrint());
            drmInfo.setContentPrintHideWater(fileDrm.getContentPrintHideWater());
            drmInfo.setContentRead(fileDrm.getContentRead());
            drmInfo.setContentScreenShot(fileDrm.getContentScreenShot());
            drmInfo.setVarId(fileDrm.getFkVarId());
            drmInfo.setVarIdType(fileDrm.getVarIdType());
            drmInfos.add(drmInfo);
        }
        resp.setDrmList(drmInfos);

        return resp;
    }

}
