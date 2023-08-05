package com.lxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxs.constants.SystemConstants;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.AddArticleDto;
import com.lxs.domain.entity.Article;
import com.lxs.domain.entity.ArticleTag;
import com.lxs.domain.entity.Category;
import com.lxs.domain.entity.Tag;
import com.lxs.domain.vo.*;
import com.lxs.mapper.ArticleMapper;
import com.lxs.service.ArticleService;
import com.lxs.service.ArticleTagService;
import com.lxs.service.CategoryService;
import com.lxs.utils.BeanCopyUtils;
import com.lxs.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @user 潇洒
 * @date 2023/3/8-14:22
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleTagService articleTagService;
    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回

        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询十条
        Page<Article> page = new Page(1,10);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();

       /* //bean拷贝
        List<HotArticleVo> articleVos = new ArrayList<>();
        for (Article article : articles) {
            HotArticleVo vo = new HotArticleVo();
            BeanUtils.copyProperties(article,vo);
            articleVos.add(vo);
            System.out.println(vo+"asd"+article);
        }
*/
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //如果有categoryId 就要 查询时和传入的相同
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //状态是正式发布的
        lambdaQueryWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        lambdaQueryWrapper.eq(Article::getDelFlag,SystemConstants.STATUS_NORMAL);
        //对idTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);
        //查询categoryName
        List<Article> articles = page.getRecords();
//        //categoryId查询categoryName
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        //1.
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        

        
        //封装查询结果vo
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);



        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 获取文章细节
     *
     * @param id id
     * @return {@code ResponseResult}
     */
    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis中获取viewCount
        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        //转换成vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
//        article.setViewCount(viewCount.longValue());
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Long viewCount1 = article.getViewCount()+viewCount;
        article.setViewCount(viewCount1);
        Category category = categoryService.getById(categoryId);
        if (category != null) {
            articleDetailVo.setCategoryName(category.getName());
        }
        //返回
        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中id对应的浏览量
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult addArticle(AddArticleDto addArticleDto) {
        Article article = BeanCopyUtils.copyBean(addArticleDto, Article.class);
        save(article);

        List<ArticleTag> list = addArticleDto.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(list);

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listArticle(Integer pageNum, Integer pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemConstants.STATUS_NORMAL);
        queryWrapper.like(Objects.nonNull(title),Article::getTitle,title);
        queryWrapper.like(Objects.nonNull(summary),Article::getSummary,summary);
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page, queryWrapper);
        List<Article> records = page.getRecords();
        PageVo pageVo = new PageVo(records,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult inquireArticle(Long id) {
        //根据id查询文章
        Article article = getById(id);
        PUTArticleVo putArticleVo = BeanCopyUtils.copyBean(article, PUTArticleVo.class);
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId, putArticleVo.getId());
        List<Long> tagList = articleTagService.list(queryWrapper).stream()
                .map(ArticleTag::getTagId)
                .collect(Collectors.toList());
        putArticleVo.setTags(tagList);
        return ResponseResult.okResult(putArticleVo);
    }

    @Override
    public ResponseResult PUTArticle(PUTArticleVo putArticleVo) {
        updateById( BeanCopyUtils.copyBean(putArticleVo, Article.class));
        List<Long> tags = putArticleVo.getTags();
        LambdaQueryWrapper<ArticleTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleTag::getArticleId,putArticleVo.getId());
        articleTagService.remove(queryWrapper);
        List<ArticleTag> collect = tags.stream()
                .map(tagId -> new ArticleTag(putArticleVo.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(collect);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}
/*
1.
@Override
  public Article apply(Article article) {
      //获取分类id 查询分类信息 获取分类名称
      Category category = categoryService.getById(article.getCategoryId());
      String name = category.getName();
      article.setCategoryName(name);
      return article;
  }*/