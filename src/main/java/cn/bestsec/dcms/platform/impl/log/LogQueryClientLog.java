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

import cn.bestsec.dcms.platform.api.Log_QueryClientLogApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.ClientLogInfo;
import cn.bestsec.dcms.platform.api.model.ClientSimpleInfo;
import cn.bestsec.dcms.platform.api.model.FileSimpleInfo;
import cn.bestsec.dcms.platform.api.model.Log_QueryClientLogRequest;
import cn.bestsec.dcms.platform.api.model.Log_QueryClientLogResponse;
import cn.bestsec.dcms.platform.api.model.UserSimpleInfo;
import cn.bestsec.dcms.platform.consts.ClientLogOp;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.ClientLogDao;
import cn.bestsec.dcms.platform.dao.RiskLevelPolicyDao;
import cn.bestsec.dcms.platform.entity.Client;
import cn.bestsec.dcms.platform.entity.ClientLog;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.RiskLevelPolicy;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.LogArchiveService;

/**
 * 查询终端操作日志
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月5日 下午1:14:31
 */
@Component
public class LogQueryClientLog implements Log_QueryClientLogApi {

    @Autowired
    private ClientLogDao clientLogDao;
    @Autowired
    private RiskLevelPolicyDao riskLevelPolicyDao;
    @Autowired
    private LogArchiveService logArchiveService;

    @Override
    @Transactional
    public Log_QueryClientLogResponse execute(Log_QueryClientLogRequest log_QueryClientLogRequest) throws ApiException {
        Specification<ClientLog> spec = new Specification<ClientLog>() {

            @Override
            public Predicate toPredicate(Root<ClientLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<Predicate>();
                In<String> in = cb.in(root.get("operateType").as(String.class));
                if (log_QueryClientLogRequest.getOperateTypes() != null
                        && !log_QueryClientLogRequest.getOperateTypes().isEmpty()) {
                    for (String operateType : log_QueryClientLogRequest.getOperateTypes()) {
                        in.value(operateType);
                    }
                    list.add(in);
                }

                Predicate[] p = new Predicate[list.size()];
                Predicate predicate = cb.and(list.toArray(p));

                if (log_QueryClientLogRequest.getOperateTimeStart() != null) {
                    predicate = cb.and(predicate, cb.ge(root.get("createTime").as(Long.class),
                            log_QueryClientLogRequest.getOperateTimeStart()));
                }
                if (log_QueryClientLogRequest.getOperateTimeEnd() != null) {
                    predicate = cb.and(predicate, cb.le(root.get("createTime").as(Long.class),
                            log_QueryClientLogRequest.getOperateTimeEnd()));
                }

                if (log_QueryClientLogRequest.getKeyword() != null
                        && !log_QueryClientLogRequest.getKeyword().isEmpty()) {
                    Predicate p1 = cb.like(root.get("operateType").as(String.class),
                            "%" + log_QueryClientLogRequest.getKeyword() + "%");
                    Predicate p2 = cb.like(root.get("operateDescription").as(String.class),
                            "%" + log_QueryClientLogRequest.getKeyword() + "%");
                    Join<ClientLog, User> userNameI = root.join("user", JoinType.INNER);
                    Predicate userName = cb.like(userNameI.get("name").as(String.class),
                            "%" + log_QueryClientLogRequest.getKeyword() + "%");
                    predicate = cb.and(predicate, cb.or(p1, p2, userName));
                }

                if (log_QueryClientLogRequest.getRiskLevel() != null && log_QueryClientLogRequest.getRiskLevel() > 0) {
                    List<String> types = riskLevelPolicyDao.findByRiskLevel(log_QueryClientLogRequest.getRiskLevel());
                    if (!types.isEmpty()) {
                        In<String> ins = cb.in(root.get("operateType").as(String.class));
                        for (String type : types) {
                            ins.value(type);
                        }
                        predicate = cb.and(predicate, ins);
                    }
                }
                return predicate;
            }

        };

        // 获取终端查询分页信息
        Pageable pageable = new PageRequest(log_QueryClientLogRequest.getPageNumber(),
                log_QueryClientLogRequest.getPageSize(), Sort.Direction.DESC, "createTime");

        Page<ClientLog> page = clientLogDao.findAll(spec, pageable);

        // 终端日志操作集合
        List<ClientLog> content = page.getContent();
        // 存储返回数据
        List<ClientLogInfo> clientLogList = new ArrayList<ClientLogInfo>();
        ClientLogInfo clientLogInfo = null;
        for (ClientLog clientLog : content) {
            clientLogInfo = new ClientLogInfo();
            clientLogInfo.setOperateDescription(logArchiveService.parseClientLogDescription(clientLog.getId(), true));
            clientLogInfo.setOperateResult(clientLog.getOperateResult());
            clientLogInfo.setOperateTime(clientLog.getCreateTime());
            clientLogInfo.setOperateType(clientLog.getOperateType());
            clientLogInfo.setOperateWay(clientLog.getOperateWay());

            clientLogInfo.setRiskLevel(1);
            RiskLevelPolicy risk = riskLevelPolicyDao.findByOperateType(clientLog.getOperateType());
            if (risk != null) {
                clientLogInfo.setRiskLevel(risk.getRiskLevel());
            }

            // 终端信息
            Client client = clientLog.getClient();
            ClientSimpleInfo clientSimpleInfo = new ClientSimpleInfo();
            if (client != null) {
                clientSimpleInfo.setCid(client.getPkCid());
                clientSimpleInfo.setLevel(client.getClientLevel());
                clientSimpleInfo.setMac(client.getMac());
                clientSimpleInfo.setOnline(UserConsts.clientOnline(client));
            }
            clientSimpleInfo.setIp(clientLog.getIp());
            clientLogInfo.setClient(clientSimpleInfo);
            // 用户信息
            User user = clientLog.getUser();
            UserSimpleInfo userSimpleInfo = new UserSimpleInfo();
            if (user != null) {
                userSimpleInfo.setAccount(user.getAccount());
                userSimpleInfo.setLevel(user.getUserLevel());
                userSimpleInfo.setName(user.getName());
                userSimpleInfo.setOnline(UserConsts.userOnline(user));
                userSimpleInfo.setUid(user.getPkUid());
            }
            clientLogInfo.setUser(userSimpleInfo);
            // 操作源文件
            File fileByFkSrcFile = clientLog.getFileByFkSrcFid();
            FileSimpleInfo srcFile = new FileSimpleInfo();
            // 为返回值设置文件数据
            setFileInfoValue(srcFile, fileByFkSrcFile, clientLog.getReserve());
            clientLogInfo.setSrcFile(srcFile);

            // // 操作目标文件
            // File fileByFkDesFile = clientLog.getFileByFkDesFid();
            // FileSimpleInfo desFile = new FileSimpleInfo();
            // // 为返回值设置文件数据
            // setFileInfoValue(desFile, fileByFkDesFile,
            // clientLog.getReserve());
            // clientLogInfo.setDesFile(desFile);
            clientLogList.add(clientLogInfo);

        }
        // 获取总行数
        Long totaoElements = page.getTotalElements();
        Log_QueryClientLogResponse resp = new Log_QueryClientLogResponse();
        resp.setTotalElements(new Integer(totaoElements.intValue()));
        resp.setTotalPages(page.getTotalPages());
        resp.setClientLogList(clientLogList);
        return resp;
    }

