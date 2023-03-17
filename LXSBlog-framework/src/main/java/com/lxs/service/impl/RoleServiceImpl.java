package com.lxs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxs.domain.entity.Role;
import com.lxs.mapper.RoleMapper;
import org.springframework.stereotype.Service;
import com.lxs.service.RoleService;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-03-16 13:08:26
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if (id == 1L) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
         }

        //负责查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }
}
