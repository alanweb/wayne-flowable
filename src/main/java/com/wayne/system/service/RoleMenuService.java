package com.wayne.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wayne.system.entity.RoleMenu;

public interface RoleMenuService extends IService<RoleMenu> {

	void deleteRoleMenusByRoleId(String roleIds);

	void deleteRoleMenusByMenuId(String menuIds);
}
