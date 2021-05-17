package com.iplume.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * template.json的vo类.
 *
 * @author: lingchen
 * @date: 2021/5/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonTable {

    /**
     * 表名.
     */
    private String tableName;

    /**
     * 级别.
     */
    private Integer level;

    /**
     * 添加字段列表.
     */
    private List<Column> insert;

    /**
     * 更新字段列表.
     */
    private List<Column> update;

    /**
     * 删除字段列表.
     */
    private List<Column> delete;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Column {

        /**
         * 字段.
         */
        private String column;
    }
}
