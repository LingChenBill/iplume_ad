package com.iplume.ad.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 工具类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/10
 */
@Slf4j
public class CommonUtils {

    /**
     * 根据key, 获取map中的对应key的对象,若map中不存在,则返回传入的factory的新的对象(空对象).
     *
     * @param key
     * @param map
     * @param factory
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> V getOrCreate(K key, Map<K, V> map, Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }

    /**
     * 字符串连接处理("-"连接).
     *
     * @param args 字符串组.
     * @return 处理后的连接字符串.
     */
    public static String stringConcat(String... args) {
        StringBuilder result = new StringBuilder();
        for (String arg : args) {
            result.append(arg);
            result.append("-");
        }
        // 删除最后一个多余的"-".
        result.deleteCharAt(result.length() - 1);

        return result.toString();
    }

    /**
     * Binlog中的日期形式格式化成Date.
     * <p>
     * sql插入时日期数据: '2021-01-01 00:00:00'.
     * Binlog打印时的日期数据: Fri Jan 01 08:00:00 CST 2021.
     *
     * @param dateString 日期字符串.
     * @return 日期/
     */
    public static Date parseDateString(String dateString) {

        try {

            DateFormat dateFormat = new SimpleDateFormat(
                    "EEE MMM dd HH:mm:ss zzz yyyy",
                    Locale.US
            );

            return DateUtils.addHours(
                    dateFormat.parse(dateString),
                    -8);
        } catch (ParseException ex) {
            log.error("parseStringDate error: {}", dateString);
            return null;
        }
    }

}
