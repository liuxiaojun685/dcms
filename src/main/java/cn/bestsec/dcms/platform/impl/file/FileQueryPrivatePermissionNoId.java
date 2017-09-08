package cn.bestsec.dcms.platform.impl.file;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.File_QueryPrivatePermissionNoIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.File_QueryPrivatePermissionNoIdRequest;
import cn.bestsec.dcms.platform.api.model.File_QueryPrivatePermissionNoIdResponse;
import cn.bestsec.dcms.platform.api.model.PermissionInfo;
import cn.bestsec.dcms.platform.consts.CommonConsts;
import cn.bestsec.dcms.platform.dao.FileLevelDecidePolicyDao;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;

/**
 * 10.7 查询个人文件操作权限（通过文件属性）
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月12日 上午11:19:15
 */
@Component
public class FileQueryPrivatePermissionNoId implements File_QueryPrivatePermissionNoIdApi {

    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private FileLevelDecidePolicyDao fileLevelDecidePolicyDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;

    @Override
    @Transactional
    public File_QueryPrivatePermissionNoIdResponse execute(
            File_QueryPrivatePermissionNoIdRequest file_QueryPrivatePermissionNoIdRequest) throws ApiException {
        Token token = tokenDao.findByToken(file_QueryPrivatePermissionNoIdRequest.getToken());
        User user = token.getUser();
        boolean isMyCreate = false;
        if (file_QueryPrivatePermissionNoIdRequest.getFileIsMyCreate() == CommonConsts.Bool.yes.getInt()) {
            isMyCreate = true;
        }
        PermissionInfo permission = securityPolicyService.getPrivatePermissionInfo(isMyCreate, file_QueryPrivatePermissionNoIdRequest.getFileState(), file_QueryPrivatePermissionNoIdRequest.getFileLevel(), user.getPkUid());

        File_QueryPrivatePermissionNoIdResponse resp = new File_QueryPrivatePermissionNoIdResponse();
        resp.setPermission(permission);
        return resp;
    }
}
