package com.iplume.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 推广计划响应对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanResponse {

    /**
     * id.
     */
    private Long id;

    /**
     * 推广计划名称.
     */
    private String planName;

}
