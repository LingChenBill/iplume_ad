package com.iplume.ad.controller;

import com.alibaba.fastjson.JSON;
import com.iplume.ad.exception.AdException;
import com.iplume.ad.service.IAdUnitService;
import com.iplume.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 推广单元控制类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/9
 */
@Slf4j
@RestController
public class AdUnitOpController {

    private final IAdUnitService unitService;

    @Autowired
    public AdUnitOpController(IAdUnitService unitService) {
        this.unitService = unitService;
    }

    /**
     * 创建推广单元.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @PostMapping("/create/adUnit")
    public AdUnitResponse createUnit(@RequestBody AdUnitRequest request) throws AdException {
        log.info("ad-sponsor: createUnit -> {}", JSON.toJSONString(request));
        return unitService.createUnit(request);
    }

    /**
     * 创建关键词限制.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @PostMapping("/create/unitKeyword")
    public AdUnitKeywordResponse createUnitKeyword(@RequestBody AdUnitKeywordRequest request) throws AdException {
        log.info("ad-sponsor: createUnitKeyword -> {}", JSON.toJSONString(request));
        return unitService.createUnitKeyword(request);
    }

    /**
     * 创建地域限制.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @PostMapping("/create/unitDistrict")
    public AdUnitDistrictResponse createUnitDistrict(@RequestBody AdUnitDistrictRequest request) throws AdException {
        log.info("ad-sponsor: createUnitDistrict -> {}", JSON.toJSONString(request));
        return unitService.createUnitDistrict(request);
    }

    /**
     * 创建兴趣限制.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @PostMapping("/create/unitIt")
    public AdUnitItResponse createUnitIt(@RequestBody AdUnitItRequest request) throws AdException {
        log.info("ad-sponsor: createUnitIt -> {}", JSON.toJSONString(request));
        return unitService.createUnitIt(request);
    }

    /**
     * 创建创意与推广单元关联.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @PostMapping("/create/creativeUnit")
    public CreativeUnitResponse createCreativeUnit(@RequestBody CreativeUnitRequest request) throws AdException {
        log.info("ad-sponsor: createCreativeUnit -> {}", JSON.toJSONString(request));
        return unitService.createCreativeUnit(request);
    }

}
