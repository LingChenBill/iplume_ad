package com.iplume.ad.entity;

import com.iplume.ad.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * ad_user实体类.
 *
 * @description: @Id: id 主键. @GeneratedValue: 自增策略.
 *               @Column: 字段对应表字段.
 *               @Basic: 表普通字段.可不写.
 * @author: lingchen
 * @date: 2021/4/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ad_user")
public class AdUser {

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 账号名称.
     */
    @Basic
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * 账户token.
     */
    @Basic
    @Column(name = "token", nullable = false)
    private String token;

    /**
     * 账号状态.
     */
    @Basic
    @Column(name = "user_status", nullable = false)
    private Integer userStatus;

    /**
     * 创建时间.
     */
    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    /**
     * 更新时间.
     */
    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public AdUser(String username, String token) {
        this.username = username;
        this.token = token;
        this.userStatus = CommonStatus.VALID.getStatus();
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }
}
