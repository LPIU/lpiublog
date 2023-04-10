package com.lxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxs.constants.SystemConstants;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.AddCategoryDto;
import com.lxs.domain.dto.PutCategoryDto;
import com.lxs.domain.entity.Article;
import com.lxs.domain.entity.Category;
import com.lxs.domain.vo.CategoryVo;
import com.lxs.domain.vo.PageVo;
import com.lxs.mapper.CategoryMapper;
import com.lxs.service.ArticleService;
import com.lxs.service.CategoryService;
import com.lxs.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-03-08 20:24:41
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //查询文章表 状态为已发布
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(queryWrapper);
        //获取文章的分类id，并去重

        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());

        //查询分类表
        System.out.println("!!!!!!!!!!!!!!!!"+categoryIds);
        List<Category> categories = list();
        System.out.println("!!!!!!!!!!!!!!!!"+categories);
        List<Category> categories1 = listByIds(categoryIds);

        categories = categories.stream().filter(category -> SystemConstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);


        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getStatus,SystemConstants.CATEGORY_STATUS_NORMAL);
        List<Category> list = list(queryWrapper);
        List<CategoryVo> voList = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return voList;
    }

    @Override
    public ResponseResult<Object> pageVoList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Objects.nonNull(status),Category::getStatus,status);
        queryWrapper.like(Objects.nonNull(name),Category::getName,name);
        Page<Category> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<Category> records = page.getRecords();
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(records, CategoryVo.class);
        PageVo pageVo = new PageVo(categoryVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addCategory(AddCategoryDto addCategoryDto) {
        Category category = BeanCopyUtils.copyBean(addCategoryDto, Category.class);
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getCategoryById(String id) {
        Category byId = getById(id);
        return ResponseResult.okResult(byId);
    }

    @Override
    public ResponseResult putCategory(PutCategoryDto putCategoryDto) {
        updateById(BeanCopyUtils.copyBean(putCategoryDto, Category.class));
        return ResponseResult.okResult();
    }
    @Override
    public ResponseResult delCategory(Long id){
        removeById(id);
        return ResponseResult.okResult();
    }

}
