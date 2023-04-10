package com.lxs.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 友链(Link)表实体类
 *
 * @author makejava
 * @since 2023-03-10 20:47:29
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkPutDto {
    private Long id;
    private String name;
    
    private String logo;
    
    private String description;
    //网站地址
    private String address;
    //审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)
    private String status;



}
