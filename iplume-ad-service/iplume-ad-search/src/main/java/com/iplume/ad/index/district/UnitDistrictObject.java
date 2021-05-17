package com.iplume.ad.index.district;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地域限制索引对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitDistrictObject {

    /**
     * 关联推广单元id.
     */
    private Long unitId;

    /**
     * 省.
     */
    private String province;

    /**
     * 市.
     */
    private String city;

    // <String, Set<Long>>
    // String: province-city
}
