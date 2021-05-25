package com.iplume.ad.controller;

import com.alibaba.fastjson.JSON;
import com.iplume.ad.annotation.IgnoreResponseAdvice;
import com.iplume.ad.client.SponsorClient;
import com.iplume.ad.client.vo.AdPlan;
import com.iplume.ad.client.vo.AdPlanGetRequest;
import com.iplume.ad.search.ISearch;
import com.iplume.ad.search.vo.SearchRequest;
import com.iplume.ad.search.vo.SearchResponse;
import com.iplume.ad.vo.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 搜索控制层.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/9
 */
@Slf4j
@RestController
public class SearchController {

    private final RestTemplate restTemplate;

    private final SponsorClient sponsorClient;

    private final ISearch search;

    @Autowired
    public SearchController(RestTemplate restTemplate,
                            @Qualifier("eureka-client-ad-sponsor") SponsorClient sponsorClient,
                            ISearch search) {
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
        this.search = search;
    }

    /**
     * 获取广告检索对象信息.
     *
     * @param request 广告检索服务请求对象.
     * @return 广告检索响应对象.
     */
    @PostMapping("/fetchAds")
    public SearchResponse fetchAds(@RequestBody SearchRequest request) {
        log.info("ad-search: fetchAds -> {}", JSON.toJSONString(request));

        return search.fetchAds(request);
    }

    /**
     * 通过ribbon, RestTemplate方式来获取AdPlan列表.
     * @IgnoreResponseAdvice: 若类和方法上有IgnoreResponseAdvice注解时,忽略此CommonResponseDataAdvice拦截封装.
     *
     * @param request
     * @return
     */
    @IgnoreResponseAdvice
    @PostMapping("/getAdPlansByRibbon")
    public CommonResponse<List<AdPlan>> getAdPlansByRibbion(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: getAdPlansByRibbion -> {}", JSON.toJSONString(request));
        return restTemplate.postForEntity(
                "http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan",
                request,
                CommonResponse.class
        ).getBody();
    }

    /**
     * 通过Feign来调用微服务ad-sponsor推广计划列表.
     *
     * @param request
     * @return
     */
    @IgnoreResponseAdvice
    @PostMapping("/getAdPlans")
    public CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: getAdPlans -> {}", request);
        return sponsorClient.getAdPlans(request);
    }
}
