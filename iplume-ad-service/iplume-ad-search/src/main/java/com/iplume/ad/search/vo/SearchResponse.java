package com.iplume.ad.search.vo;

import com.iplume.ad.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告检索响应对象.
 *
 * @author: lingchen
 * @date: 2021/5/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    /**
     * 广告检索信息Map.
     * <adSlotCode, List<Creative>>.
     */
    public Map<String, List<Creative>> adSlot2Ads = new HashMap<>();

    /**
     * 广告创意信息对象.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Creative {

        /**
         * 广告Id.
         */
        private Long adId;

        /**
         * 广告url.
         */
        private String adUrl;

        /**
         * 广告位宽度.
         */
        private Integer width;

        /**
         * 广告位高度.
         */
        private Integer height;

        /**
         * 广告类型.
         */
        private Integer type;

        /**
         * 物料类型.
         */
        private Integer materialType;

        /**
         * 展示监测url.
         */
        private List<String> showMonitorUrl = Arrays.asList("www.baidu.com", "www.baidu.com");

        /**
         * 点击监测url.
         */
        private List<String> clickMonitorUrl = Arrays.asList("www.baidu.com", "www.baidu.com");
    }

    /**
     * 创意索引对象 -> 广告创意信息对象 转换.
     *
     * @param object 创意索引对象.
     * @return 广告创意信息对象.
     */
    public static Creative convert(CreativeObject object) {

        Creative creative = new Creative();
        // 广告Id.
        creative.setAdId(object.getAdId());
        // 广告url.
        creative.setAdUrl(object.getAdUrl());
        // 广告位宽度.
        creative.setWidth(object.getWidth());
        // 广告位高度.
        creative.setHeight(object.getHeight());
        // 广告类型.
        creative.setType(object.getType());
        // 物料类型.
        creative.setMaterialType(object.getMaterialType());

        return creative;
    }
}
