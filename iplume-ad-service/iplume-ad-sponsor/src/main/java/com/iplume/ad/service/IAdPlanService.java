package com.iplume.ad.service;

import com.iplume.ad.entity.AdPlan;
import com.iplume.ad.exception.AdException;
import com.iplume.ad.vo.AdPlanGetRequest;
import com.iplume.ad.vo.AdPlanRequest;
import com.iplume.ad.vo.AdPlanResponse;

import java.util.List;

/**
 * 推广计划服务接口.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/29
 */
public interface IAdPlanService {

    /**
     * 创建推广计划.
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 获取推广计划列表.
     *
     * @param request
     * @return
     * @throws AdException
     */
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

    /**
     * 更新推广计划.
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 删除推广计划.
     *
     * @param request
     * @throws AdException
     */
    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
