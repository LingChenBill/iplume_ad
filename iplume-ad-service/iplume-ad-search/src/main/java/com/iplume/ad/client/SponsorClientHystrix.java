package com.iplume.ad.client;

import com.iplume.ad.client.vo.AdPlan;
import com.iplume.ad.client.vo.AdPlanGetRequest;
import com.iplume.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SponsorClient的服务降级处理.
 *
 * @description: 异常处理.
 * @author: lingchen
 * @date: 2021/5/9
 */
@Component
public class SponsorClientHystrix implements SponsorClient {

    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1, "eureka-client-ad-sponsor -> getAdPlanByIds error");
    }
}
