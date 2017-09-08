package cn.bestsec.dcms.platform.service;

import org.springframework.stereotype.Service;

@Service
public interface DlpService {
    void keywordDetect(String analysisId);
    int getKeywordLevel(String keyword);
}
