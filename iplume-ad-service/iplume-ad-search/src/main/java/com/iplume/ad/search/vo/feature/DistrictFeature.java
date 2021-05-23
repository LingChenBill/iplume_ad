package com.iplume.ad.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 区域信息对象.
 *
 * @author: lingchen
 * @date: 2021/5/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistrictFeature {

    /**
     * 所在省与城市列表.
     */
    private List<ProvinceAndCity> provinceAndCitys;

    /**
     * 所在省和城市对象.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProvinceAndCity {

        /**
         * 所在省.
         */
        private String province;

        /**
         * 所在城市.
         */
        private String city;
    }
}
