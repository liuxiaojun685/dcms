package cn.bestsec.dcms.platform.impl.client;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Client_AddApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Client_AddRequest;
import cn.bestsec.dcms.platform.api.model.Client_AddResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.ClientConsts;
import cn.bestsec.dcms.platform.dao.ClientDao;
import cn.bestsec.dcms.platform.entity.Client;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.IdUtils;

/**
 * 添加一个终端，只有管理员可以添加
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月26日 下午2:53:19
 */
@Component
public class ClientAdd implements Client_AddApi {

    @Autowired
    private ClientDao clientDao;

    @Override
    @Transactional
    public Client_AddResponse execute(Client_AddRequest client_AddRequest) throws ApiException {
        AdminLogBuilder adminLogBuilder = client_AddRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.client_add)
                .admin(client_AddRequest.tokenWrapper().getAdmin()).operateDescription(client_AddRequest.getMac());

        // 终端是否已存在
        Client client = clientDao.findByMacAndClientStateNot(client_AddRequest.getMac(),
                ClientConsts.State.deleted.getCode());
        if (client == null) {
            client = new Client();
            // UUID
            client.setPkCid(IdUtils.randomId(IdUtils.prefix_client_id));
            client.setClientState(0);
            client.setMac(client_AddRequest.getMac());
        }
        client.setIp(client_AddRequest.getIp());
        client.setOsType(client_AddRequest.getOsType());
        client.setVersionCode(client_AddRequest.getVersionCode());
        client.setVersionName(client_AddRequest.getVersionName());
        client.setVersionType(client_AddRequest.getVersionType());
        client.setClientLevel(client_AddRequest.getLevel());
        client.setPcName(client_AddRequest.getPcName());
        clientDao.save(client);

        Client_AddResponse resp = new Client_AddResponse();
        resp.setCid(client.getPkCid());
        return resp;
    }

}
