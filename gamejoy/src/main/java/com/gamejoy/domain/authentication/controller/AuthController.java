package com.gamejoy.domain.authentication.controller;

import com.gamejoy.config.security.UserAuthProvider;
import com.gamejoy.domain.common.dto.api.ApiError;
import com.gamejoy.domain.common.dto.api.ApiResponseWrapper;
import com.gamejoy.domain.authentication.dto.CredentialDto;
import com.gamejoy.domain.authentication.dto.SignUpDto;
import com.gamejoy.domain.usermanagement.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

import static com.gamejoy.domain.usermanagement.constants.UserApiPaths.AUTH_API_BASE_URL;

@RestController
@RequestMapping(AUTH_API_BASE_URL)
@RequiredArgsConstructor
@Tag(name = "Authentication Management", description = "APIs for authentication")
public class AuthController {

    private final AuthService authService;
    private final UserAuthProvider userAuthProvider;

    @Operation(summary = "Creates/Registers a new user", description = "Adds a new user to the system - returns user info and token")
    @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User successfully registered",
        content = @Content(schema = @Schema(implementation = UserDto.class))),
      @ApiResponse(responseCode = "409", description = "User already exists",
        content = @Content(schema = @Schema(implementation = ApiError.class))),
      @ApiResponse(responseCode = "400", description = "Invalid input, user data doesn't meet validation criteria",
        content = @Content(schema = @Schema(implementation = ApiError.class))),
    })
    @PostMapping("/register")
    public ResponseEntity<ApiResponseWrapper<UserDto>> register(@Valid @RequestBody SignUpDto signUpDto) {
        UserDto user = authService.register(signUpDto);
        user.setToken(userAuthProvider.createToken(user));

        ApiResponseWrapper<UserDto> response = new ApiResponseWrapper<>(
          user, String.format("User %s registered", signUpDto.userName()));
        return ResponseEntity.created(URI.create("/api/v1/auth/" + user.getId())).body(response);
    }

    @Operation(summary = "Create/Register a new user", description = "Registers a new user in the system and returns the user's details along with a JWT token for authentication.")
    @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User logged in",
        content = @Content(schema = @Schema(implementation = UserDto.class))),
      @ApiResponse(responseCode = "404", description = "User %s not found",
        content = @Content(schema = @Schema())),
      @ApiResponse(responseCode = "400", description = "Password is not correct",
        content = @Content(schema = @Schema()))
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponseWrapper<UserDto>> login(@RequestBody CredentialDto credentialDto) {
        UserDto user = authService.login(credentialDto);
        user.setToken(userAuthProvider.createToken(user));

        ApiResponseWrapper<UserDto> response = new ApiResponseWrapper<>(
          user, String.format("User %s logged in", credentialDto.userName()));
        return ResponseEntity.ok(response);
    }

    // todo: needs to be completed - https://medium.com/@klcberat13/jwt-authentication-and-secure-logout-in-spring-boot-e9dcff2cc677
    @Operation(summary = "Logout", description = "Log out of the system")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseWrapper<Object>> logout(HttpServletRequest request,
      HttpServletResponse response) {
        return null;
//        return logoutService.logout(request, response);
    }

    /*@PostMapping("/users/{id}/changeUsername")
    public ResponseEntity<String> changeUsername(@PathVariable Long id, @Valid @RequestBody String username) {
        String usernameChangeResponse = userService.changeUsername(id, username);
        return ResponseEntity.ok().body(usernameChangeResponse);
    }

    @PostMapping("/users/{id}/changePassword")
    public ResponseEntity<String> changePassword(@PathVariable Long id, @Valid @RequestBody char[] password) {
        String passwordChangeResponse = userService.changePassword(id, password);
        return ResponseEntity.ok().body(passwordChangeResponse);
    }*/

}
