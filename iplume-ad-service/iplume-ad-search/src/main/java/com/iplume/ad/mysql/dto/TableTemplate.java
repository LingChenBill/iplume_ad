package com.iplume.ad.mysql.dto;

import com.iplume.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 表模版索引映射类.
 *
 * @author: lingchen
 * @date: 2021/5/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTemplate {

    /**
     * 表名.
     */
    private String tableName;

    /**
     * 级别.
     */
    private String level;

    /**
     * 类型 -> 字段Map.
     */
    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();

    /**
     * 字段索引 -> 字段名映射Map.
     */
    private Map<Integer, String> posMap = new HashMap<>();
}
