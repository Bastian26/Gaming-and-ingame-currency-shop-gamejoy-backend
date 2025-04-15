package com.gamejoy.domain.userManagement.repositories;

import com.gamejoy.domain.userManagement.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
