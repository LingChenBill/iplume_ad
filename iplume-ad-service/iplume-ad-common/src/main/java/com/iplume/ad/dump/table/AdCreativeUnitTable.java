package com.iplume.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创意与推广单元的索引存储对象.
 *
 * @author: lingchen
 * @date: 2021/5/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdCreativeUnitTable {

    /**
     * 广告Id.
     */
    private Long adId;

    /**
     * 推广单元Id.
     */
    private Long unitId;

}
