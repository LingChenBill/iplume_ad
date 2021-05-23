package com.iplume.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 地理位置信息对象.
 *
 * @author: lingchen
 * @date: 2021/5/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geo {

    /**
     * 纬度.
     */
    private Float latitude;

    /**
     * 经度.
     */
    private Float longitude;

    /**
     * 所在城市.
     */
    private String city;

    /**
     * 所在省.
     */
    private String province;

}
