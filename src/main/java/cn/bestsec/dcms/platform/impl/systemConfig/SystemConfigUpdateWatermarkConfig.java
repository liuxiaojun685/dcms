package cn.bestsec.dcms.platform.impl.systemConfig;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.sun.jna.NativeLong;
import com.sun.jna.WString;

import cn.bestsec.dcms.platform.api.SystemConfig_UpdateWatermarkConfigApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Desensity;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateWatermarkConfigRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_UpdateWatermarkConfigResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.FileLevelValidPeriodDao;
import cn.bestsec.dcms.platform.dao.SystemConfigDao;
import cn.bestsec.dcms.platform.entity.FileLevelValidPeriod;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.service.WaterMarkerService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.watermarker.IWaterMarkApi;
import cn.bestsec.dcms.platform.utils.watermarker.WatermarkerEntity;

/**
 * 修改水印配置 系统管理员
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月3日 上午10:29:41
 */
@Component
public class SystemConfigUpdateWatermarkConfig implements SystemConfig_UpdateWatermarkConfigApi {

    @Autowired
    private SystemConfigDao systemConfigDao;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private WaterMarkerService waterMarkerService;
    @Autowired
    private FileLevelValidPeriodDao fileLevelValidPeriodDao;

    @Override
    @Transactional
    public SystemConfig_UpdateWatermarkConfigResponse execute(
            SystemConfig_UpdateWatermarkConfigRequest systemConfig_UpdateWatermarkConfigRequest) throws ApiException {
        SystemConfig_UpdateWatermarkConfigResponse resp = new SystemConfig_UpdateWatermarkConfigResponse();
        SystemConfig systemConfig = systemConfigService.getSystemConfig();

        WatermarkerEntity watermarkerEntity = new WatermarkerEntity();

        Integer type = systemConfig_UpdateWatermarkConfigRequest.getType();
        if (type == null || type == 1) {
            watermarkerEntity.setLeft(systemConfig_UpdateWatermarkConfigRequest.getLeft());
            watermarkerEntity.setTop(systemConfig_UpdateWatermarkConfigRequest.getTop());
            watermarkerEntity.setRight(systemConfig_UpdateWatermarkConfigRequest.getRight());
            watermarkerEntity.setBottom(systemConfig_UpdateWatermarkConfigRequest.getBottom());
            watermarkerEntity.setFontSize(systemConfig_UpdateWatermarkConfigRequest.getFontSize());
            watermarkerEntity.setColorA(systemConfig_UpdateWatermarkConfigRequest.getColorA());

            String colorRGB = systemConfig_UpdateWatermarkConfigRequest.getColorRGB();

            String colorR = colorRGB.substring(1, 3);
            String colorG = colorRGB.substring(3, 5);
            String colorB = colorRGB.substring(5, 7);
            watermarkerEntity.setColorR(colorR);
            watermarkerEntity.setColorG(colorG);
            watermarkerEntity.setColorB(colorB);
            watermarkerEntity.setFontEscapement(systemConfig_UpdateWatermarkConfigRequest.getFontEscapement());
            watermarkerEntity.setMarkLocal(systemConfig_UpdateWatermarkConfigRequest.getMarkLocal());
            watermarkerEntity.setMarkRows(systemConfig_UpdateWatermarkConfigRequest.getMarkRows());
            watermarkerEntity.setMarkCols(systemConfig_UpdateWatermarkConfigRequest.getMarkCols());
            watermarkerEntity.setCompNameType(systemConfig_UpdateWatermarkConfigRequest.getCompNameType());
            watermarkerEntity.setUserNameType(systemConfig_UpdateWatermarkConfigRequest.getUserNameType());
            watermarkerEntity.setHostAddrType(systemConfig_UpdateWatermarkConfigRequest.getHostAddrType());
            watermarkerEntity.setFontName(systemConfig_UpdateWatermarkConfigRequest.getFontName());
            watermarkerEntity.setText(systemConfig_UpdateWatermarkConfigRequest.getText());
            //////////////////////////////////////////
            watermarkerEntity.setMarkTop(systemConfig_UpdateWatermarkConfigRequest.getMarkTop());
            watermarkerEntity.setMarkLeft(systemConfig_UpdateWatermarkConfigRequest.getMarkLeft());
            watermarkerEntity.setMarkRight(systemConfig_UpdateWatermarkConfigRequest.getMarkRight());
            watermarkerEntity.setMarkBottom(systemConfig_UpdateWatermarkConfigRequest.getMarkRight());
            watermarkerEntity.setPrintTimeType(systemConfig_UpdateWatermarkConfigRequest.getPrintTimeType());

            waterMarkerService.genWatermarkerConfig4Print(watermarkerEntity);

            systemConfig.setWatermarkContentJson4Print(new Gson().toJson(watermarkerEntity));
        } else if (type != null || type == 2) {
            watermarkerEntity.setLeft(systemConfig_UpdateWatermarkConfigRequest.getLeft());
            watermarkerEntity.setTop(systemConfig_UpdateWatermarkConfigRequest.getTop());
            watermarkerEntity.setRight(systemConfig_UpdateWatermarkConfigRequest.getRight());
            watermarkerEntity.setBottom(systemConfig_UpdateWatermarkConfigRequest.getBottom());
            watermarkerEntity.setFontSize(systemConfig_UpdateWatermarkConfigRequest.getFontSize());
            watermarkerEntity.setColorA(systemConfig_UpdateWatermarkConfigRequest.getColorA());

            String colorRGB = systemConfig_UpdateWatermarkConfigRequest.getColorRGB();

            String colorR = colorRGB.substring(1, 3);
            String colorG = colorRGB.substring(3, 5);
            String colorB = colorRGB.substring(5, 7);
            watermarkerEntity.setColorR(colorR);
            watermarkerEntity.setColorG(colorG);
            watermarkerEntity.setColorB(colorB);
            watermarkerEntity.setFontEscapement(systemConfig_UpdateWatermarkConfigRequest.getFontEscapement());
            watermarkerEntity.setMarkLocal(systemConfig_UpdateWatermarkConfigRequest.getMarkLocal());
            watermarkerEntity.setMarkRows(systemConfig_UpdateWatermarkConfigRequest.getMarkRows());
            watermarkerEntity.setMarkCols(systemConfig_UpdateWatermarkConfigRequest.getMarkCols());
            watermarkerEntity.setCompNameType(systemConfig_UpdateWatermarkConfigRequest.getCompNameType());
            watermarkerEntity.setUserNameType(systemConfig_UpdateWatermarkConfigRequest.getUserNameType());
            watermarkerEntity.setHostAddrType(systemConfig_UpdateWatermarkConfigRequest.getHostAddrType());
            watermarkerEntity.setFontName(systemConfig_UpdateWatermarkConfigRequest.getFontName());
            watermarkerEntity.setText(systemConfig_UpdateWatermarkConfigRequest.getText());

            waterMarkerService.genWatermarkerConfig4Screen(watermarkerEntity);

            systemConfig.setWatermarkContentJson4Screen(new Gson().toJson(watermarkerEntity));

            // 配置密级信息
            List<Desensity> showWatermark = systemConfig_UpdateWatermarkConfigRequest.getShowWatermark();
            for (Desensity desensity : showWatermark) {
                FileLevelValidPeriod fileLevelValidPeriod = fileLevelValidPeriodDao
                        .findByfilelevel(desensity.getFileLevel());
                fileLevelValidPeriod.setShowWatermark(desensity.getIsDesensity());
                fileLevelValidPeriodDao.save(fileLevelValidPeriod);
            }
        }

        systemConfigDao.save(systemConfig);

        AdminLogBuilder adminLogBuilder = systemConfig_UpdateWatermarkConfigRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_watermark)
                .admin(systemConfig_UpdateWatermarkConfigRequest.tokenWrapper().getAdmin()).operateDescription();

