package com.wayne.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wayne.system.entity.RoleMenu;
import com.wayne.system.mapper.RoleMenuMapper;
import com.wayne.system.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service("roleMenuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    @Transactional
    public void deleteRoleMenusByRoleId(String roleIds) {
        List<String> list = Arrays.asList(roleIds.split(","));
        baseMapper.deleteBatchIds(list);
        baseMapper.delete(new QueryWrapper<RoleMenu>().in("role_Id", list));
    }

    @Override
    @Transactional
    public void deleteRoleMenusByMenuId(String menuIds) {
        List<String> list = Arrays.asList(menuIds.split(","));
        baseMapper.delete(new QueryWrapper<RoleMenu>().in("menu_Id", list));
    }

}
