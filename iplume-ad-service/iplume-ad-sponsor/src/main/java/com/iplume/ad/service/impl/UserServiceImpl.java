package com.iplume.ad.service.impl;

import com.iplume.ad.constant.Constants;
import com.iplume.ad.dao.AdUserRepository;
import com.iplume.ad.entity.AdUser;
import com.iplume.ad.exception.AdException;
import com.iplume.ad.service.IUserService;
import com.iplume.ad.utils.CommonUtils;
import com.iplume.ad.vo.CreativeUserRequest;
import com.iplume.ad.vo.CreativeUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户账号服务实现类.
 *
 * @description:
 * @author: lingchen
 * @date: 2021/4/21
 */
@Service
public class UserServiceImpl implements IUserService {

    private final AdUserRepository userRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 创建用户账号.
     *
     * @param request
     * @return
     * @throws AdException
     */
    @Override
    @Transactional(rollbackFor = AdException.class)
    public CreativeUserResponse createUser(CreativeUserRequest request) throws AdException {

        // 用户账号检验.
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        // 检验用户账号是否存在.
        AdUser oldUser = userRepository.findByUsername(request.getUsername());
        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        // 保存用户账号.
        AdUser newUser = userRepository.save(new AdUser(request.getUsername(), CommonUtils.md5(request.getUsername())));

        return new CreativeUserResponse(newUser.getId(),
                newUser.getUsername(),
                newUser.getToken(),
                newUser.getCreateTime(),
                newUser.getUpdateTime());
    }
}
