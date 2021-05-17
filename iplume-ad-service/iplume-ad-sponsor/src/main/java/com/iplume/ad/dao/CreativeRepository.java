package com.iplume.ad.dao;

import com.iplume.ad.entity.Creative;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Creative仓库类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/21
 */
public interface CreativeRepository extends JpaRepository<Creative, Long> {
}
