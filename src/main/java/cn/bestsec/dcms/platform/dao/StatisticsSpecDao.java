package cn.bestsec.dcms.platform.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.dao.entity.StatisticsFileEntity;
import cn.bestsec.dcms.platform.dao.entity.StatisticsWorkflowEntity;
import cn.bestsec.dcms.platform.utils.DButils;

/**
 * 多条件统计图表
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月13日 下午2:48:01
 */
@Repository
public class StatisticsSpecDao {

    public List<StatisticsFileEntity> getFileCount(Integer by, Integer fileLevel, List<String> scopes, Long startTime,
            Long endTime) {
        // 连接数据库
        Connection conn = DButils.getConnection();

        // 拼装查询语句
        String day = "date_format(from_unixtime(f.fileCreateTime/1000), '%Y.%m.%d')";
        String mouth = "date_format(from_unixtime(f.fileCreateTime/1000), '%Y.%m')";
        String year = "date_format(from_unixtime(f.fileCreateTime/1000), '%Y')";
        String sql = "select count(case when f.fileState=2 then 0 end) as classified, count(case when f.fileState=3 then 0 end) as issued, count(case when f.fileState=4 then 0 end) as classifiedChange, count(case when f.fileState=5 then 0 end) as declassified, ";
        if (by == 1) {
            sql += year;
        } else if (by == 2) {
            sql += mouth;
        } else if (by == 3) {
            sql += day;
        }

        sql += " as time from File f where f.fileCreateTime > ? and f.fileCreateTime < ?";
        StringBuffer query = new StringBuffer(sql);
        if (fileLevel != null) {
            query.append(" and f.fileLevel = ?");
        }

        // 统计范围
        if (scopes != null && !scopes.isEmpty()) {
            query.append(" and f.fkFileCreateUid in(");
            for (int i = 0; i < scopes.size(); i++) {
                query.append(" ?");
                if (i != scopes.size() - 1) {

                    query.append(",");
                }
            }
            query.append(")");
        }
        // 按年统计
        if (by == 1) {
            query.append(" group by from_unixtime(f.fileCreateTime/1000, '%Y')");
        } else if (by == 2) {
            query.append(" group by from_unixtime(f.fileCreateTime/1000, '%Y.%m')");
        } else if (by == 3) {
            query.append(" group by from_unixtime(f.fileCreateTime/1000, '%Y.%m.%d')");
        }
        // query.append(", f.fileState");
        // 封装返回结果集
        List<StatisticsFileEntity> statisties = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 根据拼装的sql查询
            ps = conn.prepareStatement(query.toString());
            // 为查询参数赋值
            int k = 1;
            ps.setLong(k++, startTime);
            ps.setLong(k++, endTime);
            if (fileLevel != null) {
                ps.setInt(k++, fileLevel);
            }

            if (scopes != null && !scopes.isEmpty()) {
                for (String uid : scopes) {
                    ps.setString(k++, uid);
                }
            }

            // 得到结果集
            rs = ps.executeQuery();

            while (rs.next()) {
                String time = rs.getString("time");
                Long classified = rs.getLong("classified");
                Long issued = rs.getLong("issued");
                Long classifiedChange = rs.getLong("classifiedChange");
                Long declassified = rs.getLong("declassified");
                StatisticsFileEntity statisty = new StatisticsFileEntity(time, classified, issued, classifiedChange,
                        declassified);
                statisties.add(statisty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
        return statisties;
    }

    public StatisticsWorkflowEntity getWorkflowCount(Integer workflowType, Long startTime, Long endTime) {
        Connection conn = DButils.getConnection();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(
                "SELECT COUNT(case when w.flowState=1 then 0 end) as finish,COUNT(case when w.flowState=0 then 0 end) as unfinish FROM Workflow w where w.workFlowType>0");
        if (workflowType != null && workflowType > 0) {
            sqlBuilder.append(" and w.workFlowType=" + workflowType);
        }
        if (startTime != null) {
            sqlBuilder.append(" and w.createTime>=" + startTime);
        }
        if (endTime != null) {
            sqlBuilder.append(" and w.createTime<=" + endTime);
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 根据拼装的sql查询
            ps = conn.prepareStatement(sqlBuilder.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                Long finish = rs.getLong("finish");
                Long unfinish = rs.getLong("unfinish");
                return new StatisticsWorkflowEntity(finish, unfinish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                ps.close();
                conn.close();
            } catch (SQLException e) {
            }
        }
        return new StatisticsWorkflowEntity(0L, 0L);
    }

}
