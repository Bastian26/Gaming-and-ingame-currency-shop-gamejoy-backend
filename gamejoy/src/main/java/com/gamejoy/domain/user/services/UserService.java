package com.gamejoy.domain.user.services;

import com.gamejoy.domain.user.exceptions.InvalidPasswordException;
import com.gamejoy.domain.user.exceptions.UserAlreadyExistsException;
import com.gamejoy.domain.user.exceptions.UserNotFoundException;
import com.gamejoy.domain.user.repositories.AddressRepository;
import com.gamejoy.domain.user.dtos.CredentialDto;
import com.gamejoy.domain.user.dtos.SignUpDto;
import com.gamejoy.domain.user.dtos.UserDto;
import com.gamejoy.domain.user.entities.Address;
import com.gamejoy.domain.user.entities.User;
import com.gamejoy.domain.user.entities.UserRole;
import com.gamejoy.domain.user.mappers.UserMapper;
import com.gamejoy.domain.user.repositories.UserRepository;
import com.gamejoy.domain.userIngameCurrency.services.UserIngameCurrencyService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.gamejoy.config.validation.PasswordValidator;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {
    private final UserRepository userRepository;
    private final UserIngameCurrencyService userIngameCurrencyService;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final PasswordValidator passwordValidator;

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

        if (oUser.isPresent()) {
            throw new UserAlreadyExistsException(String.format("User %s already exists", signUpDto.userName()));
        }

        // Alternative manual validation approach without annotation (@Valid, @ValidPassword etc.).
        /*if (!passwordValidator.isValid(new String(signUpDto.password()), null)) {
            throw new InvalidPasswordException(
                    String.format("Invalid password for user %s", signUpDto.userName()));
        }*/

        User user = userMapper.signUpToUser(signUpDto);
        // encode password with passwordEncoder
        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(signUpDto.password())));
        Address savedAddress = addressRepository.save(user.getAddress());
        user.setAddress(savedAddress);
        user.setUserRole(UserRole.USER);
        User savedUser = userRepository.save(user);
        log.info("User {} registered", user.getUserName());

        /**
         * Create UserIngameCurrency initial value data - so every new user have at least a amount of 0
         * for every ingame currency
         */
        userIngameCurrencyService.createInitialDataForUserIngameCurrencies(user.getId());

        return userMapper.toUserDto(savedUser);
    }

    public String changeUsername(long id, String username) {
        Optional<User> userO = userRepository.findById(id);

        if (userO.isPresent()) {
            User user = userRepository.save(userO.get());

            String responseText = String.format("Username for User %s with id %d changed",user.getUserName(), id);
            log.info("Username for User {} with id {} changed", user.getUserName(), id);
            return responseText;
        } else {
            throw new UserNotFoundException(String.format("User with %d not found", id));
        }
    }

    public String changePassword(long id, char[] password) {
        Optional<User> userO = userRepository.findById(id);
        User user = null;

//        if (!passwordValidator.isValid(new String(password), null)) {
//            throw new AppException("Invalid password", HttpStatus.BAD_REQUEST);
//        }
        if (userO.isPresent()) {
            user = userO.get();
            user.setPassword(passwordEncoder.encode(CharBuffer.wrap(password)));
            userRepository.save(user);

            String responseText = String.format("Password for User %s with id %d changed",user.getUserName(), id);
            log.info(responseText);
            return responseText;
        } else {
            throw new UserNotFoundException(String.format("User with %d not found", id));
        }
    }
}
