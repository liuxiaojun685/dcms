package cn.bestsec.dcms.platform.impl.user;

import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.User_LoginApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.exception.ErrorCode;
import cn.bestsec.dcms.platform.api.model.User_LoginRequest;
import cn.bestsec.dcms.platform.api.model.User_LoginResponse;
import cn.bestsec.dcms.platform.consts.ClientConsts;
import cn.bestsec.dcms.platform.consts.ClientLogOp;
import cn.bestsec.dcms.platform.consts.CommonConsts;
import cn.bestsec.dcms.platform.consts.TokenConsts;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.ClientDao;
import cn.bestsec.dcms.platform.dao.ClientLevelAccessPolicyDao;
import cn.bestsec.dcms.platform.dao.RoleDao;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.entity.Client;
import cn.bestsec.dcms.platform.entity.ClientLevelAccessPolicy;
import cn.bestsec.dcms.platform.entity.SystemConfig;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.service.SecurityPolicyService;
import cn.bestsec.dcms.platform.service.SystemConfigService;
import cn.bestsec.dcms.platform.utils.ClientLogBuilder;
import cn.bestsec.dcms.platform.utils.IdUtils;
import cn.bestsec.dcms.platform.utils.Md5Utils;
import cn.bestsec.dcms.platform.utils.StringEncrypUtil;

/**
 * 用户登录接口
 * 
 * @author 刘强 email:liuqiang@bestsec.cn
 * @time 2016年12月23日 下午4:09:09
 */
@Component
public class UserLogin implements User_LoginApi {

    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ClientDao clientDao;
    @Autowired
    private SystemConfigService systemConfigService;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ClientLevelAccessPolicyDao clientLevelAccessPolicyDao;
    @Autowired
    private SecurityPolicyService securityPolicyService;

