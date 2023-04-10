package com.lxs.controller;

import com.lxs.domain.ResponseResult;

import com.lxs.domain.entity.LoginUser;
import com.lxs.domain.entity.Menu;
import com.lxs.domain.entity.User;
import com.lxs.domain.enums.AppHttpCodeEnum;
import com.lxs.domain.vo.AdminUserInfoVo;
import com.lxs.domain.vo.RoutersVo;
import com.lxs.domain.vo.UserInfoVo;
import com.lxs.exception.SystemException;
import com.lxs.service.LoginService;
import com.lxs.service.MenuService;
import com.lxs.service.RoleService;
import com.lxs.service.UserService;
import com.lxs.utils.BeanCopyUtils;
import com.lxs.utils.RedisCache;
import com.lxs.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @user 潇洒
 * @date 2023/3/15-14:38
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;


    @Autowired
    private RedisCache redisCache;
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示 必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo>  getInfo(){
        //查询当前登录用户id
        LoginUser loginUser = SecurityUtils.getLoginUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        //用户id查询权限信息
        List<String> perms= menuService.selectPermsByUserId(loginUser.getUser().getId());
        //用户id查询角色信息
        List<String> rolesKeyList= roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //封装返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,rolesKeyList,userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }
    @GetMapping("getRouters")
    public ResponseResult<RoutersVo>  getRouters(){
        //查询menu 结果是tree的形式
        List<Menu> menus= menuService.selectRouterMenuTreeByUserId(SecurityUtils.getUserId());
        //封装数据 返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }


}


