package com.gamejoy.domain.ingameCurrency.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngameCurrencyDto {
    private Long id;
    private String currencyName;
    private String lastName;
    private String gameName;
}
