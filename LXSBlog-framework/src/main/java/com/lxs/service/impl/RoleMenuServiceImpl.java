package com.lxs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxs.service.RoleMenuService;
import com.lxs.domain.entity.RoleMenu;
import com.lxs.mapper.RoleMenuMapper;
import org.springframework.stereotype.Service;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-04-04 14:08:25
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}
