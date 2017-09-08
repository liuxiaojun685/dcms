package cn.bestsec.dcms.platform.impl.client;

import java.io.File;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Client_QueryNewestVersionApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Client_QueryNewestVersionRequest;
import cn.bestsec.dcms.platform.api.model.Client_QueryNewestVersionResponse;
import cn.bestsec.dcms.platform.api.model.PatchInfo;
import cn.bestsec.dcms.platform.dao.ClientPatchDao;
import cn.bestsec.dcms.platform.entity.ClientPatch;
import cn.bestsec.dcms.platform.entity.StorageLocation;

/**
 * 获取最新补丁
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月26日上午9:43:03
 */
@Component
public class ClientQueryNewestVersion implements Client_QueryNewestVersionApi {

    @Autowired
    private ClientPatchDao clientPatchDao;

    @Override
    @Transactional
    public Client_QueryNewestVersionResponse execute(Client_QueryNewestVersionRequest client_QueryNewestVersionRequest)
            throws ApiException {
        List<ClientPatch> clientPatchs = clientPatchDao.findByOsTypeAndVersionTypeOrderByVersionCodeDesc(
                client_QueryNewestVersionRequest.getOsType(), client_QueryNewestVersionRequest.getVersionType());
        if (clientPatchs.isEmpty()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        ClientPatch clientPatch = clientPatchs.get(0);
        PatchInfo patchInfo = new PatchInfo();
        patchInfo.setCreateTime(clientPatch.getCreateTime());
        patchInfo.setDescription(clientPatch.getDescription());

        StorageLocation storageLocation = clientPatch.getStorageLocation();
        patchInfo.setLocation(storageLocation.getFilePath());
        patchInfo.setMd5(clientPatch.getFileMd5());
        patchInfo.setName(clientPatch.getName());
        patchInfo.setOsType(clientPatch.getOsType());
        patchInfo.setPatchId(clientPatch.getId());
        patchInfo.setSize(clientPatch.getFileSize());
        patchInfo.setVersionCode(clientPatch.getVersionCode());
        patchInfo.setVersionName(clientPatch.getVersionName());
        patchInfo.setVersionType(clientPatch.getVersionType());

        // 下载补丁
        String path = storageLocation.getFilePath() + File.separator + clientPatch.getName();
        // DownloadUtils.downloadAsServer(path,
        // client_QueryNewestVersionRequest.httpServletRequest());
        Client_QueryNewestVersionResponse resp = new Client_QueryNewestVersionResponse();
        resp.setPatchInfo(patchInfo);
        return resp;
    }

}
