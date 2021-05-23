package com.iplume.ad.search.impl;

import com.iplume.ad.index.DataTable;
import com.iplume.ad.index.adunit.AdUnitIndex;
import com.iplume.ad.index.district.UnitDistrictIndex;
import com.iplume.ad.index.interest.UnitItIndex;
import com.iplume.ad.index.keyword.UnitKeyWordIndex;
import com.iplume.ad.search.ISearch;
import com.iplume.ad.search.vo.SearchRequest;
import com.iplume.ad.search.vo.SearchResponse;
import com.iplume.ad.search.vo.feature.DistrictFeature;
import com.iplume.ad.search.vo.feature.FeatureRelation;
import com.iplume.ad.search.vo.feature.ItFeature;
import com.iplume.ad.search.vo.feature.KeywordFeature;
import com.iplume.ad.search.vo.media.AdSlot;
import com.iplume.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 广告检索实现类.
 *
 * @author: lingchen
 * @date: 2021/5/22
 */
@Slf4j
@Service
public class SearchImpl implements ISearch {

    /**
     * 获取广告检索信息.
     *
     * @param request 广告检索服务请求对象.
     * @return 广告检索响应对象.
     */
    @Override
    public SearchResponse fetchAds(SearchRequest request) {

        // 广告位信息.
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();

        // 区域信息对象.
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        // 兴趣信息对象.
        ItFeature itFeature = request.getFeatureInfo().getItFeature();
        // 关键词信息对象.
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        // 广告检索类型.
        FeatureRelation relation = request.getFeatureInfo().getRelation();

        // 构造广告检索响应对象.
        SearchResponse response = new SearchResponse();

        // 广告检索信息Map.
        Map<String, List<SearchResponse.Creative>> adSlot2Ads = response.getAdSlot2Ads();

        for (AdSlot adSlot : adSlots) {
            // 处理后广告单元Ids.
            Set<Long> targetUnitIdSet;
            // 匹配广告单元Id集合.
            Set<Long> adUnitIdSet = DataTable.of(AdUnitIndex.class).match(adSlot.getPositionType());

            // Add时过滤.
            if (relation == FeatureRelation.AND) {
                // 三个过滤操作的合集.
                filterKeywordFeature(adUnitIdSet, keywordFeature);
                filterDistrictFeature(adUnitIdSet, districtFeature);
                filterItFeature(adUnitIdSet, itFeature);

                targetUnitIdSet = adUnitIdSet;
            } else {
                // Or时过滤.
                targetUnitIdSet = getOrRelationUnitIds(adUnitIdSet,
                        keywordFeature,
                        districtFeature,
                        itFeature);
            }
        }

        return null;
    }

    /**
     * Or关系过滤处理.
     *
     * @param adUnitIds 广告单元Ids.
     * @param keywordFeature 关键词信息对象.
     * @param districtFeature 区域信息对象.
     * @param itFeature 兴趣信息对象.
     */
    private Set<Long> getOrRelationUnitIds(Set<Long> adUnitIds,
                                      KeywordFeature keywordFeature,
                                      DistrictFeature districtFeature,
                                      ItFeature itFeature) {

        if (CollectionUtils.isEmpty(adUnitIds)) {
            return Collections.emptySet();
        }

        // 初始化三种操作的广告单元Ids.
        Set<Long> keywordUnitIdSet = new HashSet<>(adUnitIds);
        Set<Long> districtUnitIdSet = new HashSet<>(adUnitIds);
        Set<Long> itUnitIdSet = new HashSet<>(adUnitIds);

        // 三个过滤操作的合集.
        filterKeywordFeature(keywordUnitIdSet, keywordFeature);
        filterDistrictFeature(districtUnitIdSet, districtFeature);
        filterItFeature(itUnitIdSet, itFeature);

        // 返回三种操作后的adUnitIds的并集.
        return new HashSet<>(
                CollectionUtils.union(
                        CollectionUtils.union(keywordUnitIdSet, districtUnitIdSet),
                        itUnitIdSet
                )
        );
    }

    /**
     * 广告单元Id列表(关键词信息对象过滤).
     *
     * @param adUnitIds      广告单元Ids.
     * @param keywordFeature 关键词信息对象.
     */
    private void filterKeywordFeature(Collection<Long> adUnitIds, KeywordFeature keywordFeature) {

        // 空校验.
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())) {
            // 判断adUnitIds列表中每一个元素是否符合某一个匹配条件,符合则保留,否则删除该元素.
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId ->
                            DataTable.of(UnitKeyWordIndex.class)
                                    .match(adUnitId,
                                            keywordFeature.getKeywords())
            );
        }
    }

    /**
     * 广告单元Id列表(区域信息对象过滤).
     *
     * @param adUnitIds       广告单元Ids.
     * @param districtFeature 区域信息对象.
     */
    private void filterDistrictFeature(Collection<Long> adUnitIds, DistrictFeature districtFeature) {

        // 空校验.
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(districtFeature.getProvinceAndCitys())) {
            // 匹配过滤.
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId ->
                            DataTable.of(UnitDistrictIndex.class)
                                    .match(adUnitId, districtFeature.getProvinceAndCitys())
            );
        }
    }

    /**
     * 广告单元Id列表(兴趣信息对象过滤).
     *
     * @param adUnitIds 广告单元Ids.
     * @param itFeature 兴趣信息对象.
     */
    private void filterItFeature(Collection<Long> adUnitIds, ItFeature itFeature) {

        // 空校验.
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(itFeature.getIts())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId ->
                            DataTable.of(UnitItIndex.class)
                                    .match(adUnitId, itFeature.getIts())
            );
        }
    }
}
