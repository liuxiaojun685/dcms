package cn.bestsec.dcms.platform.impl.client;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.Client_AddClientPatchApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Client_AddClientPatchRequest;
import cn.bestsec.dcms.platform.api.model.Client_AddClientPatchResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.ClientPatchDao;
import cn.bestsec.dcms.platform.entity.ClientPatch;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.LocationUtils;
import cn.bestsec.dcms.platform.utils.Md5Utils;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月15日 上午11:17:03
 */
@Component
public class ClientAddClientPatch implements Client_AddClientPatchApi {
    static Logger logger = LoggerFactory.getLogger(ClientAddClientPatch.class);

    @Autowired
    private ClientPatchDao clientPatchDao;
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public Client_AddClientPatchResponse execute(Client_AddClientPatchRequest client_AddClientPatchRequest)
            throws ApiException {
        Client_AddClientPatchRequest clientPatchDto = client_AddClientPatchRequest;

        ClientPatch clientPatch = new ClientPatch();
        // 获得系统配置的补丁上传路径
        StorageLocation location = systemConfigService.getPatchLocation();

        File attachment = clientPatchDto.getAttachment();
        if (attachment == null) {
            throw new ApiException(ErrorCode.attachmentNotFound);
        }
        String remoteName = clientPatchDto.getAttachmentName();
        if (clientPatchDto.getName() != null && !clientPatchDto.getName().isEmpty()) {
            remoteName = clientPatchDto.getName();
        }

        AdminLogBuilder adminLogBuilder = client_AddClientPatchRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.client_patchUpload)
                .admin(clientPatchDto.tokenWrapper().getAdmin()).operateDescription(remoteName);

        // 上传远程
        if (LocationUtils.canUpload(location)) {
            if (!LocationUtils.upload(location, attachment.getPath(), attachment.getName())) {
                logger.error("补丁文件存储失败，请检查配置");
                throw new ApiException(ErrorCode.fileSaveFailed);
            }
        }

        clientPatch.setFileSize(attachment.length());
        clientPatch.setFileMd5(Md5Utils.getMd5ByFile(attachment));
        clientPatch.setStorageLocation(location);
        clientPatch.setCreateTime(System.currentTimeMillis());
        clientPatch.setDescription(clientPatchDto.getDescription());
        clientPatch.setName(remoteName);
        clientPatch.setOsType(clientPatchDto.getOsType());
        clientPatch.setVersionCode(clientPatchDto.getVersionCode());
        clientPatch.setVersionName(clientPatchDto.getVersionName());
        clientPatch.setVersionType(clientPatchDto.getVersionType());

        clientPatchDao.save(clientPatch);
        return new Client_AddClientPatchResponse(clientPatch.getId());
    }

}
