package com.lxs.domain.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @user 潇洒
 * @date 2023/3/17-12:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagListDto {


    //标签名
    private String name;

    //备注
    private String remark;

}
