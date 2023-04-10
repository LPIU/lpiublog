package com.lxs.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 角色菜单treeselect
 *
 * @author 25650
 * @user 潇洒
 * @date 2023/4/4-20:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleMenuTreeselectVo {
    private List<MenuTreeVo> menus;
    private List<Long> checkedKeys;
}
