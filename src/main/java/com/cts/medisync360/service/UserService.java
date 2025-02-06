package com.cts.medisync360.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cts.medisync360.entity.User;
import com.cts.medisync360.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User registerUser(User user) {
		user.setUserPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public User getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(
				() -> new RuntimeException("User not found with ID: " + id));
	}
	
	public User getUserByName(String userName) {
		return userRepository.findByUserName(userName);
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public User updateUser(Long id, User updatedUser) {
		User existingUser = userRepository.findById(id).orElseThrow(
				() -> new RuntimeException("User not found with ID: " + id));
		existingUser.setUserName(updatedUser.getUsername());
		existingUser.setUserPassword(updatedUser.getUserPassword());
		
		return userRepository.save(existingUser);
	}
	
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
	
}
