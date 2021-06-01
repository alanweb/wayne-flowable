package com.wayne.system.controller;

import com.wayne.common.entity.ResponseBo;
import com.wayne.common.util.vcode.Captcha;
import com.wayne.common.util.vcode.GifCaptcha;
import com.wayne.system.entity.User;
import com.wayne.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController  {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/login")
	@ResponseBody
	public ResponseBo login(String username, String password, String code, Boolean rememberMe) {
		if (!StringUtils.isNotBlank(code)) {
			return ResponseBo.warn("验证码不能为空！");
		}
		this.userService.updateLoginTime(username);
		return ResponseBo.ok();
	}

	@GetMapping(value = "gifCode")
	public void getGifCode(HttpServletResponse response) {
		try {
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/gif");

			Captcha captcha = new GifCaptcha(146, 33, 4);
			captcha.out(response.getOutputStream());
//			Session session = super.getSession();
//			session.removeAttribute("_code");
//			session.setAttribute("_code", captcha.text().toLowerCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/")
	public String redirectIndex() {
		return "redirect:/index";
	}

	@GetMapping("/403")
	public String forbid() {
		return "403";
	}

	@RequestMapping("/index")
	public String index(Model model) {
		model.addAttribute("user", new User());
		return "index";
	}
}
