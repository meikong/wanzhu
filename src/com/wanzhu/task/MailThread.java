package com.wanzhu.task;

import java.util.List;

import org.apache.commons.logging.LogFactory;

import com.wanzhu.base.CommonConstant;
import com.wanzhu.utils.SmtpClient;

public class MailThread extends Thread {
	private List<String> emailList = null;
	private String subject = null;
	private String content = null;

	public void setEmailList(List<String> emailList) {
		this.emailList = emailList;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void run() {
		LogFactory.getLog(MailTask.class).info("------开始批量发送邮件------");
		for (int i = 0; i < emailList.size(); i++) {
			if (emailList.get(i) != null && emailList.get(i).length() > 0) {
				try {
					SmtpClient.sendMail(CommonConstant.recommendation_mail_from_account,
							CommonConstant.recommendation_mail_from_account, CommonConstant.recommendation_mail_from_name,
							CommonConstant.recommendation_mail_from_account_password, emailList.get(i),
							CommonConstant.smtp_server, subject, content, true);
					LogFactory.getLog(MailTask.class).info("------发送邮件至邮箱[" + emailList.get(i) + "]成功------");
				} catch (Exception ex) {
					LogFactory.getLog(MailTask.class).error("------发送邮件至邮箱[" + emailList.get(i) + "]失败------", ex);
					continue;
				}
			}
		}
		emailList = null;
		subject = null;
		content = null;
		LogFactory.getLog(MailTask.class).info("------结束批量发送邮件------");
	}
}
