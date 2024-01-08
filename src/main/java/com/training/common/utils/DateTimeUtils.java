package com.training.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static final String DD_MM_YY_TT = "dd/MM/yyyy HH:mm:ss";

    private DateTimeUtils() {
    }

    public static String formatterDateTime(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DD_MM_YY_TT);
        return localDateTime.format(formatter);
    }

}
