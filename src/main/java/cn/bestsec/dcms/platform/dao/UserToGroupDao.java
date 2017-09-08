package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.Group;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.UserToGroup;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月25日上午10:53:42
 */
@Repository
public interface UserToGroupDao extends JpaRepository<UserToGroup, Integer>, JpaSpecificationExecutor<UserToGroup> {
	List<UserToGroup> findByGroupAndUser(Group group, User user);
}
