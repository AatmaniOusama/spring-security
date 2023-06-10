package com.ous.bio.ws.services.Impl;

import com.ous.bio.ws.entities.UserEntity;
import com.ous.bio.ws.repositories.UserRepository;
import com.ous.bio.ws.services.UserService;
import com.ous.bio.ws.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setEncryptedPassword("Test password");
        userEntity.setUserId("user test id");
        UserEntity createdUserEntity = userRepository.save(userEntity);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(createdUserEntity, userDto);
        return userDto;
    }
}

