package com.longi.controls.common;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @version 1.0
 * @CalssName MyAuthenticator
 * @Author sunke5
 * @Date 2019-8-8 13:56
 */
public class MyAuthenticator extends Authenticator {
    String userName = null;
    String password = null;

    //	public MyAuthenticator(){
//	}
    public MyAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    // 传用户名和密码到邮件服务里
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}
