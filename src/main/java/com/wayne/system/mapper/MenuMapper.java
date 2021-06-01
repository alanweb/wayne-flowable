package com.wayne.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wayne.system.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MenuMapper extends BaseMapper<Menu> {
	
	List<Menu> findUserPermissions(String userName);
	
	List<Menu> findUserMenus(String userName);
	
	// 删除父节点，子节点变成顶级节点（根据实际业务调整）
	void changeToTop(List<String> menuIds);
}