    @Override
    @Transactional
    public User_LoginResponse execute(User_LoginRequest user_LoginRequest) throws ApiException {
        // 验证授权
        if (!securityPolicyService.isValidLicense()) {
            throw new ApiException(ErrorCode.invalidSystemLicense);
        }
        Long currentTime = System.currentTimeMillis();
        // 通过mac查client终端，如果不存在，注册一条终端信息。如果存在，更新终端信息。
        Client client = clientDao.findByMacAndClientStateNot(user_LoginRequest.getMac(),
                ClientConsts.State.deleted.getCode());
        if (client == null) {
            client = new Client();
            // UUID
            client.setPkCid(IdUtils.randomId(IdUtils.prefix_client_id));
        }
        String clientIp = user_LoginRequest.httpServletRequest().getRemoteAddr();
        client.setIp(clientIp);
        client.setClientLevel(user_LoginRequest.getClientLevel());
        client.setVersionName(user_LoginRequest.getVersionName());
        client.setVersionCode(user_LoginRequest.getVersionCode());
        client.setVersionType(user_LoginRequest.getVersionType());
        client.setPcName(user_LoginRequest.getPcName());
        client.setOsType(user_LoginRequest.getOsType());
        client.setMac(user_LoginRequest.getMac());
        client.setLastLoginTime(currentTime);
        clientDao.save(client);
        // 通过用户Uuid查找用户
        User user = userDao.findByAccount(user_LoginRequest.getAccount());
        // 用户状态为已删除或者已锁定时不能登录
        // 如果用户已删除，则返回用户不存在
        if (user == null || user.getUserState() == UserConsts.State.deleted.getCode()) {
            throw new ApiException(ErrorCode.userNotExist);
        }

        ClientLogBuilder logBuilder = user_LoginRequest.createClientLogBuilder();
        logBuilder.operateTime(System.currentTimeMillis()).operation(ClientLogOp.user_login).client(client).user(user)
                .operateDescription(user.getName());
        // 如果用户已锁定，则返回用户已锁定
        if (user.getUserState() == UserConsts.State.locked.getCode()) {
            if (user.getUnlockTime() > currentTime) {
                throw new ApiException(ErrorCode.userAlreadyLocked);
            } else {
                user.setUserState(UserConsts.State.def.getCode());
                user.setUnlockTime(0L);
                user.setErrLoginCount(0);
            }
        }

        // 用户对该终端是否有访问权限
        ClientLevelAccessPolicy clientLevelAccessPolicy = clientLevelAccessPolicyDao
                .findByUserLevelAndClientLevel(user.getUserLevel(), user_LoginRequest.getClientLevel());
        if (clientLevelAccessPolicy.getEnable() == CommonConsts.Bool.no.getInt()) {
            throw new ApiException(ErrorCode.clientLoginNoPermission);
        }

        // 获取系统配置数据
        SystemConfig config = systemConfigService.getSystemConfig();

        // 判断账号密码验证信息是否正确
        boolean isPasswdCorrect = false;
        if (config.getLocalAuthEnable() == CommonConsts.Bool.yes.getInt()) {
            // 本地认证
            isPasswdCorrect = user.getPasswd()
                    .equals(StringEncrypUtil.encryptNonreversible(user_LoginRequest.getPasswd()));
        } else if (config.getAdAuthEnable() == CommonConsts.Bool.yes.getInt()) {
            // AD认证
            throw new ApiException(ErrorCode.unsupport);
        } else {
            throw new ApiException(ErrorCode.configNotCorrect);
        }

        if (!isPasswdCorrect) {
            // 密码不匹配
            Integer loginCountMax = config.getLocalAuthPasswdLockThreshold();
            // 用户名或者密码错误，记录用户的errLoginCount字段+1
            Integer errLoginCount = user.getErrLoginCount();
            user.setErrLoginCount(++errLoginCount);
            if (loginCountMax == errLoginCount) {
                user.setUserState(UserConsts.State.locked.getCode());
                user.setUnlockTime(currentTime + config.getLocalAuthPasswdLockTime());
            }
            userDao.save(user);
            // 返回剩余登录次数
            User_LoginResponse resp = new User_LoginResponse();
            resp.setRemainTimes(loginCountMax - errLoginCount);
            throw new ApiException(ErrorCode.accountOrPasswordError, resp);
        } else {
            // 密码匹配，登陆成功
            // 恢复登录次数为0
            user.setErrLoginCount(0);
            userDao.save(user);
            logBuilder.operateDescription(user.getName());
        }

        boolean isPasswdForceChange = false;
        // 是否强制定期修改密码
        if (config.getLocalAuthPasswdForceChange() == CommonConsts.Bool.yes.getInt()) {
            // 获取定时修改密码周期
            Long localAuthPasswdPeriod = config.getLocalAuthPasswdPeriod();
            // 当前时间-用户上次修改密码时间>=定时修改密码周期
            if (currentTime - user.getLastPasswdChangeTime() >= localAuthPasswdPeriod) {
                isPasswdForceChange = true;
            }
        }
        // 判断用户是否改过初始密码
        if (user.getPasswdState() == UserConsts.PasswdState.init.getCode()) {
            // 是否强制修改初始密码
            if (config.getLocalAuthInitPasswdForceChange() == CommonConsts.Bool.yes.getInt()) {
                isPasswdForceChange = true;
            }
        }

        // 用户首次登录时间
        if (user.getFirstLoginTime() == 0) {
            user.setFirstLoginTime(currentTime);
        }
        user.setLastLoginTime(currentTime);
        user.setLastLoginIp(clientIp);
        user.setClient(client);
        userDao.save(user);

        if (client.getUserByFkFirstLoginUid() == null) {
            client.setUserByFkFirstLoginUid(user);
            client.setFirstLoginTime(currentTime);
        }
        client.setUserByFkLastLoginUid(user);
        clientDao.save(client);

        // 生成Token数据
        String tokenValue = Md5Utils.md5Hex(user.getPkUid() + "-" + user.getAccount() + "-" + client.getPkCid() + "-"
                + user_LoginRequest.getMac() + "-" + currentTime + "-" + IdUtils.randomId());
        // 创建Token实例
        Token token = new Token();
        createToken(client, user, tokenValue, clientIp);
        // 返回该用户角色类型
        Set<Integer> roleTypes = roleDao.findRoleByUser(user.getPkUid());
        Set<Integer> roleTypes2 = roleDao.findRoleByUserAgent(user.getPkUid());
        roleTypes.addAll(roleTypes2);
        StringBuffer roleType = new StringBuffer();
        // 该用户是否有角色,默认0
        if (roleTypes != null && roleTypes.size() > 0) {
            roleTypes.add(0);
            for (Integer role : roleTypes) {
                roleType.append(role);
                roleType.append("|");
            }
            // 去除最后一位 |
            roleType.deleteCharAt(roleType.length() - 1);
        } else {
            roleTypes.add(0);
        }

        // 设置登录成功后响应数据
        User_LoginResponse resp = new User_LoginResponse();
        resp.setToken(tokenValue);
        resp.setUid(user.getPkUid());
        resp.setName(user.getName());
        resp.setLevel(user.getUserLevel());
        resp.setState(user.getUserState());
        resp.setPasswdState(user.getPasswdState());
        resp.setPasswdForceChange(isPasswdForceChange ? 1 : 0);
        resp.setCid(client.getPkCid());
        resp.setRoleType(roleType.toString());
        return resp;

    }

    /**
     * 为登录用户创建一个Token值
     * 
     * @param token
     * @param client
     * @param tokenValue
     * @param userEntity
     */
    @SuppressWarnings("deprecation")
    private Token createToken(Client client, User user, String tokenValue, String ip) {
        Long currentTime = System.currentTimeMillis();
        Token token = new Token();
        token.setUser(user);
        token.setClient(client);
        token.setToken(tokenValue);
        token.setCreateTime(currentTime);
        token.setHeartbeatTime(currentTime);
        token.setIp(ip);
        token.setLoginFrom(TokenConsts.LoginFrom.client.getCode());
        tokenDao.save(token);
        return token;
    }

}
