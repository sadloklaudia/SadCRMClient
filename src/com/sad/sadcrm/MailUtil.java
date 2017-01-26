/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sad.sadcrm;

import com.sad.sadcrm.model.ClientCnst;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author dstachyra
 */
public class MailUtil {
    /*
     Wysłanie maila
     */

    public static String sendMail(String recipients, String subject, String content) {
        final String username = "sadcrm@gmail.com";
        final String password = "aplikacja";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
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

            return ClientCnst.OK;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
