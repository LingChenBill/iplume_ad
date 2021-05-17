package com.iplume.ad.controller;

import com.alibaba.fastjson.JSON;
import com.iplume.ad.exception.AdException;
import com.iplume.ad.service.IAdCreateService;
import com.iplume.ad.vo.AdCreateRequest;
import com.iplume.ad.vo.AdCreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 创意控制层.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/9
 */
@Slf4j
@RestController
public class AdCreativeOpController {

    private final IAdCreateService createService;

    @Autowired
    public AdCreativeOpController(IAdCreateService createService) {
        this.createService = createService;
    }

    /**
     * 创建创意.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @PostMapping("/create/creative")
    public AdCreativeResponse createCreative(@RequestBody AdCreateRequest request) throws AdException {
        log.info("ad-sponsor: createCreative -> {}", JSON.toJSONString(request));
        return createService.createCreative(request);
    }
}

