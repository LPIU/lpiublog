package com.lxs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lxs.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-16 12:57:32
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 通过用户id选择
     *
     * @param userId 用户id
     * @return {@code List<String>}
     */
    List<String> selectPermsByUserId(Long userId);

    /**
     * 获取所有路由
     *
     * @return {@code List<Menu>}
     */
    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}

