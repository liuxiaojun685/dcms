package cn.bestsec.dcms.platform.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.dao.MarkKeyDao;
import cn.bestsec.dcms.platform.dao.MarkKeyHistoryDao;
import cn.bestsec.dcms.platform.entity.MarkKey;
import cn.bestsec.dcms.platform.entity.MarkKeyHistory;
import cn.bestsec.dcms.platform.service.InitializationService;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年6月29日 下午1:55:24
 */
@Service
@Transactional
public class InitializationServiceImpl implements InitializationService {
    static Logger logger = LoggerFactory.getLogger(InitializationServiceImpl.class);

    @Autowired
    private MarkKeyHistoryDao markKeyHistoryDao;
    @Autowired
    private MarkKeyDao markKeyDao;

    @Override
    public void initialize() {
        long currentTime = System.currentTimeMillis();
        MarkKeyHistory markKeyHistory = markKeyHistoryDao.findOne(1);
        if (markKeyHistory.getCreateTime() == 0) {
            markKeyHistory.setCreateTime(currentTime);
        }
        if (markKeyHistory.getEnableTime() == 0) {
            markKeyHistory.setEnableTime(currentTime);
        }
        markKeyHistoryDao.save(markKeyHistory);

        MarkKey markKey = markKeyDao.findOne(1);
        if (markKey.getCreateTime() == 0) {
            markKey.setCreateTime(currentTime);
        }
        markKeyDao.save(markKey);
    }

}
