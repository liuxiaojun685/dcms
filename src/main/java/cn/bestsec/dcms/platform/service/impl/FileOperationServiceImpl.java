package cn.bestsec.dcms.platform.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.StatisticsFileDao;
import cn.bestsec.dcms.platform.entity.StatisticsFile;
import cn.bestsec.dcms.platform.service.FileOperationService;
import cn.bestsec.dcms.platform.utils.DateUtils;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月22日 下午5:15:33
 */
@Service
public class FileOperationServiceImpl implements FileOperationService {
    @Autowired
    private FileDao fileDao;
    @Autowired
    private StatisticsFileDao statisticsFileDao;

    @Override
    @Transactional
    public void autoFileDecrypt() {
        // long currentTime = System.currentTimeMillis();
        // List<File> files = fileDao.findNeedDecryptFiles(currentTime);
        // if (files == null) {
        // return;
        // }
        // for (File file : files) {
        // file.setFileState(FileConsts.State.unSecret.getCode());
        // file.setFileDecryptTime(currentTime);
        // // TODO 文件解密动作
        // fileDao.save(file);
        // }
    }

    @Override
    public void statisticsFile() {
        Date yesterdayBegin = DateUtils.getBeginDayOfYesterday();
        Date todayBegin = DateUtils.getDayBegin();
        Map<String, Long> map = fileDao.statisticsFileState(yesterdayBegin.getTime(), todayBegin.getTime());
        Long classifiedNum = map.getOrDefault("classified", 0L);
        Long issuedNum = map.getOrDefault("issued", 0L);
        Long classifiedChangeNum = map.getOrDefault("classifiedChange", 0L);
        Long declassifiedNum = map.getOrDefault("declassified", 0L);
        Long outsourceNum = 0L;
        StatisticsFile sf = statisticsFileDao.findByCreateTime(yesterdayBegin.getTime());
        if (sf == null) {
            sf = new StatisticsFile();
        }
        sf.setCreateTime(yesterdayBegin.getTime());
        sf.setClassifiedNum(classifiedNum);
        sf.setIssuedNum(issuedNum);
        sf.setClassifiedChangeNum(classifiedChangeNum);
        sf.setDeclassifiedNum(declassifiedNum);
        sf.setOutsourceNum(outsourceNum);
        statisticsFileDao.save(sf);
    }

}
