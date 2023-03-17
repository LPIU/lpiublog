package com.lxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxs.constants.SystemConstants;
import com.lxs.domain.entity.Menu;
import com.lxs.mapper.MenuMapper;
import com.lxs.service.MenuService;
import com.lxs.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-16 12:57:35
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {


    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果是管理员，返回所有权限
        if(SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTON);
            lambdaQueryWrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(lambdaQueryWrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
        //否则返回所具有的权限

        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus;
        //判断是否是管理员
        if(SecurityUtils.isAdmin()){
            menus= menuMapper.selectAllRouterMenu();
        }
        else {
            //否则返回所符合的menu
            menus=  menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //构建tree
        //先找第一层菜单 然后找他们的子菜单设置到children属性
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    private List<Menu> builderMenuTree(List<Menu> menus, Long pateId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(pateId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取传入参数子MENU
     *
     * @param menu  菜单
     * @param menus
     * @return {@code List<Menu>}
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> collect = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return collect;
    }
}
