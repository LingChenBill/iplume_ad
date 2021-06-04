package com.iplume.ad.search;

import com.alibaba.fastjson.JSON;
import com.iplume.ad.Application;
import com.iplume.ad.search.vo.SearchRequest;
import com.iplume.ad.search.vo.feature.DistrictFeature;
import com.iplume.ad.search.vo.feature.FeatureRelation;
import com.iplume.ad.search.vo.feature.ItFeature;
import com.iplume.ad.search.vo.feature.KeywordFeature;
import com.iplume.ad.search.vo.media.AdSlot;
import com.iplume.ad.search.vo.media.App;
import com.iplume.ad.search.vo.media.Device;
import com.iplume.ad.search.vo.media.Geo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ISearch的测试类.
 *
 * @author: lingchen
 * @date: 2021/6/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SearchTest {

    @Autowired
    private ISearch search;

    /**
     * 测试: 获取广告检索信息.
     */
    @Test
    public void testFetchAds() {

        SearchRequest request = new SearchRequest();
        request.setMediaId("iplume-ad");

        // 构造RequestInfo信息.
        request.setRequestInfo(
                new SearchRequest.RequestInfo(
                        "iplume-ad-search",
                        Collections.singletonList(new AdSlot(
                                "ad-x", 1,
                                720, 1080, Arrays.asList(1, 2),
                                1000
                        )),
                        buildExampleApp(),
                        buildExampleGeo(),
                        buildExampleDevice()
                )
        );

        // 设置匹配信息.
        request.setFeatureInfo(buildExampleFeature(
                Arrays.asList("宝马", "大众"),
                Collections.singletonList(
                        new DistrictFeature.ProvinceAndCity(
                                "安徽省", "合肥市")),
                Arrays.asList("台球", "游泳"),
                FeatureRelation.OR
        ));

        System.out.println("Request: " + JSON.toJSONString(request));
        System.out.println("Response: " + JSON.toJSONString(search.fetchAds(request)));
    }

    /**
     * 构建App.
     *
     * @return
     */
    private App buildExampleApp() {
        return new App("imooc", "imooc",
                "com.imooc", "video");
    }

    /**
     * 构建Geo地理信息.
     *
     * @return
     */
    private Geo buildExampleGeo() {
        return new Geo((float) 100.28, (float) 88.61,
                "北京市", "北京市");
    }

    /**
     * 构建Device设备信息.
     *
     * @return
     */
    private Device buildExampleDevice() {
        return new Device(
                "iphone",
                "0xxxxx",
                "127.0.0.1",
                "x",
                "1080 720",
                "1080 720",
                "123456789"
        );
    }

    /**
     * 构建匹配信息.
     *
     * @param keywords 关键词列表.
     * @param provinceAndCitys 所在省和城市对象列表.
     * @param its 兴趣列表.
     * @param relation 广告检索类型.
     * @return
     */
    private SearchRequest.FeatureInfo buildExampleFeature(List<String> keywords,
                                                          List<DistrictFeature.ProvinceAndCity> provinceAndCitys,
                                                          List<String> its,
                                                          FeatureRelation relation) {

        return new SearchRequest.FeatureInfo(
                new KeywordFeature(keywords),
                new ItFeature(its),
                new DistrictFeature(provinceAndCitys),
                relation
        );
    }


}
