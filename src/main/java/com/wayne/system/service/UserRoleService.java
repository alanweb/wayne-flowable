package com.wayne.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wayne.system.entity.UserRole;

public interface UserRoleService extends IService<UserRole> {

	void deleteUserRolesByRoleId(String roleIds);

	void deleteUserRolesByUserId(String userIds);
}
