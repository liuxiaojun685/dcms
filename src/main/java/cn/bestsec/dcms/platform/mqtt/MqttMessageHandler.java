package cn.bestsec.dcms.platform.mqtt;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bestsec.dcms.platform.service.MqttMessageService;
import cn.bestsec.dcms.platform.utils.SystemProperties;
import cn.bestsec.dcms.platform.web.ApplicationContextHolder;

/**
 * @author bada email:bada@bestsec.cn
 * @time 2017年2月24日 上午9:22:20
 */
public class MqttMessageHandler {
    static Logger logger = LoggerFactory.getLogger(MqttMessageHandler.class);

    public static String TYPE_HEARTBEAT_USER = "hbu";
    public static String TYPE_FILE = "file";
    public static String TYPE_PATCH = "patch";
    public static String TYPE_WORKFLOW = "wf";
    public static String TYPE_APPROVAL = "apv";
    public static String TYPE_PUSHPOLICY = "plc";

    public static String MSG_WORKFLOW_PASS = "您有一个流程已通过审批，点击查看详情";
    public static String MSG_WORKFLOW_BACK = "您有一个流程被退回，点击查看详情";
    public static String MSG_APPROVAL = "您有一个流程待审批，点击查看详情";
    public static String MSG_PATCH_UPDATE = "您有一个新的补丁包，请点击升级";
    public static String MSG_FILE_CLASSIFIED = "您有一个文件已正式定密，点击查看详情";
    public static String MSG_FILE_ISSUED = "您有一个文件已签发，点击查看详情";
    public static String MSG_FILE_DECLASSIFIED = "您有一个文件已解密，点击查看详情";
    public static String MSG_FILE_CLASSIFIEDCHANGE = "您有一个文件发生密级变更，点击查看详情";

    public static String MSG_PUSH_ALL = "0"; // 全部
    public static String MSG_PUSH_TRUSTAPP = "1"; // 可信应用策略
    public static String MSG_PUSH_ACCESSFILE = "2"; // 可访问文件策略
    public static String MSG_PUSH_MARKKEY = "3"; // 标志密钥
    public static String MSG_PUSH_EXTENSION = "4"; // 可标密的文件拓展名策略
    public static String MSG_PUSH_LEVELACCESS = "5"; // 文件密级访问策略，用户可访问的文件密级列表
    public static String MSG_PUSH_UNINSTALLPWD = "6"; // 卸载密码

    public static String URL_WORKFLOW = "/dcms/dcms_client_web/index-change.html";
    public static String URL_FILE = "/dcms/dcms_client_web/myselfInfo-change.html";

    private static MqttClient mqttClient;
    private static String topic;
    private static String broker;
    private static String clientId;
    private static int qos;

    public static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void subscribeInit() {
        SystemProperties sp = SystemProperties.getInstance();
        topic = sp.getMqttTopic();
        broker = sp.getMqttBroker();
        clientId = sp.getMqttId();

        try {
            mqttClient = new MqttClient(broker, clientId, new MemoryPersistence());
            connect();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void connect() {
        try {
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(20);
            logger.info("MQTT(" + broker + ")连接中...");
            mqttClient.connect(connOpts);
            mqttClient.subscribe(topic);
            mqttClient.setCallback(callback);
            logger.info("MQTT(" + broker + ")连接成功");
        } catch (Throwable e) {
            logger.error("MQTT(" + broker + ")连接失败");
        }
    }

    public static void disconnect() {
        try {
            if (mqttClient != null) {
                mqttClient.disconnect();
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public static void tryPublish(String uid, String type, String msg, String url) {
        executor.execute(new Runnable() {

            @Override
            public void run() {
                ApplicationContextHolder.getApplicationContext().getBean(MqttMessageService.class).send(uid, type, msg,
                        url);
            }
        });
    }

    public static void push(String uid, String type, String msg, String url) {
        executor.execute(new Runnable() {

            @Override
            public void run() {
                ApplicationContextHolder.getApplicationContext().getBean(MqttMessageService.class).push(uid, type, msg,
                        url);
            }
        });
    }

    public synchronized static boolean publish(String to, String content) {
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(qos);

        try {
            mqttClient.publish(topic + "|" + to, message);
            return true;
        } catch (MqttPersistenceException e1) {
            e1.printStackTrace();
        } catch (MqttException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    private static MqttCallback callback = new MqttCallback() {

        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ApplicationContextHolder.getApplicationContext().getBean(MqttMessageService.class).receive(message);
                }
            }).start();
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken token) {
        }

        @Override
        public void connectionLost(Throwable cause) {
            logger.warn("MQTT(" + broker + ")连接断开，准备重连");
            connect();
        }
    };

    public class MessageBuilder {
        public String content;

        @Override
        public String toString() {
            return content;
        }
    }
}
