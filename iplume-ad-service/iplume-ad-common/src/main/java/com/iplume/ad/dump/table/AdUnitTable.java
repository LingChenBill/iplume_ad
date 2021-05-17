package com.iplume.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AdUnit索引存储对象.
 *
 * @author: lingchen
 * @date: 2021/5/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitTable {

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
}
