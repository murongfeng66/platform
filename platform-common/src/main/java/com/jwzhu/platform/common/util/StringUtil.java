package com.jwzhu.platform.common.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;

import org.springframework.util.StringUtils;

public class StringUtil {

    /**
     * 生成编码
     * yyMMddHHmmss+5位随机数
     * @param prefix 前缀
     */
    public static String createCode(String prefix) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(prefix);
        LocalDateTime localDateTime = LocalDateTime.now();
        stringBuilder.append(String.format("%02d", localDateTime.getYear() % 100));
        stringBuilder.append(String.format("%02d", localDateTime.getMonthValue()));
        stringBuilder.append(String.format("%02d", localDateTime.getDayOfMonth()));
        stringBuilder.append(String.format("%02d", localDateTime.getHour()));
        stringBuilder.append(String.format("%02d", localDateTime.getMinute()));
        stringBuilder.append(String.format("%02d", localDateTime.getSecond()));

        SecureRandom random = new SecureRandom();
        int suffix = random.nextInt(9999) + 10000;
        stringBuilder.append(suffix);

        return stringBuilder.toString();
    }

    public static String filePathConcat(String path1, String path2) {
        String path;
        if (StringUtils.isEmpty(path1)) {
            path = path2;
        } else if (StringUtils.isEmpty(path2)) {
            path = path1;
        } else {
            path1 = path1.endsWith("/") ? path1 : path1 + "/";
            path2 = path2.startsWith("/") ? path2.substring(1) : path2;
            path = path1 + path2;
        }
        return path.endsWith("/") ? path : path + "/";
    }

}
