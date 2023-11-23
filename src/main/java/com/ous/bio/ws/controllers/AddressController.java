package com.ous.bio.ws.controllers;

import com.ous.bio.ws.requests.AddressRequest;
import com.ous.bio.ws.responses.AddressResponse;
import com.ous.bio.ws.services.Impl.AddressServiceImpl;
import com.ous.bio.ws.shared.dto.AddressDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.internal.bytebuddy.description.method.MethodDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {

    @Autowired
    private AddressServiceImpl addressService;

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getAddresses(Principal principal){

        List<AddressDto> addressesDto = addressService.getAllAddresses(principal.getName());
        Type listType = new TypeToken<List<AddressResponse>>() {}.getType();

        List<AddressResponse> addresses = new ModelMapper().map(addressesDto, listType);

        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity storeAddresse(@RequestBody AddressRequest addressRequest, Principal principal){

        ModelMapper modelMapper=new ModelMapper();

        AddressDto addressDto=modelMapper.map(addressRequest,AddressDto.class);
        AddressDto createAddress=addressService.createAddress(addressDto, principal.getName());

        AddressResponse newAddress=modelMapper.map(createAddress,AddressResponse.class);

        return new ResponseEntity(newAddress,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getoneAddresse(@PathVariable(name="id") String addressId) {
        AddressDto addressDto = addressService.getAddress(addressId);
        ModelMapper modelMapper = new ModelMapper();
        AddressResponse addressResponse = modelMapper.map(addressDto, AddressResponse.class);
        return new ResponseEntity<AddressResponse>(addressResponse, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updatreAddresse(@PathVariable(name="id") String addressId) {
        return new ResponseEntity<>("update addresses", HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddresse(@PathVariable(name="id") String addressId) {
        addressService.deleteAddress(addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}