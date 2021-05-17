package com.iplume.ad.service.impl;

import com.iplume.ad.constant.Constants;
import com.iplume.ad.dao.AdPlanRepository;
import com.iplume.ad.dao.AdUnitRepository;
import com.iplume.ad.dao.CreativeRepository;
import com.iplume.ad.dao.unitcondition.AdUnitDistrictRepository;
import com.iplume.ad.dao.unitcondition.AdUnitItRepository;
import com.iplume.ad.dao.unitcondition.AdUnitKeywordRepository;
import com.iplume.ad.dao.unitcondition.CreativeUnitRepository;
import com.iplume.ad.entity.AdPlan;
import com.iplume.ad.entity.AdUnit;
import com.iplume.ad.entity.unitcondition.AdUnitDistrict;
import com.iplume.ad.entity.unitcondition.AdUnitIt;
import com.iplume.ad.entity.unitcondition.AdUnitKeyword;
import com.iplume.ad.entity.unitcondition.CreativeUnit;
import com.iplume.ad.exception.AdException;
import com.iplume.ad.service.IAdUnitService;
import com.iplume.ad.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 推广单元实现类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/30
 */
@Service
public class AdUnitServiceImpl implements IAdUnitService {

    private final AdUnitRepository unitRepository;

    private final AdPlanRepository planRepository;

    private final AdUnitKeywordRepository unitKeywordRepository;

    private final AdUnitDistrictRepository unitDistrictRepository;

    private final AdUnitItRepository unitItRepository;

    private final CreativeRepository creativeRepository;

    private final CreativeUnitRepository creativeUnitRepository;

    @Autowired
    public AdUnitServiceImpl(AdUnitRepository unitRepository,
                             AdPlanRepository planRepository,
                             AdUnitKeywordRepository unitKeywordRepository,
                             AdUnitDistrictRepository unitDistrictRepository,
                             AdUnitItRepository unitItRepository,
                             CreativeRepository creativeRepository,
                             CreativeUnitRepository creativeUnitRepository) {
        this.unitRepository = unitRepository;
        this.planRepository = planRepository;
        this.unitKeywordRepository = unitKeywordRepository;
        this.unitDistrictRepository = unitDistrictRepository;
        this.unitItRepository = unitItRepository;
        this.creativeRepository = creativeRepository;
        this.creativeUnitRepository = creativeUnitRepository;
    }

    /**
     * 创建推广单元.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @Override
    @Transactional(rollbackFor = AdException.class)
    public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {

        // 请求校验.
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 存在性校验.
        Optional<AdPlan> adPlan = planRepository.findById(request.getPlanId());
        if (!adPlan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        // 同一性校验.
        AdUnit adUnit = unitRepository.findByPlanIdAndUnitName(request.getPlanId(), request.getUnitName());
        if (adUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }

        AdUnit newAdUnit = unitRepository.save(new AdUnit(request.getPlanId(), request.getUnitName(),
                request.getPositionType(), request.getBudget()));

        return new AdUnitResponse(newAdUnit.getId(), newAdUnit.getUnitName());
    }

    /**
     * 创建关键词限制.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @Override
    @Transactional(rollbackFor = AdException.class)
    public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException {

        // 关联推广单元id列表.
        List<Long> unitIds = request.getUnitKeywords().stream()
                .map(AdUnitKeywordRequest.UnitKeyword::getUnitId)
                .collect(Collectors.toList());

        // 关联推广单元校验.
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // id列表.
        List<Long> ids = Collections.emptyList();
        // 关键词限制列表.
        List<AdUnitKeyword> unitKeywords = new ArrayList<>();

        if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {
            // 关键词限制列表装载.
            request.getUnitKeywords().forEach(i -> unitKeywords.add(
                    new AdUnitKeyword(i.getUnitId(), i.getKeyword())
            ));
            // 保存.
            ids = unitKeywordRepository.saveAll(unitKeywords).stream()
                    .map(AdUnitKeyword::getId)
                    .collect(Collectors.toList());
        }

        return new AdUnitKeywordResponse(ids);
    }

    /**
     * 创建地域限制.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @Override
    @Transactional(rollbackFor = AdException.class)
    public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException {

        // 关联推广单元id列表.
        List<Long> unitIds = request.getUnitDistricts().stream()
                .map(AdUnitDistrictRequest.UnitDistrict::getUnitId)
                .collect(Collectors.toList());

        // 关联推广单元校验.
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 地域限制列表.
        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        request.getUnitDistricts().forEach(d -> unitDistricts.add(
                new AdUnitDistrict(d.getUnitId(), d.getProvince(), d.getCity())
        ));

        // 保存.
        List<Long> ids = unitDistrictRepository.saveAll(unitDistricts).stream()
                .map(AdUnitDistrict::getId)
                .collect(Collectors.toList());

        return new AdUnitDistrictResponse(ids);
    }

    /**
     * 创建兴趣限制.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @Override
    @Transactional(rollbackFor = AdException.class)
    public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException {

        // 关联推广单元id列表.
        List<Long> unitIds = request.unitIts.stream()
                .map(AdUnitItRequest.UnitIt::getUnitId)
                .collect(Collectors.toList());

        // 关联推广单元校验.
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 兴趣限制列表.
        List<AdUnitIt> unitIts = new ArrayList<>();
        // 兴趣限制列表装载.
        request.unitIts.forEach(i -> unitIts.add(
                new AdUnitIt(i.getUnitId(), i.getItTag())
        ));

        // 保存.
        List<Long> ids = unitItRepository.saveAll(unitIts).stream()
                .map(AdUnitIt::getId)
                .collect(Collectors.toList());

        return new AdUnitItResponse(ids);
    }

    /**
     * 创建创意与推广单元关联.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @Override
    @Transactional(rollbackFor = AdException.class)
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {

        // 关联推广单元Id列表.
        List<Long> unitIds = request.getCreativeUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getUnitId)
                .collect(Collectors.toList());

        // 关联创意Id列表.
        List<Long> creativeIds = request.getCreativeUnitItems().stream()
                .map(CreativeUnitRequest.CreativeUnitItem::getCreativeId)
                .collect(Collectors.toList());

        // 关联校验.
        if (!(isRelatedUnitExist(unitIds) && isRelativeCreativeExist(creativeIds))) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 创意与推广单元关联列表.
        List<CreativeUnit> creativeUnits = new ArrayList<>();
        // 创意与推广单元关联列表装载.
        request.getCreativeUnitItems().forEach(i -> creativeUnits.add(
                new CreativeUnit(i.getCreativeId(), i.getUnitId())
        ));

        List<Long> ids = creativeUnitRepository.saveAll(creativeUnits)
                .stream()
                .map(CreativeUnit::getId)
                .collect(Collectors.toList());

        return new CreativeUnitResponse(ids);
    }

    /**
     * 关联推广单元校验.
     *
     * @param unitIds
     * @return
     */
    private boolean isRelatedUnitExist(List<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }

        // 查看unitIds是否与DB的推广单元的记录数相等.
        // HashSet进行去重处理.
        return unitRepository.findAllById(unitIds).size() == new HashSet<>(unitIds).size();
    }

    /**
     * 关联创意校验.
     *
     * @param creativeIds
     * @return
     */
    private boolean isRelativeCreativeExist(List<Long> creativeIds) {
        if (CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }

        return creativeRepository.findAllById(creativeIds).size() == new HashSet<>(creativeIds).size();
    }
}
