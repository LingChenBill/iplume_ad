package com.iplume.ad.index;

import lombok.Getter;

/**
 * 数据类型枚举类.
 *
 * @author: lingchen
 * @date: 2021/5/17
 */
@Getter
public enum DataLevel {

    /**
     * 级别2.
     */
    LEVEL2("2", "level 2"),
    /**
     * 级别3.
     */
    LEVEL3("3", "level 3"),
    /**
     * 级别4.
     */
    LEVEL4("4", "level 4");

    /**
     * 等级.
     */
    private String level;

    /**
     * 描述.
     */
    private String desc;

    DataLevel(String level, String desc) {
        this.level = level;
        this.desc = desc;
    }
}
