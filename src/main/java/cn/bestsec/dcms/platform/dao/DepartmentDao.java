package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.entity.RoleScope;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月27日上午10:03:23
 */
@Repository
public interface DepartmentDao extends JpaRepository<Department, Integer>, JpaSpecificationExecutor<Department> {
    Department findByPkDid(String id);

    List<Department> findByRoot(Integer root);

    List<Department> findByFkParentId(String pkDid);

    Department findByFkParentIdAndName(String parentDid, String name);

    @Query("select d.pkDid from Department d where d.departmentState!=1 and (d.name like :keyword or d.pkDid like :keyword)")
    List<String> findDidLikeKeyword(@Param("keyword") String keyword);

    List<Department> findByPkDidIn(List<RoleScope> roleScopes);

    Department findByPkDidAndDepartmentState(String varId, Integer code);

    @Query("from Department d where d.departmentState=:code and d.pkDid not in(:scopeAll)")
    List<Department> findByDepartmentStateAndPkDidNotIn(@Param("code") Integer code,
            @Param("scopeAll") List<String> scopeAll);
}
