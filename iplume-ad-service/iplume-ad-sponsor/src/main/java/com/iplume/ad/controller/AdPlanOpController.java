package com.iplume.ad.controller;

import com.alibaba.fastjson.JSON;
import com.iplume.ad.entity.AdPlan;
import com.iplume.ad.exception.AdException;
import com.iplume.ad.service.IAdPlanService;
import com.iplume.ad.vo.AdPlanGetRequest;
import com.iplume.ad.vo.AdPlanRequest;
import com.iplume.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 推广计划控制类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/8
 */
@Slf4j
@RestController
public class AdPlanOpController {

    private final IAdPlanService adPlanService;

    @Autowired
    public AdPlanOpController(IAdPlanService adPlanService) {
        this.adPlanService = adPlanService;
    }

    /**
     * 推广计划创建.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @PostMapping("/create/adPlan")
    public AdPlanResponse createAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: createAdPlan -> {}", JSON.toJSONString(request));
        return adPlanService.createAdPlan(request);
    }

    /**
     * 获取推广计划列表.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @PostMapping("/get/adPlan")
    public List<AdPlan> getAdPlanByIds(@RequestBody AdPlanGetRequest request) throws AdException {
        log.info("ad-sponsor: getAdPlanByIds -> {}", JSON.toJSONString(request));
        return adPlanService.getAdPlanByIds(request);
    }

    /**
     * 推广计划更新.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @PutMapping("/update/adPlan")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: updateAdPlan -> {}", JSON.toJSONString(request));
        return adPlanService.updateAdPlan(request);
    }

    /**
     * 推广计划删除.
     *
     * @param request
     * @throws AdException
     */
    @DeleteMapping("/delete/adPlan")
    public void deleteAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: deleteAdPlan -> {}", JSON.toJSONString(request));
        adPlanService.deleteAdPlan(request);
    }

}
