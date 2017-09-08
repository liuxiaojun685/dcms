package cn.bestsec.dcms.platform.impl.securePolicy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SecurePolicy_QueryMarkKeyHistoryApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.MarkKeyHistoryItem;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryMarkKeyHistoryRequest;
import cn.bestsec.dcms.platform.api.model.SecurePolicy_QueryMarkKeyHistoryResponse;
import cn.bestsec.dcms.platform.dao.MarkKeyHistoryDao;
import cn.bestsec.dcms.platform.entity.MarkKeyHistory;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月8日 下午7:16:31
 */
@Component
public class SecurePolicyQueryMarkKeyHistory implements SecurePolicy_QueryMarkKeyHistoryApi {
    @Autowired
    private MarkKeyHistoryDao markKeyHistoryDao;

    @Override
    public SecurePolicy_QueryMarkKeyHistoryResponse execute(SecurePolicy_QueryMarkKeyHistoryRequest request)
            throws ApiException {
        List<MarkKeyHistory> historys = markKeyHistoryDao.findAll(new Sort(Sort.Direction.ASC, "createTime"));
        List<MarkKeyHistoryItem> markKeyHistoryList = new ArrayList<>();
        for (MarkKeyHistory history : historys) {
            markKeyHistoryList.add(new MarkKeyHistoryItem(history.getKeyId(), history.getKeyName(),
                    history.getEnableTime(), history.getDisableTime()));
        }
        return new SecurePolicy_QueryMarkKeyHistoryResponse(markKeyHistoryList);
    }

}
