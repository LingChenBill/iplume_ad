package com.iplume.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应对象类.
 *
 * @author: lingchen
 * @date: 2021/4/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> implements Serializable {

    /**
     * 状态码.
     */
    private Integer code;

    /**
     * 信息.
     */
    private String message;

    /**
     * 数据实体.
     */
    private T data;

    public CommonResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
