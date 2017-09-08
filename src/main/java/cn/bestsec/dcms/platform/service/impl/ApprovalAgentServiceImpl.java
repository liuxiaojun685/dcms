package cn.bestsec.dcms.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.service.ApprovalAgentService;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月22日 下午4:57:55
 */
@Service
public class ApprovalAgentServiceImpl implements ApprovalAgentService {
    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional
    public void cancelAgent() {
        List<Role> roles = roleDao.findInvalidAgentRoles(System.currentTimeMillis());
        if (roles == null) {
            return;
        }
        for (Role role : roles) {
            role.setFkAgentInvalidTime(0L);
            role.setUserByFkAgentUid(null);
        }
        roleDao.save(roles);
    }

}
