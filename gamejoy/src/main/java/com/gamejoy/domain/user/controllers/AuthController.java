package com.gamejoy.domain.user.controllers;

import com.gamejoy.config.security.UserAuthProvider;
import com.gamejoy.domain.general.dto.api.ApiResponseWrapper;
import com.gamejoy.domain.user.dtos.CredentialDto;
import com.gamejoy.domain.user.dtos.SignUpDto;
import com.gamejoy.domain.user.dtos.UserDto;
import com.gamejoy.domain.user.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.gamejoy.domain.user.controllers.constants.ApiPaths.AUTH_API_BASE_URL;

@RestController
@RequestMapping(AUTH_API_BASE_URL)
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<ApiResponseWrapper<UserDto>> login(@RequestBody CredentialDto credentialDto) {
        UserDto user = userService.login(credentialDto);
        user.setToken(userAuthProvider.createToken(user));

        ApiResponseWrapper<UserDto> response = new ApiResponseWrapper<UserDto>(
                true, user, String.format("User %s logged in", credentialDto.userName()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseWrapper<UserDto>> register(@Valid @RequestBody SignUpDto signUpDto) {
        UserDto user = userService.register(signUpDto);
        user.setToken(userAuthProvider.createToken(user));

        ApiResponseWrapper<UserDto> response = new ApiResponseWrapper<UserDto>(
                true, user, String.format("User %s registered", signUpDto.userName()));
        return ResponseEntity.created(URI.create("/api/v1/users/" + user.getId())).body(response);
    }
}
