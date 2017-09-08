package cn.bestsec.dcms.platform.impl.client;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.Client_DownloadNewestVersionApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Client_DownloadNewestVersionRequest;
import cn.bestsec.dcms.platform.api.model.Client_DownloadNewestVersionResponse;
import cn.bestsec.dcms.platform.dao.ClientPatchDao;
import cn.bestsec.dcms.platform.entity.ClientPatch;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.utils.LocationUtils;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月15日 下午12:10:04
 */
@Component
public class ClientDownloadNewestVersion implements Client_DownloadNewestVersionApi {
    @Autowired
    private ClientPatchDao clientPatchDao;

    @Override
    @Transactional
    public Client_DownloadNewestVersionResponse execute(
            Client_DownloadNewestVersionRequest client_DownloadNewestVersionRequest) throws ApiException {

        String osType = client_DownloadNewestVersionRequest.getOsType();
        Integer versionType = client_DownloadNewestVersionRequest.getVersionType();
        List<ClientPatch> clientPatchs = clientPatchDao.findByOsTypeAndVersionTypeOrderByVersionCodeDesc(osType,
                versionType);
        if (clientPatchs.isEmpty()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        ClientPatch clientPatch = clientPatchs.get(0);
        StorageLocation location = clientPatch.getStorageLocation();
        String cacheDir = SystemProperties.getInstance().getCacheDir();
        String md5 = clientPatch.getFileMd5();
        File attachment = new File(cacheDir + File.separator + md5);
        // 如果缓存文件存在并且md5匹配，则免下载
        if (!attachment.exists()) {
            if (LocationUtils.canDownload(location)) {
                if (!LocationUtils.download(location, md5, cacheDir, attachment.getName())) {
                    throw new ApiException(ErrorCode.fileDownloadFailed);
                }
            }
        }

        Client_DownloadNewestVersionResponse resp = new Client_DownloadNewestVersionResponse();
        if (attachment.exists()) {
            resp.setDownload(attachment);
            resp.setDownloadName(clientPatch.getName());
        }
        return resp;
    }

}
