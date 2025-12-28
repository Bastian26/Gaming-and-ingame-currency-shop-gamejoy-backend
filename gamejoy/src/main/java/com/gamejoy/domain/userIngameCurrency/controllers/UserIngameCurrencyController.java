package com.gamejoy.domain.userIngameCurrency.controllers;

import com.gamejoy.domain.common.dto.api.ApiError;
import com.gamejoy.domain.ingamecurrency.dtos.IngameCurrencyDto;
import com.gamejoy.domain.usermanagement.exceptions.UserNotFoundException;
import com.gamejoy.domain.userIngameCurrency.services.UserIngameCurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User Ingame Currency", description = "User balances for available ingame currencies")
@Slf4j
public class UserIngameCurrencyController {

    private final UserIngameCurrencyService userIngameCurrencyService;

    @GetMapping("/ingameCurrencies/{userId}")
    @Operation(summary = "Get ingame currencies for user", description = "Returns all ingame currency balances for the given user id.")
    @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Balances retrieved",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = IngameCurrencyDto.class)))),
      @ApiResponse(responseCode = "404", description = "User or balances not found", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    public ResponseEntity<List<IngameCurrencyDto>> getIngameCurrenciesByUserId(@PathVariable("userId") Long userId)
            throws UserNotFoundException {

        log.info("Request: get ingame currencies for user id={}", userId);
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
