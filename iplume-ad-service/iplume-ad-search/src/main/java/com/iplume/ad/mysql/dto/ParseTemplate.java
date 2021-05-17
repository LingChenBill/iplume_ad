package com.iplume.ad.mysql.dto;

import com.iplume.ad.mysql.constant.OpType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * json模版解析类.
 *
 * @author: lingchen
 * @date: 2021/5/15
 */
@Data
public class ParseTemplate {

    /**
     * 数据库名.
     */
    private String database;

    /**
     * 表名 -> 表模版Map.
     */
    private Map<String, TableTemplate> tableTemplateMap = new HashMap<>();

    /**
     * template.json文件解析成ParseTemplate.
     *
     * @param jsonTemplate 模版.
     * @return
     */
    public static ParseTemplate parse(Template jsonTemplate) {

        // ParseTemplate声明.
        ParseTemplate template = new ParseTemplate();
        // 数据库.
        template.setDatabase(jsonTemplate.getDatabase());

        for (JsonTable table : jsonTemplate.getTableList()) {
            // 表名.
            String name = table.getTableName();
            // 级别.
            Integer level = table.getLevel();

            TableTemplate tableTemplate = new TableTemplate();
            tableTemplate.setTableName(name);
            tableTemplate.setLevel(level.toString());
            // 类型 -> 表模版Map.
            template.tableTemplateMap.put(name, tableTemplate);

            // 遍历操作类型对应的列.
            Map<OpType, List<String>> opTypeFieldSetMap = tableTemplate.getOpTypeFieldSetMap();

            // 添加表操作.
            for (JsonTable.Column column : table.getInsert()) {
                getAndCreateIfNeed(
                        OpType.ADD,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }

            // 更新列操作.
            for (JsonTable.Column column : table.getUpdate()) {
                getAndCreateIfNeed(
                        OpType.UPATE,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }

            // 删除列操作.
            for (JsonTable.Column column : table.getDelete()) {
                getAndCreateIfNeed(
                        OpType.DELETE,
                        opTypeFieldSetMap,
                        ArrayList::new
                ).add(column.getColumn());
            }
        }

        return template;
    }

    /**
     * 根据key来获取对应的列表(若没有则返回空列表).
     *
     * @param key
     * @param map
     * @param factory
     * @param <T>
     * @param <R>
     * @return
     */
    private static <T, R> R getAndCreateIfNeed(T key, Map<T, R> map, Supplier<R> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }
}
