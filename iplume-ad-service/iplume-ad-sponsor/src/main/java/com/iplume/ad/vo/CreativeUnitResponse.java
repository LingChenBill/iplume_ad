package com.iplume.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 创意与推广单元关联响应对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitResponse {

    /**
     * id列表.
     */
    private List<Long> ids;

}
