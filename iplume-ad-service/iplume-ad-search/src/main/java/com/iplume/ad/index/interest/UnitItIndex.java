package com.iplume.ad.index.interest;

import com.iplume.ad.index.IndexAware;
import com.iplume.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * UnitIt索引操作类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/11
 */
@Slf4j
@Component
public class UnitItIndex implements IndexAware<String, Set<Long>> {

    /**
     * 正向索引.
     * <itTag, adUnitId set>.
     */
    private static Map<String, Set<Long>> itUnitMap;

    /**
     * 反向索引.
     * <unitId, itTag set>.
     */
    private static Map<Long, Set<String>> unitItMap;

    static {
        itUnitMap = new ConcurrentHashMap<>();
        unitItMap = new ConcurrentHashMap<>();
    }

    /**
     * 获取unitId索引对象.
     *
     * @param key
     * @return
     */
    @Override
    public Set<Long> get(String key) {
        return itUnitMap.get(key);
    }

    /**
     * 添加兴趣索引.
     *
     * @param key
     * @param value
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public void add(String key, Set<Long> value) {

        log.info("UnitIndex: before add -> {}", unitItMap);

        // 先查询后添加.
        Set<Long> unitIds = CommonUtils.getOrCreate(
                key, itUnitMap, ConcurrentSkipListSet::new);
        unitIds.addAll(value);

        // 先遍历查询unitId对应的兴趣列表.
        for (Long unitId : value) {
            Set<String> itTags = CommonUtils.getOrCreate(unitId, unitItMap, ConcurrentSkipListSet::new);
            itTags.add(key);
        }
        log.info("UnitIndex: after add -> {}", unitItMap);
    }

    /**
     * 更新UnitItMap索引对象.
     *
     * @param key
     * @param value
     */
    @Override
    public void update(String key, Set<Long> value) {
        log.error("it index can not support update.");
    }

    /**
     * 删除unitIt兴趣索引.
     *
     * @param key
     * @param value
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public void delete(String key, Set<Long> value) {
        log.info("UnitItIndex, before delete -> {}", unitItMap);

        Set<Long> unitIds = CommonUtils.getOrCreate(key, itUnitMap, ConcurrentSkipListSet::new);
        unitIds.removeAll(value);

        for (Long unitId : value) {
            Set<String> itTags = CommonUtils.getOrCreate(unitId, unitItMap, ConcurrentSkipListSet::new);
            itTags.remove(key);
        }

        log.info("UnitItIndex, after delete -> {}", unitItMap);
    }

    /**
     * 兴趣索引匹配.
     *
     * @param unitId
     * @param itTags
     * @return
     */
    public boolean match(Long unitId, List<String> itTags) {

        // 存在校验.
        if (unitItMap.containsKey(unitId)
                && CollectionUtils.isNotEmpty(unitItMap.get(unitId))) {
            // 查询unitId对应的所有兴趣索引对象.
            Set<String> unitItTags = unitItMap.get(unitId);
            return CollectionUtils.isSubCollection(itTags, unitItTags);
        }

        return false;
    }
}
