package cn.bestsec.dcms.platform.impl.admin;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Admin_QueryDefaultAdminApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.AdminInfo;
import cn.bestsec.dcms.platform.api.model.Admin_QueryDefaultAdminRequest;
import cn.bestsec.dcms.platform.api.model.Admin_QueryDefaultAdminResponse;
import cn.bestsec.dcms.platform.consts.AdminConsts;
import cn.bestsec.dcms.platform.dao.AdminDao;
import cn.bestsec.dcms.platform.entity.Admin;

/**
 * 默认管理员
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月8日 下午3:14:21
 */
@Component
public class AdminQueryDefaultAdmin implements Admin_QueryDefaultAdminApi {

    @Autowired
    private AdminDao adminDao;

    @Override
    @Transactional
    public Admin_QueryDefaultAdminResponse execute(Admin_QueryDefaultAdminRequest admin_QueryDefaultAdminRequest)
            throws ApiException {

        List<AdminInfo> adminList = new ArrayList<>();
        List<Admin> admins = adminDao.findByCreateFrom(AdminConsts.From.inner.getCode());
        for (Admin admin : admins) {
            AdminInfo info = new AdminInfo();
            info.setAdminType(admin.getAdminType());
            info.setAccount(admin.getAccount());
            info.setAid(admin.getPkAid());
            info.setDescription(admin.getDescription());
            info.setCreateFrom(admin.getCreateFrom());
            adminList.add(info);
        }
        Admin_QueryDefaultAdminResponse resp = new Admin_QueryDefaultAdminResponse();
        resp.setAdminList(adminList);
        return resp;
    }

}
