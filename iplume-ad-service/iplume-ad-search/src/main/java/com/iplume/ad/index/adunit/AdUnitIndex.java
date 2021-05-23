package com.iplume.ad.index.adunit;

import com.iplume.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AdUnit索引处理类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/10
 */
@Slf4j
@Component
public class AdUnitIndex implements IndexAware<Long, AdUnitObject> {

    /**
     * AdUnit索引Map.
     */
    private static Map<Long, AdUnitObject> objectMap;

    static {
        // 线程安全的Map.
        objectMap = new ConcurrentHashMap<>();
    }

    /**
     * 根据广告物料来匹配广告单元Id集合.
     *
     * @param positionType 物料类型.
     * @return 广告单元Id集合.
     */
    public Set<Long> match(Integer positionType) {

        Set<Long> adUnitIds = new HashSet<>();

        // 广告单元Id筛选.
        objectMap.forEach(
                (k, v) -> {
                    if (AdUnitObject.isAdSlotTypeOk(positionType, v.getPositionType())) {
                        adUnitIds.add(k);
                    }
                }
        );

        return adUnitIds;
    }

    /**
     * 通过广告单元Ids来获取AdUnit索引对象列表.
     *
     * @param adUnitIds 广告单元Ids.
     * @return AdUnit索引对象列表.
     */
    public List<AdUnitObject> fetch(Collection<Long> adUnitIds) {

        // 广告单元Id集合检验.
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return Collections.emptyList();
        }

        // AdUnit索引对象列表.
        List<AdUnitObject> unitObjects = new ArrayList<>();

        // 列表装配.
        adUnitIds.forEach(u -> {
            AdUnitObject object = get(u);
            if (object == null) {
                log.error("AdUnitObject not found: {}", u);
                return;
            }

            unitObjects.add(object);
        });

        return unitObjects;
    }

    /**
     * 获取AdUnit索引对象.
     *
     * @param key
     * @return
     */
    @Override
    public AdUnitObject get(Long key) {
        return objectMap.get(key);
    }

    /**
     * 创建AdUnit索引对象.
     *
     * @param key
     * @param value
     */
    @Override
    public void add(Long key, AdUnitObject value) {
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("after add: {}", objectMap);
    }

    /**
     * 更新AdUnit索引对象.
     *
     * @param key
     * @param value
     */
    @Override
    public void update(Long key, AdUnitObject value) {
        log.info("before update: {}", objectMap);
        AdUnitObject oldObject = objectMap.get(key);
        if (oldObject == null) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }
        log.info("after update: {}", objectMap);
    }

    /**
     * 删除AdUint索引对象.
     *
     * @param key
     * @param value
     */
    @Override
    public void delete(Long key, AdUnitObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }
}
