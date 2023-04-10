package com.lxs.service.impl;

import com.lxs.domain.ResponseResult;
import com.lxs.domain.entity.LoginUser;
import com.lxs.domain.entity.User;
import com.lxs.domain.vo.BlogUserLoginVo;
import com.lxs.domain.vo.UserInfoVo;
import com.lxs.service.BlogLoginService;
import com.lxs.service.LoginService;
import com.lxs.utils.BeanCopyUtils;
import com.lxs.utils.JwtUtil;
import com.lxs.utils.RedisCache;
import com.lxs.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @user 潇洒
 * @date 2023/3/11-13:00
 */
@Service
public class SystemLoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }

        //以获取userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        //把用户信息存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //删除redis中对应的user
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }


}
