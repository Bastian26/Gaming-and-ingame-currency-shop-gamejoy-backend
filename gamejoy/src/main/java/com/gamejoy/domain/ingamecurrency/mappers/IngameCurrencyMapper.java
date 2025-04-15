package com.gamejoy.domain.ingamecurrency.mappers;

import com.gamejoy.domain.ingamecurrency.dtos.IngameCurrencyDto;
import com.gamejoy.domain.ingamecurrency.entities.IngameCurrency;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IngameCurrencyMapper {
    IngameCurrencyMapper INSTANCE = Mappers.getMapper(IngameCurrencyMapper.class);

    IngameCurrencyDto toIngameCurrencyDto(IngameCurrency ingameCurrency);

    IngameCurrency toIngameCurrency(IngameCurrencyDto ingameCurrencyDto);
}
