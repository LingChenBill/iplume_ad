package com.iplume.ad.index;

import lombok.Getter;

/**
 * 状态枚举类.
 *
 * @author: lingchen
 * @date: 2021/5/25
 */
@Getter
public enum CommonStatus {

    VALID(1, "有效状态"),
    INVALID(0, "无效状态");

    /**
     * 状态.
     */
    private Integer status;

    /**
     * 描述.
     */
    private String desc;

    /**
     * 初始化.
     *
     * @param status 状态.
     * @param desc 描述.
     */
    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

}
