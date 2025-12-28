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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Tag(name = "Authentication Management", description = "APIs for authentication")
public class AuthController {

    private final AuthService authService;
    private final UserAuthProvider userAuthProvider;

    @Operation(
      summary = "Creates/Registers a new user",
      description = "Adds a new user to the system - returns user info and token",
      security = {}
    )
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
        log.info("Request: register user {}", signUpDto.userName());
        UserDto user = authService.register(signUpDto);
        user.setToken(userAuthProvider.createToken(user));

        ApiResponseWrapper<UserDto> response = new ApiResponseWrapper<>(
          user, String.format("User %s registered", signUpDto.userName()));
        return ResponseEntity.created(URI.create("/api/v1/auth/" + user.getId())).body(response);
    }

    @Operation(
      summary = "Login user",
      description = "Authenticates an existing user and returns user details with JWT token.",
      security = {}
    )
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
        log.info("Request: login user {}", credentialDto.userName());
        UserDto user = authService.login(credentialDto);
        user.setToken(userAuthProvider.createToken(user));

        ApiResponseWrapper<UserDto> response = new ApiResponseWrapper<>(
          user, String.format("User %s logged in", credentialDto.userName()));
        return ResponseEntity.ok(response);
    }

    //reset password

    /*public String refreshToken(String token) {
        final Date createdDate = new Date();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
    }*/
}


/*Authentifizierung & Registrierung:

Login (/auth/login)

Registrierung (/auth/register)

Token-Refresh (wenn JWT verwendet wird)


Verifizierungen:

E-Mail-Bestätigung

Passwort-Vergessen/Reset

Sonstige User-spezifische Endpunkte:

Eigene Aktivitäten / Daten abrufen (z. B. /me)*/
