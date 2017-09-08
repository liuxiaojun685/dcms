package cn.bestsec.dcms.platform.impl.component;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import cn.bestsec.dcms.platform.api.Component_QueryApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.ComponentInfo;
import cn.bestsec.dcms.platform.api.model.Component_QueryRequest;
import cn.bestsec.dcms.platform.api.model.Component_QueryResponse;
import cn.bestsec.dcms.platform.dao.ComponentDao;
import cn.bestsec.dcms.platform.entity.Component;

/**
 * 查询组件列表
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年1月4日 上午10:56:09
 */
@org.springframework.stereotype.Component
public class ComponentQuery implements Component_QueryApi {

    @Autowired
    private ComponentDao componentDao;

    @Override
    @Transactional
    public Component_QueryResponse execute(Component_QueryRequest component_QueryRequest) throws ApiException {
        Component_QueryResponse resp = new Component_QueryResponse();
        // 查询所有组件
        List<Component> components = componentDao.findAll();

        // 返回的组件数据
        List<ComponentInfo> componentList = new ArrayList<ComponentInfo>();
        if (components != null) {
            for (Component component : components) {
                ComponentInfo componentInfo = new ComponentInfo();
                componentInfo.setComponentId(component.getId());
                componentInfo.setDescription(component.getDescription());
                componentInfo.setEnable(component.getEnable());
                componentInfo.setName(component.getName());
                componentList.add(componentInfo);
            }
        }
        resp.setComponentList(componentList);
        return resp;
    }

}
