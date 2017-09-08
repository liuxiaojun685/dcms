package utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import cn.bestsec.dcms.platform.utils.EmailUtils;
import cn.bestsec.dcms.platform.utils.EmailUtils.Sender;

/**
 * @author 何勇 email:heyong@bestsec.cn
 * @time：2017年1月11日 下午2:47:12
 */
public class EmailUtilsTest {
	public static void main(String[] args) {
	    EmailUtils.sendSimpleEmail(new Sender("smtp.163.com", null, "besthyhytest@163.com", "a1234567890", true), "MYQQMTEST", "MYQQMTEST", "bada@bestsec.cn;");
	}

	public static void send() throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName("smtp.163.com");
		email.setSmtpPort(25);
		email.setAuthenticator(new DefaultAuthenticator("besthyhytest@163.com", "a1234567890"));
		// email.setSSLOnConnect(true);
		email.setFrom("besthyhytest@163.com");
		email.setSubject("TestMail");
		email.setMsg("This is a test mail ... :-)");
		email.addTo("besthyhy@163.com");
		email.send();
	}

}
