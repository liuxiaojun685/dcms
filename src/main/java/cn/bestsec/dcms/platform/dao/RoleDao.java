package cn.bestsec.dcms.platform.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.User;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月25日上午11:43:12
 */
@Repository
public interface RoleDao extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    List<Role> findByRoleType(Integer roleType);

    // List<Role> findByRoleTypeAndFileLevel(Integer roleType, Integer
    // fileLevel);

    Role findById(Integer id);

    List<Role> findByUserByFkUidAndRoleType(User user, Integer roleType);

    @Query("select r.userByFkUid from Role r where r.roleType=:roleType")
    List<User> findRoleUsers(@Param("roleType") int roleType);

    @Query("select r.roleType from Role r where r.userByFkAgentUid.pkUid=:agentUid and r.fkAgentInvalidTime>:currentTimeMillis")
    List<Integer> findByUserByFkAgentUid(@Param("currentTimeMillis") long currentTimeMillis,
            @Param("agentUid") String agentUid);

    @Query("select r.roleType from Role r where r.userByFkUid.pkUid =:uid")
    List<Integer> findByUserByFkUid(@Param("uid") String uid);

    @Query("from Role r where r.roleType=:roleType and (r.userByFkUid.pkUid=:uid)")
    List<Role> findUserAllRoleByRoleType(@Param("roleType") int roleType, @Param("uid") String uid);

    @Query("from Role r where r.roleType=:roleType and (r.userByFkAgentUid.pkUid=:uid and r.fkAgentInvalidTime>:currentTimeMillis)")
    List<Role> findUserAllRoleByRoleTypeAgent(@Param("roleType") int roleType, @Param("uid") String uid,
            @Param("currentTimeMillis") long currentTimeMillis);

    @Query("select r.roleType from Role r where bitand(r.fileLevel,:fileLevelMask)!=0 and (r.userByFkUid.pkUid=:uid)")
    List<Integer> findUserAllRoleTypeByFileLevel(@Param("fileLevelMask") int fileLevelMask, @Param("uid") String uid);

    @Query("select r.roleType from Role r where bitand(r.fileLevel,:fileLevelMask)!=0 and (r.userByFkAgentUid.pkUid=:uid and r.fkAgentInvalidTime>:currentTimeMillis)")
    List<Integer> findUserAllRoleTypeAgentByFileLevel(@Param("fileLevelMask") int fileLevelMask,
            @Param("uid") String uid, @Param("currentTimeMillis") long currentTimeMillis);

    @Query("from Role r where r.userByFkUid.pkUid=:uid")
    List<Role> findUserRoles(@Param("uid") String uid);

    @Query("from Role r where r.userByFkAgentUid is not null and r.fkAgentInvalidTime<=:currentTimeMillis")
    List<Role> findInvalidAgentRoles(@Param("currentTimeMillis") long currentTimeMillis);

    // @Query("select r.roleType from Role r where r.userByFkUid.pkUid=:pkUid or
    // r.userByFkAgentUid.pkUid=:pkUid")
    // Set<Integer> findUserByFkUidOrUserByFkAgentUid(@Param("pkUid") String
    // pkUid);

    @Query("select r.roleType from Role r where r.userByFkUid.pkUid=:pkUid")
    Set<Integer> findRoleByUser(@Param("pkUid") String pkUid);

    @Query("select r.roleType from Role r where r.userByFkAgentUid.pkUid=:pkUid")
    Set<Integer> findRoleByUserAgent(@Param("pkUid") String pkUid);
}
