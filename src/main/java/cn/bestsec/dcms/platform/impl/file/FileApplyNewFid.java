package cn.bestsec.dcms.platform.impl.file;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.File_ApplyNewFidApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.File_ApplyNewFidRequest;
import cn.bestsec.dcms.platform.api.model.File_ApplyNewFidResponse;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.utils.IdUtils;

/**
 * 10.8 申请新的文件fid 终端用户
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月12日 下午1:58:40
 */
@Component
public class FileApplyNewFid implements File_ApplyNewFidApi {

    @Autowired
    private TokenDao tokenDao;

    @Override
    @Transactional
    public File_ApplyNewFidResponse execute(File_ApplyNewFidRequest file_ApplyNewFidRequest) throws ApiException {
        // 生成一个文件id
        String fid = IdUtils.randomId();
        File_ApplyNewFidResponse resp = new File_ApplyNewFidResponse();
        resp.setFid(fid);
        return resp;
    }

}
