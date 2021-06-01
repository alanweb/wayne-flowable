package com.wayne.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wayne.system.entity.User;
import com.wayne.system.entity.UserWithRole;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserMapper extends BaseMapper<User> {

	List<User> findUserWithDept(User user);
	
	List<UserWithRole> findUserWithRole(Long userId);
	
	User findUserProfile(User user);
}