package com.lxs.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @user 潇洒
 * @date 2023/4/3-16:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePutDto {
    private String roleId;
    private String status;
}
