package com.lxs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.RoleAddDto;
import com.lxs.domain.dto.RolePutByIdDto;
import com.lxs.domain.dto.RolePutDto;
import com.lxs.domain.entity.Role;
import com.lxs.domain.entity.RoleMenu;
import com.lxs.domain.vo.PageVo;
import com.lxs.domain.vo.RoleVo;
import com.lxs.mapper.RoleMapper;
import com.lxs.service.RoleMenuService;
import com.lxs.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lxs.service.RoleService;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-03-16 13:08:26
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员 如果是返回集合中只需要有admin
        if (id == 1L) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
         }

        //负责查询用户所具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult pageRoleList(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasText(roleName),Role::getRoleName,roleName);
        queryWrapper.eq(StringUtils.hasText(status),Role::getStatus,status);
        Page<Role> page = new Page<>(pageNum,pageSize);
        page(page, queryWrapper);
        List<Role> records = page.getRecords();
        PageVo pageVo = new PageVo(records,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }


    @Override
    public ResponseResult changeStatus(RolePutDto rolePutDto) {
        Long id = Long.valueOf(rolePutDto.getRoleId());
        String status = rolePutDto.getStatus();
        Role byId = getById(id);
        byId.setStatus(status);
        updateById(byId);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult putRoleById(RoleAddDto roleAddDto) {
        Role role = BeanCopyUtils.copyBean(roleAddDto, Role.class);
        save(role);

        Long id = role.getId();
        for (Long menuId : roleAddDto.getMenuIds()) {
            roleMenuService.save(new RoleMenu(id,menuId));
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectRoleById(String id) {
        Role byId = getById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(byId, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    @Override
    public ResponseResult putRole(RolePutByIdDto rolePutByIdDto) {
        Role role = BeanCopyUtils.copyBean(rolePutByIdDto, Role.class);
        updateById(role);
//        roleMenuService.removeById()
        LambdaQueryWrapper<RoleMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RoleMenu::getRoleId,rolePutByIdDto.getId());
        roleMenuService.remove(lambdaQueryWrapper);
        for (Long menuId : rolePutByIdDto.getMenuIds()) {
            roleMenuService.save(new RoleMenu(rolePutByIdDto.getId(),menuId));
        }
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult delRole(Long id) {
        removeById(id);
        LambdaQueryWrapper<RoleMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RoleMenu::getRoleId,id);
        roleMenuService.remove(lambdaQueryWrapper);
        return null;
    }

    @Override
    public ResponseResult listAllRole() {
        List<Role> list = list();
        return ResponseResult.okResult(list);
    }
}
