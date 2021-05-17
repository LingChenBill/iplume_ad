package com.iplume.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UnitIt索引存储对象.
 *
 * @author: lingchen
 * @date: 2021/5/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitItTable {

    /**
     * 关联推广单元id.
     */
    private Long unitId;

    /**
     * 兴趣标签.
     */
    private String itTag;
}
