package cn.bestsec.dcms.platform.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import cn.bestsec.dcms.platform.mqtt.MqttMessageHandler;
import cn.bestsec.dcms.platform.service.ApiPreconditionService;
import cn.bestsec.dcms.platform.service.TimerScheduleService;
import cn.bestsec.dcms.platform.service.impl.LocationServiceImpl;
import cn.bestsec.dcms.platform.utils.db.DBConf;

public class _ServletContextListener implements ServletContextListener {

    private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 初始化spring容器
        ApplicationContextHolder.init();
        // 初始化请求参数检查服务
        ApplicationContextHolder.getApplicationContext().getBean(ApiPreconditionService.class)
                .initApiParser(sce.getServletContext().getRealPath("raml/api.raml"));

        getDataBaseInfo();
        // 开始自动任务
        startTask();
        mqttInit();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        MqttMessageHandler.disconnect();
    }

    private void mqttInit() {
        new Thread() {
            @Override
            public void run() {
                MqttMessageHandler.subscribeInit();
            }
        }.start();
    }

    private void startTask() {
        new Thread() {
            public void run() {
                ApplicationContextHolder.getApplicationContext().getBean(TimerScheduleService.class).scheduleTimer();
                ApplicationContextHolder.getApplicationContext().getBean(LocationServiceImpl.class)
                        .updateLocationParams(1);
            };

        }.start();
    }

    public void getDataBaseInfo() {
        Properties prop = new Properties();
        try {
            Resource resource = resourceLoader.getResource("jdbc.properties");
            InputStream in = resource.getInputStream();
            prop.load(in);
            DBConf.USER = prop.getProperty("ds.username");
            DBConf.PASS = prop.getProperty("ds.password");
            DBConf.DB_URL = prop.getProperty("ds.url");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }
}
