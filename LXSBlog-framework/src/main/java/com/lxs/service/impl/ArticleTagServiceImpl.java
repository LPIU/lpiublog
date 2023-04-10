package com.lxs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxs.domain.entity.ArticleTag;
import com.lxs.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;
import com.lxs.service.ArticleTagService;

/**
 * 文章标签关联表(ArticleTag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-21 13:38:17
 */
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

}
