package cn.bestsec.dcms.platform.impl.file;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
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

import cn.bestsec.dcms.platform.api.File_QueryPrivateListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.FileInfo;
import cn.bestsec.dcms.platform.api.model.File_QueryPrivateListRequest;
import cn.bestsec.dcms.platform.api.model.File_QueryPrivateListResponse;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.consts.FilterEnum;
import cn.bestsec.dcms.platform.consts.RoleConsts;
import cn.bestsec.dcms.platform.consts.TimeConsts;
import cn.bestsec.dcms.platform.dao.FileAccessScopeDao;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.FileDrmDao;
import cn.bestsec.dcms.platform.dao.FileLevelChangeHistoryDao;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.RoleScopeDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.UserToDepartmentDao;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.FileAccessScope;
import cn.bestsec.dcms.platform.entity.FileDrm;
import cn.bestsec.dcms.platform.entity.FileLevelChangeHistory;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.FileService;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * 查询个人文件台账
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月30日 下午3:26:27
 */
@Component
public class FileQueryPrivateList implements File_QueryPrivateListApi {

    @Autowired
    private FileDao fileDao;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private FileDrmDao fileDrmDao;
    @Autowired
    private FileLevelChangeHistoryDao fileLevelChangeHistoryDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileAccessScopeDao fileAccessScopeDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RoleScopeDao roleScopeDao;
    @Autowired
    private UserToDepartmentDao userToDepartmentDao;

