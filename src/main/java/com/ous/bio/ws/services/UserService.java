package com.ous.bio.ws.services;

import com.ous.bio.ws.requests.UserRequest;
import com.ous.bio.ws.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    public UserDto createUser(UserDto userDto);
    public UserDto getUser(String email);
    public UserDto getUserByUserId(String userId);
    public UserDto updateUser(String userId, UserDto userDto);
    public void deleteUser(String userId);
    public List<UserDto> getUsers(int page, int limit, String search, int status);

}