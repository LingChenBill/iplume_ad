package com.iplume.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.util.List;

/**
 * 关键词限制请求对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitKeywordRequest {

    /**
     * 关键词限制列表.
     */
    private List<UnitKeyword> unitKeywords;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitKeyword {

        /**
         * 关联推广单元id.
         */
        private Long unitId;

        /**
         * 关键词.
         */
        private String keyword;

    }
}
