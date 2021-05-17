package com.iplume.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创意索引存储对象.
 *
 * @author: lingchen
 * @date: 2021/5/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdCreativeTable {

    /**
     * adId.
     */
    private Long adId;

    /**
     * 创意名称.
     */
    private String name;

    /**
     * 物料类型(图片,视频).
     */
    private Integer type;

    /**
     * 物料的类型, 比如图片可以是 bmp, jpg等等.
     */
    private Integer materialType;

    /**
     * 高度.
     */
    private Integer height;

    /**
     * 宽度.
     */
    private Integer width;

    /**
     * 审核状态.
     */
    private Integer auditStatus;

    /**
     * 物料地址.
     */
    private String adUrl;
}
