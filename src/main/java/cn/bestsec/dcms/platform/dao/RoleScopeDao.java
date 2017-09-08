package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.RoleScope;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月27日 上午11:46:34
 */
@Repository
public interface RoleScopeDao extends JpaRepository<RoleScope, Integer>, JpaSpecificationExecutor<RoleScope> {
    List<RoleScope> findByRole(Role role);

    List<RoleScope> findByFkVarId(String varId);

    @Query("select rs.fkVarId from RoleScope rs where rs.role.id=:id")
    List<String> findByRoleId(@Param("id") Integer id);

    void deleteByRoleIn(List<Role> roles);
}
