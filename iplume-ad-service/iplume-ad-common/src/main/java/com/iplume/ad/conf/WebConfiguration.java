package com.iplume.ad.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Web统一配置.
 *
 * @author: lingchen
 * @date: 2021/4/17
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /**
     * 配置信息转换处理.
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
       converters.clear();
       // 可以全局设置日期转换器等.
       converters.add(new MappingJackson2HttpMessageConverter());
    }
}
