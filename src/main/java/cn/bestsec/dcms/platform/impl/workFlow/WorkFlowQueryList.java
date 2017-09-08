package cn.bestsec.dcms.platform.impl.workFlow;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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

import cn.bestsec.dcms.platform.api.WorkFlow_QueryListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.FileSimpleInfo;
import cn.bestsec.dcms.platform.api.model.UserSimpleInfo;
import cn.bestsec.dcms.platform.api.model.WorkFlowSimpleInfo;
import cn.bestsec.dcms.platform.api.model.WorkFlow_QueryListRequest;
import cn.bestsec.dcms.platform.api.model.WorkFlow_QueryListResponse;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.FileLevelValidPeriodDao;
import cn.bestsec.dcms.platform.dao.WorkFlowDao;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.Workflow;
import cn.bestsec.dcms.platform.service.LogArchiveService;
import cn.bestsec.dcms.platform.utils.TextUtils;

/**
 * 查询流程列表
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2017年1月20日下午4:04:39
 */
@Component
public class WorkFlowQueryList implements WorkFlow_QueryListApi {
    @Autowired
    private WorkFlowDao workFlowDao;
    @Autowired
    private FileDao fileDao;
    @Autowired
    private FileLevelValidPeriodDao fileLevelValidPeriodDao;
    @Autowired
    private LogArchiveService logArchiveService;

    @Override
    @Transactional
    public WorkFlow_QueryListResponse execute(WorkFlow_QueryListRequest workFlow_QueryListRequest) throws ApiException {
        WorkFlow_QueryListResponse resp = new WorkFlow_QueryListResponse();

        Specification<Workflow> spec = buildSpecification(workFlow_QueryListRequest);

        Pageable pageable = new PageRequest(workFlow_QueryListRequest.getPageNumber(),
                workFlow_QueryListRequest.getPageSize(), Sort.Direction.DESC, "createTime");
        Page<Workflow> page = workFlowDao.findAll(spec, pageable);
        List<Workflow> workflows = page.getContent();
        List<WorkFlowSimpleInfo> workFlowSimpleInfos = new ArrayList<>();
        WorkFlowSimpleInfo workFlowSimpleInfo = null;
        UserSimpleInfo userSimpleInfo = null;
        FileSimpleInfo srcFile = null;
        for (Workflow workflow : workflows) {
            workFlowSimpleInfo = new WorkFlowSimpleInfo();
            workFlowSimpleInfo.setApplyFileLevel(workflow.getApplyFileLevel());
            workFlowSimpleInfo.setCreateTime(workflow.getCreateTime());

            userSimpleInfo = new UserSimpleInfo();
            User user = workflow.getUser();
            if (user != null) {

                userSimpleInfo.setAccount(user.getAccount());
                userSimpleInfo.setLevel(user.getUserLevel());
                userSimpleInfo.setName(user.getName());
                userSimpleInfo.setOnline(UserConsts.userOnline(user));
                userSimpleInfo.setUid(user.getPkUid());
            }
            workFlowSimpleInfo.setCreateUser(userSimpleInfo);

            workFlowSimpleInfo.setFlowState(workflow.getFlowState());

            srcFile = new FileSimpleInfo();
            File fileByFkSrcFid = fileDao.findByPkFid(workflow.getFkSrcFid());
            String fileName = workflow.getFileName();
            if (logArchiveService.isFileDesensity(workflow.getApplyFileLevel())) {
                fileName = TextUtils.getDealWithName(fileName);
            }
            srcFile.setFid(workflow.getFkSrcFid());
            srcFile.setFileName(fileName);
            if (fileByFkSrcFid != null) {
                srcFile.setFileLevel(fileByFkSrcFid.getFileLevel());
                srcFile.setUrgency(fileByFkSrcFid.getUrgency());
            }
            workFlowSimpleInfo.setSrcFile(srcFile);

            workFlowSimpleInfo.setWorkFlowId(workflow.getId());
            workFlowSimpleInfo.setWorkFlowType(workflow.getWorkFlowType());
            workFlowSimpleInfo.setUrgency(workflow.getUrgency());
            workFlowSimpleInfos.add(workFlowSimpleInfo);
        }
        Long totaoElements = page.getTotalElements();
        resp.setTotalElements(new Integer(totaoElements.intValue()));
        resp.setTotalPages(page.getTotalPages());
        resp.setWorkFlowList(workFlowSimpleInfos);
        return resp;
    }

    // 传参
    private Specification<Workflow> buildSpecification(WorkFlow_QueryListRequest workFlow_QueryListRequest) {

        return new Specification<Workflow>() {

            @Override
            public Predicate toPredicate(Root<Workflow> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                if (workFlow_QueryListRequest.getKeyword() != null
                        && !workFlow_QueryListRequest.getKeyword().isEmpty()) {
                    // 发起理由
                    Predicate createComment = cb.like(root.get("createComment").as(String.class),
                            "%" + workFlow_QueryListRequest.getKeyword() + "%");

                    // 文件名
                    Predicate fileName = cb.like(root.get("fileName").as(String.class),
                            "%" + workFlow_QueryListRequest.getKeyword() + "%");
                    Join<Workflow, User> userNameI = root.join("user", JoinType.INNER);
                    Predicate userName = cb.like(userNameI.get("name").as(String.class),
                            "%" + workFlow_QueryListRequest.getKeyword() + "%");
                    predicate = cb.or(createComment, fileName, userName);
                }

                if (workFlow_QueryListRequest.getFlowState() != null) {
                    if (predicate != null) {
                        predicate = cb.and(predicate, cb.equal(root.get("flowState").as(Integer.class),
                                workFlow_QueryListRequest.getFlowState()));
                    } else {
                        predicate = cb.equal(root.get("flowState").as(Integer.class),
                                workFlow_QueryListRequest.getFlowState());
                    }
                }
                return predicate;
            }
        };
    }

}
