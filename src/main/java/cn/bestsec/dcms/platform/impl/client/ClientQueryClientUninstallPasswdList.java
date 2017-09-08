
package cn.bestsec.dcms.platform.impl.client;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Client_QueryClientUninstallPasswdListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Client_QueryClientUninstallPasswdListRequest;
import cn.bestsec.dcms.platform.api.model.Client_QueryClientUninstallPasswdListResponse;
import cn.bestsec.dcms.platform.api.model.UninstallPasswdInfo;
import cn.bestsec.dcms.platform.dao.ClientUninstallPasswdDao;
import cn.bestsec.dcms.platform.entity.ClientUninstallPasswd;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 查询卸载密码列表
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月26日下午3:17:26
 */
@Component
public class ClientQueryClientUninstallPasswdList implements Client_QueryClientUninstallPasswdListApi {

    @Autowired
    private ClientUninstallPasswdDao clientUninstallPasswdDao;

    @Override
    @Transactional
    public Client_QueryClientUninstallPasswdListResponse execute(
            Client_QueryClientUninstallPasswdListRequest client_QueryClientUninstallPasswdListRequest)
            throws ApiException {
        Pageable pageable = new PageRequest(client_QueryClientUninstallPasswdListRequest.getPageNumber(),
                client_QueryClientUninstallPasswdListRequest.getPageSize(), Sort.Direction.DESC, "createTime");
        Page<ClientUninstallPasswd> page = clientUninstallPasswdDao.findAll(pageable);

        if (page == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        List<ClientUninstallPasswd> clientUninstallPasswds = page.getContent();
        List<UninstallPasswdInfo> uninstallPasswdInfos = new ArrayList<>();
        UninstallPasswdInfo info = null;
        for (int i = 0; i < clientUninstallPasswds.size(); i++) {
            info = new UninstallPasswdInfo();
            info.setPasswdId(clientUninstallPasswds.get(i).getId());
            info.setPasswd(StringEncrypUtil.decrypt(clientUninstallPasswds.get(i).getPasswd()));
            info.setCreateTime(clientUninstallPasswds.get(i).getCreateTime());
            info.setDescription(clientUninstallPasswds.get(i).getDescription());
            info.setSyncNum(clientUninstallPasswds.get(i).getSyncNum());
            info.setUnsyncNum(clientUninstallPasswds.get(i).getUnsyncNum());
            uninstallPasswdInfos.add(info);
        }
        // 获取总行数
        Long totaoElements = page.getTotalElements();
        Client_QueryClientUninstallPasswdListResponse resp = new Client_QueryClientUninstallPasswdListResponse();
        resp.setTotalElements(new Integer(totaoElements.intValue()));
        resp.setTotalPages(page.getTotalPages());
        resp.setUninstallPasswdList(uninstallPasswdInfos);
        return resp;
    }

}
