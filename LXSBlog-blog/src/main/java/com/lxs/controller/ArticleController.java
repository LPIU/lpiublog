package com.lxs.controller;

import com.lxs.domain.ResponseResult;
import com.lxs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @user 潇洒
 * @date 2023/3/8-14:27
 */
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {


    @Autowired
    private ArticleService articleService;
    /*@GetMapping("/list")
    public String test(){
        return articleService.list().toString();
    }*/
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        //查询热门文章 封装成ResponseResult返回
        ResponseResult result = articleService.hotArticleList();
        return result;
    }
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }
}
