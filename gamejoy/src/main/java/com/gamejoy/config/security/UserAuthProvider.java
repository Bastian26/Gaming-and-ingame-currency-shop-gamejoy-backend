package com.gamejoy.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gamejoy.config.security.model.UserPrincipal;
import com.gamejoy.domain.usermanagement.dto.UserDto;
import com.gamejoy.domain.usermanagement.entities.User;
import com.gamejoy.domain.usermanagement.entities.UserRole;
import com.gamejoy.domain.usermanagement.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

/**
 * Handles creation and validation of JWT tokens for user authentication.
 * - Generates signed tokens containing user information.
 * - Validates incoming tokens and extracts authentication data for Spring Security.
 * Used by the security configuration to identify and authorize users.
 */
@Component
@RequiredArgsConstructor
public class UserAuthProvider {


    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final UserRepository userRepository;

    @PostConstruct
    protected  void inti() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto userDto) {
        Date now = new Date();
        // JWT Token expires after 1 hour
        Date validity = new Date(now.getTime() + 36_000_00);

        return JWT.create()
                .withIssuer(userDto.getUserName())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("firstName", userDto.getFirstName())
                .withClaim("lastName", userDto.getLastName())
                .withClaim("role", UserRole.USER.name())
                .sign(Algorithm.HMAC256(secretKey));
    }

    /**
     * Decode token and extract username.
     * Then it loads the user form db. So its ensured that the user exists in the db (more secure)
     * @param token
     * @return Authentication: represents central security object of Spring Security
     */
    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        // Check if token is valid (expired,
        DecodedJWT decodedJWT = verifier.verify(token);

        String username = decodedJWT.getIssuer(); // alternative would be subject, but in this app its Issuer

        User user = userRepository.findByUserName(username)
          .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserPrincipal principal = new UserPrincipal(user);

        return new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
    }
}

/**
 * Alternative:
 * get information straight from JWT token.
 * Attention! That would be more insecure!
 *
 * User user = User.builder()
 *     .userName(decodedJWT.getIssuer())
 *     .firstName(decodedJWT.getClaim("firstName").asString())
 *     .lastName(decodedJWT.getClaim("lastName").asString())
 *     .userRole(UserRole.valueOf(decodedJWT.getClaim("role").asString()))
 *     .build();
 */
