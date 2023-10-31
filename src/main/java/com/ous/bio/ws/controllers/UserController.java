package com.ous.bio.ws.controllers;

import com.ous.bio.ws.exceptions.UserException;
import com.ous.bio.ws.responses.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ous.bio.ws.responses.UserResponse;
import com.ous.bio.ws.repositories.UserRepository;
import com.ous.bio.ws.requests.UserRequest;
import com.ous.bio.ws.services.Impl.UserServiceImpl;
import com.ous.bio.ws.shared.dto.UserDto;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@ComponentScan(basePackageClasses = UserRepository.class)
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserServiceImpl userService;

	@GetMapping(
			path = "/{id}",
			produces = {
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_VALUE
			})
	public ResponseEntity<UserResponse> getUser(@PathVariable String id) {

		UserDto createUser = userService.getUserByUserId(id);

		UserResponse userResponse = new UserResponse();

		BeanUtils.copyProperties(createUser, userResponse);

		return ResponseEntity.ok(userResponse);
	}

	@GetMapping(produces = {
			MediaType.APPLICATION_JSON_VALUE
	})
	public List<UserResponse> getAllUsers(
										  @RequestParam(value = "page",   defaultValue = "1") int page,
										  @RequestParam(value = "limit",  defaultValue = "3") int limit,
										  @RequestParam(value = "search", defaultValue = "") String search,
										  @RequestParam(value = "status", defaultValue = "0") int status
										){

		List<UserResponse> usersResponse = new ArrayList<>();

		List<UserDto> users = userService.getUsers(page, limit, search, status);

		users.forEach(userDto -> {

			ModelMapper modelMapper = new ModelMapper();

			UserResponse user = modelMapper.map(userDto, UserResponse.class);

			usersResponse.add(user);
		});

		return usersResponse;
	}

	/*@PostMapping
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) throws Exception {

		if(userRequest.getFirstName().isEmpty()) throw new UserException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userRequest, UserDto.class);

		UserDto createdUserDto = userService.createUser(userDto);
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(createdUserDto, userResponse);

		return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
	}*/
	@PostMapping(
			consumes={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces={MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
	)
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) throws Exception {

		if(userRequest.getFirstName().isEmpty()) throw new UserException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDto = modelMapper.map(userRequest, UserDto.class);

		UserDto createUser = userService.createUser(userDto);

		UserResponse userResponse =  modelMapper.map(createUser, UserResponse.class);



		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.CREATED);

	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable String id, @RequestBody UserRequest userRequest) {

		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userRequest, userDto);

		UserDto updateUser = userService.updateUser(id, userDto);

		UserResponse userResponse = new UserResponse();

		BeanUtils.copyProperties(updateUser, userResponse);

		return new ResponseEntity<>(userResponse, HttpStatus.ACCEPTED);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable String id) {

		userService.deleteUser(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}