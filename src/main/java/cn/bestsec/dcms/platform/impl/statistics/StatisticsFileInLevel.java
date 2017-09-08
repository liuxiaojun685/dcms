package cn.bestsec.dcms.platform.impl.statistics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Statistics_FileInLevelApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.LongPoint;
import cn.bestsec.dcms.platform.api.model.Statistics_FileInLevelRequest;
import cn.bestsec.dcms.platform.api.model.Statistics_FileInLevelResponse;
import cn.bestsec.dcms.platform.dao.FileAccessScopeDao;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.entity.FileAccessScope;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * 统计终端用户各密级文件的数量
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年7月25日 上午11:11:21
 */
@Component
public class StatisticsFileInLevel implements Statistics_FileInLevelApi {

    @Autowired
    private FileDao fileDao;
    @Autowired
    private FileAccessScopeDao fileAccessScopeDao;

    @Override
    @Transactional
    public Statistics_FileInLevelResponse execute(Statistics_FileInLevelRequest statistics_FileInLevelRequest)
            throws ApiException {
        User user = statistics_FileInLevelRequest.tokenWrapper().getUser();
        // 存取需查询的文件id
        Set<String> fileIdList = new HashSet<String>();
        // 被分发的文件
        List<FileAccessScope> scopes = fileAccessScopeDao.findByFkUid(user.getPkUid());
        for (FileAccessScope scope : scopes) {
            fileIdList.add(scope.getFkFid());
        }

        // 不限定知悉范围的
        if (SystemProperties.getInstance().isScopeDefaultAccess()) {
            List<String> fids = fileDao.findFileNoScopes();
            if (fids != null && !fids.isEmpty()) {
                fileIdList.addAll(fids);
            }
        } else {
            // 无知悉范围或知悉范围描述的，表示不限定知悉范围
            List<String> fids = fileDao.findByScopes();
            if (fids != null && !fids.isEmpty()) {
                fileIdList.addAll(fids);
            }
        }

        // 自己拟稿的文件
        List<String> fids = fileDao.findByUserByFkFileCreateUid(user.getPkUid());
        fileIdList.addAll(fids);
        // x坐标
        List<String> x = new ArrayList<>();
        x.add("公开");
        x.add("内部");
        x.add("秘密");
        x.add("机密");
        x.add("绝密");
        List<LongPoint> y = new ArrayList<>();
        if (fileIdList != null && !fileIdList.isEmpty()) {
            Map<String, Long> map = fileDao.statisticsCountByFileLevel(fileIdList);
            Long open = map.getOrDefault("open", 0L);
            Long inside = map.getOrDefault("inside", 0L);
            Long secret = map.getOrDefault("secret", 0L);
            Long confidential = map.getOrDefault("confidential", 0L);
            Long topSecret = map.getOrDefault("topSecret", 0L);
            y.add(new LongPoint(open));
            y.add(new LongPoint(inside));
            y.add(new LongPoint(secret));
            y.add(new LongPoint(confidential));
            y.add(new LongPoint(topSecret));
        }
        return new Statistics_FileInLevelResponse(x, y);
    }

}
