package cn.bestsec.dcms.platform.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.service.CleanService;
import cn.bestsec.dcms.platform.utils.SystemProperties;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月22日 下午5:14:07
 */
@Service
public class CleanServiceImpl implements CleanService {
    @Autowired
    private TokenDao tokendao;

    @Override
    @Transactional
    public void clean() {
        long tokenValidTime = SystemProperties.getInstance().getTokenValidTime();
        List<Token> tokens = tokendao.findInvalidToken(tokenValidTime);
        tokendao.delete(tokens);
        
        // TODO 清理cache文件
    }

}
