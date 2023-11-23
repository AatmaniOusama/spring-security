package com.ous.bio.ws.services;

import com.ous.bio.ws.shared.dto.AddressDto;

import java.util.List;

public interface AddressService {

    public List<AddressDto> getAllAddresses(String email);
    public AddressDto createAddress(AddressDto address, String email);
    public AddressDto getAddress(String addressId);
    public void deleteAddress(String addressId);

}
