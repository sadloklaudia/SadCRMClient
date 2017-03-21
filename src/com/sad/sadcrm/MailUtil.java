package com.sad.sadcrm;

import static com.sad.sadcrm.Parameters.getCredentials;

public class MailUtil {
    public static void sendMail(String recipients, String subject, String content) {
        try {
            HttpJson.post("/mail", getCredentials()
                    .add("recipients", recipients)
                    .add("subject", subject)
                    .add("message", content));
        } catch (HttpJsonException exception) {
            throw new SendMailException(exception.getMessage());
        }
    }
}
