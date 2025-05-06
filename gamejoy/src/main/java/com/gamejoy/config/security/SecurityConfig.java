package com.gamejoy.config.security;

import com.gamejoy.config.security.filters.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Application security configuration.
 * Sets up global access restrictions for endpoints, disables CSRF protection,
 * adds JWT authentication filter, and enforces stateless session management.
 * Endpoint-level restrictions can be defined additionally using @PreAuthorize annotations in controllers.
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserAuthProvider userAuthProvider;

    // Access restrictions are defined both globally (here) and locally using annotations (e.g., @PreAuthorize("hasRole('ADMIN')") in AdminController)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthFilter(userAuthProvider), BasicAuthenticationFilter.class)
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers("/auth/**").hasRole("ADMIN"); // Only admins can access /admin/** - could be rmeoved because its already defined in AdminController
                    requests.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN"); // Users and admins can access /user/** - could be rmeoved because its already defined in AdminController
                    requests.anyRequest().permitAll(); // Allow all other requests
                });

        return httpSecurity.build();
    }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> null; // returns always null, no default user will be created - so no logs and sec issues.
  }
}