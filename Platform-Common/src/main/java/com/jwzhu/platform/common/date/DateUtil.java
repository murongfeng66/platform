package com.jwzhu.platform.common.date;

import java.sql.Timestamp;

public class DateUtil {

    public static Timestamp now(){
        return new Timestamp(System.currentTimeMillis());
    }

}
