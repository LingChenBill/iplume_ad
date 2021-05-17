package com.iplume.ad.dao;

import com.iplume.ad.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * AdPlan仓库类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/21
 */
public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {

    /**
     * 根据id和userId来查询推广计划.
     *
     * @param id
     * @param userId
     * @return
     */
    AdPlan findByIdAndUserId(Long id, Long userId);

    /**
     * 根据id列表和userId来查询推广计划列表.
     *
     * @param ids
     * @param userId
     * @return
     */
    List<AdPlan> findAllByIdAndUserId(List<Long> ids, Long userId);

    /**
     * 根据userId和推广计划名称来查询推广计划.
     *
     * @param userId
     * @param planName
     * @return
     */
    AdPlan findByUserIdAndPlanName(Long userId, String planName);

    /**
     * 根据推广计划状态来查询推广计划列表.
     *
     * @param planStatus
     * @return
     */
    List<AdPlan> findAllByPlanStatus(Integer planStatus);

}
