package com.iplume.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 兴趣限制请求对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitItRequest {

    /**
     * 兴趣限制列表.
     */
    public List<UnitIt> unitIts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitIt {

        /**
         * 关联推广单元id.
         */
        private Long unitId;

        /**
         * 兴趣标签.
         */
        private String itTag;

    }
}
