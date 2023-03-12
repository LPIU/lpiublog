package com.lxs.service;

import com.lxs.domain.ResponseResult;
import com.lxs.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
