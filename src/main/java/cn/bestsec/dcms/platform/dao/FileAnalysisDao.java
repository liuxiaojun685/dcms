package cn.bestsec.dcms.platform.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.bestsec.dcms.platform.entity.FileAnalysis;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月6日 下午7:28:32
 */
public interface FileAnalysisDao extends JpaRepository<FileAnalysis, Integer>, JpaSpecificationExecutor<FileAnalysis> {

    FileAnalysis findByAnalysisId(String analysisId);

}
