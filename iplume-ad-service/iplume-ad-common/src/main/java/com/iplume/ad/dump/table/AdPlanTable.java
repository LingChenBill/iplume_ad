package com.iplume.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * AdPlan的索引存储对象.
 *
 * @author: lingchen
 * @date: 2021/5/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanTable {

    /**
     * id.
     */
    private Long id;

    /**
     * 标记当前记录所属用户.
     */
    private Long userId;

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

}
