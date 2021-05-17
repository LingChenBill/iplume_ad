package com.iplume.ad.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 索引服务的缓存类.
 * <p>
 * ApplicationContextAware: 在springboot初期化时,缓存所有的索引.
 * PriorityOrdered: 设置优先级.
 *
 * @author: lingchen
 * @date: 2021/5/12
 */
@Component
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    private static ApplicationContext applicationContext;

    /**
     * 所有索引的缓存table Map.
     */
    private static final Map<Class, Object> DATA_TABLE_MAP = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 初期化applicationContext.
        DataTable.applicationContext = applicationContext;
    }

    /**
     * 设置最高的优先级.
     *
     * @return
     */
    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

    /**
     * 通过类名clazz来获取相应的索引对象.
     * 获取方式: DataTable.of(CreativeUnitIndex.class);
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T of(Class<T> clazz) {
        T instance = (T) DATA_TABLE_MAP.get(clazz);
        if (instance != null) {
            return instance;
        }

        // 若初次时,获取为空.将索引对象设置到dataTableMap中.
        DATA_TABLE_MAP.put(clazz, bean(clazz));

        return (T) DATA_TABLE_MAP.get(clazz);
    }

    /**
     * 通过bean的字符串名称来获取Bean.
     *
     * @param beanName
     * @param <T>
     * @return
     */
    private static <T> T bean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * 通过class来获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    private static <T> T bean(Class clazz) {
        return (T) applicationContext.getBean(clazz);
    }

}
