package com.iplume.ad.index.creative;

import com.iplume.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 创意索引实现类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/11
 */
@Slf4j
@Component
public class CreativeIndex implements IndexAware<Long, CreativeObject> {

    /**
     * 创意索引Map.
     */
    private static Map<Long, CreativeObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    /**
     * 查询创意索引.
     *
     * @param key
     * @return
     */
    @Override
    public CreativeObject get(Long key) {
        return objectMap.get(key);
    }

    /**
     * 添加创意索引对象.
     *
     * @param key
     * @param value
     */
    @Override
    public void add(Long key, CreativeObject value) {
        log.info("CreativeIndex: before add -> {}", objectMap);
        objectMap.put(key, value);
        log.info("CreativeIndex: after add -> {}", objectMap);
    }

    /**
     * 更新创意索引对象.
     *
     * @param key
     * @param value
     */
    @Override
    public void update(Long key, CreativeObject value) {
        log.info("CreativeIndex: before update -> {}", objectMap);

        CreativeObject oldObject = objectMap.get(key);
        if (oldObject == null) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }

        log.info("CreativeIndex: after update -> {}", objectMap);
    }

    /**
     * 删除创意索引对象.
     *
     * @param key
     * @param value
     */
    @Override
    public void delete(Long key, CreativeObject value) {
        log.info("CreativeIndex: before delete -> {}", objectMap);
        objectMap.remove(key);
        log.info("CreativeIndex: after delete -> {}", objectMap);
    }
}
