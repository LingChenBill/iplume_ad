package com.iplume.ad.mysql;

import com.alibaba.fastjson.JSON;
import com.iplume.ad.mysql.constant.OpType;
import com.iplume.ad.mysql.dto.ParseTemplate;
import com.iplume.ad.mysql.dto.TableTemplate;
import com.iplume.ad.mysql.dto.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * 模版处理类.
 * <p>
 * 1.解析template.json模版文件.
 * 2.表列索引到列名的索引关系.
 *
 * @author: lingchen
 * @date: 2021/5/15
 */
@Slf4j
@Component
public class TemplateHolder {

    /**
     * template模版解析类.
     */
    private ParseTemplate parseTemplate;

    /**
     * JdbcTemplate.
     */
    private final JdbcTemplate jdbcTemplate;

    /**
     * 查询Binlog的sql文.
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    private String SQL_SCHEMA = "select table_schema, table_name, column_name, ordinal_position " +
            "from information_schema.columns where table_schema = ? and table_name = ?";

    @Autowired
    public TemplateHolder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 加载TemplateHolder的init方法.
     */
    @PostConstruct
    private void init() {
        // 加载template.json模版文件.
        loadJson("template.json");
    }

    /**
     * 获取指定表的字段映射关系Template.
     *
     * @param tableName 表名.
     * @return TableTemplate
     */
    public TableTemplate getTable(String tableName) {
        // 获取指定表的字段映射关系.
        return parseTemplate.getTableTemplateMap().get(tableName);
    }

    /**
     * 加载template.json模版文件.
     *
     * @param path json文件路径.
     */
    private void loadJson(String path) {

        // 通过流的方式加载文件,解析成vo对象.
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream inStream = cl.getResourceAsStream(path);

        try {

            // template.json 解析成 Template vo对象.
            Template template = JSON.parseObject(
                    inStream,
                    Charset.defaultCharset(),
                    Template.class
            );

            // 解析Json的Template类到ParseTemplate类.
            this.parseTemplate = ParseTemplate.parse(template);

            // 加载表字段索引与字段名映射.
            loadMeta();

        } catch (IOException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("Fail to parse json file.");
        }
    }

    /**
     * 加载表字段索引与字段名映射.
     */
    private void loadMeta() {
        for (Map.Entry<String, TableTemplate> entry : parseTemplate.getTableTemplateMap().entrySet()) {
            TableTemplate tableTemplate = entry.getValue();

            // 添加字段列表.
            List<String> insertFields = tableTemplate.getOpTypeFieldSetMap().get(OpType.ADD);
            // 更新字段列表.
            List<String> updateFields = tableTemplate.getOpTypeFieldSetMap().get(OpType.UPATE);
            // 删除字段列表.
            List<String> deleteFields = tableTemplate.getOpTypeFieldSetMap().get(OpType.DELETE);

            // 索引位置结构.
            // mysql> select table_schema, table_name, column_name, ordinal_position
            // 	      from information_schema.columns where table_schema = 'iplume_ad'
            // 	      and table_name = 'ad_unit_keyword';
            // +--------------+-----------------+-------------+------------------+
            // | TABLE_SCHEMA | TABLE_NAME      | COLUMN_NAME | ORDINAL_POSITION |
            // +--------------+-----------------+-------------+------------------+
            // | iplume_ad    | ad_unit_keyword | id          |                1 |
            // | iplume_ad    | ad_unit_keyword | keyword     |                3 |
            // | iplume_ad    | ad_unit_keyword | unit_id     |                2 |
            // +--------------+-----------------+-------------+------------------+

            // query语句查询.
            jdbcTemplate.query(SQL_SCHEMA, new Object[]{
                    parseTemplate.getDatabase(),
                    tableTemplate.getTableName()
            }, (rs, i) -> {
                // 字段索引.
                int pos = rs.getInt("ORDINAL_POSITION");
                // 字段名.
                String colName = rs.getString("COLUMN_NAME");

                // 字段索引 -> 字段名映射Map设置.
                if (insertFields != null && insertFields.contains(colName)) {
                    tableTemplate.getPosMap().put(pos - 1, colName);
                } else if (updateFields != null && updateFields.contains(colName)) {
                    tableTemplate.getPosMap().put(pos - 1, colName);
                } else if (deleteFields != null && deleteFields.contains(colName)) {
                    tableTemplate.getPosMap().put(pos - 1, colName);
                }

                return null;
            });
        }
    }

}
