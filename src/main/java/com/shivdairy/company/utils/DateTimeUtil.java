package com.shivdairy.company.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface DateTimeUtil {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    LocalDateTime dateTime = LocalDateTime.parse(LocalDateTime.now().format(dateTimeFormatter));
    LocalDate date = LocalDate.now();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
