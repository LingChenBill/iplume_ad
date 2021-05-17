package com.iplume.ad.index.adunit;

import com.iplume.ad.index.adplan.AdPlanObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AdUnit索引对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitObject {

    /**
     * unitId.
     */
    private Long unitId;

    /**
     * 推广单元状态.
     */
    private Integer unitStatus;

    /**
     * 广告位类型(开屏, 贴片, 中贴...).
     */
    private Integer positionType;

    /**
     * 关联推广计划id.
     */
    private Long planId;

    /**
     * AdPlan推广计划.
     */
    private AdPlanObject adPlanObject;

    /**
     * AdUnitObject索引对象更新.
     *
     * @param newObject
     */
    public void update(AdUnitObject newObject) {
        if (newObject.getUnitId() != null) {
            this.unitId = newObject.getUnitId();
        }

        if (newObject.getUnitStatus() != null) {
            this.unitStatus = newObject.getUnitStatus();
        }

        if (newObject.getPositionType() != null) {
            this.positionType = newObject.getPositionType();
        }

        if (newObject.getPlanId() != null) {
            this.planId = newObject.getPlanId();
        }

        if (newObject.getAdPlanObject() != null) {
            this.adPlanObject = newObject.getAdPlanObject();
        }
    }
}
