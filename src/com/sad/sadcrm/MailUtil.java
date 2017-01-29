package com.sad.sadcrm;

import com.sad.sadcrm.model.ClientConstants;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {
    private final static String USERNAME = "sadcrm@gmail.com";
    private final static String PASSWORD = "aplikacja";

    public static String sendMail(String recipients, String subject, String content) {
        Session session = Session.getInstance(getProperties(), getAuthenticator());

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sadcrm@gmail.com"));
            message.setRecipients(RecipientType.TO, InternetAddress.parse(recipients));
            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

            return ClientConstants.OK;
        } catch (MessagingException exception) {
            throw new RuntimeException(exception);
        }
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        return properties;
    }

    private static Authenticator getAuthenticator() {
        return new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        };
    }
}
