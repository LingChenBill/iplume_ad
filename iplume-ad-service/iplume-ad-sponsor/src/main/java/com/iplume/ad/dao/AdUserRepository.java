package com.iplume.ad.dao;

import com.iplume.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AdUser的仓库类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/21
 */
public interface AdUserRepository extends JpaRepository<AdUser, Long> {

    /**
     * 根据账户名称查询用户.
     *
     * @param userName
     * @return
     */
    AdUser findByUsername(String userName);
}
