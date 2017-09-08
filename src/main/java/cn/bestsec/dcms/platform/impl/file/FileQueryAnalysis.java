package cn.bestsec.dcms.platform.impl.file;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.bestsec.dcms.platform.api.File_QueryAnalysisApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.File_QueryAnalysisRequest;
import cn.bestsec.dcms.platform.api.model.File_QueryAnalysisResponse;
import cn.bestsec.dcms.platform.api.model.KeywordResultItem;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.dao.FileAnalysisDao;
import cn.bestsec.dcms.platform.entity.FileAnalysis;
import cn.bestsec.dcms.platform.service.DlpService;
import cn.bestsec.dcms.platform.utils.dlp.DocumentScanResult;
import cn.bestsec.dcms.platform.utils.dlp.DocumentScanResult.Risk;

/**
 * 查询密点分析结果
 */
@Component
public class FileQueryAnalysis implements File_QueryAnalysisApi {
    @Autowired
    private FileAnalysisDao fileAnalysisDao;
    @Autowired
    private DlpService dlpService;

    @Override
    @Transactional
    public File_QueryAnalysisResponse execute(File_QueryAnalysisRequest request) throws ApiException {
        List<KeywordResultItem> keywordResultList = new ArrayList<>();
        FileAnalysis result = fileAnalysisDao.findByAnalysisId(request.getAnalysisId());
        if (result == null) {
            return new File_QueryAnalysisResponse(keywordResultList, "关键词检测未执行，请耐心等待", 0);
        }
        // if (result.getKeywordResult() == null ||
        // result.getKeywordResult().isEmpty()) {
        // return new File_QueryAnalysisResponse(keywordResultList,
        // "没有检测到敏感关键词，请根据实际情况定义秘密等级", 2);
        // }
        DocumentScanResult scanResult = JSON.parseObject(result.getKeywordResult(), DocumentScanResult.class);
        String description = "";
        int maxLevel = 0;
        int level = 0;
        if (scanResult != null) {
            Map<String, Risk> riskList = scanResult.getRisklist();
            Iterator<Entry<String, Risk>> it = riskList.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, Risk> entry = it.next();
                Risk risk = entry.getValue();
                if (risk.getContent() == null) {
                    risk.setContent(new ArrayList<>());
                } else {
                    level = dlpService.getKeywordLevel(risk.getKeyword());
                }
                keywordResultList.add(new KeywordResultItem(risk.getKeyword(),
                        FileConsts.Level.parse(level).getDescription(), risk.getHitcount(), risk.getContent()));
                if (level > maxLevel) {
                    maxLevel = level;
                }
            }
        }
        // 执行状态 0未执行 1正在执行 2已完成 3执行失败
        if (result.getExecState() == 2) {
            if (!keywordResultList.isEmpty()) {
                description = "建议该文档秘密级别定义为 " + FileConsts.Level.parse(maxLevel).getDescription();
            } else {
                description = "没有检测到敏感关键词，请根据实际情况定义秘密等级";
            }
        } else if (result.getExecState() == 3) {
            description = "关键词检测发生异常，请根据实际情况定义秘密等级";
        } else if (result.getExecState() == 1) {
            description = "关键词检测正在执行，请耐心等待";
        } else if (result.getExecState() == 0) {
            description = "关键词检测未执行，请耐心等待";
        }
        return new File_QueryAnalysisResponse(keywordResultList, description, result.getExecState());
    }

}
