package com.gamejoy.domain.usermanagement.controllers;

import com.gamejoy.domain.common.dto.api.ApiResponseWrapper;
import com.gamejoy.domain.usermanagement.dto.UserDto;
import com.gamejoy.domain.usermanagement.entities.User;
import com.gamejoy.domain.usermanagement.mappers.UserMapper;
import jakarta.websocket.server.PathParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import com.gamejoy.domain.usermanagement.services.UserService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponseWrapper<UserDto>> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);

        ApiResponseWrapper<UserDto> response = new ApiResponseWrapper<>(
          userDto, String.format("User with id %d retrieved", id)
        );
        return ResponseEntity.ok(response);
    }

    /*@GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDto = userService.getAllUsers();



        return ResponseEntity.ok().body(userDto);
    }

    @
    public ResponseEntity<UserDto> deleteUser(Long id) {
        UserDto userDto = userService.getUserById(id);



        return ResponseEntity.ok().body(userDto);
    }

    @GetMapping
    public ResponseEntity<UserDto> updateUser(Long id) {
        UserDto userDto = userService.getUserById(id);



        return ResponseEntity.ok().body(userDto);
    }*/

    //todo: still todo
    @PostMapping("/users/changeUsername")
    public ResponseEntity<String> changeUsername(Long id, @Valid String username) {
        String usernameChangeResponse = userService.changeUsername(id, username);
        return ResponseEntity.ok().body(usernameChangeResponse);
    }

    //todo: still todo
    @PostMapping("/users/changePassword")
    public ResponseEntity<String> changePassword(Long id, @Valid char[] password) {
        String passwordChangeResponse = userService.changePassword(id, password);
        return ResponseEntity.ok().body(passwordChangeResponse);
    }
}
