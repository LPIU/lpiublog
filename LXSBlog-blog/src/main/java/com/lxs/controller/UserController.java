package com.lxs.controller;

import com.lxs.annotation.SystemLog;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.entity.User;
import com.lxs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @user 潇洒
 * @date 2023/3/12-16:17
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }
    @PutMapping("/userInfo")
    @SystemLog(businessName = "更新用户信息")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
    @GetMapping("/asd/{ph}")
    public ResponseResult loginasd(@PathVariable String ph){
        return userService.loginasd(ph);
    }

}
