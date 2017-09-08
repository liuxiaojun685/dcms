package cn.bestsec.dcms.platform.impl.file;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.File_QueryPrivatePermissionByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.File_QueryPrivatePermissionByIdRequest;
import cn.bestsec.dcms.platform.api.model.File_QueryPrivatePermissionByIdResponse;
import cn.bestsec.dcms.platform.api.model.PermissionInfo;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.FileDrmDao;
import cn.bestsec.dcms.platform.dao.FileLevelDecidePolicyDao;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;

/**
 * 10.6 查询个人文件操作权限（通过fid） 终端用户
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月11日 下午4:21:26
 */
@Component
public class FileQueryPrivatePermissionById implements File_QueryPrivatePermissionByIdApi {

    @Autowired
    private FileDao fileDao;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private FileDrmDao fileDrmDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private FileLevelDecidePolicyDao fileLevelDecidePolicyDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;

    @Override
    @Transactional
    public File_QueryPrivatePermissionByIdResponse execute(
            File_QueryPrivatePermissionByIdRequest file_QueryPrivatePermissionByIdRequest) throws ApiException {

        Token token = tokenDao.findByToken(file_QueryPrivatePermissionByIdRequest.getToken());
        User user = token.getUser();
        PermissionInfo permission = securityPolicyService
                .getPrivatePermissionInfo(file_QueryPrivatePermissionByIdRequest.getFid(), user.getPkUid());
        File_QueryPrivatePermissionByIdResponse resp = new File_QueryPrivatePermissionByIdResponse();
        resp.setPermission(permission);
        return resp;
    }
}
