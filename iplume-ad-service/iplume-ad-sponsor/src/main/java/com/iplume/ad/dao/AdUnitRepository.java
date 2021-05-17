package com.iplume.ad.dao;

import com.iplume.ad.entity.AdUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * AdUnit仓库类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/21
 */
public interface AdUnitRepository extends JpaRepository<AdUnit, Long> {

    /**
     * 根据推广计划Id和推广单元名称查询推广单元.
     *
     * @param planId
     * @param unitName
     * @return
     */
    AdUnit findByPlanIdAndUnitName(Long planId, String unitName);

    /**
     * 根据推广单元状态查询推广单元列表.
     *
     * @param unitStatus
     * @return
     */
    List<AdUnit> findAllByUnitStatus(Integer unitStatus);

}
