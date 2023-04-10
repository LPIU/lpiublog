package com.lxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.UpdateById;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxs.constants.SystemConstants;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.TagListDto;
import com.lxs.domain.dto.TagPUTDto;
import com.lxs.domain.entity.Category;
import com.lxs.domain.entity.Tag;
import com.lxs.domain.vo.CategoryVo;
import com.lxs.domain.vo.PageVo;
import com.lxs.domain.vo.TagVo;
import com.lxs.mapper.TagMapper;
import com.lxs.utils.BeanCopyUtils;

import com.lxs.utils.SecurityUtils;
import jdk.nashorn.internal.parser.Lexer;
import org.springframework.stereotype.Service;
import com.lxs.service.TagService;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-03-15 13:28:25
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {


    @Override
    public ResponseResult<PageVo> pagTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        queryWrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page, queryWrapper);
        //封装数据返回
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTag(Tag tag) {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        tag.setCreateBy(userId);
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<TagVo> inquireTag(Long id) {
        Tag byId = getById(id);
        TagVo tagVo = BeanCopyUtils.copyBean(byId, TagVo.class);
        return ResponseResult.okResult(tagVo);
    }

    @Override
    public ResponseResult updateTag(TagPUTDto tagPUTDto) {
        updateById(BeanCopyUtils.copyBean(tagPUTDto, Tag.class));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllTag() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getDelFlag, SystemConstants.STATUS_NORMAL);
        List<Tag> list = list(queryWrapper);
        List<TagVo> voList = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return ResponseResult.okResult(voList);
    }


}
