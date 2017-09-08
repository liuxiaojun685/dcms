package cn.bestsec.dcms.platform.impl.statistics;

import java.io.File;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Statistics_EnvironmentApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.ResourcePoint;
import cn.bestsec.dcms.platform.api.model.Statistics_EnvironmentRequest;
import cn.bestsec.dcms.platform.api.model.Statistics_EnvironmentResponse;
import cn.bestsec.dcms.platform.utils.ServerEnv;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * 统计系统资源
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年4月19日 上午10:41:03
 */
@Component
public class StatisticsEnvironment implements Statistics_EnvironmentApi {

    @Override
    public Statistics_EnvironmentResponse execute(Statistics_EnvironmentRequest statistics_EnvironmentRequest)
            throws ApiException {

        // CPU
        String cpuRatio = ServerEnv.getCpuRatio();
        if (cpuRatio.contains("%")) {
            cpuRatio = cpuRatio.replace("%", "");
        }
        ResourcePoint cpuPoint = new ResourcePoint();
        String cpuName = "CPU占用率";
        cpuPoint.setType(1);
        cpuPoint.setResourceName(cpuName);
        cpuPoint.setPercentage(cpuRatio);
        // 内存
        ResourcePoint memoryPoint = new ResourcePoint();
        long maxMemory = Runtime.getRuntime().maxMemory();
        long freeMemory = Runtime.getRuntime().freeMemory();
        String memorytage = formatDouble((1 - (double) freeMemory / (double) maxMemory) * 100);
        String resourceName = "内存占用率";
        memoryPoint.setType(2);
        memoryPoint.setResourceName(resourceName);
        memoryPoint.setPercentage(memorytage);
        // 硬盘
        File file = new File(SystemProperties.getInstance().getCacheDir());
        long totalSpace = file.getTotalSpace();
        long freeSpace = file.getFreeSpace();
        String spacetage = formatDouble((1 - (double) freeSpace / (double) totalSpace) * 100);
        String spaceName = "硬盘占用率";
        ResourcePoint spacePoint = new ResourcePoint();
        spacePoint.setType(3);
        spacePoint.setResourceName(spaceName);
        spacePoint.setPercentage(spacetage);

        Statistics_EnvironmentResponse resp = new Statistics_EnvironmentResponse();
        List<ResourcePoint> resourceList = new ArrayList<>();
        resourceList.add(memoryPoint);
        resourceList.add(spacePoint);
        resourceList.add(cpuPoint);
        resp.setResourceList(resourceList);
        return resp;
    }

    public static String formatDouble(double d) {
        NumberFormat nf = NumberFormat.getNumberInstance();

        // 保留两位小数
        nf.setMaximumFractionDigits(2);

        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        nf.setRoundingMode(RoundingMode.UP);

        return nf.format(d);
    }

}
