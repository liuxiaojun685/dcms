package cn.bestsec.dcms.platform.impl.group;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Group_QueryByIdApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Group_QueryByIdRequest;
import cn.bestsec.dcms.platform.api.model.Group_QueryByIdResponse;
import cn.bestsec.dcms.platform.dao.GroupDao;
import cn.bestsec.dcms.platform.entity.Group;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月17日 下午2:36:26
 */
@Component
public class GroupQueryById implements Group_QueryByIdApi {
    @Autowired
    private GroupDao groupDao;

    @Override
    @Transactional
    public Group_QueryByIdResponse execute(Group_QueryByIdRequest group_QueryByIdRequest) throws ApiException {
        Group group = groupDao.findByPkGid(group_QueryByIdRequest.getGid());
        if (group == null) {
            throw new ApiException(ErrorCode.targetNotExist);
        }
        Group_QueryByIdResponse resp = new Group_QueryByIdResponse();
        resp.setCreateTime(group.getCreateTime());
        if (group.getDepartment() != null) {
            resp.setDepartmentName(group.getDepartment().getName());
        }
        resp.setDescription(group.getDescription());
        resp.setGid(group.getPkGid());
        resp.setName(group.getName());
        return resp;
    }

}
