package com.lxs.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @user 潇洒
 * @date 2023/3/20-15:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagPUTDto {
    private Long id;
    //标签名
    private String name;

    //备注
    private String remark;

}
