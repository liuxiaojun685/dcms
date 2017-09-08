package cn.bestsec.dcms.platform.impl.log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Log_ExportAdminLogApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Log_ExportAdminLogRequest;
import cn.bestsec.dcms.platform.api.model.Log_ExportAdminLogResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.AdminLogDao;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.AdminLog;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.DateUtils;
import cn.bestsec.dcms.platform.utils.IdUtils;
import cn.bestsec.dcms.platform.utils.Md5Utils;
import cn.bestsec.dcms.platform.utils.SystemProperties;
import cn.bestsec.dcms.platform.utils.export.CSVUtils;

/**
 * 导出管理员日志excel
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月7日 下午3:46:55
 */
@Component
public class LogExportAdminLog implements Log_ExportAdminLogApi {

    @Autowired
    private AdminLogDao adminLogDao;

    @Override
    @Transactional
    public Log_ExportAdminLogResponse execute(Log_ExportAdminLogRequest log_ExportAdminLogRequest) throws ApiException {
        Specification<AdminLog> spec = new Specification<AdminLog>() {

            @Override
            public Predicate toPredicate(Root<AdminLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> list = new ArrayList<Predicate>();

                if (log_ExportAdminLogRequest.getAdminType() != null && log_ExportAdminLogRequest.getAdminType() != 0) {
                    Join<AdminLog, Admin> join = root.join("admin", JoinType.INNER);
                    list.add(cb.equal(join.get("adminType").as(Integer.class),
                            log_ExportAdminLogRequest.getAdminType()));
                }

                if (log_ExportAdminLogRequest.getOperateType() != null
                        && !log_ExportAdminLogRequest.getOperateType().isEmpty()) {
                    list.add(cb.equal(root.get("operateType").as(String.class),
                            log_ExportAdminLogRequest.getOperateType()));
                }

                Predicate[] p = new Predicate[list.size()];
                Predicate predicate = cb.and(list.toArray(p));
                if (log_ExportAdminLogRequest.getOperateTimeStart() != null) {
                    predicate = cb.and(predicate, cb.ge(root.get("operateTime").as(Long.class),
                            log_ExportAdminLogRequest.getOperateTimeStart()));
                }
                if (log_ExportAdminLogRequest.getOperateTimeEnd() != null) {
                    predicate = cb.and(predicate, cb.le(root.get("operateTime").as(Long.class),
                            log_ExportAdminLogRequest.getOperateTimeEnd()));
                }

                if (log_ExportAdminLogRequest.getKeyword() != null) {
                    Predicate p1 = cb.like(root.get("operateType").as(String.class),
                            "%" + log_ExportAdminLogRequest.getKeyword() + "%");
                    Predicate p2 = cb.like(root.get("operateDescription").as(String.class),
                            "%" + log_ExportAdminLogRequest.getKeyword() + "%");
                    predicate = cb.and(predicate, cb.or(p1, p2));
                }

                return predicate;
            }

        };

        List<AdminLog> logs = adminLogDao.findAll(spec);
        File file = exportAdminLog(logs);
        Log_ExportAdminLogResponse resp = new Log_ExportAdminLogResponse();
        resp.setDownload(file);
        resp.setDownloadName(file.getName());
        // 记录操作日志
        AdminLogBuilder adminLogBuilder = log_ExportAdminLogRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.log_exportAdminLog)
                .admin(log_ExportAdminLogRequest.tokenWrapper().getAdmin()).operateDescription();
        /*
         * List<AdminLogDto> adminLogDtos = new ArrayList<>(); for (AdminLog
         * adminLog : findAll) { AdminLogDto dto = new AdminLogDto();
         * dto.setAcount(adminLog.getAdmin().getAccount());
         * dto.setCreateTime(new Date(adminLog.getCreateTime()));
         * dto.setIp(adminLog.getIp());
         * dto.setOperateDescription(adminLog.getOperateDescription());
         * dto.setOperateType(adminLog.getOperateType()); adminLogDtos.add(dto);
         * } ExcelUtils<AdminLogDto> ex = new ExcelUtils<AdminLogDto>();
         * String[] headers = { "管理员账号", "操作类别", "操作详情", "操作日期", "操作IP" };
         * OutputStream out = null; try { out = new
         * FileOutputStream("E://测试.xls"); ex.exportExcel("测试", headers,
         * adminLogDtos, out, "yyyy-MM-dd"); out.close(); } catch
         * (FileNotFoundException e) { e.printStackTrace(); } catch (IOException
         * e) { e.printStackTrace(); }
         */
        return resp;
    }

    private File exportAdminLog(List<AdminLog> logs) {
        LinkedHashMap<String, String> header = new LinkedHashMap<String, String>();
        header.put("acount", "管理员账号");
        header.put("aid", "管理员ID");
        header.put("operateType", "操作类别");
        header.put("operateDescription", "操作详情");
        header.put("operateResult", "操作结果");
        header.put("operateTime", "操作日期");
        header.put("ip", "操作IP");

        List<Map<String, String>> exportData = new ArrayList<>();
        Map<String, String> row = null;
        for (AdminLog log : logs) {
            row = new HashMap<>();
            Admin admin = log.getAdmin();
            String account = "";
            String aid = "";
            if (admin != null) {
                account = admin.getAccount();
                aid = admin.getPkAid();
            }
            row.put("acount", account);
            row.put("aid", aid);
            row.put("operateType", log.getOperateType());
            row.put("operateDescription", log.getOperateDescription());
            row.put("operateResult", log.getOperateResult() == 1 ? "成功" : "失败");
            row.put("operateTime", DateUtils.timeStamp2Date(log.getOperateTime(), null));
            row.put("ip", log.getIp());
            exportData.add(row);
        }
        String path = SystemProperties.getInstance().getCacheDir();
        File file = CSVUtils.createCSVFile(exportData, header, path, IdUtils.randomId());
        String md5 = Md5Utils.getMd5ByFile(file);
        File target = new File(file.getParentFile(), md5 + ".csv");
        file.renameTo(target);
        return target;
    }
}
