package com.lxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxs.constants.SystemConstants;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.entity.Menu;
import com.lxs.domain.entity.RoleMenu;
import com.lxs.domain.enums.AppHttpCodeEnum;
import com.lxs.domain.vo.MenuTreeVo;
import com.lxs.domain.vo.MenuVo;
import com.lxs.domain.vo.RoleMenuTreeselectVo;
import com.lxs.exception.SystemException;
import com.lxs.mapper.MenuMapper;
import com.lxs.service.MenuService;
import com.lxs.service.RoleMenuService;
import com.lxs.utils.BeanCopyUtils;
import com.lxs.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-16 12:57:35
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;
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

    @Override
    public ResponseResult listMenu(String status, String menuName) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(status),Menu::getStatus,status);
        queryWrapper.like(Objects.nonNull(menuName),Menu::getMenuName,menuName);
        queryWrapper.orderByAsc(Menu::getOrderNum);
        queryWrapper.orderByAsc(Menu::getParentId);
        List<Menu> list = list(queryWrapper);
        return ResponseResult.okResult(list);
    }

    @Override
    public ResponseResult searchMenu(String id) {
        Menu menu = getById(id);
        MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult putMenu(Menu menu) {
        if (menu.getId().equals(menu.getParentId())){
            throw new SystemException(AppHttpCodeEnum.PARENT_MENU_ERROR);
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult treeSelect() {
        List<MenuTreeVo> treeVos = treeVos();
        return ResponseResult.okResult(treeVos);
    }

    @Override
    public ResponseResult treeSelectById(Long id) {
        LambdaQueryWrapper<RoleMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RoleMenu::getRoleId,id);
        List<RoleMenu> roleMenus = roleMenuService.list(lambdaQueryWrapper);

        List<Long> collect = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());

        List<MenuTreeVo> treeVos = treeVos();

        RoleMenuTreeselectVo menuTreeselectVo = new RoleMenuTreeselectVo(treeVos,collect);
        return ResponseResult.okResult(menuTreeselectVo);
    }

    private List<MenuTreeVo> treeVos(){
        List<Menu> list = list();
        List<MenuTreeVo> collect = list.stream().map(menu -> new MenuTreeVo(menu.getId(), null, menu.getParentId(), menu.getMenuName()))
                .collect(Collectors.toList());
        List<MenuTreeVo> menuTree = builderMenuTreeVo(collect,0L);
        return menuTree;
    }
    private List<Menu> builderMenuTree(List<Menu> menus, Long pateId) {
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(pateId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
    }
    private List<MenuTreeVo> builderMenuTreeVo(List<MenuTreeVo> menus,Long pateId){
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(pateId))
                .map(menu -> menu.setChildren(getChildrenVo(menu, menus)))
                .collect(Collectors.toList());
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

    private List<MenuTreeVo> getChildrenVo(MenuTreeVo menu, List<MenuTreeVo> menus) {
        List<MenuTreeVo> collect = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildrenVo(m,menus)))
                .collect(Collectors.toList());
        return collect;
    }
}
