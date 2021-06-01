package com.wayne.system.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wayne.common.controller.BaseController;
import com.wayne.common.entity.QueryRequest;
import com.wayne.common.entity.ResponseBo;
import com.wayne.system.entity.Role;
import com.wayne.system.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;

	@RequestMapping("role")
	public String index() {
		return "system/role/role";
	}

	@RequestMapping("role/list")
	@ResponseBody
	public Map<String, Object> roleList(QueryRequest request, Role role) {
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		List<Role> list = this.roleService.findAllRole(role);
		PageInfo<Role> pageInfo = new PageInfo<>(list);
		return getDataTable(pageInfo);
	}

	
	@RequestMapping("role/getRole")
	@ResponseBody
	public ResponseBo getRole(Long roleId) {
		try {
			Role role = this.roleService.findRoleWithMenus(roleId);
			return ResponseBo.ok(role);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("获取角色信息失败，请联系网站管理员！");
		}
	}

	@RequestMapping("role/checkRoleName")
	@ResponseBody
	public boolean checkRoleName(String roleName, String oldRoleName) {
		if (StringUtils.isNotBlank(oldRoleName) && roleName.equalsIgnoreCase(oldRoleName)) {
			return true;
		}
		Role result = this.roleService.findByName(roleName);
		return result == null;
	}

	@RequestMapping("role/add")
	@ResponseBody
	public ResponseBo addRole(Role role, Long[] menuId) {
		try {
			this.roleService.addRole(role, menuId);
			return ResponseBo.ok("新增角色成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("新增角色失败，请联系网站管理员！");
		}
	}
	@RequestMapping("role/delete")
	@ResponseBody
	public ResponseBo deleteRoles(String ids) {
		try {
			this.roleService.deleteRoles(ids);
			return ResponseBo.ok("删除角色成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("删除角色失败，请联系网站管理员！");
		}
	}
	@RequestMapping("role/update")
	@ResponseBody
	public ResponseBo updateRole(Role role, Long[] menuId) {
		try {
			this.roleService.updateRole(role, menuId);
			return ResponseBo.ok("修改角色成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("修改角色失败，请联系网站管理员！");
		}
	}
}
