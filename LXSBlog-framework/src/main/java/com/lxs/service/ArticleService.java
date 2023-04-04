package com.lxs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.AddArticleDto;
import com.lxs.domain.entity.Article;
import com.lxs.domain.vo.PUTArticleVo;
import org.springframework.stereotype.Service;

/**
 * @user 潇洒
 * @date 2023/3/8-14:18
 */

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto addArticleDto);

    ResponseResult listArticle(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult inquireArticle(Long id);

    ResponseResult PUTArticle(PUTArticleVo putArticleVo);

    ResponseResult deleteArticle(Long id);
}
