package com.iplume.ad.index.adunit;

import com.iplume.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
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
