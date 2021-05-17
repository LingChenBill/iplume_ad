package com.iplume.ad.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * BinLog使用的测试类.
 *
 * @author: lingchen
 * @date: 2021/5/15
 */
@Slf4j
public class BinlogServiceTest {

    // 22:11:56.503 [main] INFO com.iplume.ad.service.BinlogServiceTest - Add---------------------
    // 22:11:56.506 [main] INFO com.iplume.ad.service.BinlogServiceTest - WriteRowsEventData{tableId=72,
    // includedColumns={0, 1, 2}, rows=[
    //     [10, 10, 奥迪]
    // ]}
    // 22:11:56.507 [main] INFO com.iplume.ad.service.BinlogServiceTest - Update---------------------
    // 22:11:56.507 [main] INFO com.iplume.ad.service.BinlogServiceTest - UpdateRowsEventData{tableId=72,
    // includedColumnsBeforeUpdate={0, 1, 2}, includedColumns={0, 1, 2}, rows=[
    //     {before=[10, 10, 奥迪], after=[10, 10, 奔驰]}
    // ]}
    // 22:11:56.508 [main] INFO com.iplume.ad.service.BinlogServiceTest - Delete---------------------
    // 22:11:56.508 [main] INFO com.iplume.ad.service.BinlogServiceTest - DeleteRowsEventData{tableId=72,
    // includedColumns={0, 1, 2}, rows=[
    //     [10, 10, 奔驰]
    //]}

//     21:44:46.517 [main] INFO com.iplume.ad.service.BinlogServiceTest - Add---------------------
//             21:44:46.518 [main] INFO com.iplume.ad.service.BinlogServiceTest - WriteRowsEventData{tableId=68, includedColumns={0, 1, 2, 3, 4, 5, 6, 7}, rows=[
//     [10, 10, 计划一, 1, Fri Jan 01 08:00:00 CST 2021, Fri Jan 01 08:00:00 CST 2021, Fri Jan 01 08:00:00 CST 2021, Fri Jan 01 08:00:00 CST 2021]
// ]}

    // Fri Jan 01 08:00:00 CST 2021

    public static void main(String[] args) throws IOException {

        // BinLog客户端创建.
        BinaryLogClient client = new BinaryLogClient(
                "127.0.0.1",
                3306,
                "root",
                "Aa123456"
        );

        // 设置binlog文件.
        client.setBinlogFilename("binlog.000036");
        // 设置binlog位置.
        // client.setBinlogPosition();

        client.registerEventListener(event -> {
            EventData data = event.getData();

            // 更新,添加,删除Event.
            if (data instanceof UpdateRowsEventData) {
                log.info("Update---------------------");
                log.info(data.toString());
            } else if (data instanceof WriteRowsEventData) {
                log.info("Add---------------------");
                log.info(data.toString());
            } else if (data instanceof DeleteRowsEventData) {
                log.info("Delete---------------------");
                log.info(data.toString());
            }
        });

        // 创建连接.
        client.connect();
    }
}