    @Override
    @Transactional
    public File_QueryPrivateListResponse execute(File_QueryPrivateListRequest file_QueryPrivateListRequest)
            throws ApiException {
        File_QueryPrivateListResponse resp = new File_QueryPrivateListResponse();
        // 通过token得到当前用户
        Token token = tokenDao.findByToken(file_QueryPrivateListRequest.getToken());
        User user = token.getUser();
        // 存取需查询的文件id
        Set<String> fileIdList = new HashSet<String>();

        Integer filter = file_QueryPrivateListRequest.getFilter();
        // 得到用户的角色
        // List<Role> roles = user.getRolesForFkUid();

        Specification<File> spec = null;
        // 查询文件收件箱
        if (filter == FilterEnum.inbox.getCode()) {
            Set<String> varIds = securityPolicyService.getPrivateVarIds(user.getPkUid());
            Specification<FileDrm> varSpec = new Specification<FileDrm>() {

                @Override
                public Predicate toPredicate(Root<FileDrm> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    if (varIds != null && varIds.size() > 0) {

                        In<String> in = cb.in(root.get("fkVarId").as(String.class));
                        for (String varId : varIds) {
                            in.value(varId);
                        }
                        list.add(in);
                    }
                    Predicate[] p = new Predicate[list.size()];
                    return cb.and(list.toArray(p));
                }
            };
            if (varIds != null && varIds.size() > 0) {
                List<FileDrm> drms = fileDrmDao.findAll(varSpec);
                if (drms != null) {
                    for (FileDrm drm : drms) {
                        String fid = drm.getFkFid();
                        fileIdList.add(fid);
                    }
                }
            }

            List<FileAccessScope> scopes = fileAccessScopeDao.findByFkUid(user.getPkUid());
            for (FileAccessScope scope : scopes) {
                fileIdList.add(scope.getFkFid());
            }

            // 不限定知悉范围的
            if (SystemProperties.getInstance().isScopeDefaultAccess()) {
                List<String> fids = fileDao.findFileNoScopes();
                if (fids != null && !fids.isEmpty()) {
                    fileIdList.addAll(fids);
                }
            } else {
                // 无知悉范围或知悉范围描述的，表示不限定知悉范围
                List<String> fids = fileDao.findByScopes();
                if (fids != null && !fids.isEmpty()) {
                    fileIdList.addAll(fids);
                }
            }

            if (!fileIdList.isEmpty()) {
                spec = new Specification<File>() {
                    @Override
                    public Predicate toPredicate(Root<File> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                        List<Predicate> list = new ArrayList<Predicate>();

                        if (fileIdList != null) {
                            In<String> in = cb.in(root.get("pkFid").as(String.class));
                            for (String fileId : fileIdList) {
                                in.value(fileId);
                            }
                            list.add(in);
                        }
                        list.add(cb.equal(root.get("outOfDate").as(Integer.class), 0));
                        if (file_QueryPrivateListRequest.getFileState() != null) {
                            list.add(cb.equal(root.get("fileState").as(Integer.class),
                                    file_QueryPrivateListRequest.getFileState()));
                        }
                        if (file_QueryPrivateListRequest.getFileLevel() != null) {
                            list.add(cb.equal(root.get("fileLevel").as(Integer.class),
                                    file_QueryPrivateListRequest.getFileLevel()));
                        }
                        Predicate[] p = new Predicate[list.size()];
                        Predicate predicate = cb.and(list.toArray(p));
                        if (file_QueryPrivateListRequest.getKeyword() != null
                                && !file_QueryPrivateListRequest.getKeyword().isEmpty()) {
                            Predicate name = cb.like(root.get("name").as(String.class),
                                    "%" + file_QueryPrivateListRequest.getKeyword() + "%");
                            Predicate business = cb.like(root.get("business").as(String.class),
                                    "%" + file_QueryPrivateListRequest.getKeyword() + "%");
                            predicate = cb.and(predicate, cb.or(name, business));
                        }
                        return predicate;
                    }
                };
            }
        }

        // 拟稿箱
        if (filter == FilterEnum.draftbox.getCode()) {
            spec = new Specification<File>() {

                @Override
                public Predicate toPredicate(Root<File> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate userByFkFileCreateUid = cb.equal(root.get("userByFkFileCreateUid").as(User.class), user);
                    Predicate outOfDate = cb.equal(root.get("outOfDate").as(Integer.class), 0);
                    Predicate predicate = cb.and(userByFkFileCreateUid, outOfDate);
                    if (file_QueryPrivateListRequest.getFileState() != null) {
                        predicate = cb.and(predicate, cb.equal(root.get("fileState").as(Integer.class),
                                file_QueryPrivateListRequest.getFileState()));
                    }
                    if (file_QueryPrivateListRequest.getFileLevel() != null) {
                        predicate = cb.and(predicate, cb.equal(root.get("fileLevel").as(Integer.class),
                                file_QueryPrivateListRequest.getFileLevel()));
                    }
                    if (file_QueryPrivateListRequest.getKeyword() != null
                            && !file_QueryPrivateListRequest.getKeyword().isEmpty()) {
                        Predicate name = cb.like(root.get("name").as(String.class),
                                "%" + file_QueryPrivateListRequest.getKeyword() + "%");
                        Predicate business = cb.like(root.get("business").as(String.class),
                                "%" + file_QueryPrivateListRequest.getKeyword() + "%");
                        predicate = cb.and(predicate, cb.or(name, business));
                    }
                    query.where(predicate);
                    return query.getRestriction();
                }
            };

        }

        // 待解密(定密责任人专用)
        if (filter == FilterEnum.decrypt.getCode()) {

            if (!securityPolicyService.isPrivileged(user.getPkUid(), RoleConsts.Type.makeSecret.getCode())) {
                throw new ApiException(ErrorCode.permissionDenied);
            }
            // 定密责任人
            // 定密责任人的历史文件
            // Collection<String> changeHistoryfileIds =
            // fileLevelChangeHistoryDao
            // .findByUserByFkFileLevelDecideUid(user.getPkUid());
            // fileIdList.addAll(changeHistoryfileIds);
            // 现在时间戳+一年的时间戳 计算：目前时间 + 1年 >= 保密期限 + 定密日期
            Long timeValue = System.currentTimeMillis() + TimeConsts.year_in_millis;
            List<String> fids = fileDao.findWillDecryptFiles(timeValue);
            spec = new Specification<File>() {

                @Override
                public Predicate toPredicate(Root<File> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();

                    if (fids != null && fids.size() > 0) {
                        In<String> in = cb.in(root.get("pkFid").as(String.class));
                        for (String fileId : fids) {
                            in.value(fileId);
                        }
                        list.add(in);
                    }
                    list.add(cb.notEqual(root.get("fileState").as(Integer.class),
                            FileConsts.State.preMakeSecret.getCode()));
                    list.add(cb.notEqual(root.get("fileState").as(Integer.class), FileConsts.State.unSecret.getCode()));
                    list.add(cb.equal(root.get("outOfDate").as(Integer.class), 0));
                    if (file_QueryPrivateListRequest.getFileState() != null) {
                        list.add(cb.equal(root.get("fileState").as(Integer.class),
                                file_QueryPrivateListRequest.getFileState()));
                    }
                    if (file_QueryPrivateListRequest.getFileLevel() != null) {
                        list.add(cb.equal(root.get("fileLevel").as(Integer.class),
                                file_QueryPrivateListRequest.getFileLevel()));
                    }
                    Predicate[] p = new Predicate[list.size()];
                    Predicate predicate = cb.and(list.toArray(p));
                    // 与文件id是或者的关系
                    Predicate fileLevelDecideUser = cb.equal(root.get("userByFkFileLevelDecideUid").as(User.class),
                            user);

                    if (fileIdList != null && fileIdList.size() > 0) {
                        In<String> in = cb.in(root.get("pkFid").as(String.class));
                        for (String fileId : fileIdList) {
                            in.value(fileId);
                        }
                        predicate = cb.and(predicate, cb.or(in, fileLevelDecideUser));
                    } else {
                        predicate = cb.and(predicate, fileLevelDecideUser);
                    }

                    if (file_QueryPrivateListRequest.getKeyword() != null
                            && !file_QueryPrivateListRequest.getKeyword().isEmpty()) {
                        Predicate name = cb.like(root.get("name").as(String.class),
                                "%" + file_QueryPrivateListRequest.getKeyword() + "%");
                        Predicate business = cb.like(root.get("business").as(String.class),
                                "%" + file_QueryPrivateListRequest.getKeyword() + "%");
                        predicate = cb.and(predicate, cb.or(name, business));
                    }
                    return predicate;
                }
            };
        }

        // 已解密(定密责任人专用)
        if (filter == FilterEnum.declassified.getCode()) {
            if (!securityPolicyService.isPrivileged(user.getPkUid(), RoleConsts.Type.makeSecret.getCode())) {
                throw new ApiException(ErrorCode.permissionDenied);
            }
            // 定密责任人的历史文件
            List<FileLevelChangeHistory> changeHistorys = fileLevelChangeHistoryDao
                    .findByUserByFkFileLevelDecideUid(user.getPkUid());
            List<String> changeHistoryfids = new ArrayList<>();
            for (FileLevelChangeHistory h : changeHistorys) {
                changeHistoryfids.add(h.getFile().getPkFid());
            }
            fileIdList.addAll(changeHistoryfids);
            spec = new Specification<File>() {

                @Override
                public Predicate toPredicate(Root<File> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate fileState = cb.equal(root.get("fileState").as(Integer.class),
                            FileConsts.State.unSecret.getCode());
                    Predicate fileLevelDecide = cb.equal(root.get("userByFkFileLevelDecideUid").as(User.class), user);
                    Predicate predicate = cb.and(fileState);
                    predicate = cb.and(predicate, cb.equal(root.get("outOfDate").as(Integer.class), 0));

                    if (file_QueryPrivateListRequest.getFileState() != null) {
                        predicate = cb.and(predicate, cb.equal(root.get("fileState").as(Integer.class),
                                file_QueryPrivateListRequest.getFileState()));
                    }
                    if (file_QueryPrivateListRequest.getFileLevel() != null) {
                        predicate = cb.and(predicate, cb.equal(root.get("fileLevel").as(Integer.class),
                                file_QueryPrivateListRequest.getFileLevel()));
                    }

                    if (file_QueryPrivateListRequest.getKeyword() != null
                            && !file_QueryPrivateListRequest.getKeyword().isEmpty()) {
                        Predicate name = cb.like(root.get("name").as(String.class),
                                "%" + file_QueryPrivateListRequest.getKeyword() + "%");
                        Predicate business = cb.like(root.get("business").as(String.class),
                                "%" + file_QueryPrivateListRequest.getKeyword() + "%");
                        predicate = cb.and(predicate, cb.or(name, business));
                    }

                    if (fileIdList != null && fileIdList.size() > 0) {
                        In<String> in = cb.in(root.get("pkFid").as(String.class));
                        for (String fileId : fileIdList) {
                            in.value(fileId);
                        }
                        predicate = cb.and(predicate, cb.or(in, fileLevelDecide));
                    } else {
                        predicate = cb.and(predicate, fileLevelDecide);
                    }
                    return predicate;
                }
            };
        }

        // 定密责任人管辖内的文件(定密责任人专用)
        if (filter == FilterEnum.jurisdiction.getCode()) {
            if (!securityPolicyService.isPrivileged(user.getPkUid(), RoleConsts.Type.makeSecret.getCode())) {
                throw new ApiException(ErrorCode.permissionDenied);
            }
            // 获取定密责任人管辖的范围
            List<Role> roles = roleDao.findUserAllRoleByRoleType(RoleConsts.Type.makeSecret.getCode(), user.getPkUid());
            List<Role> roles2 = roleDao.findUserAllRoleByRoleTypeAgent(RoleConsts.Type.makeSecret.getCode(),
                    user.getPkUid(), System.currentTimeMillis());
            roles.addAll(roles2);
            // 得到所有负责的部门
            List<String> scopeAll = new ArrayList<>();
            List<String> scopes = new ArrayList<>();
            for (Role role : roles) {
                scopes.addAll(roleScopeDao.findByRoleId(role.getId()));
            }
            // 查找所有子部门
            for (String did : scopes) {
                securityPolicyService.childDepartment(did, scopeAll);
            }
            // 根据部门id得到所有用户id
            List<User> users = userToDepartmentDao.findByDId(scopeAll);
            spec = new Specification<File>() {

                @Override
                public Predicate toPredicate(Root<File> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                    if (users != null && users.size() > 0) {
                        In<User> in = cb.in(root.get("userByFkFileCreateUid").as(User.class));
                        for (User user : users) {
                            in.value(user);
                        }
                        Predicate predicate = cb.and(in, cb.equal(root.get("outOfDate").as(Integer.class), 0));
                        if (file_QueryPrivateListRequest.getFileState() != null) {
                            predicate = cb.and(predicate, cb.equal(root.get("fileState").as(Integer.class),
                                    file_QueryPrivateListRequest.getFileState()));
                        }
                        if (file_QueryPrivateListRequest.getFileLevel() != null) {
                            predicate = cb.and(predicate, cb.equal(root.get("fileLevel").as(Integer.class),
                                    file_QueryPrivateListRequest.getFileLevel()));
                        }
                        if (file_QueryPrivateListRequest.getKeyword() != null
                                && !file_QueryPrivateListRequest.getKeyword().isEmpty()) {
                            Predicate name = cb.like(root.get("name").as(String.class),
                                    "%" + file_QueryPrivateListRequest.getKeyword() + "%");
                            Predicate business = cb.like(root.get("business").as(String.class),
                                    "%" + file_QueryPrivateListRequest.getKeyword() + "%");
                            predicate = cb.and(predicate, cb.or(name, business));
                        }
                        return predicate;
                    }
                    return null;
                }
            };

        }
        Pageable pageable = new PageRequest(file_QueryPrivateListRequest.getPageNumber(),
                file_QueryPrivateListRequest.getPageSize(), Sort.Direction.DESC, "fileCreateTime");
        if (spec == null) {
            return new File_QueryPrivateListResponse(0, 0, new ArrayList<>());
        }
        Page<File> page = fileDao.findAll(spec, pageable);
        List<File> fileList = page.getContent();
        // 存储返回数据
        List<FileInfo> returnList = new ArrayList<FileInfo>();
        for (File file : fileList) {
            FileInfo fileInfo = fileService.getFileInfo(file.getPkFid());
            if (fileInfo != null) {
                returnList.add(fileInfo);
            }
        }
        // 获取总行数
        Long totaoElements = page.getTotalElements();
        resp.setTotalElements(new Integer(totaoElements.intValue()));
        resp.setTotalPages(page.getTotalPages());
        resp.setFileList(returnList);
        return resp;
    }

}
