package cn.bestsec.dcms.platform.service.impl;

import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.bestsec.dcms.platform.consts.UserConsts;
import cn.bestsec.dcms.platform.dao.TokenDao;
import cn.bestsec.dcms.platform.dao.UserDao;
import cn.bestsec.dcms.platform.dao.UserMessageDao;
import cn.bestsec.dcms.platform.dao.UserMessageHistoryDao;
import cn.bestsec.dcms.platform.entity.Token;
import cn.bestsec.dcms.platform.entity.User;
import cn.bestsec.dcms.platform.entity.UserMessage;
import cn.bestsec.dcms.platform.entity.UserMessageHistory;
import cn.bestsec.dcms.platform.mqtt.MessageEntity;
import cn.bestsec.dcms.platform.mqtt.MqttMessageHandler;
import cn.bestsec.dcms.platform.service.ApiSecurityService;
import cn.bestsec.dcms.platform.service.MqttMessageService;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年3月8日 下午2:34:59
 */
@Transactional
@Service
public class MqttMessageServiceImpl implements MqttMessageService {
    static Logger logger = LoggerFactory.getLogger(MqttMessageServiceImpl.class);

    @Autowired
    private ApiSecurityService apiSecurityService;
    @Autowired
    private TokenDao tokenDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserMessageDao userMessageDao;
    @Autowired
    private UserMessageHistoryDao userMessageHistoryDao;

    @Override
    public void receive(MqttMessage message) {
        try {
            logger.debug("MQTT收到消息: " + message.toString());
            MessageEntity entity = JSON.parseObject(message.toString(), MessageEntity.class);
            if (MqttMessageHandler.TYPE_HEARTBEAT_USER.equals(entity.getType())) {
                String token = entity.getMsg();
                apiSecurityService.heartbeat(token);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(String uid, String type, String msg, String url) {
        MessageEntity entity = new MessageEntity(type, 0L, System.currentTimeMillis(), msg, url);
        User user = userDao.findByPkUid(uid);
        if (user == null) {
            logger.warn("用户[" + uid + "]的消息通知发送失败，用户不存在，消息内容：" + JSON.toJSONString(entity));
            return;
        }
        // 用户在线
        if (UserConsts.userOnline(user) == 1) {
            List<Token> tokens = user.getTokens();
            for (Token token : tokens) {
                entity.setStime(System.currentTimeMillis());
                String message = JSON.toJSONString(entity);
                if (MqttMessageHandler.publish(token.getToken(), message)) {
                    logger.info("发送消息通知 to[" + token.getToken() + "]  " + message);
                }
            }
            userMessageHistoryDao.save(
                    new UserMessageHistory(uid, entity.getMsg(), entity.getType(), entity.getUrl(), entity.getCtime()));
        } else {
            userMessageDao.save(new UserMessage(uid, JSON.toJSONString(entity)));
        }
    }

    @Override
    public void flush(String to) {
        Token token = tokenDao.findByToken(to);
        if (token == null || token.getUser() == null) {
            return;
        }
        User user = token.getUser();
        if (UserConsts.userOnline(user) == 1) {
            List<UserMessage> msgs = userMessageDao.findUerMessage(user.getPkUid());
            for (UserMessage message : msgs) {
                String msg = message.getMessage();
                try {
                    MessageEntity entity = JSON.parseObject(message.getMessage(), MessageEntity.class);
                    entity.setStime(System.currentTimeMillis());
                    msg = JSON.toJSONString(entity);
                    if (MqttMessageHandler.publish(token.getToken(), msg)) {
                        logger.info("发送消息通知 to[" + token.getToken() + "]  " + msg);
                        userMessageHistoryDao.save(new UserMessageHistory(user.getPkUid(), entity.getMsg(),
                                entity.getType(), entity.getUrl(), entity.getCtime()));
                        userMessageDao.delete(message);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void push(String uid, String type, String msg, String url) {
        MessageEntity entity = new MessageEntity(type, 0L, System.currentTimeMillis(), msg, url);
        User user = userDao.findByPkUid(uid);
        if (user == null) {
            logger.warn("用户[" + uid + "]的消息通知发送失败，用户不存在，消息内容：" + JSON.toJSONString(entity));
            return;
        }
        // 用户在线
        if (UserConsts.userOnline(user) == 1) {
            List<Token> tokens = user.getTokens();
            for (Token token : tokens) {
                entity.setStime(System.currentTimeMillis());
                String message = JSON.toJSONString(entity);
                if (MqttMessageHandler.publish(token.getToken(), message)) {
                    logger.info("发送消息通知 to[" + token.getToken() + "]  " + message);
                }
            }
        }
    }
}
