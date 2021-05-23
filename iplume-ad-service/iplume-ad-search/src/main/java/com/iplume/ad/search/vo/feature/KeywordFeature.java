package com.iplume.ad.search.vo.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 关键词信息对象.
 *
 * @author: lingchen
 * @date: 2021/5/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeywordFeature {

    /**
     * 关键词列表.
     */
    private List<String> keywords;

}
