package com.iplume.ad.mysql.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Binlog解析常量定义类.
 *
 * @author: lingchen
 * @date: 2021/5/16
 */
public class Constant {

    /**
     * 数据库名.
     */
    private static final String DB_NAME = "iplume_ad";

    /**
     * 表名映射Map.
     */
    public static Map<String, String> table2db;

    static {
        table2db = new HashMap<>();

        // 表名与db的映射设定.
        table2db.put(AdPlanTableInfo.TABLE_NAME, DB_NAME);
        table2db.put(AdCreativeTableInfo.TABLE_NAME, DB_NAME);
        table2db.put(AdUnitTableInfo.TABLE_NAME, DB_NAME);
        table2db.put(AdCreativeUnitTableInfo.TABLE_NAME, DB_NAME);
        table2db.put(AdUnitDistrictTableInfo.TABLE_NAME, DB_NAME);
        table2db.put(AdUnitItTableInfo.TABLE_NAME, DB_NAME);
        table2db.put(AdUnitKeywordTableInfo.TABLE_NAME, DB_NAME);
    }

    /**
     * AdPlan表.
     */
    public static class AdPlanTableInfo {
        /**
         * 表名.
         */
        public static final String TABLE_NAME = "ad_plan";
        /**
         * ID.
         */
        public static final String COLUMN_ID = "id";
        /**
         * 标记当前记录所属用户.
         */
        public static final String COLUMN_USER_ID = "user_id";
        /**
         * 推广计划状态.
         */
        public static final String COLUMN_PLAN_STATUS = "plan_status";
        /**
         * 推广计划开始时间.
         */
        public static final String COLUMN_START_DATE = "start_date";
        /**
         * 推广计划结束时间.
         */
        public static final String COLUMN_END_DATE = "end_date";
    }

    /**
     * AdCreative表.
     */
    public static class AdCreativeTableInfo {
        /**
         * 表名.
         */
        public static final String TABLE_NAME = "ad_creative";
        /**
         * ID.
         */
        public static final String COLUMN_ID = "id";
        /**
         * 物料类型(图片,视频).
         */
        public static final String COLUMN_TYPE = "type";
        /**
         * 物料的类型, 比如图片可以是 bmp, jpg等等.
         */
        public static final String COLUMN_MATERIAL_TYPE = "material_type";
        /**
         * 高度.
         */
        public static final String COLUMN_HEIGHT = "height";
        /**
         * 宽度.
         */
        public static final String COLUMN_WIDTH = "width";
        /**
         * 审核状态.
         */
        public static final String COLUMN_AUDIT_STATUS = "audit_status";
        /**
         * 物料地址.
         */
        public static final String COLUMN_URL = "url";
    }

    /**
     * AdUnit表.
     */
    public static class AdUnitTableInfo {
        /**
         * 表名.
         */
        public static final String TABLE_NAME = "ad_unit";
        /**
         * id.
         */
        public static final String COLUMN_ID = "id";
        /**
         * 推广单元状态.
         */
        public static final String COLUMN_UNIT_STATUS = "unit_status";
        /**
         * 广告位类型(开屏, 贴片, 中贴...).
         */
        public static final String COLUMN_POSITION_TYPE = "position_type";
        /**
         * 关联推广计划id.
         */
        public static final String COLUMN_PLAN_ID = "plan_id";
    }

    /**
     * AdCreativeUnit表.
     */
    public static class AdCreativeUnitTableInfo {
        /**
         * 表名.
         */
        public static final String TABLE_NAME = "creative_unit";
        /**
         * 关联创意Id.
         */
        public static final String COLUMN_CREATIVE_ID = "creative_id";
        /**
         * 关联推广单元Id.
         */
        public static final String COLUMN_UNIT_ID = "unit_id";
    }

    /**
     * AdUnitDistrict表.
     */
    public static class AdUnitDistrictTableInfo {
        /**
         * 表名.
         */
        public static final String TABLE_NAME = "ad_unit_district";
        /**
         * 关联推广单元id.
         */
        public static final String COLUMN_UNIT_ID = "unit_id";
        /**
         * 省.
         */
        public static final String COLUMN_PROVINCE = "province";
        /**
         * 市.
         */
        public static final String COLUMN_CITY = "city";
    }

    /**
     * AdUnitIt表.
     */
    public static class AdUnitItTableInfo {
        /**
         * 表名.
         */
        public static final String TABLE_NAME = "ad_unit_it";
        /**
         * 关联推广单元id.
         */
        public static final String COLUMN_UNIT_ID = "unit_id";
        /**
         * 兴趣标签.
         */
        public static final String COLUMN_IT_TAG = "it_tag";
    }

    /**
     * AdUnitKeyword表.
     */
   public static class AdUnitKeywordTableInfo {

        /**
         * 表名.
         */
        public static final String TABLE_NAME = "ad_unit_keyword";
        /**
         * 关联推广单元id.
         */
        public static final String COLUMN_UNIT_ID = "unit_id";
        /**
         * 关键词.
         */
        public static final String COLUMN_KEYWORD = "keyword";
    }

}

