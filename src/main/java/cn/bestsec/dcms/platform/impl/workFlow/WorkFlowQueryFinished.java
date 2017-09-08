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

import cn.bestsec.dcms.platform.api.WorkFlow_QueryFinishedApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.FileSimpleInfo;
import cn.bestsec.dcms.platform.api.model.UserSimpleInfo;
import cn.bestsec.dcms.platform.api.model.WorkFlowSimpleInfo;
import cn.bestsec.dcms.platform.api.model.WorkFlow_QueryFinishedRequest;
import cn.bestsec.dcms.platform.api.model.WorkFlow_QueryFinishedResponse;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.consts.WorkFlowConsts;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.WorkflowTrackDao;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.Workflow;
import cn.bestsec.dcms.platform.entity.WorkflowTrack;

/**
 * 17.8 查询已办理的审批
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年2月4日 下午3:16:36
 */
@Component
public class WorkFlowQueryFinished implements WorkFlow_QueryFinishedApi {

    @Autowired
    private WorkflowTrackDao workFlowTrackDao;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private FileDao fileDao;

    @Override
    @Transactional
    public WorkFlow_QueryFinishedResponse execute(WorkFlow_QueryFinishedRequest workFlow_QueryFinishedRequest)
            throws ApiException {
        Token token = tokenDao.findByToken(workFlow_QueryFinishedRequest.getToken());
        User user = token.getUser();

        Specification<WorkflowTrack> spec = buildSpecification(user);

        Pageable pageable = new PageRequest(workFlow_QueryFinishedRequest.getPageNumber(),
                workFlow_QueryFinishedRequest.getPageSize(), Sort.Direction.DESC, "approveTime");
        Page<WorkflowTrack> page = workFlowTrackDao.findAll(spec, pageable);
        List<WorkflowTrack> content = page.getContent();
        // 存返回的数据
        List<WorkFlowSimpleInfo> workFlowList = new ArrayList<WorkFlowSimpleInfo>();
        for (WorkflowTrack workflowTrack : content) {
            WorkFlowSimpleInfo workFlowSimpleInfo = new WorkFlowSimpleInfo();
            // 当前流程
            Workflow workflow = workflowTrack.getWorkflow();
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
        WorkFlow_QueryFinishedResponse resp = new WorkFlow_QueryFinishedResponse();
        Long totaoElements = page.getTotalElements();
        resp.setTotalElements(new Integer(totaoElements.intValue()));
        resp.setTotalPages(page.getTotalPages());
        resp.setWorkFlowList(workFlowList);
        return resp;
    }

    private Specification<WorkflowTrack> buildSpecification(User user) {

        return new Specification<WorkflowTrack>() {

            @Override
            public Predicate toPredicate(Root<WorkflowTrack> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.notEqual(root.get("approveState").as(int.class),
                        WorkFlowConsts.State.notcomplete.getCode()));
                list.add(cb.equal(root.get("user").as(User.class), user));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
    }

}
