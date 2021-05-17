package com.iplume.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * 创建用户账户请求对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUserRequest {

    /**
     * 账号名称.
     */
    private String username;

    /**
     * 账号名称检验.
     *
     * @return
     */
    public boolean validate() {
        return !StringUtils.isEmpty(username);
    }
}

