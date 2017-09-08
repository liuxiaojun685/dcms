package cn.bestsec.dcms.platform.impl.client;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Client_DeleteInBatchesApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Client_DeleteInBatchesRequest;
import cn.bestsec.dcms.platform.api.model.Client_DeleteInBatchesResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.ClientConsts;
import cn.bestsec.dcms.platform.consts.ResultType;
import cn.bestsec.dcms.platform.dao.AdminLogDao;
import cn.bestsec.dcms.platform.dao.ClientDao;
import cn.bestsec.dcms.platform.entity.Client;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 批量删除终端 系统管理员
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月9日 下午4:25:22
 */
@Component
public class ClientDeleteInBatches implements Client_DeleteInBatchesApi {

    @Autowired
    private ClientDao clientDao;
    @Autowired
    private AdminLogDao adminLogDao;

    @Override
    @Transactional
    public Client_DeleteInBatchesResponse execute(Client_DeleteInBatchesRequest client_DeleteInBatchesRequest)
            throws ApiException {
        List<String> cIds = client_DeleteInBatchesRequest.getCidList();
        for (String cId : cIds) {
            Client client = clientDao.findByPkCid(cId);
            if (client == null || client.getClientState() == ClientConsts.State.deleted.getCode()) {
                continue;
            }
            client.setClientState(ClientConsts.State.deleted.getCode());
            clientDao.save(client);

            AdminLogBuilder adminLogBuilder = new AdminLogBuilder();
            adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.client_delete)
                    .admin(client_DeleteInBatchesRequest.tokenWrapper().getAdmin())
                    .ip(client_DeleteInBatchesRequest.httpServletRequest().getRemoteAddr())
                    .operateDescription(client.getMac())
                    .operateResult(ResultType.success.getCode(), ErrorCode.success.getReason());
            adminLogDao.save(adminLogBuilder.build());
        }
        return new Client_DeleteInBatchesResponse();
    }

}
