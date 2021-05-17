package com.iplume.ad.mysql.listener;

import com.iplume.ad.mysql.dto.BinlogRowData;

/**
 * Binlog的监听接口类.
 *
 * @author: lingchen
 * @date: 2021/5/15
 */
public interface Ilistener {

    /**
     * 监听注册.
     * 将Binlog传递出去.
     */
    void register();

    /**
     * 监听事件.
     * 对Binlog进行全量或者增量操作.
     *
     * @param eventData
     */
    void onEvent(BinlogRowData eventData);
}
