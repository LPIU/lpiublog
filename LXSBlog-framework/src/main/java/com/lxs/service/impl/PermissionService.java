package com.lxs.service.impl;

import com.lxs.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 判断当前用户是否具有权限
 * @user 潇洒
 * @date 2023/3/29-20:38
 */
@Service("ps")
public class PermissionService {
    @Autowired
    MenuServiceImpl menuService;

    public Boolean hasPermission(String permission) {
       //判断是否为超级管理员
        if (SecurityUtils.isAdmin()){
            return true;
        }


        List<String> perms= menuService.selectPermsByUserId(SecurityUtils.getUserId());

        return perms.contains(permission);
    }
}
