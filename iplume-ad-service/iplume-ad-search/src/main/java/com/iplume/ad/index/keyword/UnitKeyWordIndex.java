package com.iplume.ad.index.keyword;

import com.iplume.ad.index.IndexAware;
import com.iplume.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * UnitKeyWord索引实现类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/10
 */
@Slf4j
@Component
public class UnitKeyWordIndex implements IndexAware<String, Set<Long>> {

    /**
     * keyword -> Unit的索引Map.
     */
    private static Map<String, Set<Long>> keywordUnitMap;

    /**
     * unit -> keyword的索引Map(正向索引).
     */
    private static Map<Long, Set<String>> unitKeywordMap;

    static {
        keywordUnitMap = new ConcurrentHashMap<>();
        unitKeywordMap = new ConcurrentHashMap<>();
    }

    /**
     * 获取推广单元关键词索引.
     *
     * @param key
     * @return
     */
    @Override
    public Set<Long> get(String key) {

        // 判断key是否为空.空则返回空set.
        if (StringUtils.isEmpty(key)) {
            return Collections.emptySet();
        }

        Set<Long> result = keywordUnitMap.get(key);
        if (result == null) {
            return Collections.emptySet();
        }

        return result;
    }

    /**
     * 创建unitKeyword索引对象.
     *
     * @param key
     * @param value
     */
    @Override
    public void add(String key, Set<Long> value) {

        log.info("UnitKeyWordIndex, before add: {}", unitKeywordMap);

        // 获取key对应的unitIds.不存在是返回空的Set<Long>集合.
        Set<Long> unitIdSet = CommonUtils.getOrCreate(key, keywordUnitMap, ConcurrentSkipListSet::new);
        unitIdSet.addAll(value);

        // 遍历unitId的set集合来添加对应的key索引.
        for (Long unitId : value) {
            Set<String> keywordSet = CommonUtils.getOrCreate(unitId, unitKeywordMap, ConcurrentSkipListSet::new);
            keywordSet.add(key);
        }

        log.info("UnitKeyWordIndex, after add: {}", unitKeywordMap);
    }

    /**
     * 更新操作(过于复杂,不利于直接更新,可以先删除,再添加).
     *
     * @param key
     * @param value
     */
    @Override
    public void update(String key, Set<Long> value) {
        log.error("keyword index can not support update!");
    }

    /**
     * 删除keyword索引.
     *
     * @param key
     * @param value
     */
    @Override
    public void delete(String key, Set<Long> value) {

        log.info("UnitKeywordIndex, before delete: {}", unitKeywordMap);

        // 先查询再删除.
        Set<Long> unitIds = CommonUtils.getOrCreate(key, keywordUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> keywordSet = CommonUtils.getOrCreate(unitId, unitKeywordMap, ConcurrentSkipListSet::new);
            keywordSet.remove(key);
        }

        log.info("UnitKeywordIndex, after delete: {}", unitKeywordMap);
    }

    /**
     * 是否匹配unitId和keywords.
     *
     * @param unitId
     * @param keywords
     * @return
     */
    public boolean match(Long unitId, List<String> keywords) {

        // 包含校验.
        if (unitKeywordMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitKeywordMap.get(unitId))) {

            Set<String> unitKeywords = unitKeywordMap.get(unitId);
            // 判断keywords是否是unitKeywords的子集.
            return CollectionUtils.isSubCollection(keywords, unitKeywords);
        }

        return false;
    }
}
