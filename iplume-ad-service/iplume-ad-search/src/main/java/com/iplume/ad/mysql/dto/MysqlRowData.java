package com.iplume.ad.mysql.dto;

import com.iplume.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Binlog增量数据对象.
 *
 * @author: lingchen
 * @date: 2021/5/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MysqlRowData {

    /**
     * 表模版索引映射类.
     */
    private String tableName;

    /**
     * 级别.
     */
    private String level;

    /**
     * 数据类型.
     */
    private OpType type;

    /**
     * 数据Map.
     * 数据格式: <字段名, 值>.
     */
    private List<Map<String, String>> fieldValueMap = new ArrayList<>();

}
