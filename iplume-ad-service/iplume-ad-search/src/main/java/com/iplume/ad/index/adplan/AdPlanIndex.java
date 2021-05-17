package com.iplume.ad.index.adplan;

import com.iplume.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AdPlan索引的实现类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/10
 */
@Slf4j
@Component
public class AdPlanIndex implements IndexAware<Long, AdPlanObject> {

    /**
     * AdPlan索引Map.
     */
    private static Map<Long, AdPlanObject> objectMap;

    static {
        // 线程安全的Map.
        objectMap = new ConcurrentHashMap<>();
    }

    /**
     * 获取AdPlan索引.
     *
     * @param key
     * @return
     */
    @Override
    public AdPlanObject get(Long key) {
        return objectMap.get(key);
    }

    /**
     * 创建AdPlan索引.
     *
     * @param key
     * @param value
     */
    @Override
    public void add(Long key, AdPlanObject value) {
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("after add: {}", objectMap);
    }

    /**
     * 更新AdPlan索引.
     *
     * @param key
     * @param value
     */
    @Override
    public void update(Long key, AdPlanObject value) {
        log.info("before update: {}", objectMap);
        AdPlanObject oldObject = objectMap.get(key);
        if (oldObject == null) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }
        log.info("after update: {}", objectMap);
    }

    /**
     * 删除AdPlan索引.
     *
     * @param key
     * @param value
     */
    @Override
    public void delete(Long key, AdPlanObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }
}
