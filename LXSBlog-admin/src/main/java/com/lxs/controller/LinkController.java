package com.lxs.controller;

import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.LinkAddDto;
import com.lxs.domain.dto.LinkPutDto;
import com.lxs.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @user 潇洒
 * @date 2023/4/10-13:15
 */
@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;
    @GetMapping("/list")
    public ResponseResult<Object> pageVoList(Integer pageNum, Integer pageSize, String name, String status ){
        return linkService.pageVoList(pageNum,pageSize,name,status);
    }
    @PostMapping("")
    public ResponseResult addLink(@RequestBody LinkAddDto linkAddDto){
        return linkService.addLink(linkAddDto);
    }

    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable String id){
        return linkService.getLinkById(id);
    }
    @PutMapping("")
    public ResponseResult putLink(@RequestBody LinkPutDto linkPutDto){
        return linkService.putLink(linkPutDto);
    }
    @DeleteMapping("/{id}")
    public ResponseResult delLink(@PathVariable String id){
        linkService.removeById(id);
        return ResponseResult.okResult();
    }
}
