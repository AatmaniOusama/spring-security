package com.ous.bio.ws.services.Impl;

import com.ous.bio.ws.requests.UserRequest;
import com.ous.bio.ws.shared.dto.AddressDto;
import com.ous.bio.ws.shared.dto.ContactDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ous.bio.ws.entities.UserEntity;
import com.ous.bio.ws.repositories.UserRepository;
import com.ous.bio.ws.services.UserService;
import com.ous.bio.ws.shared.Utils;
import com.ous.bio.ws.shared.dto.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Utils utils;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDto createUser(UserDto user) {

        ModelMapper modelMapper = new ModelMapper();

        UserEntity checkUser = userRepository.findByEmail(user.getEmail());

        if(checkUser != null) throw new RuntimeException("User already exist");

        IntStream.range(0, user.getAddresses().size())
                        .forEach(index -> {
                            AddressDto address = user.getAddresses().get(index);
                            address.setUser(user);
                            address.setAddressId(utils.generateStringId(30));
                            user.getAddresses().set(index, address);
                        });

        user.getContact().setContactId(utils.generateStringId(30));
        user.getContact().setUser(user);

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        userEntity.setUserId(utils.generateStringId(32));

        UserEntity createdUserEntity = userRepository.save(userEntity);

        return modelMapper.map(createdUserEntity, UserDto.class);
    }

    @Override
    public UserDto getUser(String email) {

        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) throw new RuntimeException();

        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    @Override
    public UserDto getUserByUserId(String userId) {

        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null) throw new RuntimeException();

        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {

        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null) throw new RuntimeException();

        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());

        UserEntity updatedUser = userRepository.save(userEntity);

        UserDto user = new UserDto();

        BeanUtils.copyProperties(updatedUser, user);

        return user;
    }

    @Override
    public void deleteUser(String userId) {

        UserEntity userEntity = userRepository.findByUserId(userId);

        if(userEntity == null) throw new UsernameNotFoundException(userId);

        userRepository.delete(userEntity);
    }

    @Override
    public List<UserDto> getUsers(int page, int limit, String search, int status) {

        if(page > 0) page -= 1;

        List<UserDto> usersDto = new ArrayList<>();

        Pageable pageRequest = PageRequest.of(page, limit);
        Page<UserEntity> usersPage;

        if(search.isEmpty()){
            usersPage = userRepository.findAllUsers(pageRequest);
        }else{
            usersPage = userRepository.findAllUsersByCriteria(pageRequest, search, status);
        }

        List<UserEntity> users = usersPage.getContent();

        users.forEach(userEntity -> {
            ModelMapper modelMapper = new ModelMapper();
            UserDto user = modelMapper.map(userEntity, UserDto.class);
            usersDto.add(user);
        });

        return usersDto;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null) throw new UsernameNotFoundException(email);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
}