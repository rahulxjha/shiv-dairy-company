package com.shivdairy.company.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

@Slf4j
public class EmailUtil {
    public static void sendEmail(String to, String subject, String htmlBody) {
        String from = DairyUtil.getProperty("email.from");
        String password = DairyUtil.getProperty("email.password");

        Properties props = new Properties();
        props.put("mail.smtp.host", DairyUtil.getProperty("email.smtp.host"));
        props.put("mail.smtp.port", DairyUtil.getProperty("email.smtp.port"));
        props.put("mail.smtp.auth", DairyUtil.getProperty("email.smtp.auth"));
        props.put("mail.smtp.starttls.enable", DairyUtil.getProperty("email.smtp.starttls.enable"));

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(htmlBody, "text/html");

            Transport.send(message);
            log.info("Email sent successfully.");
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }
}

