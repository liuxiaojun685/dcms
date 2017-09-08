package cn.bestsec.dcms.platform.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.bestsec.dcms.platform.entity.FileAccessScope;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月6日 下午7:28:32
 */
public interface FileAccessScopeDao
        extends JpaRepository<FileAccessScope, Integer>, JpaSpecificationExecutor<FileAccessScope> {
    List<FileAccessScope> findByFkFid(String fid);

    List<FileAccessScope> findByFkFidAndUnitNoAndFkUid(String fid, String unitNo, String uid);

    List<FileAccessScope> findByFkUid(String uid);

    void deleteByFkFid(String fid);

}
