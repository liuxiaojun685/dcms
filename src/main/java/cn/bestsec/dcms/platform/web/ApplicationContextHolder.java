package cn.bestsec.dcms.platform.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationContextHolder {
    private static ApplicationContext applicationContext;

    public static void init() {
        applicationContext = new ClassPathXmlApplicationContext("spring.xml");
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
