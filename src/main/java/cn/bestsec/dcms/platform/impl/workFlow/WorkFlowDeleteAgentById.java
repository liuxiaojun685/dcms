package cn.bestsec.dcms.platform.impl.workFlow;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.WorkFlow_DeleteAgentByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.WorkFlow_DeleteAgentByIdRequest;
import cn.bestsec.dcms.platform.api.model.WorkFlow_DeleteAgentByIdResponse;
import cn.bestsec.dcms.platform.consts.WorkFlowConsts;
import cn.bestsec.dcms.platform.consts.WorkflowTrackConsts;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.WorkflowTrackDao;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.WorkflowTrack;

/**
 * 17.5 删除审核代理人
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年2月4日 下午3:05:57
 */
@Component
public class WorkFlowDeleteAgentById implements WorkFlow_DeleteAgentByIdApi {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private WorkflowTrackDao workflowTrackDao;

    @Override
    @Transactional
    public WorkFlow_DeleteAgentByIdResponse execute(WorkFlow_DeleteAgentByIdRequest workFlow_DeleteAgentByIdRequest)
            throws ApiException {
        Role role = roleDao.findById(workFlow_DeleteAgentByIdRequest.getRoleId());
        if (role == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        User agent = role.getUserByFkAgentUid();
        // 如果代理有未办完的审批，不能删除
        List<WorkflowTrack> tracks = workflowTrackDao.findByUser(agent);
        for (WorkflowTrack track : tracks) {
            if (track.getApproveState() == WorkflowTrackConsts.state.notApproved.getCode()
                    && track.getWorkflow().getFlowState() == WorkFlowConsts.State.notcomplete.getCode()) {
                throw new ApiException(ErrorCode.userhasUnfinishedApproval);
            }
        }
        role.setUserByFkAgentUid(null);
        role.setFkAgentInvalidTime(0L);
        roleDao.save(role);
        return new WorkFlow_DeleteAgentByIdResponse();
    }

}
