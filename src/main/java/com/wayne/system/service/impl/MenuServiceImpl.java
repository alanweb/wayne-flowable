package com.wayne.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wayne.common.entity.Tree;
import com.wayne.common.util.TreeUtils;
import com.wayne.system.entity.Menu;
import com.wayne.system.mapper.MenuMapper;
import com.wayne.system.service.MenuService;
import com.wayne.system.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service("menuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public List<Menu> findUserPermissions(String userName) {
        return this.menuMapper.findUserPermissions(userName);
    }

    @Override
    public List<Menu> findUserMenus(String userName) {
        return this.menuMapper.findUserMenus(userName);
    }

    @Override
    public List<Menu> findAllMenus(Menu menu) {
        return null;
    }

    @Override
    public Tree<Menu> getMenuButtonTree() {
        List<Tree<Menu>> trees = new ArrayList<>();
        List<Menu> menus = this.findAllMenus(new Menu());
        buildTrees(trees, menus);
        return TreeUtils.build(trees);
    }

    @Override
    public Tree<Menu> getMenuTree() {
        List<Tree<Menu>> trees = new ArrayList<>();
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper
                .eq("type", 0)
                .orderByAsc("create_time");
        List<Menu> menus = baseMapper.selectList(wrapper);
        buildTrees(trees, menus);
        return TreeUtils.build(trees);
    }

    private void buildTrees(List<Tree<Menu>> trees, List<Menu> menus) {
        for (Menu menu : menus) {
            Tree<Menu> tree = new Tree<>();
            tree.setId(menu.getMenuId().toString());
            tree.setParentId(menu.getParentId().toString());
            tree.setText(menu.getMenuName());
            trees.add(tree);
        }
    }

    @Override
    public Tree<Menu> getUserMenu(String userName) {
        List<Tree<Menu>> trees = new ArrayList<>();
        List<Menu> menus = this.findUserMenus(userName);
        for (Menu menu : menus) {
            Tree<Menu> tree = new Tree<>();
            tree.setId(menu.getMenuId().toString());
            tree.setParentId(menu.getParentId().toString());
            tree.setText(menu.getMenuName());
            tree.setIcon(menu.getIcon());
            tree.setUrl(menu.getUrl());
            trees.add(tree);
        }
        return TreeUtils.build(trees);
    }

    @Override
    public Menu findByNameAndType(String menuName, String type) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("type", Long.valueOf(type))
                .eq("lower(menu_name)", menuName.toLowerCase());
        List<Menu> list = baseMapper.selectList(wrapper);
        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    @Transactional
    public void addMenu(Menu menu) {
        menu.setCreateTime(new Date());
        if (menu.getParentId() == null)
            menu.setParentId(0L);
        this.save(menu);
    }

    @Override
    @Transactional
    public void deleteMenus(String menuIds) {
        List<String> list = Arrays.asList(menuIds.split(","));
        baseMapper.deleteBatchIds(list);
        this.roleMenuService.deleteRoleMenusByMenuId(menuIds);
        this.menuMapper.changeToTop(list);
    }

    @Override
    public Menu findById(Long menuId) {
        return baseMapper.selectById(menuId);
    }

    @Override
    @Transactional
    public void updateMenu(Menu menu) {
        menu.setModifyTime(new Date());
        if (menu.getParentId() == null)
            menu.setParentId(0L);
        baseMapper.updateById(menu);
    }

}
