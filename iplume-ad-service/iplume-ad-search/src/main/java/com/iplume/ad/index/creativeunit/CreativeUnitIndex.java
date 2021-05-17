package com.iplume.ad.index.creativeunit;

import com.iplume.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 创意与推广单元的索引操作类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/12
 */
@Slf4j
@Component
public class CreativeUnitIndex implements IndexAware<String, CreativeUnitObject> {

    /**
     * <adId-unitId, CreativeUnitObject>.
     */
    private static Map<String, CreativeUnitObject> objectMap;

    /**
     * <adId, unitId Set>.
     */
    private static Map<Long, Set<Long>> creativeUnitMap;

    /**
     * <unitId, adId Set>.
     */
    private static Map<Long, Set<Long>> unitCreativeMap;

    static {
        objectMap = new ConcurrentHashMap<>();
        creativeUnitMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }

    /**
     * 查询创意与推广单元索引对象.
     *
     * @param key
     * @return
     */
    @Override
    public CreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    /**
     * 添加创意与推广单元索引对象.
     *
     * @param key
     * @param value
     */
    @Override
    public void add(String key, CreativeUnitObject value) {

        log.info("CreativeUnitIndex: before add -> {}", objectMap);

        objectMap.put(key, value);

        // 添加creativeUnitMap的索引对象.
        Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
        if (CollectionUtils.isEmpty(unitSet)) {
            unitSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getAdId(), unitSet);
        }
        unitSet.add(value.getUnitId());

        // 添加unitCreativeMap的索引对象.
        Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isEmpty(creativeSet)) {
            creativeSet = new ConcurrentSkipListSet<>();
            unitCreativeMap.put(value.getUnitId(), creativeSet);
        }
        creativeSet.add(value.getAdId());

        log.info("CreativeUnitIndex: after add -> {}", objectMap);
    }

    /**
     * 更新创意与推广单元的索引对象.
     *
     * @param key
     * @param value
     */
    @Override
    public void update(String key, CreativeUnitObject value) {
        log.error("CreativeUnitIndex can not support update.");
    }

    /**
     * 删除创意与推广单元的索引对象.
     *
     * @param key
     * @param value
     */
    @Override
    public void delete(String key, CreativeUnitObject value) {

        log.info("CreativeUnitIndex: before delete -> {}", objectMap);

        objectMap.remove(key);

        Set<Long> unitSet = creativeUnitMap.get(value.getAdId());
        if (CollectionUtils.isNotEmpty(unitSet)) {
            unitSet.remove(value.getUnitId());
        }

        Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isNotEmpty(creativeSet)) {
            creativeSet.remove(value.getAdId());
        }

        log.info("CreativeUnitIndex: after delete -> {}", objectMap);
    }
}
