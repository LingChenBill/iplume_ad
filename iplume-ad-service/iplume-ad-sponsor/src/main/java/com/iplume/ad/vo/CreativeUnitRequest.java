package com.iplume.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 创意与推广单元关联请求对象.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitRequest {

    private List<CreativeUnitItem> creativeUnitItems;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreativeUnitItem {

        /**
         * 关联创意Id.
         */
        private Long creativeId;

        /**
         * 关联推广单元Id.
         */
        private Long unitId;

    }
}
