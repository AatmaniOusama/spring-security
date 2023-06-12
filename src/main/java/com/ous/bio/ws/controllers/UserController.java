package com.ous.bio.ws.controllers;

import com.ous.bio.ws.services.Impl.UserServiceImpl;
import com.ous.bio.ws.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ous.bio.ws.reponses.UserResponse;
import com.ous.bio.ws.requests.UserRequest;

import java.beans.Beans;

@RestController
@RequestMapping("/users") //	localhost:8080/users
public class UserController {
	@Autowired
	private UserServiceImpl userService;

	@GetMapping
	public String getUser() {
		return "get user was called";
	}
	
	@PostMapping
	public UserResponse createUser(@RequestBody UserRequest userRequest) {
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userRequest, userDto);


		UserDto createdUserDto = userService.createUser(userDto);
		System.out.println(createdUserDto.getUserId());
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(createdUserDto, userResponse);

		return userResponse;
	}
	
	@PutMapping
	public String updateUser() {
		return "update user was called";
	}
	
	@DeleteMapping
	public String deleteUser() {
		return "delete user was called";
	}
}
