package com.iplume.ad.constant;

import lombok.Getter;

/**
 * 共通创意状态类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/19
 */
@Getter
public enum CreativeType {

    /**
     * 图片.
     */
    IMAGE(1, "图片"),

    /**
     * 视频.
     */
    VIDEO(2, "视频"),

    /**
     * 文本.
     */
    TEXT(3, "文本");

    /**
     * 类型.
     */
    private int type;

    /**
     * 描述.
     */
    private String desc;

    CreativeType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
