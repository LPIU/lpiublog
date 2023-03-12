package com.lxs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lxs.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-08 20:27:34
 */

public interface CategoryMapper extends BaseMapper<Category> {

}

