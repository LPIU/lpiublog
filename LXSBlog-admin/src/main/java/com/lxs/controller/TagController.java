package com.lxs.controller;

import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.TagListDto;
import com.lxs.domain.vo.PageVo;
import com.lxs.mapper.TagMapper;
import com.lxs.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}