package com.iplume.ad.mysql.listener;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.iplume.ad.mysql.TemplateHolder;
import com.iplume.ad.mysql.dto.BinlogRowData;
import com.iplume.ad.mysql.dto.TableTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 收集与聚合Binlog.
 *
 * @author: lingchen
 * @date: 2021/5/15
 */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    /**
     * 数据库名.
     */
    private String dbName;

    /**
     * 表名.
     */
    private String tableName;

    /**
     * 监听Binlog的Map.
     */
    private Map<String, Ilistener> listenerMap = new HashMap<>();

    /**
     * 模版处理类.
     */
    private final TemplateHolder templateHolder;

    @Autowired
    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    /**
     * 注册监听器.
     *
     * @param dbName    数据库名.
     * @param tableName 表名.
     * @param ilistener 监听器.
     */
    public void register(String dbName, String tableName, Ilistener ilistener) {
        log.info("register: {} - {}", dbName, tableName);
        this.listenerMap.put(genKey(dbName, tableName), ilistener);
    }

    /**
     * 将Binlog的Event中的EventData转换成BinlogRowData对象.
     *
     * @param event 事件.
     */
    @Override
    public void onEvent(Event event) {

        EventType eventType = event.getHeader().getEventType();
        log.debug("event type: {}", eventType);

        if (eventType == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.tableName = data.getTable();
            this.dbName = data.getDatabase();
            return;
        }

        // 操作类型校验.
        if (eventType != EventType.EXT_WRITE_ROWS
                && eventType != EventType.EXT_UPDATE_ROWS
                && eventType != EventType.EXT_DELETE_ROWS) {
            return;
        }

        // 数据库名与表名校验.
        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            log.error("no meta data event");
            return;
        }

        // 找出对应表有兴趣的监听器.
        String key = genKey(this.dbName, this.tableName);
        Ilistener listener = this.listenerMap.get(key);

        // 监听器校验.
        if (listener == null) {
            log.debug("skip {}", key);
            return;
        }

        log.info("trigger event, {}", eventType.name());

        try {

            // EventData转换成BinlogRowData对象.
            BinlogRowData rowData = buildRowData(event.getData());
            if (rowData == null) {
                return;
            }

            rowData.setEventType(eventType);
            listener.onEvent(rowData);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        } finally {
            this.dbName = "";
            this.tableName = "";
        }
    }

    /**
     * 生成监听处理key.
     *
     * @param dbName    数据库名.
     * @param tableName 表名.
     * @return
     */
    private String genKey(String dbName, String tableName) {
        return dbName + ":" + tableName;
    }

    /**
     * 获取修改后的数据.
     *
     * @param eventData 事件数据.
     * @return 修改后的数据.
     */
    private List<Serializable[]> getAfterValues(EventData eventData) {

        // 添加类型的数据.
        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }

        // 更新类型的数据.
        if (eventData instanceof UpdateRowsEventData) {
            // 更新类型的数据只获取修改后的数据.对应的map的value值.
            return ((UpdateRowsEventData) eventData).getRows().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
        }

        // 删除类型的数据.
        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }

        // 上述以外的数据类型返回空的LIST.
        return Collections.emptyList();
    }

    /**
     * 将EventData转换成BinlogRowData对象的操作处理.
     *
     * @param data EventData.
     * @return
     */
    private BinlogRowData buildRowData(EventData data) {

        TableTemplate tableTemplate = templateHolder.getTable(this.tableName);

        // 数据表校验.
        if (tableTemplate == null) {
            log.warn("table {} not found", this.tableName);
            return null;
        }

        // Binlog的操作后数据.
        List<Map<String, String>> afterMapList = new ArrayList<>();

        for (Serializable[] afterValue : getAfterValues(data)) {

            // 集合初始化时，指定集合初始值大小。
            // 说明：HashMap 使用HashMap(int initialCapacity)初始化，如果暂时无法确定集合大小，那么指定默认值（16）即可。
            // 正例：initialCapacity = (需要存储的元素个数 / 负载因子) + 1。
            // 注意负载因子（即loader factor）默认为0.75，如果暂时无法确定初始值大小，请设置为16（即默认值）。
            // 反例：HashMap需要放置1024个元素，由于没有设置容量初始大小，随着元素不断增加，容量7次被迫扩大，resize需要重建hash表。
            // 当放置的集合元素个数达千万级别时，不断扩容会严重影响性能。

            // HashMap设置默认值.
            Map<String, String> afterMap = new HashMap<>(16);

            // 数据列的长度.
            int colLength = afterValue.length;

            for(int index = 0; index < colLength; ++index) {
                // 取出当前位置对应的列名.
                String colName = tableTemplate.getPosMap().get(index);

                // 对不需要的列名进行过滤.
                if (StringUtils.isEmpty(colName)) {
                    log.debug("Ignore position: {}", index);
                    continue;
                }

                // 设置对应的列名与数据.
                afterMap.put(colName, afterValue[index].toString());
            }

            afterMapList.add(afterMap);
        }

        // BinlogRowData数据对象设置.
        BinlogRowData rowData = new BinlogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTableTemplate(tableTemplate);

        return rowData;
    }
}
