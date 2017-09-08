package cn.bestsec.dcms.platform.impl.client;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Client_ChangeClientUninstallPasswdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Client_ChangeClientUninstallPasswdRequest;
import cn.bestsec.dcms.platform.api.model.Client_ChangeClientUninstallPasswdResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.ClientUninstallPasswdDao;
import cn.bestsec.dcms.platform.entity.ClientUninstallPasswd;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 修改卸载密码,只有系统管理员可以修改密码。
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月27日 上午9:33:45
 */
@Component
public class ClientChangeClientUninstallPasswd implements Client_ChangeClientUninstallPasswdApi {

    @Autowired
    private ClientUninstallPasswdDao clientUninstallPasswdDao;

    @Override
    @Transactional
    public Client_ChangeClientUninstallPasswdResponse execute(
            Client_ChangeClientUninstallPasswdRequest client_ChangeClientUninstallPasswdRequest) throws ApiException {
        ClientUninstallPasswd uninstPasswd = new ClientUninstallPasswd();
        uninstPasswd.setPasswd(StringEncrypUtil.encrypt(client_ChangeClientUninstallPasswdRequest.getPasswd()));
        uninstPasswd.setDescription(client_ChangeClientUninstallPasswdRequest.getDescription());
        uninstPasswd.setCreateTime(System.currentTimeMillis());
        clientUninstallPasswdDao.save(uninstPasswd);

        AdminLogBuilder adminLogBuilder = client_ChangeClientUninstallPasswdRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.client_uninstallPwd)
                .admin(client_ChangeClientUninstallPasswdRequest.tokenWrapper().getAdmin()).operateDescription();

        return new Client_ChangeClientUninstallPasswdResponse();
    }

}
