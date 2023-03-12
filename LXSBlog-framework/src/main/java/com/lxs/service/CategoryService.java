package com.lxs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-03-08 20:24:41
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
