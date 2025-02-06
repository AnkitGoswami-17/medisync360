package com.cts.medisync360.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.medisync360.dto.LoginRequestDto;
import com.cts.medisync360.entity.User;
import com.cts.medisync360.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/register")
	public User registerUser(@RequestBody User user) {
		return userService.registerUser(user);
	}
	
	@PostMapping("/login")
	public String login(@RequestBody LoginRequestDto loginRequestDto) {
	try {
			Authentication auth = authenticationManager.authenticate(
						 new UsernamePasswordAuthenticationToken(
								 loginRequestDto.getUserName(), loginRequestDto.getPassword())
						 );
//			auth.setAuthenticated(true);
		 return "Login Successful!"; 
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return "Invalid username or password!";
		}
	}
	
	@GetMapping("/logout") 
	public String logOut() {
		SecurityContextHolder.clearContext();
		return "logged out successfully";
	}
//	@GetMapping("/{userId}")
//	public User getUserById(@PathVariable Long userId) {
//		return userService.getUserById(userId);
//	}
//	
//	@GetMapping("/{userName}")
//	public User getUserByName(@PathVariable String userName) {
//		return userService.getUserByName(userName);
//	}
//	
//	@GetMapping()
//	public List<User> getAllUsers() {
//		return userService.getAllUsers();
//	}
//	
//	@PutMapping("/{userId}")
//	public User updateUser(@PathVariable Long userId, @RequestBody User updtedUser) {
//		return userService.updateUser(userId, updtedUser);
//	}
//	
//	@DeleteMapping("/{userId}")
//	public void deleteUser(@PathVariable Long userId) {
//		userService.deleteUser(userId);
//	}

}
