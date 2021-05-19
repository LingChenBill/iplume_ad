package com.iplume.ad.sender.index;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iplume.ad.dump.table.AdCreativeTable;
import com.iplume.ad.dump.table.AdCreativeUnitTable;
import com.iplume.ad.dump.table.AdPlanTable;
import com.iplume.ad.dump.table.AdUnitTable;
import com.iplume.ad.handler.AdLevelDataHandler;
import com.iplume.ad.index.DataLevel;
import com.iplume.ad.mysql.constant.Constant;
import com.iplume.ad.mysql.dto.MysqlRowData;
import com.iplume.ad.sender.ISender;
import com.iplume.ad.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 增量数据投递类.
 *
 * @author: lingchen
 * @date: 2021/5/19
 */
@Slf4j
@Component("indexSender")
public class IndexSender implements ISender {

    /**
     * 消息投递方法.
     *
     * @param rowData 消息数据.
     */
    @Override
    public void sender(MysqlRowData rowData) {

        // 数据层级.
        String level = rowData.getLevel();

        if (DataLevel.LEVEL2.getLevel().equals(level)) {
            // 第二层级处理方法.
            handleLevel2RowData(rowData);
        } else if (DataLevel.LEVEL3.getLevel().equals(level)) {
            // 第三层级处理方法.
            handleLevel3RowData(rowData);
        } else if (DataLevel.LEVEL4.getLevel().equals(level)) {
            // 第四层级处理方法.
        } else {
            log.error("MysqlRowData error: {}", JSON.toJSONString(rowData));
        }
    }



    /**
     * 第二层级数据类型处理.
     *
     * @param rowData 消息数据.
     */
    @SuppressWarnings("AlibabaMethodTooLong")
    private void handleLevel2RowData(MysqlRowData rowData) {

        // AdPlanTable处理.
        if (Constant.AdPlanTableInfo.TABLE_NAME.equals(rowData.getTableName())) {

            // AdPlan的索引存储对象.
            List<AdPlanTable> adPlanTables = new ArrayList<>();
            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {

                AdPlanTable adPlanTable = new AdPlanTable();

                // 针对表的每个字段进行处理.
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AdPlanTableInfo.COLUMN_ID:
                            adPlanTable.setId(Long.valueOf(v));
                            break;
                        case Constant.AdPlanTableInfo.COLUMN_USER_ID:
                            adPlanTable.setUserId(Long.valueOf(v));
                            break;
                        case Constant.AdPlanTableInfo.COLUMN_PLAN_STATUS:
                            adPlanTable.setPlanStatus(Integer.valueOf(v));
                            break;
                        case Constant.AdPlanTableInfo.COLUMN_START_DATE:
                            adPlanTable.setStartDate(CommonUtils.parseDateString(v));
                            break;
                        case Constant.AdPlanTableInfo.COLUMN_END_DATE:
                            adPlanTable.setEndDate(CommonUtils.parseDateString(v));
                            break;
                        default:
                            break;
                    }
                });

                adPlanTables.add(adPlanTable);
            }

            // 第二层级数据进行处理.
            adPlanTables.forEach(p -> AdLevelDataHandler.handleLevel2(p, rowData.getType()));

        } else if (Constant.AdCreativeTableInfo.TABLE_NAME.equals(rowData.getTableName())) {

            // 创意索引存储对象.
            List<AdCreativeTable> creativeTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {

                AdCreativeTable creativeTable = new AdCreativeTable();

                // 针对表的每个字段进行处理.
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AdCreativeTableInfo.COLUMN_ID:
                            creativeTable.setAdId(Long.valueOf(v));
                            break;
                        case Constant.AdCreativeTableInfo.COLUMN_TYPE:
                            creativeTable.setType(Integer.valueOf(v));
                            break;
                        case Constant.AdCreativeTableInfo.COLUMN_MATERIAL_TYPE:
                            creativeTable.setMaterialType(Integer.valueOf(v));
                            break;
                        case Constant.AdCreativeTableInfo.COLUMN_AUDIT_STATUS:
                            creativeTable.setAuditStatus(Integer.valueOf(v));
                            break;
                        case Constant.AdCreativeTableInfo.COLUMN_HEIGHT:
                            creativeTable.setHeight(Integer.valueOf(v));
                            break;
                        case Constant.AdCreativeTableInfo.COLUMN_WIDTH:
                            creativeTable.setWidth(Integer.valueOf(v));
                            break;
                        case Constant.AdCreativeTableInfo.COLUMN_URL:
                            creativeTable.setAdUrl(v);
                            break;
                        default:
                            break;
                    }
                });

                creativeTables.add(creativeTable);
            }

            // 第二层级数据进行处理.
            creativeTables.forEach(c -> AdLevelDataHandler.handleLevel2(c, rowData.getType()));

        } else {
            // 不进行处理.
            log.debug("HandleLevel2RowData error: {}", rowData);
        }
    }

    /**
     * 第三层级数据类型处理.
     *
     * @param rowData 消息数据,
     */
    private void handleLevel3RowData(MysqlRowData rowData) {

        // AdUnit表.
        if (Constant.AdUnitTableInfo.TABLE_NAME.equals(rowData.getTableName())) {

            List<AdUnitTable> unitTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {

                // AdUnit索引存储对象.
                AdUnitTable unitTable = new AdUnitTable();

                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AdUnitTableInfo.COLUMN_ID:
                            unitTable.setUnitId(Long.valueOf(v));
                            break;
                        case Constant.AdUnitTableInfo.COLUMN_PLAN_ID:
                            unitTable.setPlanId(Long.valueOf(v));
                            break;
                        case Constant.AdUnitTableInfo.COLUMN_POSITION_TYPE:
                            unitTable.setPositionType(Integer.valueOf(v));
                            break;
                        case Constant.AdUnitTableInfo.COLUMN_UNIT_STATUS:
                            unitTable.setUnitStatus(Integer.valueOf(v));
                            break;
                        default:
                            break;
                    }
                });

                unitTables.add(unitTable);
            }

            // 第三层级数据处理.
            unitTables.forEach(u -> AdLevelDataHandler.handleLevel3(u, rowData.getType()));

        } else if (Constant.AdCreativeUnitTableInfo.TABLE_NAME.equals(rowData.getTableName())) {

            List<AdCreativeUnitTable> creativeUnitTables = new ArrayList<>();

            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {

                // 创意与推广单元的索引存储对象.
                AdCreativeUnitTable creativeUnitTable = new AdCreativeUnitTable();

                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.AdCreativeUnitTableInfo.COLUMN_UNIT_ID:
                            creativeUnitTable.setUnitId(Long.valueOf(v));
                            break;
                        case Constant.AdCreativeUnitTableInfo.COLUMN_CREATIVE_ID:
                            creativeUnitTable.setAdId(Long.valueOf(v));
                            break;
                        default:
                            break;
                    }
                });

                creativeUnitTables.add(creativeUnitTable);
            }

            // 第三层级数据处理.
            creativeUnitTables.forEach(c -> AdLevelDataHandler.handleLevel3(c, rowData.getType()));
        } else {
            // 不进行处理.
            log.debug("HandleLevel3RowData error, {}", JSON.toJSONString(rowData));
        }
    }
}
