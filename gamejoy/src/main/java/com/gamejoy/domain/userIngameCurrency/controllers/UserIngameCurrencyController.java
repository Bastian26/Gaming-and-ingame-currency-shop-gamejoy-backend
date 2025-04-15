package com.gamejoy.domain.userIngameCurrency.controllers;

import com.gamejoy.domain.ingamecurrency.dtos.IngameCurrencyDto;
import com.gamejoy.domain.userManagement.exceptions.UserNotFoundException;
import com.gamejoy.domain.userIngameCurrency.services.UserIngameCurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserIngameCurrencyController {

    private final UserIngameCurrencyService userIngameCurrencyService;

    @GetMapping("/ingameCurrencies/{userId}")
    public ResponseEntity<List<IngameCurrencyDto>> getIngameCurrenciesByUserId(@PathVariable("userId") Long userId)
            throws UserNotFoundException {

        List<IngameCurrencyDto> ingameCurrenciesDto = userIngameCurrencyService.getIngameCurrenciesByUserId(userId);
        if (ingameCurrenciesDto == null) {
            throw new UserNotFoundException(String.format("No Ingame Currency found for User with id %d", userId));
        }  else {
            return ResponseEntity.ok(ingameCurrenciesDto);
        }
    }

 /*   @PutMapping("/ingameCurrencies/{userId}")
    public ResponseEntity<List<IngameCurrency>> updateUserIngameCurrenciesByUserId
            (@PathVariable("userId") long id, @Valid @RequestBody List<IngameCurrencyDto> IngameCurrencyDto) {
        ClimbingRoute climbingRoute = userIngameCurrencyService.updateClimbingRoute(id, climbingRouteDto);
        if (climbingRoute == null) {
            throw new NotFoundException(String.format("No Route with id %d found", id));
        } else {
            return new ResponseEntity<>(climbingRoute, HttpStatus.OK);
        }
    }*/
}
