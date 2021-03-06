package com.sad.sadcrm;

import static com.sad.sadcrm.Parameters.getCredentials;

public class MailSender {
    public static void sendMail(String recipients, String subject, String message) {
        try {
            HttpJson.post("/mail", getCredentials()
                    .add("recipients", recipients)
                    .add("subject", subject)
                    .add("message", message));
        } catch (HttpJsonException exception) {
            throw new SendMailException(exception.getMessage());
        }
    }
}
