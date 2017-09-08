package cn.bestsec.dcms.platform.impl.log;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
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

import cn.bestsec.dcms.platform.api.Log_QueryFileLevelManagementLogApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.ClientLogInfo;
import cn.bestsec.dcms.platform.api.model.ClientSimpleInfo;
import cn.bestsec.dcms.platform.api.model.FileSimpleInfo;
import cn.bestsec.dcms.platform.api.model.Log_QueryFileLevelManagementLogRequest;
import cn.bestsec.dcms.platform.api.model.Log_QueryFileLevelManagementLogResponse;
import cn.bestsec.dcms.platform.api.model.UserSimpleInfo;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.ClientLogDao;
import cn.bestsec.dcms.platform.dao.RiskLevelPolicyDao;
import cn.bestsec.dcms.platform.entity.Client;
import cn.bestsec.dcms.platform.entity.ClientLog;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.RiskLevelPolicy;
import cn.bestsec.dcms.platform.entity.User;

/**
 * 查询终端操作日志
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月5日 下午1:14:31
 */
@Component
public class LogQueryFileLevelManagementLog implements Log_QueryFileLevelManagementLogApi {

    @Autowired
    private ClientLogDao clientLogDao;
    @Autowired
    private RiskLevelPolicyDao riskLevelPolicyDao;

    @Override
    public Log_QueryFileLevelManagementLogResponse execute(Log_QueryFileLevelManagementLogRequest request)
            throws ApiException {
        Specification<ClientLog> spec = new Specification<ClientLog>() {

            @Override
            public Predicate toPredicate(Root<ClientLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                In<String> in = cb.in(root.get("operateType").as(String.class));
                in.value("预定密").value("正式定密").value("文件签发").value("密级变更").value("文件解密");
                list.add(in);

                if (request.getOperateType() != null && !request.getOperateType().isEmpty()) {
                    list.add(cb.equal(root.get("operateType").as(String.class), request.getOperateType()));
                }

                Predicate[] p = new Predicate[list.size()];
                Predicate predicate = cb.and(list.toArray(p));

                if (request.getOperateTimeStart() != null) {
                    predicate = cb.and(predicate,
                            cb.ge(root.get("createTime").as(Long.class), request.getOperateTimeStart()));
                }
                if (request.getOperateTimeEnd() != null) {
                    predicate = cb.and(predicate,
                            cb.le(root.get("createTime").as(Long.class), request.getOperateTimeEnd()));
                }
                return predicate;
            }

        };

        // 获取终端查询分页信息
        Pageable pageable = new PageRequest(request.getPageNumber(), request.getPageSize(), Sort.Direction.DESC,
                "createTime");

        Page<ClientLog> page = clientLogDao.findAll(spec, pageable);

        // 终端日志操作集合
        List<ClientLog> content = page.getContent();
        // 存储返回数据
        List<ClientLogInfo> clientLogList = new ArrayList<ClientLogInfo>();
        ClientLogInfo clientLogInfo = null;
        for (ClientLog clientLog : content) {
            clientLogInfo = new ClientLogInfo();
            clientLogInfo.setOperateDescription(clientLog.getOperateDescription());
            // 操作结果
            clientLogInfo.setOperateResult(clientLog.getOperateResult());
            clientLogInfo.setOperateTime(clientLog.getCreateTime());
            clientLogInfo.setOperateType(clientLog.getOperateType());
            // clientLogInfo.setReserve(clientLog.getReserve());
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
        Log_QueryFileLevelManagementLogResponse resp = new Log_QueryFileLevelManagementLogResponse();
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

}
