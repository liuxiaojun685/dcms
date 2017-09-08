package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.Admin;

@Repository
public interface AdminDao extends JpaRepository<Admin, Integer>, JpaSpecificationExecutor<Admin> {

    Admin findByAccountAndPasswdAndAdminStateNot(String Account, String passwd, Integer adminState);

    Admin findByPkAidAndAdminStateNot(String id, Integer adminState);

    List<Admin> findByCreateFrom(Integer code);

    Admin findByAccountAndAdminStateNot(String account, Integer code);

    List<Admin> findByFkParentIdAndAdminStateNot(String pkAid, Integer code);

    @Query("from Admin a where a.adminType=:adminType and a.fkParentId=:fkParentId and a.adminState=:adminState and a.createFrom=:createFrom")
    List<Admin> findByAdminTypeAndFkParentIdAndAdminStateAndCreateFrom(@Param("adminType") Integer adminType,
            @Param("fkParentId") String fkParentId, @Param("adminState") Integer adminState,
            @Param("createFrom") Integer createFrom);

    List<Admin> findByCreateFromAndAdminStateNot(Integer code, Integer code2);

    @Query("from Admin a where a.adminType=:adminType and a.adminState=:adminState and a.createFrom=:createFrom")
    List<Admin> findByAdminTypeAndAdminStateAndCreateFrom(@Param("adminType") Integer adminType,
            @Param("adminState") Integer adminState, @Param("createFrom") Integer createFrom);

}
