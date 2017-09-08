package cn.bestsec.dcms.platform.impl.statistics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Statistics_FileInStateApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.LongPoint;
import cn.bestsec.dcms.platform.api.model.Statistics_FileInStateRequest;
import cn.bestsec.dcms.platform.api.model.Statistics_FileInStateResponse;
import cn.bestsec.dcms.platform.dao.FileAccessScopeDao;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.entity.FileAccessScope;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * 统计终端用户各状态文件的数量
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年7月25日 下午2:13:48
 */
@Component
public class StatisticsFileInState implements Statistics_FileInStateApi {

    @Autowired
    private FileDao fileDao;
    @Autowired
    private FileAccessScopeDao fileAccessScopeDao;

    @Override
    @Transactional
    public Statistics_FileInStateResponse execute(Statistics_FileInStateRequest statistics_FileInStateRequest)
            throws ApiException {
        User user = statistics_FileInStateRequest.tokenWrapper().getUser();
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
        x.add("预定密");
        x.add("正式定密");
        x.add("文件签发");
        x.add("文件解密");
        List<LongPoint> y = new ArrayList<>();
        if (fileIdList != null && !fileIdList.isEmpty()) {
            Map<String, Long> map = fileDao.statisticsCountByFileState(fileIdList);
            Long preclassifiedNum = map.getOrDefault("preclassified", 0L);
            Long classifiedNum = map.getOrDefault("classified", 0L);
            Long issuedNum = map.getOrDefault("issued", 0L);
            Long declassifiedNum = map.getOrDefault("declassified", 0L);
            y.add(new LongPoint(preclassifiedNum));
            y.add(new LongPoint(classifiedNum));
            y.add(new LongPoint(issuedNum));
            y.add(new LongPoint(declassifiedNum));
        }
        return new Statistics_FileInStateResponse(x, y);
    }

}
