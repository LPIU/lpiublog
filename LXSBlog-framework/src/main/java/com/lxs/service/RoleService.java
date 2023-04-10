package com.lxs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.RoleAddDto;
import com.lxs.domain.dto.RolePutByIdDto;
import com.lxs.domain.dto.RolePutDto;
import com.lxs.domain.entity.Role;
import com.lxs.domain.vo.PageVo;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-03-16 13:08:25
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult<PageVo> pageRoleList(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(RolePutDto rolePutDto);


    ResponseResult putRoleById(RoleAddDto roleAddDto);

    ResponseResult selectRoleById(String id);

    ResponseResult putRole(RolePutByIdDto rolePutByIdDto);

    ResponseResult delRole(Long id);

    ResponseResult listAllRole();
}
