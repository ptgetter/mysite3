package com.bigdata2017.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bigdata2017.mysite.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 1. handler 종류확인
		if(handler instanceof HandlerMethod == false) {
			return true;
		}
		
		//2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3. Method @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		//3.1 메서드 말고 class에 @Auth가 붙은 경우 체크
		if(auth == null) {
			auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);
		}
		
		//4.@Auth가 안붙은 경우 인증체크할 필요가 없는 페이지이므로 true
		if(auth == null) {
			return true;
		}
		
		// 5. @Auth가 붙어있는경우 인증체크
		HttpSession session = request.getSession();
		if(session == null) { // 인증이 안되어있네
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// 6. 사용자가 인증했나?
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		// 관리자권한 체크
		if(auth.role() == Auth.Role.ADMIN) {
			if(authUser.getRole() == Auth.Role.ADMIN.toString()) {
				return true;
			}
			else {
//				response.getWriter().write("<script>alert('권한이 없습니다.');</script>");
//				response.sendRedirect(request.getContextPath() + "/");
//				return false;
				request.setAttribute("errorMessage", "권한 없음.");
				request.getRequestDispatcher( "/WEB-INF/views/error/exception2.jsp" ).
				forward( request, response );
			}	
		}
		
		// 모든 인증을 통과했으므로 true
		return true;
	}

}
