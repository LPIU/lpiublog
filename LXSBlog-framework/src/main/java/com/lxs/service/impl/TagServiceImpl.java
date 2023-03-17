package com.lxs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxs.domain.entity.Tag;
import com.lxs.mapper.TagMapper;
import org.springframework.stereotype.Service;
import com.lxs.service.TagService;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-15 13:28:25
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}
