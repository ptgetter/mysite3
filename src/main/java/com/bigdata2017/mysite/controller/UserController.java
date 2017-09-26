package com.bigdata2017.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bigdata2017.mysite.exception.UserDaoException;
import com.bigdata2017.mysite.service.UserService;
import com.bigdata2017.mysite.vo.UserVo;
import com.bigdata2017.security.Auth;
import com.bigdata2017.security.AuthUser;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute UserVo userVo) {
		userService.join(userVo);
		return "redirect:joinsuccess";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	
	//AuthLoginInterCeptor에서 처리
//	@RequestMapping(value="/login", method=RequestMethod.POST)
//	public String login(
//			HttpSession session,	// 추후 빼기
//			@RequestParam(value="email", required=true, defaultValue="") String email, 
//			@RequestParam(value="password", required=true, defaultValue="") String password) {
//		UserVo userVo = userService.getUser(email, password);
//		if(userVo == null) {
//			return "user/login_fail";
//		}
//		
//		session.setAttribute("authUser", userVo);
//		return "redirect:/";
//	}
	
	//AuthLogoutInterceptor
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//		session.removeAttribute("authUser");
//		session.invalidate();
//		return "redirect:/";
//	}
	
	@Auth
	@RequestMapping(value="modify", method=RequestMethod.GET)
	public String modify(@AuthUser UserVo authUser) {
		System.out.println(authUser);
		//UserVo userVo = userService.getUser(authUser.getNo());
		return "user/modify";
	}
	
	@ExceptionHandler( UserDaoException.class )
	public String handleUserDaoException() {
		// 로깅
		// 사과페이지
		return "error/exception";
	}
}
