package com.wanzhu.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.wanzhu.base.CommonConstant;

public class SmtpClient {
	public static void sendMail(String fromAccount, String user, String name, String password, String toAccount, String smtpHost,
			String subject, String content, boolean html) throws MessagingException, UnsupportedEncodingException {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.auth", "true");
		Session session = Session.getDefaultInstance(props, new SimpleAuthenticator(user, password));
		session.setDebug(false);

		MimeMessage msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(fromAccount, name));

		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toAccount));
		msg.setSubject(subject);

		MimeBodyPart mbp = new MimeBodyPart();
		if (html) {
			mbp.setContent(content, "text/html; charset=utf-8");
		} else {
			mbp.setText(content);
		}
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(mbp);
		msg.setContent(mp);

		msg.setSentDate(new Date());

		Transport.send(msg);
	}

	public static void main(String[] args) throws MessagingException, UnsupportedEncodingException {
		sendMail(CommonConstant.recommendation_mail_from_account, CommonConstant.recommendation_mail_from_account,
				CommonConstant.recommendation_mail_from_name, CommonConstant.recommendation_mail_from_account_password,
				"t-c-b@163.com", CommonConstant.smtp_server, "测试邮件", "<html><body><font color=\"red\">测试</font></body></html>",
				true);
	}
}
