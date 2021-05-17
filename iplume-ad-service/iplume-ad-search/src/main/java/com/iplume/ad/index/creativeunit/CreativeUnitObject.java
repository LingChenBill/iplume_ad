package com.iplume.ad.index.creativeunit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创意与推广单元的索引对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitObject {

    /**
     * 广告Id.
     */
    private Long adId;

    /**
     * 推广单元Id.
     */
    private Long unitId;

    // key: adId-unitId
}
