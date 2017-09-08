package cn.bestsec.dcms.platform.impl.file;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.File_QueryListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.FileInfo;
import cn.bestsec.dcms.platform.api.model.File_QueryListRequest;
import cn.bestsec.dcms.platform.api.model.File_QueryListResponse;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.FileLevelValidPeriodDao;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.service.FileService;
import cn.bestsec.dcms.platform.service.LogArchiveService;
import cn.bestsec.dcms.platform.utils.TextUtils;

/**
 * 查询文件台账
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月28日下午3:01:32
 */
@Component
public class FileQueryList implements File_QueryListApi {
    @Autowired
    private FileDao fileDao;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileLevelValidPeriodDao fileLevelValidPeriodDao;
    @Autowired
    private LogArchiveService logArchiveService;

    @Override
    @Transactional
    public File_QueryListResponse execute(File_QueryListRequest file_QueryListRequest) throws ApiException {
        File_QueryListResponse resp = new File_QueryListResponse();
        Pageable pageable = new PageRequest(file_QueryListRequest.getPageNumber(), file_QueryListRequest.getPageSize(),
                Sort.Direction.DESC, "fileCreateTime");
        String keyword = file_QueryListRequest.getKeyword();
        // 添加查询条件
        Specification<File> spec = null;
        if (keyword != null) {
            spec = new Specification<File>() {

                @Override
                public Predicate toPredicate(Root<File> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    list.add(cb.like(root.get("name").as(String.class), "%" + keyword + "%"));
                    list.add(cb.like(root.get("majorUnit").as(String.class), "%" + keyword + "%"));
                    list.add(cb.like(root.get("minorUnit").as(String.class), "%" + keyword + "%"));
                    Predicate[] p = new Predicate[list.size()];
                    return cb.or(list.toArray(p));
                }
            };
        }
        Page<File> page = fileDao.findAll(spec, pageable);

        List<File> files = page.getContent();
        List<FileInfo> fileInfos = new ArrayList<FileInfo>();
        for (File file : files) {
            FileInfo fileInfo = fileService.getFileInfo(file.getPkFid());
            if (fileInfo != null) {
                if (logArchiveService.isFileDesensity(file.getFileLevel())) {
                    String fileName = TextUtils.getDealWithName(fileInfo.getFileName());
                    fileInfo.setFileName(fileName);
                }
                fileInfos.add(fileInfo);
            }
        }
        // 获取总行数
        Long totaoElements = page.getTotalElements();
        resp.setTotalElements(new Integer(totaoElements.intValue()));
        resp.setTotalPages(page.getTotalPages());
        resp.setFileList(fileInfos);
        return resp;
    }

}
