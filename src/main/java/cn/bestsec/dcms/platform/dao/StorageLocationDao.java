package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.bestsec.dcms.platform.entity.StorageLocation;

/**
 * 文件存储位置描述数据层
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月26日 下午5:46:14
 */
@Repository
public interface StorageLocationDao extends JpaRepository<StorageLocation, Integer> {
    StorageLocation findById(Integer id);

    List<StorageLocation> findByDomainNameAndIpAndPortAndFilePathAndProtocol(String domainName, String ip, String port, String filePath, String protocol);
}
