package cn.bestsec.dcms.platform.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.consts.DepartmentConsts;
import cn.bestsec.dcms.platform.consts.FileConsts;
import cn.bestsec.dcms.platform.consts.RoleConsts;
import cn.bestsec.dcms.platform.consts.SystemConsts;
import cn.bestsec.dcms.platform.dao.DepartmentDao;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyDao;
import cn.bestsec.dcms.platform.dao.WorkflowPolicyStepDao;
import cn.bestsec.dcms.platform.dao.entity.DepartmentInLevel;
import cn.bestsec.dcms.platform.entity.Department;
import cn.bestsec.dcms.platform.entity.Role;
import cn.bestsec.dcms.platform.entity.RoleScope;
import cn.bestsec.dcms.platform.entity.WorkflowPolicy;
import cn.bestsec.dcms.platform.entity.WorkflowPolicyStep;
import cn.bestsec.dcms.platform.service.ConnectTestService;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;
import cn.bestsec.dcms.platform.utils.TextUtils;

/**
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2017年6月30日 下午5:44:31
 */
@Service
@Transactional
public class ConnectTestServiceImpl implements ConnectTestService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private WorkflowPolicyDao workflowPolicyDao;
    @Autowired
    private WorkflowPolicyStepDao workflowPolicyStepDao;

    @Override
    public List<String> checkResult(Integer type) throws ApiException {
        List<Role> findByRoleType = null;
        List<String> resultStr = null;
        if (type == SystemConsts.ConnectTest.makeSecret.getCode()) {
            findByRoleType = roleDao.findByRoleType(RoleConsts.Type.makeSecret.getCode());
            resultStr = checkPersonLiable(findByRoleType);
        } else if (type == SystemConsts.ConnectTest.dispatchman.getCode()) {
            findByRoleType = roleDao.findByRoleType(RoleConsts.Type.dispatchman.getCode());
            resultStr = checkPersonLiable(findByRoleType);
        } else if (type == SystemConsts.ConnectTest.workflowPolicy.getCode()) {
            resultStr = new ArrayList<>();
            // 查找所有已配置的流程策略
            List<WorkflowPolicy> workflowPolicys = workflowPolicyDao.findAll();
            if (workflowPolicys == null || workflowPolicys.isEmpty()) {
                resultStr.add("还没有配置流程策略");
            } else {
                for (WorkflowPolicy workflowPolicy : workflowPolicys) {
                    // 获取流程类型、密级
                    String workflowType = TextUtils.getWorkflowType(workflowPolicy.getWorkFlowType());
                    String level = TextUtils.getLevel(workflowPolicy.getFileLevel());
                    // 得到流程每级负责人
                    for (int step = 1;; step++) {
                        List<WorkflowPolicyStep> stepList = workflowPolicyStepDao
                                .findByFkWorkFlowPolicyIdAndStep(workflowPolicy.getId(), step);
                        if (stepList.isEmpty()) {
                            // 该流程策略还未配置级数
                            if (step == 1) {
                                resultStr.add(workflowType + "[" + level + "]未配置流程步骤(级数)");
                            }

                            break;
                        }
                        // 所有已经配置了审批人的部门id
                        List<String> scopes = new ArrayList<>();
                        // 所有部门的子部门
                        List<String> scopeAll = new ArrayList<>();

                        for (WorkflowPolicyStep workflowPolicyStep : stepList) {
                            Role role = workflowPolicyStep.getRole();
                            List<RoleScope> roleScopes = role.getRoleScopes();
                            for (RoleScope roleScope : roleScopes) {
                                scopes.add(roleScope.getFkVarId());
                            }

                            for (String did : scopes) {
                                securityPolicyService.childDepartment(did, scopeAll);
                            }
                        }

                        // 根据得到的部门筛选没有责任人的部门
                        List<Department> departments = departmentDao.findByDepartmentStateAndPkDidNotIn(
                                DepartmentConsts.state.undelete.getCode(), scopeAll);
                        StringBuffer sb = new StringBuffer();
                        if (departments != null && !departments.isEmpty()) {
                            for (int i = 0; i < departments.size(); i++) {
                                if (i == departments.size() - 1) {
                                    sb.append(departments.get(i).getName());
                                } else {
                                    sb.append(departments.get(i).getName() + "、");
                                }
                            }

                            resultStr.add(sb + workflowType + "[" + level + "]第" + step + "级缺少审批人");
                        }
                    }
                }
            }
        }
        return resultStr;
    }

    // 返回数据
    public List<String> checkPersonLiable(List<Role> findByRoleType) throws ApiException {
        // 所有已经配置了定密责任人的部门id
        List<String> scopes = new ArrayList<>();
        List<DepartmentInLevel> departmentInLevels = new ArrayList<>();
        for (Role role : findByRoleType) {

            List<RoleScope> roleScopes = role.getRoleScopes();
            for (RoleScope roleScope : roleScopes) {
                if (roleScope.getVarType() == 3) {
                    scopes.add(roleScope.getFkVarId());

                }
            }
            // 获取该角色负责的密级
            List<Integer> fileLevels = new ArrayList<>();
            for (int level = FileConsts.Level.open.getCode(); level <= FileConsts.Level.topSecret.getCode(); level++) {
                if (((role.getFileLevel() >> level) & 1) == 1) {
                    fileLevels.add(level);
                }
            }
            if (roleScopes != null && roleScopes.size() > 0) {
                List<String> scopeAll = new ArrayList<>();
                // 每个部门id对应所属的密级
                for (RoleScope roleScope : roleScopes) {
                    // 获取所有子部门
                    if (roleScope.getVarType() == 3) {
                        securityPolicyService.childDepartment(roleScope.getFkVarId(), scopeAll);
                    }
                }
                for (String varId : scopeAll) {
                    // 定义一个key，用于判断是否有相同的varId
                    Boolean key = false;
                    DepartmentInLevel departmentInLevel = new DepartmentInLevel();
                    // varId 在departmentInLevels是否有相同的，是，密级叠加
                    if (!departmentInLevels.isEmpty()) {
                        for (int i = 0; i < departmentInLevels.size(); i++) {
                            if (departmentInLevels.get(i).getVarId().equals(varId)) {
                                key = true;
                                // 叠加密级
                                List<Integer> fileLevels1 = departmentInLevels.get(i).getFileLevels();
                                fileLevels.removeAll(fileLevels1);
                                fileLevels.addAll(fileLevels1);
                                departmentInLevels.remove(i);
                                departmentInLevels.add(i, new DepartmentInLevel(fileLevels, varId));
                            }

                        }
                    }

                    if (!key) {
                        departmentInLevel.setFileLevels(fileLevels);
                        departmentInLevel.setVarId(varId);
                        departmentInLevels.add(departmentInLevel);
                    }
                }

            }
            // 获取该角色负责的部门 scopes为负责的部门id
            // List<Department> departments =
            // departmentDao.findByPkDidIn(roleScopes);
            // 没有负责该部门密级文件（公开、内部....）的部门
            // 一个角色负责一个部门的公开、内部的文件，也需要检测出该部门
        }

        // 返回结果
        List<String> resultStr = new ArrayList<>();
        // 根据叠加后的密级，检测缺少的密级
        for (DepartmentInLevel departmentInLevel : departmentInLevels) {
            Department department = departmentDao.findByPkDidAndDepartmentState(departmentInLevel.getVarId(),
                    DepartmentConsts.state.undelete.getCode());
            List<String> levelStrs = TextUtils.getLessLevel(departmentInLevel.getFileLevels());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < levelStrs.size(); i++) {
                if (i == levelStrs.size() - 1) {
                    sb.append(levelStrs.get(i));
                } else {
                    sb.append(levelStrs.get(i) + "、");
                }
            }
            if (sb != null && sb.length() > 0) {
                String r = department.getName() + "缺少" + sb + "文件负责人";
                resultStr.add(r);
            }

        }
        // if所有部门都没有配置责任人
        if (scopes == null || scopes.isEmpty()) {
            throw new ApiException(ErrorCode.targetNotExist);
        }

        // 所有部门的子部门
        List<String> scopeAll = new ArrayList<>();
        for (String did : scopes) {
            securityPolicyService.childDepartment(did, scopeAll);
        }
        // 根据得到的部门筛选没有责任人的部门
        List<Department> departments = departmentDao
                .findByDepartmentStateAndPkDidNotIn(DepartmentConsts.state.undelete.getCode(), scopeAll);
        if (departments != null && !departments.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < departments.size(); i++) {
                if (i == departments.size() - 1) {
                    sb.append(departments.get(i).getName());
                } else {
                    sb.append(departments.get(i).getName() + "、");
                }
            }
            sb.append("缺少负责人");

            resultStr.add(sb + "");
        }
        return resultStr;

    }

}
