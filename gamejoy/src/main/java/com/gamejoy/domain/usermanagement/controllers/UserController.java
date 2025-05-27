package com.gamejoy.domain.usermanagement.controllers;

import com.gamejoy.config.security.model.UserPrincipal;
import com.gamejoy.domain.common.dto.api.ApiResponseWrapper;
import com.gamejoy.domain.usermanagement.dto.UserDto;
import com.gamejoy.domain.usermanagement.entities.PasswordChangeRequest;
import com.gamejoy.domain.usermanagement.mappers.UserMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import com.gamejoy.domain.usermanagement.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<UserDto>> getUserById(@PathVariable Long id) {
        UserDto userDto = userService.getUserById(id);

        ApiResponseWrapper<UserDto> response = new ApiResponseWrapper<>(
          userDto, String.format("User with id %d retrieved", id)
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<ApiResponseWrapper<List<UserDto>>> getAllUsers() {
        List<UserDto> userDtoList = userService.getAllUsers();

        ApiResponseWrapper<List<UserDto>> response = new ApiResponseWrapper<>(
          userDtoList, "Retrieved all Users"
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();  // Return HTTP 204 No Content to indicate successful deletion
    }
/*
    @GetMapping
    public ResponseEntity<UserDto> updateUser(Long id) {
        UserDto userDto = userService.getUserById(id);



        return ResponseEntity.ok().body(userDto);
    }*/

    /*//todo: still todo - move to admin
    @PostMapping("/changeUsername")
    public ResponseEntity<String> changeUsername(Long id, @Valid String username) {
        String usernameChangeResponse = userService.changeUsername(id, username);
        return ResponseEntity.ok().body(usernameChangeResponse);
    }**/

    // User should only change pw for himself
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid PasswordChangeRequest request,
      @AuthenticationPrincipal UserPrincipal userDetails) {

        String response = userService.changePassword(
          userDetails.user().getId(), // username of authenticated user (fetched from SecurityContext by @AuthenticationPrincipal)
          request
        );

        return ResponseEntity.ok(response);
    }
}
