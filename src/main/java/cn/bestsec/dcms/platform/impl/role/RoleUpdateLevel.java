package cn.bestsec.dcms.platform.impl.role;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Role_UpdateLevelApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Role_UpdateLevelRequest;
import cn.bestsec.dcms.platform.api.model.Role_UpdateLevelResponse;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.entity.Role;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月27日 下午4:33:41
 */
@Component
public class RoleUpdateLevel implements Role_UpdateLevelApi {
    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional
    public Role_UpdateLevelResponse execute(Role_UpdateLevelRequest role_UpdateLevelRequest) throws ApiException {
        Role role = roleDao.findOne(role_UpdateLevelRequest.getRoleId());
        if (role == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        
        int requestLevel = role_UpdateLevelRequest.getRoleLevel();
        int enable = role_UpdateLevelRequest.getEnable();
        int level = role.getFileLevel();
        if (enable != 0) {
            level |= (1 << requestLevel);
        } else {
            level &= ~(1 << requestLevel);
        }
        role.setFileLevel(level);
        roleDao.save(role);

        return new Role_UpdateLevelResponse();
    }

}
