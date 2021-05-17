package com.iplume.ad.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * AdPlan请求对象类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanGetRequest {

    /**
     * 标记当前记录所属用户.
     */
    private Long userId;

    /**
     * id列表.
     */
    private List<Long> ids;
}
