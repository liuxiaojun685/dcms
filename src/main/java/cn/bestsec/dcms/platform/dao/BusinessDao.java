package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.Business;

/**
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月27日下午3:45:54
 */
@Repository
public interface BusinessDao extends JpaRepository<Business, Integer>, JpaSpecificationExecutor<Business> {
    Business findById(Integer id);

    List<Business> findByFkParentId(Integer parentId);
}