        return resp;
    }

    public static void main(String[] args) {
        WatermarkerEntity watermarkerEntity = new WatermarkerEntity();

        watermarkerEntity.setLeft(1);
        watermarkerEntity.setTop(1);
        watermarkerEntity.setRight(1);
        watermarkerEntity.setBottom(1);
        watermarkerEntity.setFontSize(22);
        watermarkerEntity.setColorA(25);
        // watermarkerEntity.setColorR(0);
        // watermarkerEntity.setColorG(0);
        // watermarkerEntity.setColorB(0);
        watermarkerEntity.setFontEscapement(-45);
        watermarkerEntity.setMarkLocal(1);
        watermarkerEntity.setMarkRows(8);
        watermarkerEntity.setMarkCols(8);
        watermarkerEntity.setMarkTop(1);
        watermarkerEntity.setMarkLeft(1);
        watermarkerEntity.setMarkRight(1);
        watermarkerEntity.setMarkBottom(1);
        watermarkerEntity.setPrintTime(4294967295L);
        watermarkerEntity.setCompNameType(0);
        watermarkerEntity.setUserNameType(0);
        watermarkerEntity.setHostAddrType(0);
        watermarkerEntity.setCustInfoType(0);
        watermarkerEntity.setPrintTimeType(0);
        watermarkerEntity.setFontName("黑体");
        watermarkerEntity.setText("保密");
        watermarkerEntity.setCustInfo("勇");

        Gson gson = new Gson();
        String json = gson.toJson(watermarkerEntity);
        System.out.println(json);

    }

    static void write() {
        IWaterMarkApi.instanceDll.writeToFileHead(
                "C:\\Users\\besthyhy\\Documents\\0Desktop\\dcms_water_marker\\config.dat", //
                new NativeLong(1), // 左 //页边距，取值[0,+)。取值会影响显示文字的距离。
                new NativeLong(1), // 上
                new NativeLong(1), // 右
                new NativeLong(1), // 下
                new NativeLong(12), // 字体大小
                (byte) 25, // A //颜色ARGB，透明度由alpha通道控制
                (byte) 0, // R
                (byte) 0, // G
                (byte) 0, // B
                new NativeLong(0), // 倾斜度。负数逆时针旋转，正数顺时针旋转。
                2, // 字体倾斜，无效
                1, // 水印位置，无效
                0, // 水印布局，无效
                8, // 水印行数
                8, // 水印列数
                1, // 页眉：0左对齐，1居中，2右对齐，3不显示
                1, // 页左：0上对齐，1居中，2下对齐，3不显示
                1, // 页右：0上对齐，1居中，2下对齐，3不显示
                1, // 页脚：0左对齐，1居中，2右对齐，3不显示
                0, // 水印质量：0速度优先，1质量优先
                0, // 水印类型：0文字水印，1图片水印
                (int) 4294967295L, // 打印时间
                // 计算机名称、用户、ip、打印时间、自定义信息的显示方式：
                // 基本方式：0不选，1页眉，2页脚，4左侧，8右侧，16跟随水印
                // 符合方式取基本方式之和。
                0, // 计算机名的显示方式
                0, // 用户名的显示方式
                0, // ip地址的显示方式
                16, // 自定义信息的显示方式
                0, // 打印时间的显示方式
                new WString("宋体"), // 字体：仅常见的几种字体可选：宋体、黑体、微软雅黑、楷体、仿宋、新宋体
                new WString("保密"), // 文字水印内容
                new WString("C:\\Users\\Public\\Pictures\\Sample Pictures\\Chrysanthemum.jpg"), // 图片位置
                new WString("KVM-Win7"), // 计算机名
                new WString("besthyhy"), // 用户名
                new WString("192.168.122.30"), // ip
                new WString("勇")// 自定义信息
        );
    }

}
