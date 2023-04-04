package com.lxs.controller;

import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.AddArticleDto;
import com.lxs.domain.vo.PUTArticleVo;
import com.lxs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @user 潇洒
 * @date 2023/3/21-13:58
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping("")
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticleDto){
        return articleService.addArticle(addArticleDto);
    }
    @GetMapping("/list")
    public ResponseResult listArticle(Integer pageNum,Integer pageSize,String title,String summary ){
        return articleService.listArticle(pageNum,pageSize,title,summary);
    }
    @GetMapping("/{id}")
    public ResponseResult inquireArticle(@PathVariable Long id){
        return articleService.inquireArticle(id);
    }



    @PutMapping("")
    public ResponseResult PUTArticle(@RequestBody PUTArticleVo putArticleVo){
        return articleService.PUTArticle(putArticleVo);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable Long id){
        return articleService.deleteArticle(id);
    }
}
