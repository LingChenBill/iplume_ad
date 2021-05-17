package com.iplume.ad.index.keyword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UnitKeyWord索引对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitKeyWordObject {

    /**
     * 关联推广单元id.
     */
    private Long unitId;

    /**
     * 关键词.
     */
    private String keyword;

}
