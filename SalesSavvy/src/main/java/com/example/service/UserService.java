package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.repository.UserRepository;

@Service
public class UserService {
	UserRepository userRepository;
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public User registerUser(User user) {
		
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
			throw new RuntimeException("Username is already taken");
		}
		
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new RuntimeException("Email already exist, please login");
		}
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		
		return userRepository.save(user);
	}
}
