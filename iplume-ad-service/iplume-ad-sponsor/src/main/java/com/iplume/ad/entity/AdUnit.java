package com.iplume.ad.entity;

import com.iplume.ad.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * ad_unit实体类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_unit")
public class AdUnit {

    /**
     * id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 关联推广计划id.
     */
    @Basic
    @Column(name = "plan_id", nullable = false)
    private Long planId;

    /**
     * 推广单元名称.
     */
    @Basic
    @Column(name = "unit_name", nullable = false)
    private String unitName;

    /**
     * 推广单元状态.
     */
    @Basic
    @Column(name = "unit_status", nullable = false)
    private Integer unitStatus;

    /**
     * 广告位类型(开屏, 贴片, 中贴...).
     */
    @Basic
    @Column(name = "position_type", nullable = false)
    private Integer positionType;

    /**
     * 预算.
     */
    @Basic
    @Column(name = "budget", nullable = false)
    private Long budget;

    /**
     * 创建时间.
     */
    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /**
     * 更新时间.
     */
    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public AdUnit(Long planId, String unitName, Integer positionType, Long budget) {
        this.planId = planId;
        this.unitName = unitName;
        this.unitStatus = CommonStatus.VALID.getStatus();
        this.positionType = positionType;
        this.budget = budget;
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }

}
