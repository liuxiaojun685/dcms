package cn.bestsec.dcms.platform.impl.role;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Role_QueryTreeApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.Role_QueryTreeRequest;
import cn.bestsec.dcms.platform.api.model.Role_QueryTreeResponse;
import cn.bestsec.dcms.platform.dao.RoleDao;

/**
 * 查询角色树
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月28日 下午2:56:15
 */
@Component
public class RoleQueryTree implements Role_QueryTreeApi {

    @Autowired
    private RoleDao roleDao;

    @Override
    @Transactional
    public Role_QueryTreeResponse execute(Role_QueryTreeRequest role_QueryTreeRequest) throws ApiException {
//        Role_QueryTreeResponse resp = new Role_QueryTreeResponse();
//        // 添加所有角色类型数据
//        List<Integer> roleTypeData = new ArrayList<Integer>();
//        // 定密责任人
//        roleTypeData.add(RoleConsts.Type.makeSecret.getCode());
//        // 文件签发人
//        roleTypeData.add(RoleConsts.Type.dispatchman.getCode());
//        // 签入人(特权)
//        roleTypeData.add(RoleConsts.Type.logOn.getCode());
//        // 签出人(特权)
//        roleTypeData.add(RoleConsts.Type.logOff.getCode());
//
//        // 添加文件密级
//        List<Integer> fileLevelData = new ArrayList<Integer>();
//        fileLevelData.add(FileConsts.Level.open.getCode());// 公开
//        fileLevelData.add(FileConsts.Level.inside.getCode());// 内部
//        fileLevelData.add(FileConsts.Level.secret.getCode());// 秘密
//        fileLevelData.add(FileConsts.Level.confidential.getCode());// 机密
//        fileLevelData.add(FileConsts.Level.topSecret.getCode());// 绝密
//
//        // 存储响应所有数据
//        List<RoleTypeInfo> returnList = new ArrayList<RoleTypeInfo>();
//        for (Integer roleType : roleTypeData) {
//            RoleTypeInfo roleTypeInfo = new RoleTypeInfo();
//            roleTypeInfo.setRoleType(roleType);
//            // 用于存储文件密级信息
//            List<FileLevelInfo> fileLevelList = new ArrayList<FileLevelInfo>();
//            for (Integer fileLevel : fileLevelData) {
//                FileLevelInfo fileLevelInfo = new FileLevelInfo();
//                fileLevelInfo.setFileLevel(fileLevel);
//                // 用于存储用户
//                List<RoleSimpleInfo> userList = new ArrayList<RoleSimpleInfo>();
//                // 通过角色类型和文件密级查找用户
//                List<Role> userData = roleDao.findByRoleTypeAndFileLevel(roleType, fileLevel);
//                for (Role userInfo : userData) {
//                    RoleSimpleInfo roleInfo = new RoleSimpleInfo();
//                    // 获取角色对应的用户
//                    User user = userInfo.getUserByFkUid();
//                    roleInfo.setAccount(user.getAccount());
//                    roleInfo.setLevel(user.getUserLevel());
//                    roleInfo.setName(user.getName());
//                    roleInfo.setOnline(UserServiceImpl.userOnline(user));
//                    roleInfo.setUid(user.getPkUid());
//                    roleInfo.setRoleId(userInfo.getId());
//                    roleInfo.setRoleType(userInfo.getRoleType());
//                    userList.add(roleInfo);
//                }
//                fileLevelInfo.setUserList(userList);
//                fileLevelList.add(fileLevelInfo);
//            }
//
//            roleTypeInfo.setFileLevelList(fileLevelList);
//            returnList.add(roleTypeInfo);
//        }
//        resp.setRoleTypeList(returnList);
//        return resp;
        return new Role_QueryTreeResponse();
    }

}
