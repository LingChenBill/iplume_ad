package com.iplume.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 推广单元响应对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitResponse {

    /**
     * id.
     */
    private Long id;

    /**
     * 推广单元名称.
     */
    private String unitName;

}
