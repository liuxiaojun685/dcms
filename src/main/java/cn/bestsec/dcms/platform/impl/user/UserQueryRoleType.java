package cn.bestsec.dcms.platform.impl.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.User_QueryRoleTypeApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.User_QueryRoleTypeRequest;
import cn.bestsec.dcms.platform.api.model.User_QueryRoleTypeResponse;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.entity.User;

/**
 * 获取用户角色
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月23日 上午11:01:26
 */
@Component
public class UserQueryRoleType implements User_QueryRoleTypeApi {

    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional
    public User_QueryRoleTypeResponse execute(User_QueryRoleTypeRequest user_QueryRoleTypeRequest) throws ApiException {
        User user = user_QueryRoleTypeRequest.tokenWrapper().getUser();

        // 返回该用户角色类型
        Set<Integer> roleTypes = roleDao.findRoleByUser(user.getPkUid());
        Set<Integer> roleTypes2 = roleDao.findRoleByUserAgent(user.getPkUid());
        roleTypes.addAll(roleTypes2);
        List<Integer> types = new ArrayList<>();
        for (Integer integer : roleTypes) {
            types.add(integer);
        }

        User_QueryRoleTypeResponse resp = new User_QueryRoleTypeResponse();
        resp.setRoleTypes(types);
        return resp;
    }

}
