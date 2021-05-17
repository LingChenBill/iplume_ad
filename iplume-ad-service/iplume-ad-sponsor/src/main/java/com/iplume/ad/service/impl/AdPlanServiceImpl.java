package com.iplume.ad.service.impl;

import com.iplume.ad.constant.CommonStatus;
import com.iplume.ad.constant.Constants;
import com.iplume.ad.dao.AdPlanRepository;
import com.iplume.ad.dao.AdUserRepository;
import com.iplume.ad.entity.AdPlan;
import com.iplume.ad.entity.AdUser;
import com.iplume.ad.exception.AdException;
import com.iplume.ad.service.IAdPlanService;
import com.iplume.ad.utils.CommonUtils;
import com.iplume.ad.vo.AdPlanGetRequest;
import com.iplume.ad.vo.AdPlanRequest;
import com.iplume.ad.vo.AdPlanResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 推广计划服务实现类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/29
 */
@Service
public class AdPlanServiceImpl implements IAdPlanService {

    private final AdUserRepository userRepository;

    private final AdPlanRepository planRepository;

    @Autowired
    public AdPlanServiceImpl(AdPlanRepository planRepository, AdUserRepository userRepository) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }

    /**
     * 创建推广计划.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @Override
    @Transactional(rollbackFor = AdException.class)
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {

        // 参数校验.
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 存在校验.
        Optional<AdUser> adUser = userRepository.findById(request.getId());
        if (!adUser.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        // 同一性校验.
        AdPlan oldPlan = planRepository.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
        if (oldPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }

        AdPlan newAdPlan = planRepository.save(new AdPlan(request.getUserId(), request.getPlanName(),
                CommonUtils.parseStringDate(request.getStartDate()),
                CommonUtils.parseStringDate(request.getEndDate())));

        return new AdPlanResponse(newAdPlan.getId(), newAdPlan.getPlanName());
    }

    /**
     * 获取推广计划列表.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {

        // 参数校验.
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        return planRepository.findAllByIdAndUserId(request.getIds(), request.getUserId());
    }

    /**
     * 更新推广计划.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @Override
    @Transactional(rollbackFor = AdException.class)
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {

        // 参数校验.
        if (!request.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 存在性校验.
        AdPlan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        // 更新非空字段.

        if (!StringUtils.isEmpty(request.getPlanName())) {
            plan.setPlanName(request.getPlanName());
        }

        if (!StringUtils.isEmpty(request.getStartDate())) {
            plan.setStartDate(CommonUtils.parseStringDate(request.getStartDate()));
        }

        if (!StringUtils.isEmpty(request.getEndDate())) {
            plan.setEndDate(CommonUtils.parseStringDate(request.getEndDate()));
        }

        plan.setUpdateTime(new Date());

        // 更新推广计划.
        plan = planRepository.save(plan);

        return new AdPlanResponse(plan.getId(), plan.getPlanName());
    }

    /**
     * 删除推广计划.
     *
     * @param request
     * @throws AdException
     */
    @Override
    @Transactional(rollbackFor = AdException.class)
    public void deleteAdPlan(AdPlanRequest request) throws AdException {

        // 参数校验.
        if (!request.deleteValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 存在性校验.
        AdPlan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        // 更新推广计划有效->无效.
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        planRepository.save(plan);
    }

}
