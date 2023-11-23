package com.ous.bio.ws.repositories;

import com.ous.bio.ws.entities.AddressEntity;
import com.ous.bio.ws.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    public List<AddressEntity> findByUser(UserEntity user);
    public AddressEntity findByAddressId(String addressId);

}
