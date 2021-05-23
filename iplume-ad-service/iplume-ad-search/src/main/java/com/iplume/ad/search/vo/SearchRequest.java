package com.iplume.ad.search.vo;

import com.iplume.ad.search.vo.feature.DistrictFeature;
import com.iplume.ad.search.vo.feature.FeatureRelation;
import com.iplume.ad.search.vo.feature.ItFeature;
import com.iplume.ad.search.vo.feature.KeywordFeature;
import com.iplume.ad.search.vo.media.AdSlot;
import com.iplume.ad.search.vo.media.App;
import com.iplume.ad.search.vo.media.Device;
import com.iplume.ad.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 广告检索服务请求对象.
 *
 * @author: lingchen
 * @date: 2021/5/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    /**
     * 媒体方请求标识.
     */
    private String mediaId;

    /**
     * 请求基本信息.
     */
    private RequestInfo requestInfo;

    /**
     * 匹配信息.
     */
    private FeatureInfo featureInfo;

    /**
     * 请求基本信息.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestInfo {

        /**
         * 请求Id.
         */
        private String requestId;

        /**
         * 广告位信息列表.
         */
        private List<AdSlot> adSlots;

        /**
         * 应用.
         */
        private App app;

        /**
         * 地理信息.
         */
        private Geo geo;

        /**
         * 设备信息.
         */
        private Device device;

    }

    /**
     * 匹配信息.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeatureInfo {

        /**
         * 关键词.
         */
        private KeywordFeature keywordFeature;

        /**
         * 兴趣.
         */
        private ItFeature itFeature;

        /**
         * 区域.
         */
        private DistrictFeature districtFeature;

        /**
         * 广告检索类型(默认是与).
         */
        private FeatureRelation relation = FeatureRelation.AND;

    }
}
