package cn.bestsec.dcms.platform.impl.file;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.File_QueryExistByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.File_QueryExistByIdRequest;
import cn.bestsec.dcms.platform.api.model.File_QueryExistByIdResponse;
import cn.bestsec.dcms.platform.consts.CommonConsts;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.entity.File;

/**
 * 10.11 查询文件是否存在或是否不同步
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年2月13日 上午9:23:20
 */
@Component
public class FileQueryExistById implements File_QueryExistByIdApi {

    @Autowired
    private FileDao fileDao;

    @Override
    @Transactional
    public File_QueryExistByIdResponse execute(File_QueryExistByIdRequest file_QueryExistByIdRequest)
            throws ApiException {
        File_QueryExistByIdResponse resp = new File_QueryExistByIdResponse();
        File file = fileDao.findByPkFid(file_QueryExistByIdRequest.getFid());
        // 默认为不同步
        resp.setOutSync(CommonConsts.Bool.no.getInt());
        if (file == null) {
            resp.setExist(CommonConsts.Bool.no.getInt());
        } else {
            resp.setExist(CommonConsts.Bool.yes.getInt());
            if (file_QueryExistByIdRequest.getFileMd5() != null && !file_QueryExistByIdRequest.getFileMd5().isEmpty()) {
                if (file_QueryExistByIdRequest.getFileMd5().equals(file.getFileMd5())) {
                    resp.setOutSync(CommonConsts.Bool.yes.getInt());
                }
            }
        }
        return resp;
    }

}
