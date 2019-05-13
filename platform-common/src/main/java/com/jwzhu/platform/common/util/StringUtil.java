package com.jwzhu.platform.common.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;

public class StringUtil {

    public static String create(String prefix) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(prefix);
        LocalDateTime localDateTime = LocalDateTime.now();
        stringBuilder.append(localDateTime.getYear() % 100);
        stringBuilder.append(localDateTime.getMonthValue());
        stringBuilder.append(localDateTime.getDayOfMonth());
        stringBuilder.append(localDateTime.getHour());
        stringBuilder.append(localDateTime.getMinute());
        stringBuilder.append(localDateTime.getSecond());

        SecureRandom random = new SecureRandom();
        int suffix = random.nextInt(9999) + 10000;
        stringBuilder.append(suffix);

        return stringBuilder.toString();
    }

}
