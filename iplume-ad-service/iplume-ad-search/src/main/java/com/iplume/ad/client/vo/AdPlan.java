package com.iplume.ad.client.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * adPlan vo对象类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/9
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlan {

    /**
     * id.
     */
    private Long id;

    /**
     * 标记当前记录所属用户.
     */
    private Long userId;

    /**
     * 推广计划名称.
     */
    private String planName;

    /**
     * 推广计划状态.
     */
    private Integer planStatus;

    /**
     * 推广计划开始时间.
     */
    private Date startDate;

    /**
     * 推广计划结束时间.
     */
    private Date endDate;

    /**
     * 创建时间.
     */
    private Date createTime;

    /**
     * 结束时间.
     */
    private Date updateTime;
}
