package com.lxs.domain.vo;

import com.lxs.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @user 潇洒
 * @date 2023/4/5-21:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSelectByIdVo {
    private List<Long> roleIds;
    private List<Role> roles;
    private UserInfoVo user;
    
}
