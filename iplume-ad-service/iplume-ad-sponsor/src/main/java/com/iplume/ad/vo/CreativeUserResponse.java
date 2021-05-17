package com.iplume.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户账号响应对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUserResponse {

    /**
     * Id.
     */
    private Long userId;

    /**
     * 账号名称.
     */
    private String username;

    /**
     * 账户token.
     */
    private String token;

    /**
     * 创建时间.
     */
    private Date createTime;

    /**
     * 更新时间.
     */
    private Date updateTime;
}
