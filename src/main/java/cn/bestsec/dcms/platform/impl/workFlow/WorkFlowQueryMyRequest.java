package cn.bestsec.dcms.platform.impl.workFlow;

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

import cn.bestsec.dcms.platform.api.WorkFlow_QueryMyRequestApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.FileSimpleInfo;
import cn.bestsec.dcms.platform.api.model.UserSimpleInfo;
import cn.bestsec.dcms.platform.api.model.WorkFlowSimpleInfo;
import cn.bestsec.dcms.platform.api.model.WorkFlow_QueryMyRequestRequest;
import cn.bestsec.dcms.platform.api.model.WorkFlow_QueryMyRequestResponse;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.WorkFlowDao;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.Workflow;

/**
 * 17.9 查询我提交的审批
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年2月4日 下午4:19:59
 */
@Component
public class WorkFlowQueryMyRequest implements WorkFlow_QueryMyRequestApi {

    @Autowired
    private WorkFlowDao workFlowDao;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private FileDao fileDao;

    @Override
    @Transactional
    public WorkFlow_QueryMyRequestResponse execute(WorkFlow_QueryMyRequestRequest workFlow_QueryMyRequestRequest)
            throws ApiException {
        Token token = tokenDao.findByToken(workFlow_QueryMyRequestRequest.getToken());
        User user = token.getUser();
        // workFlow_QueryMyRequestRequest.getState() 1完成 0未完成
        Specification<Workflow> spec = buildSpecification(user, workFlow_QueryMyRequestRequest.getState());

        Pageable pageable = new PageRequest(workFlow_QueryMyRequestRequest.getPageNumber(),
                workFlow_QueryMyRequestRequest.getPageSize(), Sort.Direction.DESC, "createTime");
        Page<Workflow> page = workFlowDao.findAll(spec, pageable);
        List<Workflow> content = page.getContent();
        // 存返回的数据
        List<WorkFlowSimpleInfo> workFlowList = new ArrayList<WorkFlowSimpleInfo>();
        for (Workflow workflow : content) {
            WorkFlowSimpleInfo workFlowSimpleInfo = new WorkFlowSimpleInfo();
            workFlowSimpleInfo.setApplyFileLevel(workflow.getApplyFileLevel());
            workFlowSimpleInfo.setCreateTime(workflow.getCreateTime());
            UserSimpleInfo createUser = new UserSimpleInfo();
            // 当前流程的创建用户
            User fkUser = workflow.getUser();
            createUser.setAccount(fkUser.getAccount());
            createUser.setLevel(fkUser.getUserLevel());
            createUser.setName(fkUser.getName());
            createUser.setOnline(UserConsts.userOnline(fkUser));
            createUser.setUid(fkUser.getPkUid());
            workFlowSimpleInfo.setCreateUser(createUser);

            workFlowSimpleInfo.setFlowState(workflow.getFlowState());
            FileSimpleInfo srcFile = new FileSimpleInfo();
            // 源文件
            File fileByFkSrcFid = fileDao.findByPkFid(workflow.getFkSrcFid());
            srcFile.setFid(workflow.getFkSrcFid());
            srcFile.setFileName(workflow.getFileName());
            if (fileByFkSrcFid != null) {
                srcFile.setFileLevel(fileByFkSrcFid.getFileLevel());
                srcFile.setUrgency(fileByFkSrcFid.getUrgency());
            }
            workFlowSimpleInfo.setSrcFile(srcFile);

            workFlowSimpleInfo.setWorkFlowId(workflow.getId());
            workFlowSimpleInfo.setWorkFlowType(workflow.getWorkFlowType());
            workFlowSimpleInfo.setUrgency(workflow.getUrgency());
            workFlowList.add(workFlowSimpleInfo);
        }

        WorkFlow_QueryMyRequestResponse resp = new WorkFlow_QueryMyRequestResponse();
        Long totaoElements = page.getTotalElements();
        resp.setTotalElements(new Integer(totaoElements.intValue()));
        resp.setTotalPages(page.getTotalPages());
        resp.setWorkFlowList(workFlowList);
        return resp;
    }

    private Specification<Workflow> buildSpecification(User user, Integer flowState) {

        return new Specification<Workflow>() {

            @Override
            public Predicate toPredicate(Root<Workflow> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.equal(root.get("user").as(User.class), user);
                Predicate state = cb.equal(root.get("flowState").as(Integer.class), flowState);
                return cb.and(predicate, state);
            }
        };
    }

}
