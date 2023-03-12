package com.lxs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.entity.Article;
import org.springframework.stereotype.Service;

/**
 * @user 潇洒
 * @date 2023/3/8-14:18
 */

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);
}
