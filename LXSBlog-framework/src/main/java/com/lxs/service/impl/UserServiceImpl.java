package com.lxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.UserAddDto;
import com.lxs.domain.entity.Role;
import com.lxs.domain.entity.SysUserRole;
import com.lxs.domain.entity.User;
import com.lxs.domain.enums.AppHttpCodeEnum;
import com.lxs.domain.vo.PageVo;
import com.lxs.domain.vo.UserInfoVo;
import com.lxs.domain.vo.UserSelectByIdVo;
import com.lxs.domain.vo.UserVo;
import com.lxs.exception.SystemException;
import com.lxs.mapper.UserMapper;
import com.lxs.service.RoleService;
import com.lxs.service.SysUserRoleService;
import com.lxs.service.UserService;
import com.lxs.utils.BeanCopyUtils;
import com.lxs.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-03-10 21:27:32
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private RoleService roleService;

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

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    /**
     * 注册用户
     *
     * @param user 用户
     * @return {@code ResponseResult}
     */
    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName()) ){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName()) ){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword()) ){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail()) ){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        //对数据进行是否存在判断
        if (existName(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (existEmail(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        if (existNikeName(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NIKENAME_EXIST);
        }
        //对数据加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        //存数据库
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult pageAllUser(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(userName),User::getUserName,userName);
        queryWrapper.eq(Objects.nonNull(phonenumber),User::getPhonenumber,phonenumber);
        queryWrapper.eq(Objects.nonNull(status),User::getStatus,status);
        Page<User> page=new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        List<User> list = page.getRecords();
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(list, UserVo.class);
        PageVo pageVo = new PageVo(userVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addUserAndRole(UserAddDto userAddDto) {
        User user = BeanCopyUtils.copyBean(userAddDto, User.class);
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        save(user);
        Long id = user.getId();
        Long[] roleIds = userAddDto.getRoleIds();
        for (Long roleId : roleIds) {
            sysUserRoleService.save(new SysUserRole(id,roleId));
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delUser(Long id) {
        Long userId = SecurityUtils.getUserId();
        if (userId.equals(id)){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectUserById(Long id) {
        User user = getById(id);

        List<Role> list = roleService.list();
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.select(SysUserRole::getRoleId).eq(SysUserRole::getUserId,id);
        List<SysUserRole> sysUserRoles = sysUserRoleService.list(queryWrapper);
        List<Long> collect = sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        UserSelectByIdVo userSelectByIdVo = new UserSelectByIdVo(collect,list,BeanCopyUtils.copyBean(user, UserInfoVo.class));

        return ResponseResult.okResult(userSelectByIdVo);
    }


    private boolean existEmail(String email) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,email);
        return count(queryWrapper)>0;
    }

    private boolean existName(String userName) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userName);

        return count(queryWrapper)>0;
    }
    private boolean existNikeName(String userName) {
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,userName);

        return count(queryWrapper)>0;
    }


}
