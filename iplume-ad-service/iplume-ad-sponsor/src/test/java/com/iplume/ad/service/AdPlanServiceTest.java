package com.iplume.ad.service;

import com.alibaba.fastjson.JSON;
import com.iplume.ad.Application;
import com.iplume.ad.exception.AdException;
import com.iplume.ad.vo.AdPlanGetRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

/**
 * AdPlanService的测试类.
 *
 * @author: lingchen
 * @date: 2021/6/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class AdPlanServiceTest {

    @Autowired
    private IAdPlanService planService;

    /**
     * 测试: 获取推广计划列表.
     *
     * @throws AdException
     */
    @Test
    public void testGetAdPlan() throws AdException {

        log.info("AgPlan List: {}",
                JSON.toJSONString(planService.getAdPlanByIds(
                        new AdPlanGetRequest(15L, Collections.singletonList(10L)))
                ));
    }
}
