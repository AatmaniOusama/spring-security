package com.ous.bio.ws.services.Impl;

import com.ous.bio.ws.entities.AddressEntity;
import com.ous.bio.ws.entities.UserEntity;
import com.ous.bio.ws.repositories.AddressRepository;
import com.ous.bio.ws.repositories.UserRepository;
import com.ous.bio.ws.services.AddressService;
import com.ous.bio.ws.shared.Utils;
import com.ous.bio.ws.shared.dto.AddressDto;
import com.ous.bio.ws.shared.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.bytebuddy.description.method.MethodDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Utils utils;


    @Override
    public List<AddressDto> getAllAddresses(String email) {

        UserEntity currentUser = userRepository.findByEmail(email);

        List<AddressEntity> addresses = currentUser.getAdmin() ? addressRepository.findAll() :
                                                                 addressRepository.findByUser(currentUser);

        Type listType = new TypeToken<List<AddressDto>>() {}.getType();
        List<AddressDto> addressesDto = new ModelMapper().map(addresses, listType);

        return addressesDto;
    }

    @Override
    public AddressDto createAddress(AddressDto address, String email) {

        UserDto userDto = new ModelMapper().map(userRepository.findByEmail(email), UserDto.class);

        address.setUser(userDto);

        address.setAddressId(utils.generateStringId(30));

        AddressEntity addressEntity = new ModelMapper().map(address, AddressEntity.class);

        AddressDto savedAddress = new ModelMapper().map(addressRepository.save(addressEntity), AddressDto.class);

        return savedAddress;
    }

    @Override
    public AddressDto getAddress (String addressId) {
        AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
        ModelMapper modelMapper = new ModelMapper ();
        AddressDto addressDto = modelMapper.map(addressEntity, AddressDto.class);
        return addressDto;
    }
    @Override
    public void deleteAddress (String addressId) {
        AddressEntity address = addressRepository.findByAddressId(addressId);
        if(address == null) throw new RuntimeException("Address not found");
        addressRepository.delete(address);
    }
}
