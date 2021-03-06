package com.bigdata2017.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bigdata2017.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(MethodParameter methodParameter, 
			ModelAndViewContainer modelAndViewContainer, 
			NativeWebRequest nativeWebRequest,
			WebDataBinderFactory webDataBinderFactory) throws Exception {

		if( supportsParameter(methodParameter) == false) {
			return WebArgumentResolver.UNRESOLVED;
		}
		
		HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
		HttpSession session = request.getSession();
		if(session == null) {
			return null;
		}
				
		return session.getAttribute("authUser");
	}

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {

		AuthUser authUser = methodParameter.getParameterAnnotation(AuthUser.class);
		if(authUser == null) {
			return false;
		}
		
		if(methodParameter.getParameterType().equals(UserVo.class) == false) {
			return false;
		}
		return true;
	}

}
