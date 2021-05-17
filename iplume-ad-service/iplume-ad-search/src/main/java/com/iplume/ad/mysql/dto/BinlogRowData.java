package com.iplume.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Binlog的行数据的vo类.
 *
 * @author: lingchen
 * @date: 2021/5/15
 */
@Data
public class BinlogRowData {

    /**
     * 表模版索引映射类.
     */
    private TableTemplate tableTemplate;

    /**
     * 事件类型.
     */
    private EventType eventType;

    /**
     * Binlog的操作前数据.
     */
    private List<Map<String, String>> before;

    /**
     * Binlog的操作后数据.
     */
    private List<Map<String, String>> after;
}
