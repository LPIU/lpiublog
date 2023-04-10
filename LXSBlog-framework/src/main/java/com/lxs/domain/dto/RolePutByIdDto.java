package com.lxs.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @user 潇洒
 * @date 2023/4/4-21:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePutByIdDto {
    private Long id;
    private Long[] menuIds;
    private String remark;
    private String roleKey;
    private String roleName;
    private Integer roleSort;
    private String status;
}
