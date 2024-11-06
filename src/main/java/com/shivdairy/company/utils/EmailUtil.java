package com.shivdairy.company.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

@Slf4j
public class EmailUtil {
    public void sendEmailMessage(List<String> primaryRecipientList, List<String> copiedRecipientList,
                                 List<String> hiddenRecipientList, String fromAddress, String htmlBody,
                                 String textBody, String subject, File attachment, Boolean isBatch){
        List<File> attachmentList = new ArrayList<>();
        if (attachment != null) attachmentList.add(attachment);
        this.sendEmailMessage(primaryRecipientList, copiedRecipientList, hiddenRecipientList, fromAddress, htmlBody,
                textBody, subject, attachmentList, isBatch);
    }


    public void sendEmailMessage(List<String> primaryRecipientList, List<String> copiedRecipientList,
                                        List<String> hiddenRecipientList, String fromAddress, String htmlBody,
                                        String textBody, String subject, List<File> attachment, Boolean isBatch) {
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
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(primaryRecipient));
            message.setSubject(subject);
            message.setContent(htmlBody, "text/html");

            Transport.send(message);
            log.info("Email sent successfully.");
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }
}

