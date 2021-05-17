package com.iplume.ad.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Binlog相关的mysql属性设置类.
 * <p>
 * 此类将application.yml中的adconf.mysql中的属性相对应.
 * 属性名要一致.
 *
 * @author: lingchen
 * @date: 2021/5/17
 */
@Component
@ConfigurationProperties(prefix = "adconf.mysql")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinlogConfig {

    /**
     * IP地址.
     */
    private String host;

    /**
     * 端口.
     */
    private Integer port;

    /**
     * 用户名.
     */
    private String username;

    /**
     * 密码.
     */
    private String password;

    /**
     * binlog文件名.
     */
    private String binlogName;

    /**
     * binlog的偏移位置.
     */
    private Long position;

}
