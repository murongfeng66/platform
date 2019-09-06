package com.jwzhu.platform.common.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;


public class StringUtil {

    /**
     * 判断字符串是否为空，去除前后空白符
     */
    public static boolean isEmpty(String text){
        return text == null || "".equals(text.trim());
    }

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

    /**
     * 文件路径拼接
     * @param path1 前路径
     * @param path2 后路径
     */
    public static String filePathConcat(String path1, String path2) {
        String path;
        if (isEmpty(path1)) {
            path = path2;
        } else if (isEmpty(path2)) {
            path = path1;
        } else {
            path1 = path1.endsWith("/") ? path1 : path1 + "/";
            path2 = path2.startsWith("/") ? path2.substring(1) : path2;
            path = path1 + path2;
        }
        return path.endsWith("/") ? path : path + "/";
    }

    /**
     * 数组转字符串
     * @param array 数组
     * @param separator 分隔符，默认为','
     */
    public static String join(String[] array, Character separator){
        if(array == null || array.length == 0){
            return null;
        }
        separator = separator == null ? ',' : separator;
        StringBuilder sb = new StringBuilder(array[0]);
        for (int i = 1; i < array.length; i++) {
            if(isEmpty(array[i])){
                continue;
            }
            sb.append(separator);
            sb.append(array[i]);
        }
        return sb.toString();
    }

    /**
     * 数组转字符串，以','分割
     */
    public static String join(String[] array){
        return join(array, ',');
    }

    /**
     * 添加参数到URL上<br/>
     * 如已存在，则覆盖
     * @param url URL
     * @param name 参数名
     * @param value 参数值
     */
    public static String addParamToUrl(String url, String name, String value){
        if(isEmpty(name) || isEmpty(value)){
            return url;
        }

        String paramName = name + "=";
        String param = paramName + value;
        if(isEmpty(url)){
            return param;
        }

        String[] urls = url.split("\\?");
        if(urls.length == 1){
            return urls[0] + "?" + param;
        }

        String[] params = urls[1].split("&");
        if(params.length == 0){
            return urls[0] + "?" + param;
        }else{
            boolean add = true;
            for (int i = 0; i < params.length; i++) {
                if(params[i].startsWith(paramName)){
                    params[i] = param;
                    add = false;
                }
            }
            urls[1] = join(params, '&');
            if(add){
                urls[1] = urls[1] + "&" + param;
            }
        }

        return urls[0] + "?" + urls[1];
    }

}
