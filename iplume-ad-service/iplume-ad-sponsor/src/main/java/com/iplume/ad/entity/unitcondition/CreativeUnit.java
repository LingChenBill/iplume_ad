package com.iplume.ad.entity.unitcondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * creative_unit实体类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "creative_unit")
public class CreativeUnit {

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 关联创意Id.
     */
    @Basic
    @Column(name = "creative_id", nullable = false)
    private Long creativeId;

    /**
     * 关联推广单元Id.
     */
    @Basic
    @Column(name = "unit_id", nullable = false)
    private Long unitId;

    public CreativeUnit(Long creativeId, Long unitId) {
        this.creativeId = creativeId;
        this.unitId = unitId;
    }

}
