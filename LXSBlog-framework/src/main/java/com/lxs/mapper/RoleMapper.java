package com.lxs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lxs.domain.entity.Role;

import java.util.List;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-16 13:08:23
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}

