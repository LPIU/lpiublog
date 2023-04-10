package com.lxs.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @user 潇洒
 * @date 2023/4/10-12:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCategoryDto {
    private String name;
    private String description;
    private String status;
}
