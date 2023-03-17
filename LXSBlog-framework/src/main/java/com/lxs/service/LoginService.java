package com.lxs.service;

import com.lxs.domain.ResponseResult;
import com.lxs.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
