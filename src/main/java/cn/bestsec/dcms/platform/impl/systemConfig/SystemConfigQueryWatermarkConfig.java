package cn.bestsec.dcms.platform.impl.systemConfig;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import cn.bestsec.dcms.platform.api.SystemConfig_QueryWatermarkConfigApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Desensity;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryWatermarkConfigRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_QueryWatermarkConfigResponse;
import cn.bestsec.dcms.platform.dao.FileLevelValidPeriodDao;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.FileLevelValidPeriod;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.service.WaterMarkerService;
import cn.bestsec.dcms.platform.utils.watermarker.WatermarkerEntity;

/**
 * 获取水印信息
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月3日 上午9:56:27
 */
@Component
public class SystemConfigQueryWatermarkConfig implements SystemConfig_QueryWatermarkConfigApi {

    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private WaterMarkerService waterMarkerService;
    @Autowired
    private SystemConfigDao systemConfigDao;
    @Autowired
    private FileLevelValidPeriodDao fileLevelValidPeriodDao;

    @Override
    @Transactional
    public SystemConfig_QueryWatermarkConfigResponse execute(
            SystemConfig_QueryWatermarkConfigRequest systemConfig_QueryWatermarkConfigRequest) throws ApiException {
        SystemConfig_QueryWatermarkConfigResponse resp = new SystemConfig_QueryWatermarkConfigResponse();

        SystemConfig systemConfig = systemConfigService.getSystemConfig();

        Integer type = systemConfig_QueryWatermarkConfigRequest.getType();
        WatermarkerEntity watermarkerEntity = null;
        if (type == null || type == 1) {
            String json1 = systemConfig.getWatermarkContentJson4Print();
            if (json1 == null || "".equals(json1)) {
                String initConfigWhileQuery = waterMarkerService.initConfigWhileQuery();

                systemConfig.setWatermarkContentJson4Print(initConfigWhileQuery);
                systemConfigDao.save(systemConfig);
            }

            String json2 = systemConfig.getWatermarkContentJson4Print();
            Gson gson = new Gson();
            watermarkerEntity = gson.fromJson(json2, WatermarkerEntity.class);
        } else if (type == 2) {
            String json1 = systemConfig.getWatermarkContentJson4Screen();
            if (json1 == null || "".equals(json1)) {
                String initConfigWhileQuery = waterMarkerService.initConfigWhileQuery();

                systemConfig.setWatermarkContentJson4Screen(initConfigWhileQuery);
                systemConfigDao.save(systemConfig);
            }

            String json2 = systemConfig.getWatermarkContentJson4Screen();
            Gson gson = new Gson();
            watermarkerEntity = gson.fromJson(json2, WatermarkerEntity.class);
            // 获取密级配置
            List<Desensity> showWatermark = new ArrayList<>();
            List<FileLevelValidPeriod> findAll = fileLevelValidPeriodDao.findAll();
            for (FileLevelValidPeriod fileLevelValidPeriod : findAll) {
                Desensity desensity = new Desensity();
                desensity.setFileLevel(fileLevelValidPeriod.getFilelevel());
                desensity.setIsDesensity(fileLevelValidPeriod.getShowWatermark());
                showWatermark.add(desensity);
            }
            resp.setShowWatermark(showWatermark);
        }

        resp.setLeft(watermarkerEntity.getLeft());
        resp.setTop(watermarkerEntity.getTop());
        resp.setRight(watermarkerEntity.getRight());
        resp.setBottom(watermarkerEntity.getBottom());
        resp.setFontSize(watermarkerEntity.getFontSize());
        resp.setColorA(watermarkerEntity.getColorA());

        String colorRGB = "#" + watermarkerEntity.getColorR() + watermarkerEntity.getColorG()
                + watermarkerEntity.getColorB();

        resp.setColorRGB(colorRGB);
        resp.setFontEscapement(watermarkerEntity.getFontEscapement());
        resp.setMarkLocal(watermarkerEntity.getMarkLocal());
        resp.setMarkRows(watermarkerEntity.getMarkRows());
        resp.setMarkCols(watermarkerEntity.getMarkCols());
        resp.setMarkTop(watermarkerEntity.getMarkTop());
        resp.setMarkLeft(watermarkerEntity.getMarkLeft());
        resp.setMarkRight(watermarkerEntity.getMarkRight());
        resp.setMarkBottom(watermarkerEntity.getMarkBottom());
        resp.setCompNameType(watermarkerEntity.getCompNameType());
        resp.setUserNameType(watermarkerEntity.getUserNameType());
        resp.setHostAddrType(watermarkerEntity.getHostAddrType());
        resp.setPrintTimeType(watermarkerEntity.getPrintTimeType());
        resp.setFontName(watermarkerEntity.getFontName());
        resp.setText(watermarkerEntity.getText());

        // resp.setWatermarkContent(systemConfig.getWatermarkContent());
        // resp.setWatermarkDispAlign(systemConfig.getWatermarkDispAlign());
        // resp.setWatermarkDispAlpha(systemConfig.getWatermarkDispAlpha());
        // resp.setWatermarkDispAngle(systemConfig.getWatermarkDispAngle());
        // resp.setWatermarkDispDepartment(systemConfig.getWatermarkDispDepartment());
        // resp.setWatermarkDispIp(systemConfig.getWatermarkDispIp());
        // resp.setWatermarkDispPcName(systemConfig.getWatermarkDispPcName());
        // resp.setWatermarkDispPrintTime(systemConfig.getWatermarkDispPrintTime());
        // resp.setWatermarkDispTextColor(systemConfig.getWatermarkDispTextColor());
        // resp.setWatermarkDispTextSize(systemConfig.getWatermarkDispTextSize());
        // resp.setWatermarkDispUser(systemConfig.getWatermarkDispUser());

        return resp;
    }

}
