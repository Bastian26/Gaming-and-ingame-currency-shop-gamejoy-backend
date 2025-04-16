package com.gamejoy.domain.usermanagement.services;

import com.gamejoy.domain.usermanagement.dto.UserDto;
import com.gamejoy.domain.usermanagement.exceptions.UserNotFoundException;
import com.gamejoy.domain.usermanagement.entities.User;
import com.gamejoy.domain.usermanagement.mappers.UserMapper;
import com.gamejoy.domain.usermanagement.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
            () -> new UserNotFoundException(String.format("User with %d not found", id))
          );
        return userMapper.toUserDto(user);
    }

   /* public UserDto getAllUsers() {
        Optional<User> user = userRepository.findById(id).orElseThrow(
          () -> new UserNotFoundException(String.format("User with %d not found", id))
        );
        UserDto userDto = userMapper.toUserDto(user.get());
        return userDto;
    }

    public UserDto deleteUserById(Long id) {
        userRepository.deleteById(id);
        return userDto;
    }

    public UserDto updateUserById(Long id) {
        userRepository.updateUser(id);
        return userDto;
    }*/

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
