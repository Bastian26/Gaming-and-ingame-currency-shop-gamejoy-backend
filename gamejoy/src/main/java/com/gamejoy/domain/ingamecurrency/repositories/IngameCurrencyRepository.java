package com.gamejoy.domain.ingamecurrency.repositories;

import com.gamejoy.domain.ingamecurrency.entities.IngameCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngameCurrencyRepository extends JpaRepository<IngameCurrency, Long> {
}
