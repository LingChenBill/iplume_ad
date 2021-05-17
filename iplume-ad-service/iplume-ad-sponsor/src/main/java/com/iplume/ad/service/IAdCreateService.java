package com.iplume.ad.service;

import com.iplume.ad.vo.AdCreateRequest;
import com.iplume.ad.vo.AdCreativeResponse;

/**
 * 创意服务接口类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/7
 */
public interface IAdCreateService {

    /**
     * 创建创意.
     *
     * @param request
     * @return
     */
    AdCreativeResponse createCreative(AdCreateRequest request);

}
