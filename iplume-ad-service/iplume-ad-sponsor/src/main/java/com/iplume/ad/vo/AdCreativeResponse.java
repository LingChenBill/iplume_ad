package com.iplume.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创意响应对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdCreativeResponse {

    /**
     * Id.
     */
    private Long id;

    /**
     * 创意名称.
     */
    private String name;
}
