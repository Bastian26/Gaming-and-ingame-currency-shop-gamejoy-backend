package com.gamejoy.domain.authentication.controller;

import com.gamejoy.domain.userIngameCurrency.services.UserIngameCurrencyService;
import com.gamejoy.domain.authentication.dto.CredentialDto;
import com.gamejoy.domain.authentication.dto.SignUpDto;
import com.gamejoy.domain.usermanagement.dto.UserDto;
import com.gamejoy.domain.usermanagement.entities.Address;
import com.gamejoy.domain.usermanagement.entities.User;
import com.gamejoy.domain.usermanagement.entities.UserRole;
import com.gamejoy.domain.usermanagement.exceptions.InvalidPasswordException;
import com.gamejoy.domain.usermanagement.exceptions.UserAlreadyExistsException;
import com.gamejoy.domain.usermanagement.exceptions.UserNotFoundException;
import com.gamejoy.domain.usermanagement.mappers.UserMapper;
import com.gamejoy.domain.usermanagement.repositories.AddressRepository;
import com.gamejoy.domain.usermanagement.repositories.UserRepository;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class AuthService {
    private final UserRepository userRepository;
    private final UserIngameCurrencyService userIngameCurrencyService;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto login(CredentialDto credentialDto) {
        User user = userRepository.findByUserName(credentialDto.userName())
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User %s not found", credentialDto.userName())));

        // Check if password matches
        if (passwordEncoder.matches(CharBuffer.wrap(credentialDto.password()),
                user.getPassword())) {
            log.info("User {} logged in", user.getUserName());
            return userMapper.toUserDto(user);
        }
        throw new InvalidPasswordException("Invalid password for user " + credentialDto.userName());
    }

    public UserDto register(SignUpDto signUpDto) {
        Optional<User> oUser = userRepository.findByUserName(signUpDto.userName());
        char[] password = signUpDto.password();

        if (oUser.isPresent()) {
            throw new UserAlreadyExistsException(String.format("User %s already exists", signUpDto.userName()));
        }

        // Alternative manual validation approach without annotation (@Valid, @ValidPassword etc.).
        /*if (!passwordValidator.isValid(new String(signUpDto.password()), null)) {
            throw new InvalidPasswordException(
                    String.format("Invalid password for user %s", signUpDto.userName()));
        }*/

        User user = userMapper.signUpDtoToUser(signUpDto);
        // encode password with passwordEncoder
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(password)));
        Arrays.fill(password, '\0');  // set all chars as null - important for security - a string would remain in memory

        Address savedAddress = addressRepository.save(user.getAddress());
        user.setAddress(savedAddress);
        user.setUserRole(UserRole.USER);
        User savedUser = userRepository.save(user);
        log.info("User {} successfully registered", user.getUserName());

        /**
         * Create UserIngameCurrency initial value data - so every new user have at least a amount of 0
         * for every ingame currency
         */
        userIngameCurrencyService.createInitialDataForUserIngameCurrencies(user.getId());

        return userMapper.toUserDto(savedUser);
    }
}
