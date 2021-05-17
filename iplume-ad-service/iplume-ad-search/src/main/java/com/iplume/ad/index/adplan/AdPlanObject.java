package com.iplume.ad.index.adplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * AdPlan的索引对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanObject {

    /**
     * planId.
     */
    private Long planId;

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

    /**
     * 更新操作.
     *
     * @param newObject
     */
    public void update(AdPlanObject newObject) {

        if (newObject.getPlanId() != null) {
            this.planId = newObject.getPlanId();
        }

        if (newObject.getUserId() != null) {
            this.userId = newObject.getUserId();
        }

        if (newObject.getPlanStatus() != null) {
            this.planStatus = newObject.getPlanStatus();
        }

        if (newObject.getStartDate() != null) {
            this.startDate = newObject.getStartDate();
        }

        if (newObject.getEndDate() != null) {
            this.endDate = newObject.getEndDate();
        }
    }

}
