package com.lxs.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lxs.domain.entity.Article;
import com.lxs.mapper.ArticleMapper;
import com.lxs.utils.RedisCache;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @user 潇洒
 * @date 2023/3/14-14:23
 */
@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;
    @Override
    public void run(String... args) throws Exception {
        //查询博客信息 id ViewCount
        List<Article> articles = articleMapper.selectList(new LambdaQueryWrapper<Article>().eq(Article::getDelFlag,0));
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(
                        article1 -> article1.getId().toString(),
                        article -> 0
                        )
                );
        //存储到redis
        redisCache.setCacheMap("article:viewCount",viewCountMap);
    }
}
