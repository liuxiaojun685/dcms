package cn.bestsec.dcms.platform.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Admin_LoginApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.Admin_LoginRequest;
import cn.bestsec.dcms.platform.api.model.Admin_LoginResponse;
import cn.bestsec.dcms.platform.consts.AdminConsts;
import cn.bestsec.dcms.platform.consts.AdminLogOp;
import cn.bestsec.dcms.platform.dao.AdminDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.entity.Admin;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;
import cn.bestsec.dcms.platform.utils.AdminLogBuilder;
import cn.bestsec.dcms.platform.utils.IdUtils;
import cn.bestsec.dcms.platform.utils.Md5Utils;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;
import cn.bestsec.dcms.platform.utils.SystemProperties;

@Component
public class AdminLogin implements Admin_LoginApi {
    private static final Logger logger = LoggerFactory.getLogger(AdminLogin.class);

    @Autowired
    private AdminDao adminDao;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;

    @Transactional
    @Override
    public Admin_LoginResponse execute(Admin_LoginRequest admin_LoginRequest) throws ApiException {
        long loginTime = System.currentTimeMillis();
        Admin_LoginResponse resp = new Admin_LoginResponse();

        Admin admin = adminDao.findByAccountAndAdminStateNot(admin_LoginRequest.getAccount(),
                AdminConsts.AdminState.deleted.getCode());
        if (admin == null) {
            throw new ApiException(ErrorCode.accountOrPasswordError);
        }

        AdminLogBuilder adminLogBuilder = admin_LoginRequest.createAdminLogBuilder();
        adminLogBuilder.operateTime(loginTime).operation(AdminLogOp.admin_login)
                .operateDescription(admin_LoginRequest.getAccount()).admin(admin);

        // 验证授权
        if (!securityPolicyService.isValidLicense()) {
            throw new ApiException(ErrorCode.invalidSystemLicense);
        }

        HttpServletRequest request = admin_LoginRequest.httpServletRequest();
        if (SystemProperties.getInstance().isIdentifyingCodeEnable()) {
            HttpSession session = request.getSession();
            StringBuffer code = (StringBuffer) session.getAttribute("codes");
            if (code == null) {
                throw new ApiException(ErrorCode.pageOutofdate);
            }
            if (!admin_LoginRequest.getKeyCode().equalsIgnoreCase(code.toString())) {
                throw new ApiException(ErrorCode.identifyingCodeError);
            }
        }

        if (!admin.getPasswd().equals(StringEncrypUtil.encryptNonreversible(admin_LoginRequest.getPasswd()))) {
            throw new ApiException(ErrorCode.accountOrPasswordError);
        }

        // 生成Token
        String md5Hex = Md5Utils.md5Hex(admin.getPkAid() + "-" + admin.getAccount() + "-" + admin.getPasswd() + "-"
                + loginTime + "-" + IdUtils.randomId());
        // 获取终端ip
        String clientIp = request.getRemoteAddr();

        Token token = new Token();
        token.setAdmin(admin);
        token.setToken(md5Hex);
        token.setCreateTime(loginTime);
        token.setHeartbeatTime(loginTime);
        token.setIp(clientIp);
        tokenDao.save(token);

        resp.setAid(admin.getPkAid());
        resp.setAccount(admin.getAccount());
        resp.setToken(md5Hex);
        resp.setName(admin.getName());
        resp.setAdminType(admin.getAdminType());
        resp.setCreateFrom(admin.getCreateFrom());
        return resp;

    }

}
