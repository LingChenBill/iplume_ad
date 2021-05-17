package com.iplume.ad.service;

import com.iplume.ad.exception.AdException;
import com.iplume.ad.vo.CreativeUserRequest;
import com.iplume.ad.vo.CreativeUserResponse;

/**
 * 用户账户服务接口.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/21
 */
public interface IUserService {

    /**
     * 创建用户账号.
     *
     * @param request
     * @return
     * @throws AdException
     */
    CreativeUserResponse createUser(CreativeUserRequest request) throws AdException;

}
