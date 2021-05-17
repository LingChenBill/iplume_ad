package com.iplume.ad.client;

import com.iplume.ad.client.vo.AdPlan;
import com.iplume.ad.client.vo.AdPlanGetRequest;
import com.iplume.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 通过Feign来调用ad-sponsor服务.
 *
 * @description: fallback: 服务降级,异常处理.
 * @author: lingchen
 * @date: 2021/5/9
 */
@FeignClient(value = "eureka-client-ad-sponsor", fallback = SponsorClientHystrix.class)
public interface SponsorClient {

    /**
     * 获取推广计划列表服务接口.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/ad-sponsor/get/adPlan", method = RequestMethod.POST)
    CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request);

}
