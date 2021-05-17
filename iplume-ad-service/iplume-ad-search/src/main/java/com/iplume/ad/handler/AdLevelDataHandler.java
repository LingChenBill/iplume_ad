package com.iplume.ad.handler;

import com.alibaba.fastjson.JSON;
import com.iplume.ad.dump.table.*;
import com.iplume.ad.index.DataTable;
import com.iplume.ad.index.IndexAware;
import com.iplume.ad.index.adplan.AdPlanIndex;
import com.iplume.ad.index.adplan.AdPlanObject;
import com.iplume.ad.index.adunit.AdUnitIndex;
import com.iplume.ad.index.adunit.AdUnitObject;
import com.iplume.ad.index.creative.CreativeIndex;
import com.iplume.ad.index.creative.CreativeObject;
import com.iplume.ad.index.creativeunit.CreativeUnitIndex;
import com.iplume.ad.index.creativeunit.CreativeUnitObject;
import com.iplume.ad.index.district.UnitDistrictIndex;
import com.iplume.ad.index.interest.UnitItIndex;
import com.iplume.ad.index.keyword.UnitKeyWordIndex;
import com.iplume.ad.mysql.constant.OpType;
import com.iplume.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 索引对象的全量和增量的处理类.
 * <p>
 * 1.索引之间存在着层级的划分, 也就是依赖关系的划分.
 * 2.加载全量索引其实是增量索引 "添加" 的一种特殊实现.
 * 索引不与其它索引相关联: level2.
 * 索引与其它索引相关联: level3.
 * 索引与AdUnit索引相关联: level4.
 *
 * @author: lingchen
 * @date: 2021/5/14
 */
@Slf4j
public class AdLevelDataHandler {

    /**
     * AdPlanIndex索引处理方法(全量处理,不与其它索引有关联,设置为level2).
     *
     * @param planTable AdPlan索引存储对象.
     * @param type      处理类型.
     */
    public static void handleLevel2(AdPlanTable planTable, OpType type) {

        // planTable -> planObject装载.
        AdPlanObject planObject = new AdPlanObject(
                planTable.getId(),
                planTable.getUserId(),
                planTable.getPlanStatus(),
                planTable.getStartDate(),
                planTable.getEndDate()
        );

        // 索引对象的处理.
        handleBinlogEvent(
                DataTable.of(AdPlanIndex.class),
                planObject.getPlanId(),
                planObject,
                type
        );
    }

    /**
     * AdCreativeIndex索引处理方法.
     *
     * @param creativeTable 创意索引存储对象.
     * @param type          处理类型.
     */
    public static void handleLevel2(AdCreativeTable creativeTable, OpType type) {

        // creativeTable -> creativeObject装配.
        CreativeObject creativeObject = new CreativeObject(
                creativeTable.getAdId(),
                creativeTable.getName(),
                creativeTable.getType(),
                creativeTable.getMaterialType(),
                creativeTable.getHeight(),
                creativeTable.getWidth(),
                creativeTable.getAuditStatus(),
                creativeTable.getAdUrl()
        );

        // 索引对象的处理.
        handleBinlogEvent(
                DataTable.of(CreativeIndex.class),
                creativeObject.getAdId(),
                creativeObject,
                type
        );
    }

    /**
     * AdUnitIndex索引处理方法.
     *
     * @param unitTable AdUnit索引存储对象.
     * @param type      处理类型.
     */
    public static void handleLevel3(AdUnitTable unitTable, OpType type) {

        // 关联的AdPlan索引校验.
        AdPlanObject planObject = DataTable.of(AdPlanIndex.class).get(unitTable.getPlanId());
        if (planObject == null) {
            log.error("handleLevel3 found AdPlanObject error: {}", unitTable.getPlanId());
            return;
        }

        // unitTable -> unitObject装载.
        AdUnitObject unitObject = new AdUnitObject(
                unitTable.getUnitId(),
                unitTable.getUnitStatus(),
                unitTable.getPositionType(),
                unitTable.getPlanId(),
                planObject
        );

        // 索引操作处理.
        handleBinlogEvent(
                DataTable.of(AdUnitIndex.class),
                unitObject.getUnitId(),
                unitObject,
                type
        );
    }

