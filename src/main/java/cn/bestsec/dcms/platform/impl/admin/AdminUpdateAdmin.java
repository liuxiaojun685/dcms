package cn.bestsec.dcms.platform.impl.admin;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Admin_UpdateAdminApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Admin_UpdateAdminRequest;
import cn.bestsec.dcms.platform.api.model.Admin_UpdateAdminResponse;
import cn.bestsec.dcms.platform.consts.AdminConsts;
import cn.bestsec.dcms.platform.dao.AdminDao;
import cn.bestsec.dcms.platform.entity.Admin;

/**
 * 修改管理员角色用户
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月7日 上午10:16:22
 */
@Component
public class AdminUpdateAdmin implements Admin_UpdateAdminApi {

    @Autowired
    private AdminDao adminDao;

    @Override
    @Transactional
    public Admin_UpdateAdminResponse execute(Admin_UpdateAdminRequest admin_UpdateAdminRequest) throws ApiException {
        Admin admin = adminDao.findByPkAidAndAdminStateNot(admin_UpdateAdminRequest.getAid(),
                AdminConsts.AdminState.deleted.getCode());
        if (admin == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        admin.setDerive(admin_UpdateAdminRequest.getDerive());
        adminDao.save(admin);
        return new Admin_UpdateAdminResponse();
    }

}
