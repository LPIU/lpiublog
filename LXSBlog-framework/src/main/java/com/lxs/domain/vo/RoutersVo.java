package com.lxs.domain.vo;

import com.lxs.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @user 潇洒
 * @date 2023/3/16-14:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutersVo {
    private List<Menu> menus;
}
