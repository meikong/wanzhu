package com.wanzhu.utils;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SimpleAuthenticator extends Authenticator {
	String user = null;
	String password = null;

	public SimpleAuthenticator(String user, String password) {
		this.user = user;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.user, this.password);
	}
}