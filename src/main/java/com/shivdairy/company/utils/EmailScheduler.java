package com.shivdairy.company.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class EmailScheduler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void start() {
        Runnable emailTask = () -> {
            String to = "recipient-email@gmail.com";
            String subject = "Scheduled Email";
            String htmlBody = "<h1>Scheduled Email</h1><p>This is an automated email sent every 10 days.</p>";

            EmailUtil.sendEmail(to, subject, htmlBody);
        };

        scheduler.scheduleAtFixedRate(emailTask, 0, 10, TimeUnit.DAYS);
        log.info("Email scheduler started, will send email every 10 days.");
    }

    public void stop() {
        scheduler.shutdown();
        log.info("Email scheduler stopped.");
    }
}
