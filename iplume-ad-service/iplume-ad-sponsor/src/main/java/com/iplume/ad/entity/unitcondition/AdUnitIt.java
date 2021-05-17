package com.iplume.ad.entity.unitcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * ad_unit_it实体类.
 * 
 * @description:
 * @author: lingchen
 * @date: 2021/4/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_unit_it")
public class AdUnitIt {

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 关联推广单元id.
     */
    @Basic
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    /**
     * 兴趣标签.
     */
    @Basic
    @Column(name = "it_tag", nullable = false)
    private String itTag;

    public AdUnitIt(Long unitId, String itTag) {
        this.unitId = unitId;
        this.itTag = itTag;
    }
}
