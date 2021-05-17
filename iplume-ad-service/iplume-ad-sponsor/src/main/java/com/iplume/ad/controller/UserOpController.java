package com.iplume.ad.controller;

import com.alibaba.fastjson.JSON;
import com.iplume.ad.exception.AdException;
import com.iplume.ad.service.IUserService;
import com.iplume.ad.vo.CreativeUserRequest;
import com.iplume.ad.vo.CreativeUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/5/8
 */
@Slf4j
@RestController
public class UserOpController {

    private final IUserService userService;

    @Autowired
    public UserOpController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 创建用户.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @PostMapping("/create/user")
    public CreativeUserResponse createUser(@RequestBody CreativeUserRequest request) throws AdException {
        log.info("ad-sponsor: createUser -> {}", JSON.toJSONString(request));
        return userService.createUser(request);
    }

}
