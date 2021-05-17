package com.iplume.ad.constant;

import lombok.Getter;

/**
 * 共通状态类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/19
 */
@Getter
public enum CommonStatus {

    /**
     * 有效状态.
     */
    VALID(1, "有效状态"),

    /**
     * 无效状态.
     */
    INVALID(0, "无效状态");

    /**
     * 状态码.
     */
    private Integer status;

    /**
     * 状态描述.
     */
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
}
