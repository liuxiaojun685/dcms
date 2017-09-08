package cn.bestsec.dcms.platform.impl.log;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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

import cn.bestsec.dcms.platform.api.Log_QueryAdminLogApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.AdminLogInfo;
import cn.bestsec.dcms.platform.api.model.AdminSimpleInfo;
import cn.bestsec.dcms.platform.api.model.Log_QueryAdminLogRequest;
import cn.bestsec.dcms.platform.api.model.Log_QueryAdminLogResponse;
import cn.bestsec.dcms.platform.dao.AdminLogDao;
import cn.bestsec.dcms.platform.dao.RiskLevelPolicyDao;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.AdminLog;
import cn.bestsec.dcms.platform.entity.RiskLevelPolicy;

/**
 * 查询管理员日志
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月30日下午1:51:30
 */
@Component
public class LogQueryAdminLog implements Log_QueryAdminLogApi {
    @Autowired
    private AdminLogDao adminLogDao;
    @Autowired
    private RiskLevelPolicyDao riskLevelPolicyDao;

    @Override
    @Transactional
    public Log_QueryAdminLogResponse execute(Log_QueryAdminLogRequest log_QueryAdminLogRequest) throws ApiException {
        Specification<AdminLog> spec = new Specification<AdminLog>() {

            @Override
            public Predicate toPredicate(Root<AdminLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<Predicate>();

                if (log_QueryAdminLogRequest.getAdminType() != null && log_QueryAdminLogRequest.getAdminType() != 0) {
                    Join<AdminLog, Admin> join = root.join("admin", JoinType.INNER);
                    list.add(
                            cb.equal(join.get("adminType").as(Integer.class), log_QueryAdminLogRequest.getAdminType()));
                }

                if (log_QueryAdminLogRequest.getOperateType() != null
                        && !log_QueryAdminLogRequest.getOperateType().isEmpty()) {
                    list.add(cb.equal(root.get("operateType").as(String.class),
                            log_QueryAdminLogRequest.getOperateType()));
                }

                Predicate[] p = new Predicate[list.size()];
                Predicate predicate = cb.and(list.toArray(p));
                if (log_QueryAdminLogRequest.getOperateTimeStart() != null) {
                    predicate = cb.and(predicate, cb.ge(root.get("operateTime").as(Long.class),
                            log_QueryAdminLogRequest.getOperateTimeStart()));
                }
                if (log_QueryAdminLogRequest.getOperateTimeEnd() != null) {
                    predicate = cb.and(predicate, cb.le(root.get("operateTime").as(Long.class),
                            log_QueryAdminLogRequest.getOperateTimeEnd()));
                }

                if (log_QueryAdminLogRequest.getKeyword() != null) {
                    Predicate p1 = cb.like(root.get("operateType").as(String.class),
                            "%" + log_QueryAdminLogRequest.getKeyword() + "%");
                    Predicate p2 = cb.like(root.get("operateDescription").as(String.class),
                            "%" + log_QueryAdminLogRequest.getKeyword() + "%");
                    predicate = cb.and(predicate, cb.or(p1, p2));
                }

                if (log_QueryAdminLogRequest.getRiskLevel() != null && log_QueryAdminLogRequest.getRiskLevel() > 0) {
                    List<String> types = riskLevelPolicyDao.findByRiskLevel(log_QueryAdminLogRequest.getRiskLevel());
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

        Pageable pageable = new PageRequest(log_QueryAdminLogRequest.getPageNumber(),
                log_QueryAdminLogRequest.getPageSize(), Sort.Direction.DESC, "operateTime");
        Page<AdminLog> page = adminLogDao.findAll(spec, pageable);
        List<AdminLog> adminLogs = page.getContent();
        List<AdminLogInfo> adminLogInfos = new ArrayList<>();
        AdminLogInfo adminLogInfo = null;
        AdminSimpleInfo adminSimpleInfo = null;
        for (AdminLog adminLog : adminLogs) {
            adminLogInfo = new AdminLogInfo();
            adminSimpleInfo = new AdminSimpleInfo();
            if (adminLog.getAdmin() != null) {
                adminSimpleInfo.setAccount(adminLog.getAdmin().getAccount());
                adminSimpleInfo.setAdminType(adminLog.getAdmin().getAdminType());
                adminSimpleInfo.setAid(adminLog.getAdmin().getPkAid());
                adminSimpleInfo.setName(adminLog.getAdmin().getName());
            }
            adminLogInfo.setAdmin(adminSimpleInfo);
            adminLogInfo.setAdminLogId(adminLog.getId());
            adminLogInfo.setCreateTime(adminLog.getCreateTime());
            adminLogInfo.setIp(adminLog.getIp());
            adminLogInfo.setOperateDescription(adminLog.getOperateDescription());
            adminLogInfo.setOperateResult(adminLog.getOperateResult());
            adminLogInfo.setOperateTime(adminLog.getOperateTime());
            adminLogInfo.setOperateType(adminLog.getOperateType());
            adminLogInfo.setRiskLevel(1);
            RiskLevelPolicy risk = riskLevelPolicyDao.findByOperateType(adminLog.getOperateType());
            if (risk != null) {
                adminLogInfo.setRiskLevel(risk.getRiskLevel());
            }
            adminLogInfo.setReserve(adminLog.getReserve());
            adminLogInfos.add(adminLogInfo);
        }
        Long totaoElements = page.getTotalElements();
        Log_QueryAdminLogResponse resp = new Log_QueryAdminLogResponse();
        resp.setTotalElements(new Integer(totaoElements.intValue()));
        resp.setTotalPages(page.getTotalPages());
        resp.setAdminLogList(adminLogInfos);
        return resp;
    }

}
