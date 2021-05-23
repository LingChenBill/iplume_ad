package com.iplume.ad.search;

import com.iplume.ad.search.vo.SearchRequest;
import com.iplume.ad.search.vo.SearchResponse;

/**
 * 广告检索服务接口.
 *
 * @author: lingchen
 * @date: 2021/5/21
 */
public interface ISearch {

    /**
     * 获取广告检索信息.
     *
     * @param request 广告检索服务请求对象.
     * @return 广告检索响应对象.
     */
    SearchResponse fetchAds(SearchRequest request);

}
