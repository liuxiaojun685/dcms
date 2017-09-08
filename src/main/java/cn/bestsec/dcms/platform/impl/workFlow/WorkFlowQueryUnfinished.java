package cn.bestsec.dcms.platform.impl.workFlow;

import java.util.ArrayList;
import java.util.List;

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

import cn.bestsec.dcms.platform.api.WorkFlow_QueryUnfinishedApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.FileSimpleInfo;
import cn.bestsec.dcms.platform.api.model.UserSimpleInfo;
import cn.bestsec.dcms.platform.api.model.WorkFlowSimpleInfo;
import cn.bestsec.dcms.platform.api.model.WorkFlow_QueryUnfinishedRequest;
import cn.bestsec.dcms.platform.api.model.WorkFlow_QueryUnfinishedResponse;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.FileDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.WorkFlowDao;
import cn.bestsec.dcms.platform.dao.WorkflowTrackDao;
import cn.bestsec.dcms.platform.entity.File;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.Workflow;
import cn.bestsec.dcms.platform.entity.WorkflowTrack;

/**
 * 查询未办理的审批
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2017年1月3日下午12:06:24
 */
@Component
public class WorkFlowQueryUnfinished implements WorkFlow_QueryUnfinishedApi {
    @Autowired
    private WorkFlowDao workFlowDao;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private WorkflowTrackDao workFlowTrackDao;
    @Autowired
    private FileDao fileDao;

    @Override
    @Transactional
    public WorkFlow_QueryUnfinishedResponse execute(WorkFlow_QueryUnfinishedRequest workFlow_QueryUnfinishedRequest)
            throws ApiException {
        Token token = tokenDao.findByToken(workFlow_QueryUnfinishedRequest.getToken());
        User user = token.getUser();
        // 得到该用户全部未审批的流程详情信息WorkflowTrack
        List<WorkflowTrack> trackList = workFlowTrackDao.findMyTurn(user.getPkUid());
        Specification<Workflow> spec = new Specification<Workflow>() {

            @Override
            public Predicate toPredicate(Root<Workflow> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = null;
                if (trackList != null && trackList.size() > 0) {
                    In<Integer> in = cb.in(root.get("id").as(Integer.class));
                    for (WorkflowTrack track : trackList) {
                        in.value(track.getWorkflow().getId());
                    }
                    predicate = cb.and(in);
                }
                return predicate;
            }
        };
        Pageable pageable = new PageRequest(workFlow_QueryUnfinishedRequest.getPageNumber(),
                workFlow_QueryUnfinishedRequest.getPageSize(), Sort.Direction.DESC, "createTime");
        if (trackList.isEmpty()) {
            return new WorkFlow_QueryUnfinishedResponse(0, 0, new ArrayList<>());
        }
        Page<Workflow> page = workFlowDao.findAll(spec, pageable);
        List<Workflow> workflows = page.getContent();

        List<WorkFlowSimpleInfo> workFlowSimpleInfos = new ArrayList<WorkFlowSimpleInfo>();
        for (Workflow workflow : workflows) {
            WorkFlowSimpleInfo workFlowSimpleInfo = new WorkFlowSimpleInfo();
            workFlowSimpleInfo.setApplyFileLevel(workflow.getApplyFileLevel());
            workFlowSimpleInfo.setCreateTime(workflow.getCreateTime());

            UserSimpleInfo userSimpleInfo = new UserSimpleInfo();
            userSimpleInfo.setAccount(workflow.getUser().getAccount());
            userSimpleInfo.setLevel(workflow.getUser().getUserLevel());
            userSimpleInfo.setName(workflow.getUser().getName());
            userSimpleInfo.setOnline(UserConsts.userOnline(workflow.getUser()));
            userSimpleInfo.setUid(workflow.getUser().getPkUid());
            workFlowSimpleInfo.setCreateUser(userSimpleInfo);

            // 源文件
            FileSimpleInfo srcFile = new FileSimpleInfo();
            File file = fileDao.findByPkFid(workflow.getFkSrcFid());
            srcFile.setFid(workflow.getFkSrcFid());
            srcFile.setFileName(workflow.getFileName());
            if (file != null) {
                srcFile.setFileLevel(file.getFileLevel());
                srcFile.setUrgency(file.getUrgency());
            }
            workFlowSimpleInfo.setSrcFile(srcFile);
            workFlowSimpleInfo.setWorkFlowId(workflow.getId());
            workFlowSimpleInfo.setWorkFlowType(workflow.getWorkFlowType());
            workFlowSimpleInfo.setUrgency(workflow.getUrgency());
            workFlowSimpleInfos.add(workFlowSimpleInfo);
        }
        WorkFlow_QueryUnfinishedResponse resp = new WorkFlow_QueryUnfinishedResponse();
        // 获取总行数
        Long totaoElements = page.getTotalElements();
        resp.setTotalElements(new Integer(totaoElements.intValue()));
        resp.setTotalPages(page.getTotalPages());
        resp.setWorkFlowList(workFlowSimpleInfos);
        return resp;
    }

}
