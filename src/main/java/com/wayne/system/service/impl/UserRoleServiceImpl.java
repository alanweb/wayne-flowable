package com.wayne.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wayne.system.entity.UserRole;
import com.wayne.system.mapper.UserRoleMapper;
import com.wayne.system.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service("userRoleService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    @Transactional
    public void deleteUserRolesByRoleId(String roleIds) {
        List<String> list = Arrays.asList(roleIds.split(","));
        baseMapper.delete(new QueryWrapper<UserRole>().in("role_Id", list));
    }

    @Override
    @Transactional
    public void deleteUserRolesByUserId(String userIds) {
        List<String> list = Arrays.asList(userIds.split(","));
        baseMapper.delete(new QueryWrapper<UserRole>().in("user_Id", list));
    }

}
