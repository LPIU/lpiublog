package com.lxs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.TagListDto;
import com.lxs.domain.dto.TagPUTDto;
import com.lxs.domain.entity.Tag;
import com.lxs.domain.vo.PageVo;
import com.lxs.domain.vo.TagVo;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-03-15 13:28:24
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pagTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);

    ResponseResult addTag(Tag tagVo);

    ResponseResult deleteTag(Long id);

    ResponseResult<TagVo> inquireTag(Long id);

    ResponseResult updateTag(TagPUTDto tagPUTDto);

    ResponseResult listAllTag();
}
