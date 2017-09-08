package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.UserToDepartment;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月24日下午9:14:43
 */
@Repository
public interface UserToDepartmentDao extends JpaRepository<UserToDepartment, Integer> {

    @Query("select ud.user from UserToDepartment ud where ud.department.pkDid in(:scopes)")
    List<User> findByDId(@Param("scopes") List<String> scopes);

}
