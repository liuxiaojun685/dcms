package cn.bestsec.dcms.platform.impl.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.api.Statistics_QueryFileListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.LongPoint;
import cn.bestsec.dcms.platform.api.model.Statistics_QueryFileListRequest;
import cn.bestsec.dcms.platform.api.model.Statistics_QueryFileListResponse;
import cn.bestsec.dcms.platform.dao.FileDao;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2017年4月19日下午1:33:21
 */
@Component
public class QueryFileList implements Statistics_QueryFileListApi {
	@Autowired
	private FileDao fileDao;

	@Override
	@Transactional
	public Statistics_QueryFileListResponse execute(Statistics_QueryFileListRequest statistics_QueryFileListRequest)
			throws ApiException {
		List<String> x = new ArrayList<>();
		x.add("正式定密");
		x.add("文件签发");
		x.add("文件解密");
		List<LongPoint> y = new ArrayList<>();
		Map<String, Long> map = fileDao.statisticsQueryFileList();
		Long fixed = map.getOrDefault("fixed", 0L);
		Long Issue = map.getOrDefault("Issue", 0L);
		Long decrypt = map.getOrDefault("decrypt", 0L);
		y.add(new LongPoint(fixed));
		y.add(new LongPoint(Issue));
		y.add(new LongPoint(decrypt));
		return new Statistics_QueryFileListResponse(x, y);
	}

}
