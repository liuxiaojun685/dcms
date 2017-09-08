
package cn.bestsec.dcms.platform.impl.client;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Client_DeleteByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Client_DeleteByIdRequest;
import cn.bestsec.dcms.platform.api.model.Client_DeleteByIdResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.ClientConsts;
import cn.bestsec.dcms.platform.dao.ClientDao;
import cn.bestsec.dcms.platform.entity.Client;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 删除终端
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月27日下午1:44:03
 *
 */
@Component
public class ClientDeleteById implements Client_DeleteByIdApi {

    @Autowired
    private ClientDao ClientDao;

    @Override
    @Transactional
    public Client_DeleteByIdResponse execute(Client_DeleteByIdRequest client_DeleteByIdRequest) throws ApiException {
        Client client = ClientDao.findByPkCid(client_DeleteByIdRequest.getCid());
        if (client == null || client.getClientState() == ClientConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.clientNotExist);
        }

        client.setClientState(ClientConsts.State.deleted.getCode());
        ClientDao.save(client);

        AdminLogBuilder adminLogBuilder = client_DeleteByIdRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.client_delete)
                .admin(client_DeleteByIdRequest.tokenWrapper().getAdmin()).operateDescription(client.getMac());

        return new Client_DeleteByIdResponse();
    }

}
