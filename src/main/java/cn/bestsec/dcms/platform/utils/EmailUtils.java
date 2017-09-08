package cn.bestsec.dcms.platform.utils;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2017年1月12日 上午10:26:34
 */
public class EmailUtils {

    public static class Sender {
        private String hostName;
        private String smtpPort;
        private String user;
        private String passwd;
        private boolean isSsl;

        public Sender(String hostName, String smtpPort, String user, String passwd) {
            this.hostName = hostName;
            this.smtpPort = smtpPort;
            this.user = user;
            this.passwd = passwd;
        }

        public Sender(String hostName, String smtpPort, String user, String passwd, boolean isSsl) {
            this.hostName = hostName;
            this.smtpPort = smtpPort;
            this.user = user;
            this.passwd = passwd;
            this.isSsl = isSsl;
        }
    }

    public static boolean sendSimpleEmail(Sender sender, String subject, String msg, String to) {
        SimpleEmail email = new SimpleEmail();
        email.setCharset("UTF-8");
        email.setHostName(sender.hostName);
        email.setAuthentication(sender.user, sender.passwd);
        if (sender.isSsl) {
            email.setSSLOnConnect(true);
            if (sender.smtpPort != null) {
                email.setSslSmtpPort(sender.smtpPort);
            }
        } else {
            if (sender.smtpPort != null) {
                email.setSmtpPort(Integer.valueOf(sender.smtpPort));
            }
        }
        try {
            email.setFrom(sender.user);
            email.setSubject(subject); // 标题
            email.setMsg(msg); // 内容

            String[] addrs = to.split(";");
            if (addrs == null || addrs.length == 0) {
                return false;
            }
            for (String addr : addrs) {
                if (StringHelper.isEmail(addr)) {
                    email.addTo(addr);
                }
            }
            if (email.getToAddresses().isEmpty()) {
                return false;
            }
            email.send();
            return true;
        } catch (EmailException e) {
            e.printStackTrace();
        }
        return false;
    }
}
