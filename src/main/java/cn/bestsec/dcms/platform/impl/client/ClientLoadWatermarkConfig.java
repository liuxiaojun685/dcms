package cn.bestsec.dcms.platform.impl.client;

import java.io.File;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import cn.bestsec.dcms.platform.api.Client_LoadWatermarkConfigApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Client_LoadWatermarkConfigRequest;
import cn.bestsec.dcms.platform.api.model.Client_LoadWatermarkConfigResponse;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.service.WaterMarkerService;
import cn.bestsec.dcms.platform.utils.SystemProperties;
import cn.bestsec.dcms.platform.utils.watermarker.WatermarkerEntity;

@Component
public class ClientLoadWatermarkConfig implements Client_LoadWatermarkConfigApi {
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private WaterMarkerService waterMarkerService;
    @Autowired
    private SystemConfigDao systemConfigDao;

    @Override
    @Transactional
    public Client_LoadWatermarkConfigResponse execute(
            Client_LoadWatermarkConfigRequest client_LoadWatermarkConfigRequest) throws ApiException {
        Client_LoadWatermarkConfigResponse resp = new Client_LoadWatermarkConfigResponse();
        SystemConfig systemConfig = systemConfigService.getSystemConfig();

        Integer type = client_LoadWatermarkConfigRequest.getType();
        if (type == null || type == 1) {
            File configFile = new File(SystemProperties.getInstance().getCacheDir() + "/config4Print.dat");
            if (!configFile.exists()) {

                String json1 = systemConfig.getWatermarkContentJson4Print();

                if (json1 == null || "".equals(json1)) {
                    String initConfigWhileQuery = waterMarkerService.initConfigWhileQuery();

                    systemConfig.setWatermarkContentJson4Print(initConfigWhileQuery);
                    systemConfigDao.save(systemConfig);
                }

                String json2 = systemConfig.getWatermarkContentJson4Print();
                Gson gson = new Gson();
                WatermarkerEntity watermarkerEntity = gson.fromJson(json2, WatermarkerEntity.class);

                waterMarkerService.genWatermarkerConfig4Print(watermarkerEntity);
            }

            if (configFile.exists()) {
                resp.setDownload(configFile);
                resp.setDownloadName("config4Print.dat");
            }
        } else if (type == 2) {
            File configFile = new File(SystemProperties.getInstance().getCacheDir() + "/config4Screen.dat");
            if (!configFile.exists()) {

                String json1 = systemConfig.getWatermarkContentJson4Screen();
                if (json1 == null || "".equals(json1)) {
                    String initConfigWhileQuery = waterMarkerService.initConfigWhileQuery();

                    systemConfig.setWatermarkContentJson4Screen(initConfigWhileQuery);
                    systemConfigDao.save(systemConfig);
                }

                String json2 = systemConfig.getWatermarkContentJson4Screen();
                Gson gson = new Gson();
                WatermarkerEntity watermarkerEntity = gson.fromJson(json2, WatermarkerEntity.class);

                waterMarkerService.genWatermarkerConfig4Screen(watermarkerEntity);
            }

            if (configFile.exists()) {
                resp.setDownload(configFile);
                resp.setDownloadName("config4Screen.dat");
            }
        }

        return resp;
    }

}