    /**
     * 为源文件和目标文件设置值
     * 
     * @param fileInfo
     * @param file
     * @param fileName
     */
    private void setFileInfoValue(FileSimpleInfo fileInfo, File file, String fileName) {
        if (file != null) {
            fileInfo.setFid(file.getPkFid());
            fileInfo.setFileLevel(file.getFileLevel());
            fileInfo.setFileName(file.getName());
            fileInfo.setUrgency(file.getUrgency());
        } else {
            fileInfo.setFileName(fileName);
        }
    }

    private String parseDescription(ClientLog clientLog) {
        String description = "";
        String userName = clientLog.getUser().getName();
        ClientLogOp operateType = ClientLogOp.parse(clientLog.getOperateType());
        String result = clientLog.getOperateResult() == 1 ? "成功" : "失败";
        if (clientLog.getOperateResult() != 1 && clientLog.getReserve() != null && !clientLog.getReserve().isEmpty()) {
            result += "，原因：" + clientLog.getReserve();
        }
        switch (operateType) {
        case read:
        case edit:
        case screenshot:
        case paste:
        case delete:
        case filePreclassified:
        case fileClassified:
        case fileIssued:
        case fileClassifiedChange:
        case fileDeclassified:
        case fileRestore:
        case file_download:
            description = String.format(operateType.getFormat(), userName, clientLog.getSrcName(), result);
            break;
        case print:
        case send:
            description = String.format(operateType.getFormat(), userName, clientLog.getOperateWay(),
                    clientLog.getSrcName(), result);
            break;
        case copy:
        case saveAs:
        case rename:
            description = String.format(operateType.getFormat(), userName, clientLog.getSrcName(),
                    clientLog.getDesName(), result);
            break;
        default:
            description = clientLog.getOperateDescription();
            break;
        }
        return description;
    }

}
