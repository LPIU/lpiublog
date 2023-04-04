package com.lxs.controller;

import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.TagListDto;
import com.lxs.domain.dto.TagPUTDto;
import com.lxs.domain.entity.Tag;
import com.lxs.domain.vo.PageVo;

import com.lxs.domain.vo.TagVo;
import com.lxs.mapper.TagMapper;
import com.lxs.service.TagService;
import com.lxs.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @user 潇洒
 * @date 2023/3/15-13:33
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, TagListDto tagListDto){
        return tagService.pagTagList(pageNum,pageSize,tagListDto);
    }

    @PostMapping("")
    public ResponseResult AddTag(@RequestBody Tag tag){
        return tagService.addTag(tag);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id){
        return tagService.deleteTag(id);
    }
    @GetMapping("/{id}")
    public ResponseResult<TagVo> inquireTag(@PathVariable("id") Long id){
        return tagService.inquireTag(id);
    }
    @PutMapping("")
    public ResponseResult updateTag(@RequestBody TagPUTDto tagPUTDto){
        return tagService.updateTag(tagPUTDto);
    }
    @GetMapping("listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }
}
