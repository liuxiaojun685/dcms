
package cn.bestsec.dcms.platform.impl.client;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Client_DeleteClientPatchApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Client_DeleteClientPatchRequest;
import cn.bestsec.dcms.platform.api.model.Client_DeleteClientPatchResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.ClientPatchDao;
import cn.bestsec.dcms.platform.entity.ClientPatch;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.LocationUtils;

/**
 * 删除补丁
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月26日上午11:59:39
 */
@Component
public class ClientDeleteClientPatch implements Client_DeleteClientPatchApi {

    @Autowired
    private ClientPatchDao clientPatchDao;

    @Override
    @Transactional
    public Client_DeleteClientPatchResponse execute(Client_DeleteClientPatchRequest client_DeleteClientPatchRequest)
            throws ApiException {
        ClientPatch clientPatch = clientPatchDao.findByid(client_DeleteClientPatchRequest.getPatchId());
        if (clientPatch == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        StorageLocation location = clientPatch.getStorageLocation();
        if (location != null && LocationUtils.canDelete(location)) {
            try {
                LocationUtils.delete(location, clientPatch.getName());
            } catch (Throwable e) {
            }
        }
        clientPatchDao.delete(clientPatch);

        AdminLogBuilder adminLogBuilder = client_DeleteClientPatchRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.client_patchDelete)
                .admin(client_DeleteClientPatchRequest.tokenWrapper().getAdmin()).operateDescription(clientPatch.getName());

        return new Client_DeleteClientPatchResponse();
    }

}
