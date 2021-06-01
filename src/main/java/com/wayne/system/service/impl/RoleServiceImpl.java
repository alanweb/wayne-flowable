package com.wayne.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wayne.system.entity.Role;
import com.wayne.system.entity.RoleMenu;
import com.wayne.system.entity.RoleWithMenu;
import com.wayne.system.mapper.RoleMapper;
import com.wayne.system.mapper.RoleMenuMapper;
import com.wayne.system.service.RoleMenuService;
import com.wayne.system.service.RoleService;
import com.wayne.system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("roleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<Role> findUserRole(String userName) {
        return this.roleMapper.findUserRole(userName);
    }

    @Override
    public List<Role> findAllRole(Role role) {
        try {
            QueryWrapper<Role> wrapper = new QueryWrapper<>();
            wrapper.eq("role_name", role.getRoleName())
                    .orderByDesc("create_time");
            return baseMapper.selectList(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Role findByName(String roleName) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("lower(role_name)", roleName.toLowerCase());
        List<Role> list = baseMapper.selectList(wrapper);
        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    @Transactional
    public void addRole(Role role, Long[] menuIds) {
        this.save(role);
        setRoleMenus(role, menuIds);
    }

    private void setRoleMenus(Role role, Long[] menuIds) {
        for (Long menuId : menuIds) {
            RoleMenu rm = new RoleMenu();
            rm.setMenuId(menuId);
            rm.setRoleId(role.getRoleId());
            this.roleMenuMapper.insert(rm);
        }
    }

    @Override
    @Transactional
    public void deleteRoles(String roleIds) {
        List<String> list = Arrays.asList(roleIds.split(","));
        baseMapper.deleteBatchIds(list);
        this.roleMenuService.deleteRoleMenusByRoleId(roleIds);
        this.userRoleService.deleteUserRolesByRoleId(roleIds);

    }

    @Override
    public RoleWithMenu findRoleWithMenus(Long roleId) {
        List<RoleWithMenu> list = this.roleMapper.findById(roleId);
        List<Long> menuList = new ArrayList<>();
        for (RoleWithMenu rwm : list) {
            menuList.add(rwm.getMenuId());
        }
        if (list.size() == 0) {
            return null;
        }
        RoleWithMenu roleWithMenu = list.get(0);
        roleWithMenu.setMenuIds(menuList);
        return roleWithMenu;
    }

    @Override
    @Transactional
    public void updateRole(Role role, Long[] menuIds) {
        baseMapper.updateById(role);
        roleMenuMapper.delete(new QueryWrapper<RoleMenu>().eq("role_id", role.getRoleId()));
        setRoleMenus(role, menuIds);
    }

}
