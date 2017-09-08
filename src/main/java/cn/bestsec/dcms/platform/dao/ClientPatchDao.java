package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.ClientPatch;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月26日下午1:42:33
 */
@Repository
public interface ClientPatchDao extends JpaRepository<ClientPatch, Integer>, JpaSpecificationExecutor<ClientPatch> {

    ClientPatch findByid(Integer id);

    List<ClientPatch> findByOsTypeAndVersionType(String osType, Integer versionType);

    List<ClientPatch> findByOsTypeAndVersionTypeOrderByVersionCodeDesc(String osType, Integer versionType);
}
