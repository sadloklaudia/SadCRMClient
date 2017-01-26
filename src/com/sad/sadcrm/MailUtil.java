package com.sad.sadcrm;

import com.sad.sadcrm.model.ClientConstants;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {
    private final static String username = "sadcrm@gmail.com";
    private final static String password = "aplikacja";

    public static String sendMail(String recipients, String subject, String content) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("sadcrm@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));

            message.setSubject(subject);
            message.setText(content);

            Transport.send(message);

            return ClientConstants.OK;
        } catch (MessagingException exception) {
            throw new RuntimeException(exception);
        }
    }
}
