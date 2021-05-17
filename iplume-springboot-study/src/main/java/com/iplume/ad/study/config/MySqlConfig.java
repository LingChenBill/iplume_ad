package com.iplume.ad.study.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Mysql定制配置.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/17
 */
@Data
@Component
@ConfigurationProperties(prefix = "adconf.mysql")
public class MySqlConfig {

    /**
     * IP.
     */
    private String host;

    /**
     * 端口.
     */
    private String port;

    /**
     * 用户名.
     */
    private String username;

    /**
     * 密码.
     */
    private String password;
}
