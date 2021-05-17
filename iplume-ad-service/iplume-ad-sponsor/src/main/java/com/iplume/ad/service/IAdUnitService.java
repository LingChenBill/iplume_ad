package com.iplume.ad.service;

import com.iplume.ad.exception.AdException;
import com.iplume.ad.vo.*;

/**
 * 推广单元服务类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/30
 */
public interface IAdUnitService {

    /**
     * 创建推广单元.
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;

    /**
     * 创建关键词限制.
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException;

    /**
     * 创建地域限制.
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException;

    /**
     * 创建兴趣限制.
     *
     * @param request
     * @return
     * @throws AdException
     */
    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException;

    /**
     * 创建创意与推广单元关联.
     *
     * @param request
     * @return
     * @throws AdException
     */
    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException;
}
