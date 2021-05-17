package com.iplume.ad.sender;

import com.iplume.ad.mysql.dto.MysqlRowData;

/**
 * 消息传递接口.
 *
 * @author: lingchen
 * @date: 2021/5/16
 */
public interface ISender {

    /**
     * 传递消息数据.
     *
     * @param rowData 消息数据.
     */
    void sender(MysqlRowData rowData);
}
