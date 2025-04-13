package com.gamejoy.domain.userIngameCurrency.repositories;

import com.gamejoy.domain.userIngameCurrency.entities.UserIngameCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserIngameCurrencyRepository extends JpaRepository<UserIngameCurrency, Long> {

    Optional<List<UserIngameCurrency>> findAllByUser_Id(Long userId);
}
