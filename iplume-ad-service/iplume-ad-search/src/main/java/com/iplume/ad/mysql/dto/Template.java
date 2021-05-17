package com.iplume.ad.mysql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 模版vo类.
 *
 * @author: lingchen
 * @date: 2021/5/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Template {

    /**
     * 数据库名.
     */
    private String database;

    /**
     * 表列表.
     */
    private List<JsonTable> tableList;
}
