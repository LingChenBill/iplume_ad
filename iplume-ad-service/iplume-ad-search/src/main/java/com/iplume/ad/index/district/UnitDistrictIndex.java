package com.iplume.ad.index.district;

import com.iplume.ad.index.IndexAware;
import com.iplume.ad.search.vo.feature.DistrictFeature;
import com.iplume.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * 区域限制索引操作类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/11
 */
@Slf4j
@Component
public class UnitDistrictIndex implements IndexAware<String, Set<Long>> {

    /**
     * 区域限制 -> unitIds索引Map.
     */
    private static Map<String, Set<Long>> districtUnitMap;

    /**
     * unitId -> 区域限制索引Map.
     */
    private static Map<Long, Set<String>> unitDistrictMap;

    static {
        districtUnitMap = new ConcurrentHashMap<>();
        unitDistrictMap = new ConcurrentHashMap<>();
    }

    /**
     * 查询区域限制索引.
     *
     * @param key
     * @return
     */
    @Override
    public Set<Long> get(String key) {
        return districtUnitMap.get(key);
    }

    /**
     * 添加区域限制索引.
     *
     * @param key
     * @param value
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public void add(String key, Set<Long> value) {

        log.info("UnitDistrictIndex: before add -> {}", unitDistrictMap);

        Set<Long> unitIds = CommonUtils.getOrCreate(
                key,
                districtUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.addAll(value);

        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getOrCreate(
                    unitId,
                    unitDistrictMap,
                    ConcurrentSkipListSet::new
            );
            districts.add(key);
        }

        log.info("UnitDistrictIndex: after add -> {}", unitDistrictMap);
    }

    /**
     * 更新区域限制索引.
     *
     * @param key
     * @param value
     */
    @Override
    public void update(String key, Set<Long> value) {
        log.error("district index can not support update.");
    }

    /**
     * 删除区域限制索引.
     *
     * @param key
     * @param value
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public void delete(String key, Set<Long> value) {

        log.info("UnitDistrictIndex: before delete -> {}", unitDistrictMap);

        Set<Long> unitIds = CommonUtils.getOrCreate(
                key,
                districtUnitMap,
                ConcurrentSkipListSet::new
        );
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> districts = CommonUtils.getOrCreate(
                    unitId,
                    unitDistrictMap,
                    ConcurrentSkipListSet::new
            );
            districts.remove(key);
        }

        log.info("UnitDistrictIndex: after delete -> {}", unitDistrictMap);
    }

    /**
     * 校验是否匹配.
     *
     * @param adUnitId  广告单元Id.
     * @param districts 所在省和城市对象.
     * @return 匹配结果.
     */
    public boolean match(Long adUnitId, List<DistrictFeature.ProvinceAndCity> districts) {

        if (unitDistrictMap.containsKey(adUnitId)
                && CollectionUtils.isNotEmpty(districts)) {

            // 筛选出adUnitId所对应的province-city列表.
            Set<String> unitDistricts = unitDistrictMap.get(adUnitId);

            List<String> targetDistricts = districts.stream().map(
                    // 组合key: province-city.
                    d -> CommonUtils.stringConcat(
                            d.getProvince(),
                            d.getCity()
                    )
            ).collect(Collectors.toList());

            return CollectionUtils.isSubCollection(targetDistricts, unitDistricts);
        }

        return false;
    }
}
