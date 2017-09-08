package cn.bestsec.dcms.platform.impl.securePolicy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_QueryMarkKeyListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.AdminSimpleInfo;
import cn.bestsec.dcms.platform.api.model.MarkKeyItem;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryMarkKeyListRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryMarkKeyListResponse;
import cn.bestsec.dcms.platform.dao.MarkKeyDao;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.MarkKey;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月8日 下午7:16:31
 */
@Component
public class SecurePolicyQueryMarkKeyList implements SecurePolicy_QueryMarkKeyListApi {
    @Autowired
    private MarkKeyDao markKeyDao;

    @Override
    public SecurePolicy_QueryMarkKeyListResponse execute(SecurePolicy_QueryMarkKeyListRequest request)
            throws ApiException {
        List<MarkKey> markKeys = markKeyDao.findAll(new Sort(Direction.DESC, "createTime"));
        List<MarkKeyItem> markKeyList = new ArrayList<>();
        for (MarkKey key : markKeys) {
            Admin admin = key.getAdmin();
            AdminSimpleInfo adminInfo = new AdminSimpleInfo();
            if (admin != null) {
                adminInfo = new AdminSimpleInfo(admin.getPkAid(), admin.getAccount(), admin.getName(),
                        admin.getAdminType(), admin.getDescription());
            }
            markKeyList.add(new MarkKeyItem(key.getKeyId(), key.getKeyName(), key.getKeyLength(), key.getCreateTime(),
                    key.getCreateFrom(), key.getEnable(), adminInfo, key.getMarkVersion()));
        }
        return new SecurePolicy_QueryMarkKeyListResponse(markKeyList);
    }

}
