package lxspider;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class SendEmail {
	
    public static void Send(String emailContent, int sendType) throws MessagingException, GeneralSecurityException {
    	
        //创建一个配置文件并保存
        Properties properties = new Properties();

        properties.setProperty("mail.host", "smtp.qq.com");

        properties.setProperty("mail.transport.protocol", "smtp");

        properties.setProperty("mail.smtp.auth", "true");
        
        InternetAddress[] emailAddress = {new InternetAddress("119092298@qq.com")/*, new InternetAddress("1804864916@qq.com")*/};
        InternetAddress[] registerEmail = {new InternetAddress("794051393@qq.com")/*, new InternetAddress("1804864916@qq.com")*/};

        
        //QQ存在一个特性设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        //创建一个session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("794051393@qq.com","hrgueitaqqeobebb");
            }
        });

        //开启debug模式
        session.setDebug(false);

        //获取连接对象
        Transport transport = session.getTransport();

        //连接服务器
        transport.connect("smtp.qq.com","794051393@qq.com", "hrgueitaqqeobebb");

        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);

        //邮件发送人
        mimeMessage.setFrom(new InternetAddress("794051393@qq.com"));

        if(0 == sendType)
        {  	
        	//邮件接收人
            mimeMessage.setRecipients(Message.RecipientType.TO, emailAddress);
            //邮件标题
        	mimeMessage.setSubject("招募贴吧吧主名单--发邮件功能测试");	
        }
        else if(1 == sendType)
        {
        	mimeMessage.setRecipients(Message.RecipientType.TO, registerEmail);
        	mimeMessage.setSubject("用户注册");
        }
        
        //邮件内容
        mimeMessage.setContent(emailContent, "text/html;charset=UTF-8");

        //发送邮件
        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());

        //关闭连接
        transport.close();
    }
}