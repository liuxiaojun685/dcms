package cn.bestsec.dcms.platform.impl.log;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Log_QueryClientRequestLogApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.ClientRequestLogInfo;
import cn.bestsec.dcms.platform.api.model.ClientSimpleInfo;
import cn.bestsec.dcms.platform.api.model.Log_QueryClientRequestLogRequest;
import cn.bestsec.dcms.platform.api.model.Log_QueryClientRequestLogResponse;
import cn.bestsec.dcms.platform.api.model.UserSimpleInfo;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.ClientRequestLogDao;
import cn.bestsec.dcms.platform.dao.RiskLevelPolicyDao;
import cn.bestsec.dcms.platform.entity.ClientRequestLog;
import cn.bestsec.dcms.platform.entity.RiskLevelPolicy;

/**
 * 查询终端请求日志
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月30日下午1:53:02
 */
@Component
public class LogQueryClientRequestLog implements Log_QueryClientRequestLogApi {
    @Autowired
    private ClientRequestLogDao clientRequestLogDao;
    @Autowired
    private RiskLevelPolicyDao riskLevelPolicyDao;

    @Override
    @Transactional
    public Log_QueryClientRequestLogResponse execute(Log_QueryClientRequestLogRequest log_QueryClientRequestLogRequest)
            throws ApiException {
        Specification<ClientRequestLog> spec = new Specification<ClientRequestLog>() {

            @Override
            public Predicate toPredicate(Root<ClientRequestLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<Predicate>();

                if (log_QueryClientRequestLogRequest.getOperateType() != null
                        && !log_QueryClientRequestLogRequest.getOperateType().isEmpty()) {
                    list.add(cb.equal(root.get("operateType").as(String.class),
                            log_QueryClientRequestLogRequest.getOperateType()));
                }

                Predicate[] p = new Predicate[list.size()];
                Predicate predicate = cb.and(list.toArray(p));
                if (log_QueryClientRequestLogRequest.getOperateTimeStart() != null) {
                    predicate = cb.and(predicate, cb.ge(root.get("operateTime").as(Long.class),
                            log_QueryClientRequestLogRequest.getOperateTimeStart()));
                }
                if (log_QueryClientRequestLogRequest.getOperateTimeEnd() != null) {
                    predicate = cb.and(predicate, cb.le(root.get("operateTime").as(Long.class),
                            log_QueryClientRequestLogRequest.getOperateTimeEnd()));
                }

                if (log_QueryClientRequestLogRequest.getKeyword() != null) {
                    Predicate p1 = cb.like(root.get("operateType").as(String.class),
                            "%" + log_QueryClientRequestLogRequest.getKeyword() + "%");
                    Predicate p2 = cb.like(root.get("operateDescription").as(String.class),
                            "%" + log_QueryClientRequestLogRequest.getKeyword() + "%");
                    predicate = cb.and(predicate, cb.or(p1, p2));
                }

                if (log_QueryClientRequestLogRequest.getRiskLevel() != null
                        && log_QueryClientRequestLogRequest.getRiskLevel() > 0) {
                    List<String> types = riskLevelPolicyDao
                            .findByRiskLevel(log_QueryClientRequestLogRequest.getRiskLevel());
                    if (!types.isEmpty()) {
                        In<String> in = cb.in(root.get("operateType").as(String.class));
                        for (String type : types) {
                            in.value(type);
                        }
                        predicate = cb.and(predicate, in);
                    }
                }
                return predicate;
            }

        };

        Pageable pageable = new PageRequest(log_QueryClientRequestLogRequest.getPageNumber(),
                log_QueryClientRequestLogRequest.getPageSize(), Sort.Direction.DESC, "operateTime");
        Page<ClientRequestLog> page = clientRequestLogDao.findAll(spec, pageable);
        List<ClientRequestLog> clientRequestLogs = page.getContent();
        List<ClientRequestLogInfo> clientRequestLogInfos = new ArrayList<>();
        ClientRequestLogInfo clientRequestLogInfo = null;
        UserSimpleInfo userSimpleInfo = null;
        ClientSimpleInfo clientSimpleInfo = null;
        for (ClientRequestLog clientRequestLog : clientRequestLogs) {
            clientRequestLogInfo = new ClientRequestLogInfo();
            clientSimpleInfo = new ClientSimpleInfo();
            clientSimpleInfo.setCid(clientRequestLog.getClient().getPkCid());
            clientSimpleInfo.setIp(clientRequestLog.getClient().getIp());
            clientSimpleInfo.setLevel(clientRequestLog.getClient().getClientLevel());
            clientSimpleInfo.setMac(clientRequestLog.getClient().getMac());
            clientSimpleInfo.setOnline(UserConsts.clientOnline(clientRequestLog.getClient()));
            clientRequestLogInfo.setClient(clientSimpleInfo);
            clientRequestLogInfo.setClientRequestLogId(clientRequestLog.getId());
            clientRequestLogInfo.setCreateTime(clientRequestLog.getCreateTime());
            clientRequestLogInfo.setOperateDescription(clientRequestLog.getOperateDescription());
            clientRequestLogInfo.setOperateResult(clientRequestLog.getOperateResult());
            clientRequestLogInfo.setOperateTime(clientRequestLog.getCreateTime());
            clientRequestLogInfo.setOperateType(clientRequestLog.getOperateType());
            clientRequestLogInfo.setRiskLevel(1);
            RiskLevelPolicy risk = riskLevelPolicyDao.findByOperateType(clientRequestLog.getOperateType());
            if (risk != null) {
                clientRequestLogInfo.setRiskLevel(risk.getRiskLevel());
            }
            clientRequestLogInfo.setReserve(clientRequestLog.getReserve());
            userSimpleInfo = new UserSimpleInfo();
            if (clientRequestLog.getUser() != null) {
                userSimpleInfo.setAccount(clientRequestLog.getUser().getAccount());
                userSimpleInfo.setLevel(clientRequestLog.getUser().getUserLevel());
                userSimpleInfo.setName(clientRequestLog.getUser().getName());
                userSimpleInfo.setOnline(UserConsts.userOnline(clientRequestLog.getUser()));
                userSimpleInfo.setUid(clientRequestLog.getUser().getPkUid());
            }
            clientRequestLogInfo.setUser(userSimpleInfo);
            clientRequestLogInfos.add(clientRequestLogInfo);
        }
        Long totaoElements = page.getTotalElements();
        Log_QueryClientRequestLogResponse resp = new Log_QueryClientRequestLogResponse();
        resp.setTotalElements(new Integer(totaoElements.intValue()));
        resp.setTotalPages(page.getTotalPages());
        resp.setClientRequestLogList(clientRequestLogInfos);
        return resp;
    }

}
