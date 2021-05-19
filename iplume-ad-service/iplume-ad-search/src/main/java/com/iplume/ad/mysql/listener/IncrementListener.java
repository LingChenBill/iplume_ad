package com.iplume.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.event.EventType;
import com.iplume.ad.mysql.constant.Constant;
import com.iplume.ad.mysql.constant.OpType;
import com.iplume.ad.mysql.dto.BinlogRowData;
import com.iplume.ad.mysql.dto.MysqlRowData;
import com.iplume.ad.mysql.dto.TableTemplate;
import com.iplume.ad.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Binlog增量数据监听类.
 *
 * @author: lingchen
 * @date: 2021/5/16
 */
@Slf4j
@Component
public class IncrementListener implements Ilistener{

    /**
     * 消息.
     */
    @Resource(name = "indexSender")
    private ISender sender;

    /**
     * 收集与聚合Binlog监听类.
     */
    private final AggregationListener aggregationListener;

    @Autowired
    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    /**
     * 监听注册.
     */
    @Override
    public void register() {

        log.info("IncrementListener register db and table info");

        // 每个表的监听注册.
        Constant.table2db.forEach(
                (k, v) -> aggregationListener.register(v, k, this));
    }

    /**
     * 监听事件.
     *
     * @param eventData 事件数据.
     */
    @Override
    public void onEvent(BinlogRowData eventData) {

        // 表模版索引映射类.
        TableTemplate tableTemplate = eventData.getTableTemplate();
        // 事件类型.
        EventType eventType = eventData.getEventType();

        // 包装成最后需要投递的数据.
        MysqlRowData rowData = new MysqlRowData();
        // 表名.
        rowData.setTableName(tableTemplate.getTableName());
        // 级别.
        rowData.setLevel(eventData.getTableTemplate().getLevel());
        // 事件类型转换成数据处理类型.
        OpType type = OpType.to(eventType);
        // 数据类型
        rowData.setType(type);

        // 取出模板中该操作对应的字段列表.
        List<String> fieldList = tableTemplate.getOpTypeFieldSetMap().get(type);
        if (fieldList == null) {
            log.warn("{} not support for {}", type, tableTemplate.getTableName());
            return;
        }

        for (Map<String, String> after : eventData.getAfter()) {

            // 修改后数据Map.
            Map<String, String> afterMap = new HashMap<>(16);

            for (Map.Entry<String, String> entry : after.entrySet()) {
                // 表名.
                String colName = entry.getKey();
                // 表数据.
                String colValue = entry.getValue();
                afterMap.put(colName, colValue);
            }

            // 数据Map.
            rowData.getFieldValueMap().add(afterMap);
        }

        // 消息传递.
        sender.sender(rowData);
    }
}
