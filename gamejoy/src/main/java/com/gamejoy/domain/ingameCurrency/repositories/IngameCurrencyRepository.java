package com.gamejoy.domain.ingameCurrency.repositories;

import com.gamejoy.domain.ingameCurrency.entities.IngameCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngameCurrencyRepository extends JpaRepository<IngameCurrency, Long> {
}
