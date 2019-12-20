package com.longi.controls.utils;

import com.longi.controls.common.MyAuthenticator;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * @version 1.0
 * @CalssName MsgUtil
 * @Author sunke5
 * @Date 2019-8-8 13:52
 */
public class MsgUtil {
    // 发送邮件的服务器
    private static final String MAILSERVERHOST = "mail.longigroup.com";
    //（服务器设置的时候默认）端口号：465，有ssl验证；端口号：25，无ssl验证
    private static final String MAILSERVERPORT = "25";
    // 登陆邮件发送服务器的用户名
    private static final String USERNAME = "bigdata@longigroup.com";
    // 登陆邮件发送服务器的密码
    private static final String PASSWORD = "Longi@123";
    // 邮件发送者的地址
    private static final String FROMADDRESS = "bigdata@longigroup.com";
    // 邮件接收者的地址
    private String[] toAddress;
    // 是否需要身份验证
    private static final boolean VALIDATE = true;
    // 邮件主题
    private String subject;
    // 邮件的文本内容
    private String content;

    public String[] getToAddress() {
        return toAddress;
    }

    public void setToAddress(String[] toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    /**
     * 读取Java配置文件中的某些属性
     */
    public Properties getProperties() {
        // 创建Properties对象
        Properties p = new Properties();
        // 添加smtp服务器属性
        p.put("mail.smtp.host", MAILSERVERHOST);
        p.put("mail.smtp.port", MAILSERVERPORT);
        // 需要验证
        p.put("mail.smtp.auth", VALIDATE ? "true" : "false");
        // 用户名和密码
        p.put("mail.smtp.userName", USERNAME);
        p.put("mail.smtp.password", PASSWORD);

        return p;
    }

    /**
     * 以HTML格式发送邮件 ——图形，超链接等
     *
     * @param msgUtil 待发送的邮件信息
     */
    public boolean sendHtmlMail(MsgUtil msgUtil) {
        // 判断是否需要身份认证
        MyAuthenticator authenticator = null;
        Properties prop = msgUtil.getProperties();
        // 如果需要身份认证，则创建一个密码验证器
        if (VALIDATE) {
            authenticator = new MyAuthenticator(MsgUtil.USERNAME, MsgUtil.PASSWORD);
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(prop, authenticator);
        try {
            // 根据session创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address fromaddress = new InternetAddress(FROMADDRESS);
            // 设置邮件消息的发送者
            mailMessage.setFrom(fromaddress);
            // 创建邮件的接收者地址，并设置到邮件消息中
            String[] mailToAddress = msgUtil.getToAddress();
            int len = mailToAddress.length;
            Address toaddress[] = new InternetAddress[len];
            for (int i = 0; i < len; i++) {
                toaddress[i] = new InternetAddress(mailToAddress[i]);
            }
            // Address toaddress = new InternetAddress(msgUtil.toAddress);
            // Message.RecipientType.TO :接收者的类型为TO(主接收人)、CC（抄送人）、BCC（秘密抄送人）
            // setRecipient:设置邮件的接收者；addRecipient：添加邮件的接收者
            mailMessage.setRecipients(Message.RecipientType.TO, toaddress);
            // 设置邮件消息的主题
            mailMessage.setSubject(msgUtil.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容
            html.setContent(msgUtil.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
