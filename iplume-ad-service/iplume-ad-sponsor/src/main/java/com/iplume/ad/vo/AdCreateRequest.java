package com.iplume.ad.vo;

import com.iplume.ad.constant.CommonStatus;
import com.iplume.ad.entity.Creative;

import java.util.Date;

/**
 * 创意请求对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/7
 */
public class AdCreateRequest {

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
     * 物料大小.
     */
    private Long size;

    /**
     * 持续时长, 只有视频不为0.
     */
    private Integer duration;

    /**
     * 标记当前所属用户.
     */
    private Long userId;

    /**
     * 物料地址.
     */
    private String url;

    /**
     * 请求对象转换成Entity.
     *
     * @return
     */
    public Creative convert2Entity() {
        Creative creative = new Creative();
        // 创意名称.
        creative.setName(this.name);
        // 物料类型.
        creative.setType(this.type);
        // 物料的类型.
        creative.setMaterialType(this.materialType);
        // 高度.
        creative.setHeight(this.height);
        // 宽度,
        creative.setWidth(this.width);
        // 物料大小.
        creative.setSize(this.size);
        // 持续时长.
        creative.setDuration(this.duration);
        // 审核状态.
        creative.setAuditStatus(CommonStatus.VALID.getStatus());
        // 标记当前所属用户.
        creative.setUserId(this.userId);
        // 物料地址.
        creative.setUrl(this.url);
        // 创建时间.
        creative.setCreateTime(new Date());
        // 更新时间.
        creative.setUpdateTime(creative.getCreateTime());

        return creative;
    }
}
