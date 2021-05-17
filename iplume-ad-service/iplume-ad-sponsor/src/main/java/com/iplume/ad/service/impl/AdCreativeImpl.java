package com.iplume.ad.service.impl;

import com.iplume.ad.dao.CreativeRepository;
import com.iplume.ad.entity.Creative;
import com.iplume.ad.service.IAdCreateService;
import com.iplume.ad.vo.AdCreateRequest;
import com.iplume.ad.vo.AdCreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 创意服务实现类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/7
 */
@Service
public class AdCreativeImpl implements IAdCreateService {

    private final CreativeRepository creativeRepository;

    @Autowired
    public AdCreativeImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    /**
     * 创建创意.
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AdCreativeResponse createCreative(AdCreateRequest request) {

        Creative creative = creativeRepository.save(request.convert2Entity());

        return new AdCreativeResponse(creative.getId(), creative.getName());
    }
}
