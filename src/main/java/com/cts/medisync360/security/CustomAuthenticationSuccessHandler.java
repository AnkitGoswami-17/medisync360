package com.cts.medisync360.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.cts.medisync360.entity.User;
import com.cts.medisync360.entity.UserType;
import com.cts.medisync360.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	
	private final UserService userService;
	
	public CustomAuthenticationSuccessHandler(UserService userService) {
		this.userService=userService;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,Authentication authentication) throws IOException,ServletException {
		String userName = authentication.getName();
		User user = userService.getUserByName(userName);
		
		if (user.getUserType() == UserType.DOCTOR) {
			response.sendRedirect("/doctor");
		} else {
			response.sendRedirect("/patient");
		}
	}
	
	
}
