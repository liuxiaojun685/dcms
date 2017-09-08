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

import cn.bestsec.dcms.platform.api.Client_QueryClientPatchListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Client_QueryClientPatchListRequest;
import cn.bestsec.dcms.platform.api.model.Client_QueryClientPatchListResponse;
import cn.bestsec.dcms.platform.api.model.PatchInfo;
import cn.bestsec.dcms.platform.dao.ClientPatchDao;
import cn.bestsec.dcms.platform.entity.ClientPatch;
import cn.bestsec.dcms.platform.entity.StorageLocation;
import cn.bestsec.dcms.platform.utils.LocationUtils;

/**
 * 查询补丁列表 只有系统管理员可以查询
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月26日 下午4:24:22
 */
@Component
public class ClientQueryClientPatchList implements Client_QueryClientPatchListApi {

    @Autowired
    private ClientPatchDao clientPatchDao;

    @Override
    @Transactional
    public Client_QueryClientPatchListResponse execute(
            Client_QueryClientPatchListRequest client_QueryClientPatchListRequest) throws ApiException {
        // 分页数据
        Pageable pageable = new PageRequest(client_QueryClientPatchListRequest.getPageNumber(),
                client_QueryClientPatchListRequest.getPageSize(), Sort.Direction.DESC, "createTime");
        Page<ClientPatch> page = clientPatchDao.findAll(pageable);
        List<ClientPatch> clientPatchList = page.getContent();
        if (clientPatchList == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        // 响应数据包装
        List<PatchInfo> patchList = new ArrayList<PatchInfo>();
        PatchInfo patch = null;
        for (ClientPatch clientPatch : clientPatchList) {
            patch = new PatchInfo();
            patch.setCreateTime(clientPatch.getCreateTime());
            patch.setDescription(clientPatch.getDescription());
            patch.setName(clientPatch.getName());
            patch.setOsType(clientPatch.getOsType());
            patch.setPatchId(clientPatch.getId());
            patch.setSize(clientPatch.getFileSize());
            // 获取补丁路径
            StorageLocation storageLocation = clientPatch.getStorageLocation();
            patch.setLocation(LocationUtils.toUrl(storageLocation));
            patch.setVersionCode(clientPatch.getVersionCode());
            patch.setVersionName(clientPatch.getVersionName());
            patch.setVersionType(clientPatch.getVersionType());
            patchList.add(patch);
        }
        // 获取总行数
        Long totaoElements = page.getTotalElements();
        Client_QueryClientPatchListResponse resp = new Client_QueryClientPatchListResponse();
        resp.setTotalElements(new Integer(totaoElements.intValue()));
        resp.setTotalPages(page.getTotalPages());
        resp.setPatchList(patchList);
        return resp;
    }

}
