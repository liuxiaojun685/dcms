package cn.bestsec.dcms.platform.impl.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.User_QueryMessageHistoryApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.MessageHistoryInfo;
import cn.bestsec.dcms.platform.api.model.User_QueryMessageHistoryRequest;
import cn.bestsec.dcms.platform.api.model.User_QueryMessageHistoryResponse;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.UserMessageHistoryDao;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.UserMessageHistory;

@Component
public class UserQueryMessageHistory implements User_QueryMessageHistoryApi {
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private UserMessageHistoryDao userMessageHistoryDao;

    @Override
    @Transactional
    public User_QueryMessageHistoryResponse execute(User_QueryMessageHistoryRequest user_QueryMessageHistoryRequest)
            throws ApiException {
        Token token = tokenDao.findByToken(user_QueryMessageHistoryRequest.getToken());
        Specification<UserMessageHistory> spec = new Specification<UserMessageHistory>() {

            @Override
            public Predicate toPredicate(Root<UserMessageHistory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("fkUid").as(String.class), token.getUser().getPkUid());
            }
        };

        Pageable pageable = new PageRequest(user_QueryMessageHistoryRequest.getPageNumber(),
                user_QueryMessageHistoryRequest.getPageSize(), Sort.Direction.DESC, "createTime");
        Page<UserMessageHistory> page = userMessageHistoryDao.findAll(spec, pageable);
        List<UserMessageHistory> historys = page.getContent();
        List<MessageHistoryInfo> messageHistoryList = new ArrayList<>();
        for (UserMessageHistory history : historys) {
            messageHistoryList.add(new MessageHistoryInfo(history.getId(), history.getMessage(), history.getMsgType(),
                    history.getUrl(), history.getCreateTime()));
        }

        return new User_QueryMessageHistoryResponse(page.getTotalPages(), (int) page.getTotalElements(),
                messageHistoryList);
    }

}
