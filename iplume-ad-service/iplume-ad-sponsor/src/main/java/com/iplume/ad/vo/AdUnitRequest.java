package com.iplume.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * 推广单元请求对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitRequest {

    /**
     * 关联推广计划id.
     */
    private Long planId;

    /**
     * 推广单元名称.
     */
    private String unitName;

    /**
     * 广告位类型(开屏, 贴片, 中贴...).
     */
    private Integer positionType;

    /**
     * 预算.
     */
    private Long budget;

    /**
     * 创建校验.
     *
     * @return
     */
    public boolean createValidate() {
        return planId != null && !StringUtils.isEmpty(unitName)
                && positionType != null && budget != null;
    }

}
