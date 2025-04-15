package com.gamejoy.domain.userIngameCurrency.services;

import com.gamejoy.domain.ingamecurrency.dtos.IngameCurrencyDto;
import com.gamejoy.domain.ingamecurrency.entities.IngameCurrency;
import com.gamejoy.domain.usermanagement.entities.User;
import com.gamejoy.domain.userIngameCurrency.entities.UserIngameCurrency;
import com.gamejoy.domain.ingamecurrency.mappers.IngameCurrencyMapper;
import com.gamejoy.domain.ingamecurrency.repositories.IngameCurrencyRepository;
import com.gamejoy.domain.userIngameCurrency.repositories.UserIngameCurrencyRepository;
import com.gamejoy.domain.usermanagement.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserIngameCurrencyService {

    private final UserIngameCurrencyRepository userIngameCurrencyRepository;
    private final IngameCurrencyRepository ingameCurrencyRepository;
    private final UserRepository userRepository;
    private final IngameCurrencyMapper ingameCurrencyMapper;

    public List<IngameCurrencyDto> getIngameCurrenciesByUserId(Long userId) {

        Optional<List<UserIngameCurrency>> userIngameCurrenciesO = userIngameCurrencyRepository.findAllByUser_Id(userId);
        if (userIngameCurrenciesO.isPresent()) {
            List<IngameCurrency> ingameCurrencies = userIngameCurrenciesO.get().stream()
                    .map(UserIngameCurrency::getIngameCurrency)
                    .collect(Collectors.toList());

            List<IngameCurrencyDto> ingameCurrencyDtos = ingameCurrencies.stream()
                    .map(ingameCurrencyMapper::toIngameCurrencyDto)
                    .sorted(Comparator.comparing(IngameCurrencyDto::getCurrencyName))
                    .collect(Collectors.toList());

            return ingameCurrencyDtos;
        }
        return null;
    }

    /*public ClimbingRoute updateUserIngameCurrenciesByUserId(long id, ClimbingRouteDto climbingRouteDto) {
        Optional<ClimbingRoute> climbingRouteData = climbingRouteRepository.findById(id);
        ClimbingRoute climbingRoute = null;
        if (climbingRouteData.isPresent()) {
            climbingRoute = mapper.toModel(climbingRouteDto);
            climbingRouteRepository.save(climbingRoute);
        }
        return climbingRoute;
    }*/

    public List<UserIngameCurrency> createInitialDataForUserIngameCurrencies(long userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            List<UserIngameCurrency> userIngameCurrencies = userIngameCurrencyRepository.findAllByUser_Id(userId).get();
            List<IngameCurrency> ingameCurrencies = ingameCurrencyRepository.findAll();

            // Checks that there are no In Game Currencies for the user
            if (userIngameCurrencies.isEmpty()) {

                List<UserIngameCurrency> userIngameCurrenciesList = new ArrayList<>();

                for (int i = 0; i < ingameCurrencies.size(); i++) {
                    // Create a UserIngameCurrency object and set the user and ingame currency
                    UserIngameCurrency userIngameCurrency = new UserIngameCurrency().builder()
                            .user(user)
                            .ingameCurrency(ingameCurrencies.get(i))
                            .amount(0)
                            .build();
                    userIngameCurrenciesList.add(userIngameCurrency);
                }
                userIngameCurrencyRepository.saveAll(userIngameCurrenciesList);
                return userIngameCurrenciesList;
            }
        }

        // Return null if the user does not exist or already has ingame currencies
        return null;
    }
}
