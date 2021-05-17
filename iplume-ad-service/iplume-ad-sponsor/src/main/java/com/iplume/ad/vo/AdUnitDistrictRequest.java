package com.iplume.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 地域限制请求对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitDistrictRequest {

    /**
     * 地域限制列表.
     */
    private List<UnitDistrict> unitDistricts;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UnitDistrict {

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
}
