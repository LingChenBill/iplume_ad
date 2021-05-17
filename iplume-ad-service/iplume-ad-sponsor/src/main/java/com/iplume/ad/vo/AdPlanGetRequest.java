package com.iplume.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 推广计划取得请求对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanGetRequest {

    /**
     * 标记当前记录所属用户.
     */
    private Long userId;

    /**
     * id列表.
     */
    private List<Long> ids;

    /**
     * 取得检验.
     *
     * @return
     */
    public boolean validate() {
        return userId != null && !CollectionUtils.isEmpty(ids);
    }
}
