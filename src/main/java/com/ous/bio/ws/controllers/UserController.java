package com.ous.bio.ws.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import com.ous.bio.ws.reponses.UserResponse;
import com.ous.bio.ws.repositories.UserRepository;
import com.ous.bio.ws.requests.UserRequest;
import com.ous.bio.ws.services.Impl.UserServiceImpl;
import com.ous.bio.ws.shared.dto.UserDto;

@RestController
@ComponentScan(basePackageClasses = UserRepository.class)
@RequestMapping("/users") //	localhost:8080/users
public class UserController {
	@Autowired
	private UserServiceImpl userService;

	@GetMapping(path = "/{id}")
	public UserResponse getUser(@PathVariable String id) {

		UserDto createUser = userService.getUserByUserId(id);

		UserResponse userResponse = new UserResponse();

		BeanUtils.copyProperties(createUser, userResponse);

		return userResponse;
	}

	@PostMapping
	public UserResponse createUser(@RequestBody UserRequest userRequest) {

		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userRequest, userDto);

		UserDto createdUserDto = userService.createUser(userDto);
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(createdUserDto, userResponse);

		return userResponse;
	}

	@PutMapping(path = "/{id}")
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserRequest userRequest) {

		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userRequest, userDto);

		UserDto updateUser = userService.updateUser(id, userDto);

		UserResponse userResponse = new UserResponse();

		BeanUtils.copyProperties(updateUser, userResponse);

		return userResponse;	}

	@DeleteMapping(path = "/{id}")
	public String deleteUser(@PathVariable String id) {

		userService.deleteUser(id);

		return "";
	}
}
