package cn.bestsec.dcms.platform.impl.systemConfig;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.SystemConfig_ConnectTestApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.SystemConfig_ConnectTestRequest;
import cn.bestsec.dcms.platform.api.model.SystemConfig_ConnectTestResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.service.ConnectTestService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 连接验证测试
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月30日上午11:04:12
 */
@Component
public class SystemConfigConnectTest implements SystemConfig_ConnectTestApi {

    @Autowired
    private ConnectTestService connectTestService;

    @Override
    @Transactional
    public SystemConfig_ConnectTestResponse execute(SystemConfig_ConnectTestRequest systemConfig_ConnectTestRequest)
            throws ApiException {
        Integer type = systemConfig_ConnectTestRequest.getType();
        // 检测定密责任人配置策略
        // if(type == SystemConsts.ConnectTest.makeSecret.getCode()) {
        List<String> result = connectTestService.checkResult(type);

        SystemConfig_ConnectTestResponse resp = new SystemConfig_ConnectTestResponse();

        resp.setResultList(result);
        AdminLogBuilder adminLogBuilder = systemConfig_ConnectTestRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.config_conntest)
                .admin(systemConfig_ConnectTestRequest.tokenWrapper().getAdmin()).operateDescription();
        return resp;
    }

}
