package com.lxs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.UserAddDto;
import com.lxs.domain.dto.UserPutDto;
import com.lxs.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-03-10 21:27:32
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult pageAllUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status);

    ResponseResult addUserAndRole(UserAddDto userAddDto);

    ResponseResult delUser(Long id);

    ResponseResult selectUserById(Long id);

    ResponseResult putUser(UserPutDto userPutDto);
}