    /**
     * AdCreativeUnitIndex索引处理方法.
     *
     * @param creativeUnitTable 创意与推广单元的索引存储对象.
     * @param type              处理类型.
     */
    public static void handleLevel3(AdCreativeUnitTable creativeUnitTable, OpType type) {

        // 更新操作不处理.
        if (type == OpType.UPATE) {
            log.error("CreativeUnitIndex can not support update.");
            return;
        }

        // 关联索引校验.
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(creativeUnitTable.getUnitId());
        CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(creativeUnitTable.getAdId());
        if (unitObject == null || creativeObject == null) {
            log.error("AdCreativeUnitTable index error: {}", JSON.toJSONString(creativeUnitTable));
            return;
        }

        // creativeUnitTable -> creativeUnitObject装配.
        CreativeUnitObject creativeUnitObject = new CreativeUnitObject(
                creativeUnitTable.getAdId(),
                creativeUnitTable.getUnitId()
        );

        // 索引操作处理.
        handleBinlogEvent(
                DataTable.of(CreativeUnitIndex.class),
                CommonUtils.stringConcat(
                        creativeUnitTable.getAdId().toString(),
                        creativeUnitTable.getUnitId().toString()
                ),
                creativeUnitObject,
                type
        );
    }

    /**
     * AdUnitDistrictIndex索引处理方法.
     *
     * @param unitDistrictTable 地域限制索引存储对象.
     * @param type              处理类型.
     */
    public static void handleLevel4(AdUnitDistrictTable unitDistrictTable, OpType type) {

        // 更新操作不处理.
        if (type == OpType.UPATE) {
            log.error("District index can not support update.");
            return;
        }

        // 关联索引校验.
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitDistrictTable.getUnitId());
        if (unitObject == null) {
            log.error("AdUnitDistrictTable index error: {}", unitDistrictTable.getUnitId());
            return;
        }

        // key.
        String key = CommonUtils.stringConcat(
                unitDistrictTable.getProvince(),
                unitDistrictTable.getCity()
        );
        // value.
        Set<Long> value = new HashSet<>(
                Collections.singleton(unitDistrictTable.getUnitId())
        );

        // 索引操作.
        handleBinlogEvent(
                DataTable.of(UnitDistrictIndex.class),
                key,
                value,
                type
        );
    }

    /**
     * AdUnitItIndex索引处理方法.
     *
     * @param unitItTable UnitIt索引存储对象.
     * @param type        处理类型.
     */
    public static void handleLevel4(AdUnitItTable unitItTable, OpType type) {

        // 索引更新不支持.
        if (type == OpType.UPATE) {
            log.error("UnitIt index can not support update.");
            return;
        }

        // 关联索引校验.
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitItTable.getUnitId());
        if (unitObject == null) {
            log.error("AdUnitItTable index error, {}", unitItTable.getUnitId());
            return;
        }

        // value.
        Set<Long> value = new HashSet<>(
                Collections.singleton(unitItTable.getUnitId())
        );

        // 索引处理.
        handleBinlogEvent(
                DataTable.of(UnitItIndex.class),
                unitItTable.getItTag(),
                value,
                type
        );
    }

    /**
     * AdUnitKeywordIndex索引处理方法.
     *
     * @param keywordTable UnitKeyWord索引存储对象.
     * @param type         处理类型.
     */
    public static void handleLevel4(AdUnitKeywordTable keywordTable, OpType type) {

        // 更新操作不支持.
        if (type == OpType.UPATE) {
            log.error("UnitKeyword index can not support update.");
            return;
        }

        // 关联Unit索引校验.
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(keywordTable.getUnitId());
        if (unitObject == null) {
            log.error("AdUnitKeywordTable index error, {}", keywordTable.getUnitId());
            return;
        }

        // value: unitId.
        Set<Long> value = new HashSet<>(
                Collections.singleton(keywordTable.getUnitId())
        );

        // 索引处理.
        handleBinlogEvent(
                DataTable.of(UnitKeyWordIndex.class),
                keywordTable.getKeyword(),
                value,
                type
        );
    }

    /**
     * 利用IndexAware接口类来实现对索引的操作处理.
     *
     * @param index 具体的索引处理Index对象.
     * @param key   索引key.
     * @param value 索引存储对象.
     * @param type  处理类型.
     * @param <K>   key.
     * @param <V>   value.
     */
    private static <K, V> void handleBinlogEvent(
            IndexAware<K, V> index,
            K key,
            V value,
            OpType type) {

        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case UPATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;
        }

    }
}
