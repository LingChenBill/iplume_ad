package com.iplume.ad.index.interest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UnitIt索引对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitItObject {

    /**
     * 关联推广单元id.
     */
    private Long unitId;

    /**
     * 兴趣标签.
     */
    private String itTag;

}
