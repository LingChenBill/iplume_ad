package com.iplume.ad.utils;


import com.iplume.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Utils实体类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/21
 */
public class CommonUtils {

    /**
     * 时间格式串.
     */
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    /**
     * 获取md5加密数据.
     *
     * @param value
     * @return
     */
    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    /**
     * 时间格式化(string -> Date).
     *
     * @param dateStr
     * @return
     * @throws AdException
     */
    public static Date parseStringDate(String dateStr) throws AdException {
        try {
            return DateUtils.parseDate(dateStr, parsePatterns);
        } catch (ParseException e) {
            throw new AdException(e.getMessage());
        }
    }

}
