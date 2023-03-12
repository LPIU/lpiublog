package com.lxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.vo.UserInfoVo;
import com.lxs.service.UserService;
import com.lxs.utils.BeanCopyUtils;
import com.lxs.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.lxs.domain.entity.User;
import com.lxs.mapper.UserMapper;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-10 21:27:32
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }
}
