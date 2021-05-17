package com.iplume.ad.constant;

import lombok.Getter;

/**
 * 共通创意物料状态类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/19
 */
@Getter
public enum CreativeMetrialType {

    /**
     * jpg.
     */
    JPG(1, "jpg"),

    /**
     * bmp.
     */
    BMP(2, "bmp"),

    /**
     * mp4.
     */
    MP4(3, "mp4"),

    /**
     * avi.
     */
    AVI(4, "avi"),

    /**
     * txt.
     */
    TXT(5, "txt");

    /**
     * 物料类型.
     */
    private int type;

    /**
     * 描述.
     */
    private String desc;

    CreativeMetrialType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
