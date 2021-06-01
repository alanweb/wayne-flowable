package com.wayne.system.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wayne.common.controller.BaseController;
import com.wayne.common.entity.QueryRequest;
import com.wayne.common.entity.ResponseBo;
import com.wayne.system.entity.User;
import com.wayne.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	private static final String ON = "on";

	@RequestMapping("user")
	public String index(Model model) {
		model.addAttribute("user", null);
		return "system/user/user";
	}

	@RequestMapping("user/checkUserName")
	@ResponseBody
	public boolean checkUserName(String username, String oldusername) {
		if (StringUtils.isNotBlank(oldusername) && username.equalsIgnoreCase(oldusername)) {
			return true;
		}
		User result = this.userService.findByName(username);
		return result == null;
	}

	@RequestMapping("user/getUser")
	@ResponseBody
	public ResponseBo getUser(Long userId) {
		try {
			User user = this.userService.findById(userId);
			return ResponseBo.ok(user);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("获取用户信息失败，请联系网站管理员！");
		}
	}

	@RequestMapping("user/list")
	@ResponseBody
	public Map<String, Object> userList(QueryRequest request, User user) {
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		PageInfo<User> pageInfo = new PageInfo<>(new ArrayList<>());
		return getDataTable(pageInfo);
	}


	@RequestMapping("user/theme")
	@ResponseBody
	public ResponseBo updateTheme(User user) {
		try {
			this.userService.updateTheme(user.getTheme(), user.getUsername());
			return ResponseBo.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error();
		}
	}

	@RequestMapping("user/add")
	@ResponseBody
	public ResponseBo addUser(User user, Long[] roles) {
		try {
			if (ON.equalsIgnoreCase(user.getStatus()))
				user.setStatus(User.STATUS_VALID);
			else
				user.setStatus(User.STATUS_LOCK);
			this.userService.addUser(user, roles);
			return ResponseBo.ok("新增用户成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("新增用户失败，请联系网站管理员！");
		}
	}
	@RequestMapping("user/update")
	@ResponseBody
	public ResponseBo updateUser(User user, Long[] rolesSelect) {
		try {
			if (ON.equalsIgnoreCase(user.getStatus()))
				user.setStatus(User.STATUS_VALID);
			else
				user.setStatus(User.STATUS_LOCK);
			this.userService.updateUser(user, rolesSelect);
			return ResponseBo.ok("修改用户成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("修改用户失败，请联系网站管理员！");
		}
	}

	@RequestMapping("user/delete")
	@ResponseBody
	public ResponseBo deleteUsers(String ids) {
		try {
			this.userService.deleteUsers(ids);
			return ResponseBo.ok("删除用户成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("删除用户失败，请联系网站管理员！");
		}
	}

	@RequestMapping("user/checkPassword")
	@ResponseBody
	public boolean checkPassword(String password) {
		return true;
	}

	@RequestMapping("user/updatePassword")
	@ResponseBody
	public ResponseBo updatePassword(String newPassword) {
		try {
			this.userService.updatePassword(newPassword);
			return ResponseBo.ok("更改密码成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("更改密码失败，请联系网站管理员！");
		}
	}

	@RequestMapping("user/profile")
	public String profileIndex(Model model) {
		User user = this.userService.findUserProfile(null);
		String ssex = user.getSsex();
		if (User.SEX_MALE.equals(ssex)) {
			user.setSsex("性别：男");
		} else if (User.SEX_FEMALE.equals(ssex)) {
			user.setSsex("性别：女");
		} else {
			user.setSsex("性别：保密");
		}
		model.addAttribute("user", user);
		return "system/user/profile";
	}

	@RequestMapping("user/getUserProfile")
	@ResponseBody
	public ResponseBo getUserProfile(Long userId) {
		try {
			User user = new User();
			user.setUserId(userId);
//			return ResponseBo.ok(this.userService.findUserProfile(user));
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("获取用户信息失败，请联系网站管理员！");
		}
	}

	@RequestMapping("user/updateUserProfile")
	@ResponseBody
	public ResponseBo updateUserProfile(User user) {
		try {
			//this.userService.updateUserProfile(user);
			return ResponseBo.ok("更新个人信息成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("获取用户信息失败，请联系网站管理员！");
		}
	}

	@RequestMapping("user/changeAvatar")
	@ResponseBody
	public ResponseBo changeAvatar(String imgName) {
		try {
			String[] img = imgName.split("/");
			String realImgName = img[img.length-1];
			//User user = getCurrentUser();
			//user.setAvatar(realImgName);
			//this.userService.updateNotNull(user);
			return ResponseBo.ok("更新头像成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("更新头像失败，请联系网站管理员！");
		}
	}
}
