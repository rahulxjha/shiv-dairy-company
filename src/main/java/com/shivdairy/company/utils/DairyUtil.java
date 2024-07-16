package com.shivdairy.company.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class DairyUtil {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DairyUtil.class.getClassLoader().getResourceAsStream("application-dev.properties")) {
            if (input == null) {
                log.debug("Sorry, unable to find application-dev.properties");
            }
            properties.load(input);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
