package cn.bestsec.dcms.platform.impl.component;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import cn.bestsec.dcms.platform.api.Component_UpdateByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Component_UpdateByIdRequest;
import cn.bestsec.dcms.platform.api.model.Component_UpdateByIdResponse;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.consts.CommonConsts;
import cn.bestsec.dcms.platform.dao.ComponentDao;
import cn.bestsec.dcms.platform.entity.Component;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;

/**
 * 修改组件状态
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月30日下午1:56:30
 */
@org.springframework.stereotype.Component
public class ComponentUpdateById implements Component_UpdateByIdApi {
    @Autowired
    private ComponentDao componentDao;

    @Override
    @Transactional
    public Component_UpdateByIdResponse execute(Component_UpdateByIdRequest component_UpdateByIdRequest)
            throws ApiException {
        Component_UpdateByIdResponse resp = new Component_UpdateByIdResponse();
        Component component = componentDao.findById(component_UpdateByIdRequest.getComponentId());
        if (component == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        component.setEnable(component_UpdateByIdRequest.getEnable());
        componentDao.save(component);

        AdminLogBuilder adminLogBuilder = component_UpdateByIdRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(System.currentTimeMillis()).operation(AdminLogOp.component_update)
                .admin(component_UpdateByIdRequest.tokenWrapper().getAdmin()).operateDescription(
                        CommonConsts.Bool.parse(component.getEnable()).getDescription(), component.getName());
        return resp;
    }

}
