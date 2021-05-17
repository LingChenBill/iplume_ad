package com.iplume.ad.index;

/**
 * 索引的增删改查操作接口.
 *
 * @description: 使用泛型: K -> 索引key, V -> 返回值.
 * @author: lingchen
 * @date: 2021/5/10
 */
public interface IndexAware<K, V> {

    /**
     * 获取索引(按key查询).
     *
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 创建索引(key和value).
     *
     * @param key
     * @param value
     */
    void add(K key, V value);

    /**
     * 更新索引(按key更新).
     *
     * @param key
     * @param value
     */
    void update(K key, V value);

    /**
     * 删除索引(按key删除).
     *
     * @param key
     * @param value
     */
    void delete(K key, V value);

}
