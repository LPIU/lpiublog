package com.lxs.controller;

import com.lxs.domain.ResponseResult;
import com.lxs.domain.dto.RoleAddDto;
import com.lxs.domain.dto.RolePutByIdDto;
import com.lxs.domain.dto.RolePutDto;
import com.lxs.domain.dto.TagListDto;
import com.lxs.domain.vo.PageVo;
import com.lxs.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @user 潇洒
 * @date 2023/4/3-15:58
 */
@RestController
@RequestMapping("system/role")
public class RoleSystemController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult<PageVo> list(Integer pageNum, Integer pageSize, String roleName,String status){
        return roleService.pageRoleList(pageNum,pageSize,roleName,status);
    }
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RolePutDto rolePutDto){
        return roleService.changeStatus(rolePutDto);
    }


    @PostMapping("")
    public ResponseResult putRoleById(@RequestBody RoleAddDto roleAddDto){
        return roleService.putRoleById(roleAddDto);
    }
    @GetMapping("/{id}")
    public ResponseResult selectRoleById(@PathVariable String id){
        return roleService.selectRoleById(id);
    }

    @PutMapping("")
    public ResponseResult putRole(@RequestBody RolePutByIdDto rolePutByIdDto){
        return roleService.putRole(rolePutByIdDto);
    }
    @DeleteMapping("/{id}")
    public ResponseResult delRole(@PathVariable Long id){
        return roleService.delRole(id);
    }
    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){
        return roleService.listAllRole();
    }
}
