
package cn.bestsec.dcms.platform.impl.client;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Client_UpdateByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Client_UpdateByIdRequest;
import cn.bestsec.dcms.platform.api.model.Client_UpdateByIdResponse;
import cn.bestsec.dcms.platform.consts.ClientConsts;
import cn.bestsec.dcms.platform.dao.ClientDao;
import cn.bestsec.dcms.platform.entity.Client;

/**
 * 修改终端信息
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月26日上午10:45:33
 */
@Component
public class ClientUpdateById implements Client_UpdateByIdApi {

    @Autowired
    private ClientDao clientDao;

    @Override
    @Transactional
    public Client_UpdateByIdResponse execute(Client_UpdateByIdRequest client_UpdateByIdRequest) throws ApiException {
        Client client = clientDao.findByPkCid(client_UpdateByIdRequest.getCid());
        if (client == null || client.getClientState() == ClientConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.clientNotExist);
        }
        if (client_UpdateByIdRequest.getDescription() != null) {
            client.setDescription(client_UpdateByIdRequest.getDescription());
        }
        clientDao.save(client);
        return new Client_UpdateByIdResponse();
    }

}
