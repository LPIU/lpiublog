package com.lxs.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lxs.domain.entity.Article;
import com.lxs.service.ArticleService;
import com.lxs.utils.RedisCache;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @user 潇洒
 * @date 2023/3/14-15:33
 */
@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCount(){
        //查询获redis中浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");
//        List<Article> articles = viewCountMap.entrySet()
//                .stream()
//                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
//                .collect(Collectors.toList());
//        //更新到数据库中
//        for (Article article : articles) {
//            Article byId = articleService.getById(article.getId());
//            Long viewCount = byId.getViewCount();
//            byId.setViewCount(viewCount+article.getViewCount());
//            articleService.updateById(byId);
//        }
        viewCountMap.forEach((key, value) -> {
            if (!value.equals(0)) {
                Long viewCount = articleService.getById(Long.valueOf(key)).getViewCount();
                Article article = new Article(Long.valueOf(key),viewCount+value);
                articleService.updateById(article);
                Map<String, Integer> stringIntegerMap = new HashMap<>();
                stringIntegerMap.put(key,0);
                redisCache.setCacheMap("article:viewCount",stringIntegerMap);
            }
        });


    }
}
