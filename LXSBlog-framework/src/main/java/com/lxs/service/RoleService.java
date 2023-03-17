package com.lxs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxs.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-03-16 13:08:25
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);
}
