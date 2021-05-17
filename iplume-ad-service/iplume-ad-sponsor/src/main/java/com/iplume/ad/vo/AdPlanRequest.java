package com.iplume.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * 推广计划请求对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanRequest {

    /**
     * id.
     */
    private Long id;

    /**
     * 标记当前记录所属用户.
     */
    private Long userId;

    /**
     * 推广计划名称.
     */
    private String planName;

    /**
     * 推广计划开始时间.
     */
    private String startDate;

    /**
     * 推广计划结束时间.
     */
    private String endDate;

    /**
     * 创建检验.
     *
     * @return
     */
    public boolean createValidate() {
        return userId != null
                && !StringUtils.isEmpty(planName)
                && !StringUtils.isEmpty(startDate)
                && !StringUtils.isEmpty(endDate);
    }

    /**
     * 更新检验.
     *
     * @return
     */
    public boolean updateValidate() {
        return id != null && userId != null;
    }

    /**
     * 删除检验.
     *
     * @return
     */
    public boolean deleteValidate() {
        return id != null && userId != null;
    }
}
