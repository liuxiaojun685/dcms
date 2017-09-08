
package cn.bestsec.dcms.platform.impl.client;

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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import cn.bestsec.dcms.platform.api.Client_QueryListApi;
import cn.bestsec.dcms.platform.api.exception.ApiException;
import cn.bestsec.dcms.platform.api.model.ClientInfo;
import cn.bestsec.dcms.platform.api.model.Client_QueryListRequest;
import cn.bestsec.dcms.platform.api.model.Client_QueryListResponse;
import cn.bestsec.dcms.platform.api.model.UserSimpleInfo;
import cn.bestsec.dcms.platform.consts.ClientConsts;
import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.ClientDao;
import cn.bestsec.dcms.platform.entity.Client;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * 查询终端列表(关键字暂时定的是ip与mac先注释掉)
 * 
 * @author 牛犀 email:niuxi@bestsec.cn
 * @time 2016年12月26日上午9:38:57
 */
@Component
public class ClientQueryList implements Client_QueryListApi {
    @Autowired
    private ClientDao clientDao;

    @Override
    @Transactional
    public Client_QueryListResponse execute(Client_QueryListRequest client_QueryListRequest) throws ApiException {
        Specification<Client> spec = new Specification<Client>() {

            @Override
            public Predicate toPredicate(Root<Client> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();

                // 过滤掉已删除的终端
                list.add(cb.notEqual(root.get("clientState").as(Integer.class), ClientConsts.State.deleted.getCode()));
                if (client_QueryListRequest.getLevel() != null) {
                    list.add(cb.equal(root.get("clientLevel").as(Integer.class), client_QueryListRequest.getLevel()));
                }
                if (client_QueryListRequest.getOsType() != null && !client_QueryListRequest.getOsType().equals("")) {
                    list.add(cb.equal(root.get("osType").as(String.class), client_QueryListRequest.getOsType()));
                }
                if (client_QueryListRequest.getVersionType() != null) {
                    list.add(cb.equal(root.get("versionType").as(Integer.class),
                            client_QueryListRequest.getVersionType()));
                }
                if (client_QueryListRequest.getVersionName() != null
                        && !client_QueryListRequest.getVersionName().equals("")) {
                    list.add(cb.equal(root.get("versionName").as(String.class),
                            client_QueryListRequest.getVersionName()));
                }
                if (client_QueryListRequest.getVersionCode() != null) {
                    list.add(cb.equal(root.get("versionCode").as(Integer.class),
                            client_QueryListRequest.getVersionCode()));
                }
                if (client_QueryListRequest.getOnline() != null) {
                    if (client_QueryListRequest.getOnline() == 1) {
                        list.add(cb.gt(root.get("heartbeatTime").as(Long.class),
                                System.currentTimeMillis() - SystemProperties.getInstance().getUserOfflineTime()));
                    } else if (client_QueryListRequest.getOnline() == 0) {
                        list.add(cb.le(root.get("heartbeatTime").as(Long.class),
                                System.currentTimeMillis() - SystemProperties.getInstance().getUserOfflineTime()));
                    }
                }
                Predicate[] p = new Predicate[list.size()];
                Predicate predicate = cb.and(list.toArray(p));
                if (client_QueryListRequest.getLastLoginTimeStart() != null) {
                    predicate = cb.and(predicate, cb.ge(root.get("lastLoginTime").as(Long.class),
                            client_QueryListRequest.getLastLoginTimeStart()));

                }
                if (client_QueryListRequest.getLastLoginTimeEnd() != null) {
                    predicate = cb.and(predicate, cb.le(root.get("lastLoginTime").as(Long.class),
                            client_QueryListRequest.getLastLoginTimeEnd()));

                }

                if (client_QueryListRequest.getKeyword() != null && !client_QueryListRequest.getKeyword().equals("")) {
                    Predicate ip = cb.like(root.get("ip").as(String.class),
                            "%" + client_QueryListRequest.getKeyword() + "%");
                    Predicate mac = cb.like(root.get("mac").as(String.class),
                            "%" + client_QueryListRequest.getKeyword() + "%");
                    Predicate osType = cb.like(root.get("osType").as(String.class),
                            "%" + client_QueryListRequest.getKeyword() + "%");
                    Predicate versionName = cb.like(root.get("versionName").as(String.class),
                            "%" + client_QueryListRequest.getKeyword() + "%");
                    Predicate pcName = cb.like(root.get("pcName").as(String.class),
                            "%" + client_QueryListRequest.getKeyword() + "%");
                    predicate = cb.and(predicate, cb.or(ip, mac, osType, versionName, pcName));
                }

                return predicate;
            }
        };
        Pageable pageable = new PageRequest(client_QueryListRequest.getPageNumber(),
                client_QueryListRequest.getPageSize());
        Page<Client> page = clientDao.findAll(spec, pageable);

        List<Client> clients = page.getContent();
        List<ClientInfo> clientList = new ArrayList<>();
        ClientInfo clientInfo = null;
        UserSimpleInfo userSimpleInfo = null;
        UserSimpleInfo userSimpleInfo1 = null;

        for (Client client : clients) {
            clientInfo = new ClientInfo();
            clientInfo.setCid(client.getPkCid());
            clientInfo.setLevel(client.getClientLevel());
            clientInfo.setIp(client.getIp());
            clientInfo.setMac(client.getMac());
            clientInfo.setOnline(UserConsts.clientOnline(client));
            clientInfo.setOsType(client.getOsType());
            clientInfo.setVersionType(client.getVersionType());
            clientInfo.setVersionName(client.getVersionName());
            clientInfo.setVersionCode(client.getVersionCode());
            clientInfo.setPcName(client.getPcName());
            userSimpleInfo = new UserSimpleInfo();
            User firstLoginUser = client.getUserByFkFirstLoginUid();
            if (firstLoginUser != null) {
                userSimpleInfo.setAccount(firstLoginUser.getAccount());
                userSimpleInfo.setLevel(firstLoginUser.getUserLevel());
                userSimpleInfo.setName(firstLoginUser.getName());
                userSimpleInfo.setOnline(UserConsts.userOnline(firstLoginUser));
                userSimpleInfo.setUid(firstLoginUser.getPkUid());
            }
            clientInfo.setFirstLoginUser(userSimpleInfo);

            userSimpleInfo1 = new UserSimpleInfo();
            User lastLoginUser = client.getUserByFkLastLoginUid();
            if (lastLoginUser != null) {
                userSimpleInfo1.setAccount(lastLoginUser.getAccount());
                userSimpleInfo1.setLevel(lastLoginUser.getUserLevel());
                userSimpleInfo1.setName(lastLoginUser.getName());
                userSimpleInfo1.setOnline(UserConsts.userOnline(lastLoginUser));
                userSimpleInfo1.setUid(lastLoginUser.getPkUid());
            }
            clientInfo.setLastLoginUser(userSimpleInfo1);
            clientInfo.setFirstLoginTime(client.getFirstLoginTime());
            clientInfo.setLastLoginTime(client.getLastLoginTime());
            clientInfo.setDescription(client.getDescription());
            clientInfo.setState(client.getClientState());
            clientList.add(clientInfo);
        }
        Long totaoElements = page.getTotalElements();
        Client_QueryListResponse resp = new Client_QueryListResponse();
        resp.setTotalElements(new Integer(totaoElements.intValue()));
        resp.setTotalPages(page.getTotalPages());
        resp.setClientList(clientList);
        return resp;
    }

}
