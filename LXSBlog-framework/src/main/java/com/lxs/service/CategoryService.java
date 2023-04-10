package com.lxs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.AddCategoryDto;
import com.lxs.domain.dto.PutCategoryDto;
import com.lxs.domain.entity.Category;
import com.lxs.domain.vo.CategoryVo;

import java.util.List;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-03-08 20:24:41
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();

    List<CategoryVo> listAllCategory();

    ResponseResult<Object> pageVoList(Integer pageNum, Integer pageSize, String name, String status);

    ResponseResult addCategory(AddCategoryDto addCategoryDto);

    ResponseResult getCategoryById(String id);

    ResponseResult putCategory(PutCategoryDto putCategoryDto);

    ResponseResult delCategory(Long id);
}
