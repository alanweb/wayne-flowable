package com.wayne.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wayne.system.entity.Role;
import com.wayne.system.entity.RoleWithMenu;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> findUserRole(String userName);

    List<RoleWithMenu> findById(Long roleId);
}