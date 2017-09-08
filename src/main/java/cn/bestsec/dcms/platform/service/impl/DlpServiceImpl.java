package cn.bestsec.dcms.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.bestsec.dcms.platform.dao.FileAnalysisDao;
import cn.bestsec.dcms.platform.dao.FileLevelValidPeriodDao;
import cn.bestsec.dcms.platform.entity.FileAnalysis;
import cn.bestsec.dcms.platform.entity.FileLevelValidPeriod;
import cn.bestsec.dcms.platform.service.DlpService;
import cn.bestsec.dcms.platform.utils.dlp.DocumentScanParam;
import cn.bestsec.dcms.platform.utils.dlp.DocumentScanResult;
import cn.bestsec.dcms.platform.utils.dlp.SearchTool;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月22日 下午5:14:07
 */
@Service
public class DlpServiceImpl implements DlpService {
    private static Logger logger = LoggerFactory.getLogger(DlpServiceImpl.class);
    @Autowired
    private FileAnalysisDao fileAnalysisDao;
    @Autowired
    private FileLevelValidPeriodDao fileLevelValidPeriodDao;

    @Override
    public void keywordDetect(String analysisId) {
        FileAnalysis analysis = null;
        int count = 0;
        while (count++ < 10) {
            try {
                Thread.sleep(200);
                analysis = fileAnalysisDao.findByAnalysisId(analysisId);
                if (analysis != null) {
                    break;
                }
            } catch (InterruptedException e) {
            }
        }
        if (analysis == null) {
            logger.warn("密点分析检测中断，数据库数据异常");
            return;
        }
        analysis.setExecState(1); // 正在执行
        fileAnalysisDao.saveAndFlush(analysis);
        List<DocumentScanResult> results = new ArrayList<>();

        List<String> keywords = new ArrayList<>();
        List<FileLevelValidPeriod> policys = fileLevelValidPeriodDao.findAll();
        for (FileLevelValidPeriod policy : policys) {
            String kws = policy.getKeyword();
            if (kws != null && !kws.trim().isEmpty()) {
                String[] arr = kws.split("\\|");
                for (String key : arr) {
                    if (key != null && !key.trim().isEmpty()) {
                        keywords.add(key);
                    }
                }
            }
        }
        DocumentScanParam scanParam = new DocumentScanParam();
        scanParam.setTaskid(analysisId);
        scanParam.setKeywords(keywords);
        try {
            int ret = SearchTool.search(analysisId, analysis.getFilePath(), scanParam.toJsonString(), results);
            analysis.setExecState(ret);
            if (ret == 2 && !results.isEmpty()) {
                analysis.setKeywordResult(JSON.toJSONString(results.get(0)));
            }
            fileAnalysisDao.saveAndFlush(analysis);
        } catch (Exception e) {
            analysis.setExecState(3); // 执行失败
            fileAnalysisDao.saveAndFlush(analysis);
        }
    }

    @Override
    public int getKeywordLevel(String keyword) {
        int level = 0;
        List<FileLevelValidPeriod> fileLevelValidPeriods = fileLevelValidPeriodDao.findAll();
        for (FileLevelValidPeriod fileLevelValidPeriod : fileLevelValidPeriods) {
            if (level < fileLevelValidPeriod.getFilelevel()) {
                String keywords = fileLevelValidPeriod.getKeyword();
                if (keywords != null && !keywords.isEmpty() && keywords.contains(keyword)) {
                    String[] ever = keywords.split("\\|");
                    for (String e : ever) {
                        if (e.equals(keyword)) {
                            level = fileLevelValidPeriod.getFilelevel();
                        }
                    }
                }
            }
        }
        return level;
    }

}
