package com.gamejoy.domain.usermanagement.repositories;

import com.gamejoy.domain.usermanagement.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
