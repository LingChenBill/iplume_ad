package com.iplume.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地域限制索引存储对象.
 *
 * @author: lingchen
 * @date: 2021/5/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitDistrictTable {

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

}