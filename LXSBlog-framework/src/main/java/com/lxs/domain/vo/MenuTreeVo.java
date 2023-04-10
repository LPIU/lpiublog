package com.lxs.domain.vo;

import com.lxs.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @user 潇洒
 * @date 2023/4/3-19:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MenuTreeVo {
    //菜单ID
    private Long id;
    private List<MenuTreeVo> children;
    //父菜单ID
    private Long parentId;

    //菜单名称
    private String label;


}
