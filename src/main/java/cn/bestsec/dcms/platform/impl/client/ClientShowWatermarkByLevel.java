package cn.bestsec.dcms.platform.impl.client;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import cn.bestsec.dcms.platform.api.Client_ShowWatermarkByLevelApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Client_ShowWatermarkByLevelRequest;
import cn.bestsec.dcms.platform.api.model.Client_ShowWatermarkByLevelResponse;
import cn.bestsec.dcms.platform.api.model.Desensity;
import cn.bestsec.dcms.platform.consts.CommonConsts;
import cn.bestsec.dcms.platform.dao.FileLevelValidPeriodDao;
import cn.bestsec.dcms.platform.entity.FileLevelValidPeriod;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;

/**
 * 根据密级配置是否显示屏幕水印以及获取全局高级管控配置
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月26日 上午10:43:55
 */
@Component
public class ClientShowWatermarkByLevel implements Client_ShowWatermarkByLevelApi {

    @Autowired
    private FileLevelValidPeriodDao fileLevelValidPeriodDao;
    @Autowired
    private SystemConfigService systemConfigService;

    @Override
    @Transactional
    public Client_ShowWatermarkByLevelResponse execute(
            Client_ShowWatermarkByLevelRequest client_ShowWatermarkByLevelRequest) throws ApiException {
        List<Desensity> desensityList = new ArrayList<>();
        // 根据密级配置显示水印
        List<FileLevelValidPeriod> findAll = fileLevelValidPeriodDao.findAll();
        for (FileLevelValidPeriod fileLevelValidPeriod : findAll) {
            Desensity desensity = new Desensity();
            desensity.setFileLevel(fileLevelValidPeriod.getFilelevel());
            desensity.setIsDesensity(fileLevelValidPeriod.getShowWatermark());
            desensityList.add(desensity);

        }
        Client_ShowWatermarkByLevelResponse resp = new Client_ShowWatermarkByLevelResponse();

        // 获取全局高级管控配置
        SystemConfig systemConfig = systemConfigService.getSystemConfig();

        resp.setProhibitDelete(CommonConsts.Bool.no.getInt());
        resp.setProhibitMailSend(CommonConsts.Bool.no.getInt());
        resp.setProhibitNetwork(CommonConsts.Bool.no.getInt());
        resp.setProhibitRename(CommonConsts.Bool.no.getInt());
        resp.setProhibitRightSend(CommonConsts.Bool.no.getInt());
        String advancedControlStrategy = systemConfig.getAdvancedControlStrategy();
        JSONObject json = JSONObject.parseObject(advancedControlStrategy);

        if (json != null && !json.isEmpty()) {
            resp.setProhibitDelete(json.getInteger("prohibitDelete"));
            resp.setProhibitMailSend(json.getInteger("prohibitMailSend"));
            resp.setProhibitNetwork(json.getInteger("prohibitNetwork"));
            resp.setProhibitRename(json.getInteger("prohibitRename"));
            resp.setProhibitRightSend(json.getInteger("prohibitRightSend"));
        }
        resp.setShowWatermarkList(desensityList);
        return resp;
    }

}
