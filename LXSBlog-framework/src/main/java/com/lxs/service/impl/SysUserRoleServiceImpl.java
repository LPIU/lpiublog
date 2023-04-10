package com.lxs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lxs.service.SysUserRoleService;
import com.lxs.domain.entity.SysUserRole;
import com.lxs.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;

/**
 * 用户和角色关联表(SysUserRole)表服务实现类
 *
 * @author makejava
 * @since 2023-04-03 21:02:48
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
