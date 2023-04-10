package com.lxs.controller;

import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.UserAddDto;
import com.lxs.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @user 潇洒
 * @date 2023/4/5-16:39
 */
@RestController
@RequestMapping("system/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, String userName,String phonenumber,String status){
        return  userService.pageAllUser(pageNum,pageSize,userName,phonenumber,status);
    }
    @PostMapping("")
    public ResponseResult addUserAndRole(@RequestBody UserAddDto userAddDto){
        return userService.addUserAndRole(userAddDto);
    }
    @DeleteMapping("/{id}")
    public ResponseResult delUser(@PathVariable Long id){
        return userService.delUser(id);
    }
    @GetMapping("/{id}")
    public ResponseResult selectUserById(@PathVariable Long id){
        return userService.selectUserById(id);
    }
}
