package com.gamejoy.domain.ingameCurrency.mappers;

import com.gamejoy.domain.ingameCurrency.dtos.IngameCurrencyDto;
import com.gamejoy.domain.ingameCurrency.entities.IngameCurrency;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IngameCurrencyMapper {
    IngameCurrencyMapper INSTANCE = Mappers.getMapper(IngameCurrencyMapper.class);

    IngameCurrencyDto toIngameCurrencyDto(IngameCurrency ingameCurrency);

    IngameCurrency toIngameCurrency(IngameCurrencyDto IngameCurrencyDto);
}
