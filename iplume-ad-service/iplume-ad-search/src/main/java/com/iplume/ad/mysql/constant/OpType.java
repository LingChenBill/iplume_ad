package com.iplume.ad.mysql.constant;


import com.github.shyiko.mysql.binlog.event.EventType;

import static com.github.shyiko.mysql.binlog.event.EventType.*;

/**
 * 索引操作类型常量标识类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/14
 */
public enum OpType {

    /**
     * 添加.
     */
    ADD,

    /**
     * 更新.
     */
    UPATE,

    /**
     * 删除.
     */
    DELETE,

    /**
     * 其它操作.
     */
    OTHER;

    /**
     * EventType -> OpType转换处理.
     *
     * @param eventType 事件类型.
     * @return 操作类型.
     */
    public static OpType to(EventType eventType) {

        switch (eventType) {
            case EXT_WRITE_ROWS:
                return ADD;
            case EXT_UPDATE_ROWS:
                return UPATE;
            case EXT_DELETE_ROWS:
                return DELETE;
            default:
                return OTHER;
        }
    }

}
