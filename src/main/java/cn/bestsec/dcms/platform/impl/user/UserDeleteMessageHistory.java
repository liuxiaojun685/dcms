package cn.bestsec.dcms.platform.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.User_DeleteMessageHistoryApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.User_DeleteMessageHistoryRequest;
import cn.bestsec.dcms.platform.api.model.User_DeleteMessageHistoryResponse;
import cn.bestsec.dcms.platform.dao.UserMessageHistoryDao;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月29日 下午12:27:43
 */
@Component
public class UserDeleteMessageHistory implements User_DeleteMessageHistoryApi {
    @Autowired
    private UserMessageHistoryDao userMessageHistoryDao;

    @Override
    @Transactional
    public User_DeleteMessageHistoryResponse execute(User_DeleteMessageHistoryRequest user_DeleteMessageHistoryRequest)
            throws ApiException {
        userMessageHistoryDao.delete(user_DeleteMessageHistoryRequest.getMsgId());
        return new User_DeleteMessageHistoryResponse();
    }

}
