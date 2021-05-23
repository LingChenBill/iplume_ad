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

    /**
     * 判断广告位类型是否开屏.
     *
     * @param positionType 广告位类型.
     * @return 是否开屏.
     */
    private static boolean isKaiPing(int positionType) {
        return (positionType & AdUnitConstant.PositionType.KAIPING) > 0;
    }

    /**
     * 判断广告位类型是否贴片.
     *
     * @param positionType 广告位类型.
     * @return 是否贴片.
     */
    private static boolean isTiePian(int positionType) {
        return (positionType & AdUnitConstant.PositionType.TIEPIAN) > 0;
    }

    /**
     * 判断广告位类型是否贴片中间.
     *
     * @param positionType 广告位类型.
     * @return 是否贴片中间.
     */
    private static boolean isTiePianMiddle(int positionType) {
        return (positionType & AdUnitConstant.PositionType.TIEPIAN_MIDDLE) > 0;
    }

    /**
     * 判断广告位类型是否贴片暂停.
     *
     * @param positionType 广告位类型.
     * @return 是否贴片暂停.
     */
    private static boolean isTiePianPause(int positionType) {
        return (positionType & AdUnitConstant.PositionType.TIEPIAN_PAUSE) > 0;
    }

    /**
     * 判断广告位类型是否贴片后置.
     *
     * @param positionType 广告位类型.
     * @return 是否贴片后置.
     */
    private static boolean isTiePianPost(int positionType) {
        return (positionType & AdUnitConstant.PositionType.TIEPIAN_POST) > 0;
    }

    /**
     * 广告位物料类型判断.
     *
     * @param adSlotType 物料类型.
     * @param positionType 位置类型.
     * @return 物料类型是否符合.
     */
    public static boolean isAdSlotTypeOk(int adSlotType, int positionType) {

        switch (adSlotType) {
            case AdUnitConstant.PositionType.KAIPING:
                return isKaiPing(positionType);
            case AdUnitConstant.PositionType.TIEPIAN:
                return isTiePian(positionType);
            case AdUnitConstant.PositionType.TIEPIAN_MIDDLE:
                return isTiePianMiddle(positionType);
            case AdUnitConstant.PositionType.TIEPIAN_PAUSE:
                return isTiePianPause(positionType);
            case AdUnitConstant.PositionType.TIEPIAN_POST:
                return isTiePianPost(positionType);
            default:
                return false;
        }
    }

}
