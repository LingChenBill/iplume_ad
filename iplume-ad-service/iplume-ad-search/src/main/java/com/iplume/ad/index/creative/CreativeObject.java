package com.iplume.ad.index.creative;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 创意索引对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeObject {

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

    /**
     * 更新创意索引对象.
     *
     * @param newObject
     */
    public void update(CreativeObject newObject) {
        if (newObject.getAdId() != null) {
            this.adId = newObject.getAdId();
        }

        if (newObject.getName() != null) {
            this.name = newObject.getName();
        }

        if (newObject.getType() != null) {
            this.type = newObject.getType();
        }

        if (newObject.getMaterialType() != null) {
            this.materialType = newObject.getMaterialType();
        }

        if (newObject.getHeight() != null) {
            this.height = newObject.getHeight();
        }

        if (newObject.getWidth() != null) {
            this.width = newObject.getWidth();
        }

        if (newObject.getAuditStatus() != null) {
            this.auditStatus = newObject.getAuditStatus();
        }

        if (newObject.getAdUrl() != null) {
            this.adUrl = newObject.getAdUrl();
        }
    }

}
